(ns advent-of-code.test-day-3
  (:require [clojure.test :refer :all]
            [advent-of-code.day-3 :as day-3]))


(deftest examples
  (are [n reponse]
    (= reponse (day-3/carried n))
    1 0
    12 3
    23 2
    1024 31
    368078 371))
