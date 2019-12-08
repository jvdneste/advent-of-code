(ns advent-of-code-2019.day-01-part-2
  (:require [advent-of-code-2019.day-01-part-1 :refer [needed-fuel module-masses]]))

(defn needed-fuel-recur [mass]
  (if (<= mass 0)
    0
    (+ mass (needed-fuel-recur (needed-fuel mass)))))

(defn needed-fuels [masses] (map #(needed-fuel-recur (needed-fuel %)) masses))

(defn total-needed-fuel [] (reduce + (needed-fuels module-masses)))

;(println "total fuel" (total-needed-fuel))
