(ns advent-of-code.test-day-17
  (:require [clojure.test :refer :all]
            [advent-of-code.day-17 :refer [solution-1 solution-2]]))

(deftest test-solution-1
  (is (= 777 (solution-1))))


(deftest test-solution-2
  (is (= 39289581 (solution-2))))