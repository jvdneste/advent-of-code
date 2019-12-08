(ns advent-of-code-2019.day-01-part-1-test
  (:require [clojure.test :refer :all])
  (:require [advent-of-code-2019.day-01-part-1 :refer :all]))

(deftest total-fuel-is-correct
  (testing "is total fuel correct"
    (is (= 3266516 (calc-total-fuel)))))
