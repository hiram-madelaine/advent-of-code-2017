(ns advent-of-code.test-day-20
  (:require
    [clojure.test :refer :all]
    [advent-of-code.day-20 :refer [solution-2 solution input-day-20]]))


(deftest test-solution-1
  (is (= 243 (last (solution 500 input-day-20)))))


(deftest test-solution-2
  (is (= 648 (solution-2 11 input-day-20))))
