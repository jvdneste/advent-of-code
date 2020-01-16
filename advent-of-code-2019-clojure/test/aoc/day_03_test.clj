(ns aoc-2019.day-03-test
  (:require [clojure.test :refer :all])
  (:require [aoc-2019.day-03 :refer :all])
  (:import (aoc_2019.day_03 Translation Segment)))

(deftest split-segment-no-op-test
  (testing "split segments no-op"
    (is (= [(Segment. [1 -1] (Translation. 0 5))]
           (split-segment (Segment. [1 -1] (Translation. 0 5)))))
    (is (= [(Segment. [-1 1] (Translation. 1 5))]
           (split-segment (Segment. [-1 1] (Translation. 1 5)))))
    (is (= [(Segment. [1 -1] (Translation. 1 -5))]
           (split-segment (Segment. [1 -1] (Translation. 1 -5)))))
    (is (= [(Segment. [-1 1] (Translation. 0 -5))]
           (split-segment (Segment. [-1 1] (Translation. 0 -5)))))
    (is (= [(Segment. [0 0] (Translation. 0 -5))]
           (split-segment (Segment. [0 0] (Translation. 0 -5)))))
    (is (= [(Segment. [0 0] (Translation. 0 5))]
           (split-segment (Segment. [0 0] (Translation. 0 5)))))
    (is (= [(Segment. [0 0] (Translation. 1 -5))]
           (split-segment (Segment. [0 0] (Translation. 1 -5)))))
    (is (= [(Segment. [0 0] (Translation. 1 5))]
           (split-segment (Segment. [0 0] (Translation. 1 5)))))))

(deftest split-segment-reverse-test
  (testing "split segments no-op"
    (is (= [(Segment. [-2 1] (Translation. 0 -3))]
           (split-segment (Segment. [-5 1] (Translation. 0 3)))))
    (is (= [(Segment. [1 -2] (Translation. 1 -3))]
           (split-segment (Segment. [1 -5] (Translation. 1 3)))))
    (is (= [(Segment. [2 1] (Translation. 0 3))]
           (split-segment (Segment. [5 1] (Translation. 0 -3)))))
    (is (= [(Segment. [1 2] (Translation. 1 3))]
           (split-segment (Segment. [1 5] (Translation. 1 -3)))))))

(deftest split-segment-do-test
  (testing "split segments no-op"
    (is (= [(Segment. [0 1] (Translation. 0 -5)) (Segment. [0 1] (Translation. 0 3))]
           (split-segment (Segment. [-5 1] (Translation. 0 8)))))
    (is (= [(Segment. [0 1] (Translation. 0 5)) (Segment. [0 1] (Translation. 0 -3))]
           (split-segment (Segment. [5 1] (Translation. 0 -8)))))
    (is (= [(Segment. [-5 0] (Translation. 1 -1)) (Segment. [-5 0] (Translation. 1 7))]
           (split-segment (Segment. [-5 -1] (Translation. 1 8)))))
    (is (= [(Segment. [5 0] (Translation. 1 1)) (Segment. [5 0] (Translation. 1 -7))]
           (split-segment (Segment. [5 1] (Translation. 1 -8)))))))
