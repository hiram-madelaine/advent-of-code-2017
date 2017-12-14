(ns advent-of-code.day-12
  "You need to figure out how many programs are in the group that contains program ID 0.
   For example, suppose you go door-to-door like a travelling salesman and record the following list:

   0 <-> 2
   1 <-> 1
   2 <-> 0, 3, 4
   3 <-> 2, 4
   4 <-> 2, 3, 6
   5 <-> 6
   6 <-> 4, 5

   In this example, the following programs are in the group that contains program ID 0:
   Program 0 by definition.
   Program 2, directly connected to program 0.
   Program 3 via program 2.
   Program 4 via program 2.
   Program 5 via programs 6, then 4, then 2.
   Program 6 via programs 4, then 2.

   Therefore, a total of 6 programs are in this group; all but program 1, which has a pipe that connects it to itself.

   How many programs are in the group that contains program ID 0?"
  (:require [clojure.string :as string]))

(defn ->db
  [input]
  (->> (string/split-lines input)
       (map #(string/split % #" <-> "))
       (map (fn [[id connections]]
              {id (string/split connections #", ")}))
       (into {})))

(defn connected
  [result db id]
  (let [result (conj result id)
        connections (set (db id))
        unknown (clojure.set/difference  connections result)]
    (set (reduce concat result (map #(connected result db %) unknown)))))

(defn solution
  [input id]
  (let [db (->db input)]
    (connected #{} db id)))


(defn solution-2
  [input]
  (let [db (->db input)
        programs (-> (sorted-set)
                     (into (keys db))
                     (into (flatten (vals db))))]
    (letfn [(all-groups
              [ids acc]
              (let [[f & r] ids
                    seen (connected #{} db f)]
                (if (seq r)
                  (all-groups (clojure.set/difference (set r) seen) (assoc acc f seen))
                  (assoc acc f seen))))]
      (all-groups programs (sorted-map)))))


