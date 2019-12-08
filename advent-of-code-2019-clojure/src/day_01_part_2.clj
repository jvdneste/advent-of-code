(ns day-01-part-2
  (:require [rocket-module :refer :all]))

(defn calculate-total-fuel [mass]
  (if (<= mass 0)
    0
    (+ mass (calculate-total-fuel (needed-fuel mass)))))

(def module-fuels (map #(calculate-total-fuel (needed-fuel %)) module-masses))

(def total-fuel (reduce + module-fuels))

(println "total fuel" total-fuel)
