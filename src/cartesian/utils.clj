(ns cartesian.utils
  (:require
    [clojure.spec.alpha :as s]))

(s/def :cartesian/point int?)

(s/def :cartesian/coords (s/tuple :cartesian/point :cartesian/point))


(defn add
  [from delta]
  (let [[x y] from
        [dx dy] delta]
    [(+ x dx) (+ y dy)]))

(s/fdef add
        :args (s/cat :point :cartesian/coords :delta :cartesian/coords)
        :ret :cartesian/coords)


(defn right
  [[x y]]
  [y (- x)])

(defn opposite
  [[x y]]
  [(- x) (- y)])

(defn left
  [coord]
  (opposite (right coord)))


(defn manhattan-distance
  [from to]
  (let [[x y] from
        [x' y'] to]
    (+
      (Math/abs (- x' x))
      (Math/abs (- y' y)))))


(def moves->coords
 "
            y
  ┌─────────────────▶
  │
  │         ▲ up
x │         │
  │ left ◀──┼──▶ righ
  │         │
  │         ▼
  ▼        down
"
  {:up    [-1 0]
   :down  [1 0]
   :right [0 1]
   :left  [0 -1]})

(def coords->moves (clojure.set/map-invert moves->coords))

(def headings (set (keys moves->coords)))


(comment

  (manhattan-distance [-1 0] [2 0])
  (s/exercise-fn `add)

  (let [init [0 0]
        identite (comp left right)]
    (= init (identite init)))

  (let [init [0 0]
        left-left (comp left left)]
    (= (opposite init) (left-left init)))

  (let [init [0 0]
        right-right (comp right right)]
    (= (opposite init) (right-right init))))


