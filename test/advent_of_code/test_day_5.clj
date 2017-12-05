(ns advent-of-code.test-day-5
  (:require [clojure.test :refer :all]
            [advent-of-code.day-5 :as day-5]))


(deftest test-example
  (is (= 5 (day-5/solution {:advent-of-code.day-5/list-of-jumps [0 3 0 1 -3]
                            :advent-of-code.day-5/position      0}))))

(deftest test-solution
  (is (= 372139 (day-5/solution day-5/input))))