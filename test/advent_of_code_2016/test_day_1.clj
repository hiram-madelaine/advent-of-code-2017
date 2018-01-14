(ns advent-of-code-2016.test-day-1
  (:require
    [clojure.test :refer :all]
    [advent-of-code-2016.day-1 :refer [solution-1 solution-2 init input]]))

(deftest test-part-1
  (is (= 250 (solution-1 init input))))

(deftest test-part-2
  (is (= 151 (solution-2 init input))))
