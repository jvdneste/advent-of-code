(ns aoc-2019.day-01-test
  (:require [clojure.test :refer :all])
  (:require [aoc-2019.day-01 :refer :all]))

(deftest total-fuel-is-correct
  (testing "is total fuel correct"
    (is (= 3266516 (calc-total-fuel)))))

(deftest total-needed-fuel-is-correct
  (testing "is total needed fuel correct"
    (is (= 4896902 (total-needed-fuel)))))
