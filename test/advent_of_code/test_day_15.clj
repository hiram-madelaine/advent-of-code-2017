(ns advent-of-code.test-day-15
  (:require [clojure.test :refer :all]
            [advent-of-code.day-15 :as day-15 :refer [solution-1 solution-2]]))

(deftest test-solution-1
  (is (= 594 (solution-1 [0 703 516]))))

(deftest test-solution-2
  (is (= 328 (solution-2 5e6 703 516))))
