(ns advent-of-code.test-day-5
  (:require [clojure.test :refer :all]
            [advent-of-code.day-5 :as day-5]))


(deftest test-not-exited?
  (are [expected state]
    (= expected (day-5/not-exited? state))
    true {::day-5/jumps                  [0 3 0 1 -3]
          :advent-of-code.day-5/position 0}
    false {::day-5/jumps                  [0 3 0 1 -3]
           :advent-of-code.day-5/position -1}
    false {::day-5/jumps                  [0 3 0 1 -3]
           :advent-of-code.day-5/position 5}))

(deftest test-example
  (is (= 5 (day-5/solution-part-1 {::day-5/jumps                  [0 3 0 1 -3]
                                   :advent-of-code.day-5/position 0}))))

(deftest test-solution
    (is (= 372139 (day-5/solution-part-1 day-5/input))))