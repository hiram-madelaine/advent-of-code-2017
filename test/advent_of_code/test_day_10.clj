(ns advent-of-code.test-day-10
  (:require [clojure.test :refer :all]
            [advent-of-code.day-10 :refer [solution-1 solution-2]]))



(deftest test-solution-1
  (is (= 38628 (solution-1 [130, 126, 1, 11, 140, 2, 255, 207, 18, 254, 246, 164, 29, 104, 0, 224]))))

(deftest test-examples
  (are [lengths expected]
    (= expected (solution-2 lengths))
    "" "a2582a3a0e66e6e86e3812dcb672a272"
    "AoC 2017" "33efeb34ea91902bb2f59c9920caa6cd"
    "1,2,3" "3efbe78a8d82f29979031a4aa0b16a9d"
    "1,2,4" "63960835bcdc130f0b66d7ff4f6a5a8e"))

(deftest test-solution-2
  (is (= "e1462100a34221a7f0906da15c1c979a"
         (solution-2 "130,126,1,11,140,2,255,207,18,254,246,164,29,104,0,224") )))
