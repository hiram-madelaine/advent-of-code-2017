(ns advent-of-code.test-day-25
  (:require [clojure.test :refer :all]
            [advent-of-code.day-25 :as day-25 :refer [solution input-day-25]]))


(deftest test-solution
  (is (= 2832 (solution 12794428 input-day-25))))
