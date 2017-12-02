(ns advent-of-code.day-1-part-2
  "It wants you to consider the digit halfway around the circular list.
   That is, if your list contains 10 items, only include a digit in your sum if the digit 10/2 = 5 steps forward matches it.
   Fortunately, your list has an even number of elements.
   1212 produces 6: the list contains 4 items, and all four digits match the digit 2 items ahead.
   1221 produces 0, because every comparison is between a 1 and a 2.
   123425 produces 4, because both 2s match each other, but no other digit has a match.
   123123 produces 12.
   12131415 produces 4"
  (:require [advent-of-code.day-1 :refer [x-day-1]]))

(defn halfway
  "Give the corresponding element halfway of index position in Digits as a circular list.
   (halfway \"1234\" 0) => \\3
   (halfway \"1234\" 1) => \\4
   (halfway \"1234\" 2) => \\1
   (halfway \"1234\" 3) => \\2"
  [digits idx]
  (let [half (/ (count digits) 2)
        halfway-idx (+ idx half)]
    (->> digits
         cycle
         (drop halfway-idx)
         (take 1)
         first)))

(defn checksum
  [digits]
  (->> digits
       (map-indexed (fn [idx value]
                      [(halfway digits idx) value]))
       (transduce x-day-1 +)))