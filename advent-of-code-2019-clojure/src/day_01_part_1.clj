(ns day-01-part-1
  (:require [rocket-module :refer :all]))

(def module-fuels (map needed-fuel module-masses))

(def total-fuel (reduce + module-fuels))

(println "total mass" (reduce + module-masses))
(println "total fuel" total-fuel)
