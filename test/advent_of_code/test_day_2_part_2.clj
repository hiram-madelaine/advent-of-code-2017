(ns advent-of-code.test-day-2-part-2
  (:require [clojure.test :refer :all]
            [advent-of-code.day-2 :as day-2]
            [advent-of-code.day-2-part-2 :refer [checksum]]))


(deftest example
  (is (= 9 (checksum "5 9 2 8\n9 4 7 3\n3 8 6 5"))))


(deftest test-day-2-part2
  (is (= 244 (checksum day-2/day-2-input))))