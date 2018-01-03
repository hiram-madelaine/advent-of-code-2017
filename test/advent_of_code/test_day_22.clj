(ns advent-of-code.test-day-22
  (:require [clojure.test :refer :all]
            [advent-of-code.day-22 :refer [solution-1 solution-2 input-day-22 example]]))


(deftest test-example
  (is (= 5587 (solution-1 example))))

(deftest test-solution-1
  (is (= 5223 (solution-1 input-day-22))))


(deftest test-example-2
  (is (= 2511944 (solution-2 example 10000001))))

(deftest test-solution-2
  (is (= 2511456 (solution-2 input-day-22 10000001))))
