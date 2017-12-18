(ns advent-of-code.test-day-18
  (:require [clojure.test :refer :all]
            [advent-of-code.day-18 :as day-18 :refer [solution]]))


(deftest test-example
  (is (= 4 (:sound (solution day-18/example)))))

(deftest test-solution-1
  (is (= 3423 (:sound (solution day-18/input-day-18)))))