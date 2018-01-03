(ns advent-of-code.test-day-24
  (:require [clojure.test :refer :all]
            [advent-of-code.day-24 :as day-24 :refer [solution-1 solution-2 input-day-24 example]]))


(deftest test-example
  (is (= 31 (solution-1 example))))

(deftest test-solution-1
  (is (= 1656 (solution-1 input-day-24))))

(deftest test-example-2
  (is (= 19 (solution-2 example))))

(deftest test-solution-2
  (is (= 1642 (solution-2 input-day-24))))