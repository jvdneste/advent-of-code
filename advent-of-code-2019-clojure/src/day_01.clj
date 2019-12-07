(ns day-01
  (:require [rocket-module :refer :all]))

(def module-fuels (map #(- (quot % 3) 2) module-masses))

(def total-fuel (reduce + module-fuels))

(print total-fuel)
