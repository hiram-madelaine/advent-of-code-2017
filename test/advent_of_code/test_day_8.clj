(ns advent-of-code.test-day-8
  (:require [clojure.test :refer :all]
            [advent-of-code.day-8 :refer [solution solution-2 example input-day-8]]))




(deftest test-example
  (is (= 1 (solution example))))

(deftest test-day-8-1
  (is (= 5215 (solution input-day-8))))

(deftest test-day-8-2
  (is (= 6419 (solution-2 input-day-8))))