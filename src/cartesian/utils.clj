(ns cartesian.utils
  (:require
    [clojure.spec.alpha :as s]))

(s/def :cartesian/point int?)

(s/def :cartesian/coords (s/tuple :cartesian/point :cartesian/point))


(defn add
  [[x y] [dx dy]]
  [(+ x dx) (+ y dy)])

(s/fdef add
        :args (s/cat :point :cartesian/coords :delta :cartesian/coords)
        :ret :cartesian/coords)


(defn right
  [[x y]]
  [y (- x)])

(defn left
  [[x y]]
  [(- y) x])

(defn opposite
  [[x y]]
  [(- x) (- y)])


(comment
  (s/exercise-fn `add)

  (let [init [0 0]
        identite (comp left right)]
    (= init (identite init)))

  (let [init [0 0]
        left-left (comp left left)]
    (= (opposite init) (left-left init)))


  )