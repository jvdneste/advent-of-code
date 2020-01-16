(ns aoc-2019.day-02-test
  (:require [clojure.test :refer :all])
  (:require [aoc-2019.day-02 :refer :all]))

(deftest program-eval-at-position-tests
  (testing "program-eval-at-position basic tests"
    (is (= [2 0 0 0 99] (program-eval [1 0 0 0 99])))
    (is (= [2 3 0 6 99] (program-eval [2 3 0 3 99])))
    (is (= [2 4 4 5 99 9801] (program-eval [2 4 4 5 99 0])))
    (is (= [30 1 1 4 2 5 6 0 99] (program-eval [1 1 1 4 99 5 6 0 99])))))

;(deftest total-fuel-is-correct
;  (testing "eval some basic programs"
;    (is (= [2 0 0 0 99] (program-eval [1 0 0 0 99])))))
