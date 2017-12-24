(ns advent-of-code.test-day-22
  (:require [clojure.test :refer :all]
            [advent-of-code.day-22 :refer [solution input-day-22 example]]))


(deftest test-example
  (is (= 5587 (solution example))))

(deftest test-solution-1
  (is (= 5223 (solution input-day-22))))
