(ns advent-of-code.test-day-13
  (:require [clojure.test :refer :all]
            [advent-of-code.day-13 :as day-13 :refer [solution solution-2]]))


(deftest test-example
  (is (= 24 (solution day-13/example))))

(deftest test-solution-1
  (is (= 1876 (solution day-13/input-day-13))))


(deftest  test-example-2
  (is (= 10 (solution-2 day-13/example))))

(deftest test-solution-2
  (is (= 3964778 (solution-2 day-13/input-day-13))))