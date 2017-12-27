(ns advent-of-code.test-day-16
  (:require [clojure.test :refer :all]
            [advent-of-code.day-16 :refer [solution-1 solution-2 input-day-16]]))


(deftest test-solution-1
  (is (= "bkgcdefiholnpmja" (solution-1 input-day-16))))


(deftest test-solution-2
  (is (= "knmdfoijcbpghlea" (solution-2))))

