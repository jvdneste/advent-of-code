(ns advent-of-code-2019.day-01-part-2-test
  (:require [clojure.test :refer :all])
  (:require [advent-of-code-2019.day-01-part-2 :refer [total-needed-fuel]]))

(deftest total-fuel-is-correct
  (testing "is total fuel correct"
    (is (= 4896902 (total-needed-fuel)))))
