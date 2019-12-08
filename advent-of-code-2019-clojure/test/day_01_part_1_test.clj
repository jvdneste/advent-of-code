(ns day-01-part-1-test
  (:require [clojure.test :refer :all])
  (:require [day-01-part-1 :refer :all]))

(deftest total-fuel-is-correct
  (is (= total-fuel 3266516)))
