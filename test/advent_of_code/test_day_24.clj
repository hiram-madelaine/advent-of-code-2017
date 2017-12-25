(ns advent-of-code.test-day-24
  (:require [clojure.test :refer :all]
            [advent-of-code.day-24 :as day-24 :refer [solution-1 input-day-24 example]]))


(deftest test-solution-1
  (is (= 1656 (solution-1 input-day-24))))