(ns advent-of-code.day-3
  "You come across an experimental new kind of memory stored on an infinite two-dimensional grid.
   Each square on the grid is allocated in a spiral pattern starting at a location marked 1
   and then counting up while spiraling outward.
   For example, the first few squares are allocated like this:
   17  16  15  14  13
   18   5   4   3  12
   19   6   1   2  11
   20   7   8   9  10
   21  22  23---> ...
   While this is very space-efficient (no squares are skipped),
   requested data must be carried back to square 1 (the location of the only access port for this memory system)
   by programs that can only move :

   * up
   * down
   * left
   * right

   They always take the shortest path: the Manhattan Distance between the location of the data and square 1.

   For example :

   Data from square 1 is carried 0 steps, since it's at the access port.
   Data from square 12 is carried 3 steps, such as: down, left, left.
   Data from square 23 is carried only 2 steps: up twice.
   Data from square 1024 must be carried 31 steps.

   How many steps are required to carry the data from the square identified in your puzzle input all the way to the access port?"
  (:require [clojure.spec.alpha :as s]))

; Because the Manhattan distance is easy to compute in cartesian coordinates,
; my first Idea was to map the spirale coordinate to cartesian coordinates.
; Starting with 1 at the central coordinates [0 0]
; I follow the trail of the spirale :right :up :left :left :down ...
; At each step the new coordinate is computed from the last.


(def odds (iterate #(+ 2 %) 1))

(comment
  (take 10 (conj (map (fn [[l h]]
                        [(inc l) h]) (partition 2 1 (map #(* % %) odds))) [1 1])))
(defn spirale
  [n]
  (->> (conj (map (fn [[l h]]
                    [(inc l) h]) (partition 2 1 (map #(* % %) odds))) [1 1])
       (take-while
         (fn [[l h]]
           (or (> n h)
               (<= l n h))))
       (map (fn [[l h]]
              (range l (inc h))))))

(def moves {:right [1 0]
            :up    [0 1]
            :left  [-1 0]
            :down  [0 -1]})

(defn rank-moves
  "Generate the path of the outer spiral of rank n"
  [n]
  (let [r (dec n)]
    (-> [:right]
        (concat (repeat (dec r) :up))
        (concat (repeat r :left))
        (concat (repeat r :down))
        (concat (repeat r :right)))))

(defn rank-moves'
  "Generate the path of the outer spiral of rank n"
  [rank]
  (let [[n steps] rank
        r (dec n)]
    [(inc n) (-> [[1 0]]
                 (concat (repeat (dec r) [0 1]))
                 (concat (repeat r [-1 0]))
                 (concat (repeat r [0 -1]))
                 (concat (repeat r [1 0])))]))

(comment

  (map second (take 3 (iterate rank-moves' [1 [[0 0]]])))
  )

(defn grid
  "Generate the correspondance between numbers and cartesian coordinates."
  [n]
  (reduce
    (fn [acc [n move]]
      (let [[x y] (get acc (dec n))
            [x' y'] (move moves)]
        (assoc acc n [(+ x x') (+ y y')])))
    (sorted-map 1 [0 0])
    (drop 1 (map
              vector
              (flatten (spirale n))
              (mapcat rank-moves odds)))))

(defn carried
  "Give the Manhattan distance of any number from the center."
  [n]
  (let [spi->cartesian (grid n)
        [x' y'] (get spi->cartesian n)]
    (+ (Math/abs x')
       (Math/abs y'))))
