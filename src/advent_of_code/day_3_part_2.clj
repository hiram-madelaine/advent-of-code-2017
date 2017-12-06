(ns advent-of-code.day-3-part-2
  "As a stress test on the system, the programs here clear the grid and then store the value 1 in square 1.
   Then, in the same allocation order as shown above, they store the sum of the values in all adjacent squares, including diagonals.
   So, the first few squares' values are chosen as follows:
   Square 1 starts with the value 1
   Square 2 has only one adjacent filled square (with value 1), so it also stores 1.
   Square 3 has both of the above squares as neighbors and stores the sum of their values, 2
   Square 4 has all three of the aforementioned squares as neighbors and stores the sum of their values, 4.
   Square 5 only has the first and fourth squares as neighbors, so it gets the value 5.

   Once a square is written, its value does not change.
   Therefore, the first few squares would receive the following values:
   147  142  133  122   59
   304    5    4    2   57
   330   10    1    1   54
   351   11   23   25   26
   362  747  806--->   ...

   What is the first value written that is larger than your puzzle input?

   Your puzzle input is still 368078"


  (:require [advent-of-code.day-3 :as day-3]))


(defn adjacent?
  [p p']
  (let [[x y] p
        [x' y'] p']
    (and
      (<= 0 (Math/abs (- x x')) 1)
      (<= 0 (Math/abs (- y y')) 1))))


(defn sum-all-adjacent-squares
  "Add all known adjacents"
  [coord known-squares]
  (->> known-squares
       (filter (fn [[coord' value]]
                 (adjacent? coord coord')))
       (vals)
       (reduce +))
  )

(defn square
  [n]
  (reduce (fn [acc [n coord]]
            (assoc acc coord
                       (sum-all-adjacent-squares coord acc))) (sorted-map [0 0] 1)
          (into [] (day-3/grid n))))


(comment
  (square 1)
  (square 12)

  (filter #(> % 368078) (sort (vals (square 50)))))