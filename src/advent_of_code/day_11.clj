(ns advent-of-code.day-11
  "I have solved this one using this precious source :
   https://www.redblobgames.com/grids/hexagons/#distances"
  (:require [clojure.string :as string]))


(def steps {"n"  [0 1 -1]
            "ne" [1 0 -1]
            "se" [1 -1 0]
            "s"  [0 -1 1]
            "sw" [-1 0 1]
            "nw" [-1 1 0]})
(defn move
  [position direction]
  (mapv + position (steps direction)))

(defn distance
  [destination]
  (/ (->> destination
          (map #(Math/abs %))
          (reduce +)) 2))

(defn trail
  [from path]
  (reductions move from path))

(defn base-solution
  [input]
  (->> (string/split input #",")
       (trail [0 0 0])))

(defn solution
  [input]
  (->> (base-solution input)
       last
       distance))

(defn solution-2
  [input]
  (->> (base-solution input)
       (map distance)
       (reduce max)))
