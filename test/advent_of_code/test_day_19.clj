(ns advent-of-code.test-day-19
  (:require [clojure.test :refer :all]
            [advent-of-code.day-19 :as day-19 :refer [solution-1 solution-2]]))


(deftest test-example
  (is (= "ABCDEF" (solution-1 day-19/example))))

(deftest test-solution-1
  (is (= "EPYDUXANIT" (solution-1 day-19/input-day-19))))


(deftest test-example-2
  (is (= 38 (solution-2 day-19/example))))

(deftest test-solution-2
  (is (= 17544 (solution-2 day-19/input-day-19))))


