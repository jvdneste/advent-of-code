(ns advent-of-code-2019.day-02
  (:require [clojure.string :as str]))

(def program-text "1,0,0,3,1,1,2,3,1,3,4,3,1,5,0,3,2,1,6,19,1,19,6,23,2,23,6,27,2,6,27,31,2,13,31,35,1,9,35,39,2,10,39,43,1,6,43,47,1,13,47,51,2,6,51,55,2,55,6,59,1,59,5,63,2,9,63,67,1,5,67,71,2,10,71,75,1,6,75,79,1,79,5,83,2,83,10,87,1,9,87,91,1,5,91,95,1,95,6,99,2,10,99,103,1,5,103,107,1,107,6,111,1,5,111,115,2,115,6,119,1,119,6,123,1,123,10,127,1,127,13,131,1,131,2,135,1,135,5,0,99,2,14,0,0")

(defn with-noun-and-verb [program noun verb]
  (-> program (assoc 1 noun) (assoc 2 verb)))

(defn program-values-from-text [text]
  (let [split (str/split text #",")]
    (-> (map #(Integer. %) split) (vec) (with-noun-and-verb 12 2))))

(defn program-terminates [program position]
  (= (nth program position) 99))

(defn program-eval-at-position [program position]
  (let [opcode (nth program position) ptr1 (nth program (+ position 1)) ptr2 (nth program (+ position 2)) result-position (nth program (+ position 3))]
    (let [arg1 (nth program ptr1) arg2 (nth program ptr2)]
      (case opcode
        1 (assoc program result-position (+ arg1 arg2))
        2 (assoc program result-position (* arg1 arg2))
        program))))

(defn program-eval-from-position-to-end [program position]
  (if (>= position (count program))
    program
    (if (program-terminates program position)
      program
      (program-eval-from-position-to-end (program-eval-at-position program position) (+ position 4)))))

(defn program-eval "evaluate a complete program" [program] (program-eval-from-position-to-end program 0))

(defn brute-force-all-nouns-and-verbs [program]
  (for [noun (range 0 99) verb (range 0 99)]
    (let [eval (program-eval (with-noun-and-verb program noun verb))]
      [noun verb eval])))

(defn brute-force-find-noun-and-verb [program result]
  (->>
    program
    (brute-force-all-nouns-and-verbs)
    (filter #(let [[noun verb prog] %] (= result (first prog))))
    (first)))

;(println "result" (brute-force-find-noun-and-verb (program-values-from-text program-text) 19690720))
