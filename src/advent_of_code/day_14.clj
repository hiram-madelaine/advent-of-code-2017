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

(def x-solution-2
  (comp
    x-day-10
    x-to-binaries))

(defn solution-2
  []
  (let [width (range 128)
        depth (range 128)
        matrix (into [] x-solution-2 width)]
    (into (sorted-set)
          (for [w width
                d depth
                :let [row (get matrix w)
                      b (get row d)]
                :when (= \1 b)]
            [d w]))))

(defn find-groups
  "Add the notion of already seen."
  [{:keys [db group]} root]
  (let [group (conj group root)
        neighboors (intersection (adjacents root) db)
        not-seen (clojure.set/difference neighboors group)
        group (into group not-seen)]
    (if (seq not-seen)
      (let [{new-db :db adjacent-group :group} (reduce find-groups
                                                       {:db    (difference db group)
                                                        :group group}
                                                       not-seen)]
        {:group (into group adjacent-group)
         :db    new-db})
      {:group group
       :db    db})))

(defn find-all
  [result db]
  (if (seq db)
    (let [root (first db)
          {new-db :db group :group} (find-groups {:db    db
                                                  :group #{}}
                                                 root)
          new-db (difference new-db group)]
      (recur (assoc result root group) new-db))
    result))


(comment


  (def db (solution-2))
  (def result (find-all {} db))
  (count result)

  (let [groups (keys result)]
    (every?  (fn [square]
               (empty? (filter (adjacents square) result))) groups))


  (let [db #{[0 0] [1 0] [0 1] [0 2] [1 2] [2 2] [9 0] [9 1]}
        result {}
        root [0 0]
        group (find-groups {:db    db
                            :group #{}}
                           root)
        result (assoc result root group)]
    result
    (let [new-db (difference db group)
          new-root (first new-db)
          new-group (find-groups {:db new-db :group #{}} new-root)]
      (assoc result new-root new-group)))




  (def result )

  (count result)

  (->> result
       vals
       (map count)
       (reduce +))

  )
