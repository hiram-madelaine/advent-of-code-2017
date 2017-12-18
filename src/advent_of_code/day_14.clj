(ns advent-of-code.day-14
  (:require [advent-of-code.day-10 :as day-10]
            [clojure.pprint :refer [cl-format]]
            [clojure.string :as str]
            [clojure.set :refer [union difference intersection]]))

(def x-to-binaries
  (comp
    (map #(read-string (str "0x" %)))
    (map #(cl-format nil "~4,'0',B" %))))

(def x-to-count-used
  (comp
    x-to-binaries
    (map #(filter (fn [b] (= \1 b)) %))
    (map count)))

(def x-day-10
  (comp
    (map #(str "vbqugkhl-" %))
    (map #(day-10/solution-2 %))))

(def x-solution-1
  (comp
    x-day-10
    x-to-count-used))

(defn solution-1
  []
  (->> (range 128)
       (transduce x-solution-1 +)))

(comment
  (= 8148 (solution-1)))

;A region is :
;a group of used squares
;that are all adjacent, not including diagonals.

;Possible strategies :
;Find the first used square and find the all the adjacent squares.


(defn adjacents
  [[x y]]
  (set
    (filter
      (fn [[x y]]
        (and (>= x 0) (>= y 0)))
      [[(inc x) y]
       [x (dec y)]
       [(dec x) y]
       [x (inc y)]])))

(adjacents [0 0])

(def x-solution-2
  (comp
    x-day-10
    x-to-binaries))

(defn solution-2
  []
  (let [size (range 128)
        depth (range 128)
        matrix (into [] x-solution-2 size)]
    (into #{}
          (for [l size
                r depth
                :let [row (get matrix l)
                      b (get row r)]
                :when (= \1 b)]
            [l r]))))

(defn find-groups
  "Add the notion of already seen."
  [db group root]
  (let [group (conj group root)
        neighboors (intersection (adjacents root) db)
        not-seen (clojure.set/difference neighboors group)
        group (into group not-seen)]
    (if (seq not-seen)
      (into  group (mapcat #(find-groups (difference db group) group %) not-seen))
      group)))

(comment
  (def db (solution-2))
  (count db)

  )

(comment
  (difference  #{1 3 5 7} #{7})



  (let [                                                    ;db #{[0 0] [1 0] [0 1] [0 2] [1 2] [2 2] [9 0] [9 1]}
      result {}
        root [0 0]
        group (find-groups db #{} root)
        result (assoc result root group)]
    result
    (let [new-db (difference db group)
            new-root (first new-db)
            new-group (find-groups new-db #{} new-root)]
        (assoc result new-root new-group)))
  )
