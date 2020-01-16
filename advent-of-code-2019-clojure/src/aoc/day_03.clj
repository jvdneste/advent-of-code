(ns aoc.day-03
  (:require [clojure.string :as str]))

(def center [0 0])

(defn manhattan-distance [point]
  (reduce + (map #(Math/abs %) point)))

(defrecord Step [axis value]
  Object (toString [_] (format "Step{:axis %d :value %5d}" axis value)))

(defn step [axis value]
  {:pre [(not (zero? value))]}
  (->Step axis value))

(defn step-parse [text]
  (let [direction-text (subs text 0 1) distance-text (subs text 1)]
    (let [distance (Integer. distance-text)]
      (case direction-text
        "R" (step 0 distance)
        "L" (step 0 (- distance))
        "U" (step 1 distance)
        "D" (step 1 (- distance))))))

(defn step-list-parse [text]
  (->>
    (str/split text #",")
    (map step-parse)))

(defn step-apply-to [step point]
  (let [{:keys [axis value]} step]
    (assoc point axis (+ (nth point axis) value))))

(defn step-advance [current-step point]
  (let [{:keys [axis value]} current-step]
    (let [sign (Integer/signum value)]
      (let [new-point (assoc point axis (+ (nth point axis) sign))]
        (let [new-value (- value sign)]
          (if (zero? new-value)
            [nil new-point]
            [(step axis new-value) new-point]))))))

(defrecord Segment [point step sort-key]
  Object (toString [_] (let [[x y] point] (format "Segment{:point [%d %d] :step %s}" x y (str step)))))

(defn segment [point step]
  (let [distance (manhattan-distance point) length (:value step)]
    (let [sort-key [distance length]]
      (->Segment point step sort-key))))

(defn segment-next [current-segment next-step]
  (let [{:keys [point step]} current-segment]
    (segment (step-apply-to step point) next-step)))

(defn segment-split-do [point axis translation-value-1 translation-value-2]
  [(segment (assoc point axis 0) (->Step axis translation-value-1))
   (segment (assoc point axis 0) (->Step axis translation-value-2))])

(defn segment-with [point axis value translation-value]
  [ (segment (assoc point axis value) (->Step axis translation-value)) ])

(defn segment-split [segment]
  "rewrite a segment to point away from the center, breaking it up in 2 if it crosses the x or y axis"
  (let [{p :point {step-value :value axis :axis} :step} segment]
    (let [p-value (nth p axis)]
      (let [translated (+ p-value step-value)]
        (cond
          (zero? p-value) [ segment ]
          (neg? p-value)
            (if (or (zero? step-value) (neg? step-value))
              [segment]
              (if (> translated 0)
                (segment-split-do p axis p-value translated)
                (segment-with p axis translated (- step-value)))) ; reverse it
          :else (if (or (zero? step-value) (pos? step-value))
                  [ segment ]
                  (if (< translated 0)
                    (segment-split-do p axis p-value translated)
                    (segment-with p axis translated (- step-value)))))))))

(defn segment-compare [segment-1 segment-2]
  (compare (:sort-key segment-1) (:sort-key segment-2)))

(defn segment-advance [current-segment]
  "return the first point and what's left of the wire"
  (let [{:keys [point step]} current-segment]
    (let [[new-step new-point] (step-advance step point)]
      (if (nil? new-step)
        [nil point]
        [(segment new-point new-step) point]))))

(defrecord Wire [id segments sort-key]
  Object (toString [_] (format "Wire{:id %d :segments %s}" id segments)))

(defn wire [id segments]
  (let [segment-sort-key (-> segments first :sort-key)]
    (let [sort-key [segment-sort-key id]]
      (->Wire id segments sort-key))))

(defn wire-from-steps [id steps]
  (let [init (segment center (first steps))]
    (let [segments (reductions segment-next init (rest steps))]
      (let [normalized (mapcat segment-split segments)]
        (let [sorted-segments (into (sorted-set-by segment-compare) normalized)]
          (wire id sorted-segments))))))

(defn wire-comparator [wire-1 wire-2]
  (compare (:sort-key wire-1) (:sort-key wire-2)))

(defn wire-advance [current-wire]
  (let [{:keys [id segments]} current-wire]
    (let [first-segment (first segments)]
      (let [[segment point] (segment-advance first-segment)]
        (let [remaining (disj segments first-segment)]
          (if (nil? segment)
            (if (empty? remaining)
              [nil point]
              [(wire id remaining) point])
            [(wire id (conj remaining segment)) point]))))))

(defn queue [wires]
  (into (sorted-set-by wire-comparator) wires))

(defn queue-advance [queue]
  (let [first-wire (first queue)]
    (let [remainder (disj queue first-wire)]
      (let [[wire point] (wire-advance first-wire)]
        (if (nil? wire)
          [remainder point (:id first-wire)]
          [(conj remainder wire) point (:id first-wire)])))))

(defn find-closest-collision [wires]
  (loop [allocated {} q (queue wires)]
    (let [[new-queue point id] (queue-advance q)]
      (let [existing (get allocated point)]
        (if (and (not (= point [0 0])) (not (or (nil? existing) (= existing id))))
          [point id existing]
          (recur (assoc allocated point id) new-queue))))))