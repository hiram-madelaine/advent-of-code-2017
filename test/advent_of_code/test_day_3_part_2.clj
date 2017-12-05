(ns advent-of-code.test-day-3-part-2
  (:require [clojure.test :refer :all]
            [advent-of-code.day-3-part-2 :refer [square]]))


(deftest examples
  (are [n result]
    (= result (square n))
    1 1
    2 1
    3 2
    4 4
    5 5))
