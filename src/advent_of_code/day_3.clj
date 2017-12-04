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





(def odds (iterate #(+ 2 %) 1))

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
  [n]
  (-> [:right]
      (concat (take (dec (dec n)) (repeat :up)))
      (concat (take (dec n) (repeat :left)))
      (concat (take (dec n) (repeat :down)))
      (concat (take (dec n) (repeat :right)))))

(map rank-moves (take 4 odds))

(defn grid [n]
  (let [spi (spirale n)]
    (reduce
      (fn [acc [n move]]
        (let [[x y] (get acc (dec n))
              [x' y'] (move moves)]
          (assoc acc n [(+ x x') (+ y y')])))
      {1 [0 0]}
      (drop 1 (map
                (fn [n move]
                  [n move])
                (flatten (mapcat (fn [data]
                                   [data]) spi))
                (mapcat rank-moves (take (count spi) odds)))))))

(defn carried
  [n]
  (let [spi->cartesian (grid n)
        [x' y'] (get spi->cartesian n)]
    (+ (Math/abs x')
       (Math/abs y'))))

(carried 368078)