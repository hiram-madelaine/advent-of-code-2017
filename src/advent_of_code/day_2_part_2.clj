(ns advent-of-code.day-2-part-2
  "It sounds like the goal is to find the only two numbers in each row
   where one evenly divides the other -
   that is, where the result of the division operation is a whole number.
   They would like you to find those numbers on each line,
   divide them, and add up each line's result.
   For example, given the following spreadsheet:
   [[5 9 2 8]
    [9 4 7 3]
    [3 8 6 5]]
   In the first row, the only two numbers that evenly divide are 8 and 2;
    the result of this division is 4.
   In the second row, the two numbers are 9 and 3; the result is 3.
   In the third row, the result is 2.
   In this example, the sum of the results would be 4 + 3 + 2 = 9"
  (:require [advent-of-code.day-2 :as day-2]
            [clojure.string :as string]
            [clojure.math.combinatorics :as combo]))


(def x-find-evenly-dividers (comp
                              (map sort)
                              (map reverse)
                              (map #(apply / %))
                              (filter int?)))
(defn evenly-dividers
  "Find the only two numbers in a row where one evenly divides the other."
  [ns]
  (->> (combo/combinations ns 2)
       (into [] x-find-evenly-dividers)
       first))

(def x-day-2-part-2 (comp day-2/x-grid
                          (map evenly-dividers)))

(defn checksum
  [spreasheet]
  (->> spreasheet
       (string/split-lines)
       (transduce x-day-2-part-2 +)))
