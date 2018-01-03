(ns advent-of-code.day-25
  (:require [clojure.spec.alpha :as s]
            [instaparse.core :as insta]))

(s/def :turing/bit #{0 1})
(s/def :rule/id #{:A :B :C :D :E :F})

(s/def :rule/write :turing/bit)
(s/def :rule/move #{-1 1})
(s/def :rule/next :rule/id)

(s/def :turing/rule (s/keys :req [:rule/write
                                  :rule/move
                                  :rule/next]))

(s/def :turing/state (s/map-of :turing/bit :turing/rule :count 2))
(s/def :turing/tape (s/map-of int? :turing/bit))
(s/def :turing/cursor int?)
(s/def :turing/states (s/map-of :rule/next :turing/state :count 6))
(s/def :turing/rule-id :rule/id)

(s/def :turing/world (s/keys :req [:turing/tape
                                   :turing/states
                                   :turing/cursor
                                   :turing/rule-id]))
(defn step
  [world]
  (let [{:turing/keys [cursor rule-id]} world
        current (get-in world [:turing/tape cursor] 0)
        {:rule/keys [write move next]} (get-in world [:turing/states rule-id current])
        cursor' (+ cursor move)]
    (-> world
        (assoc-in [:turing/tape cursor] write)
        (assoc :turing/cursor cursor')
        (assoc :turing/rule-id next))))


(def input-day-25 "In state A:\n  If the current value is 0:\n    - Write the value 1.\n    - Move one slot to the right.\n    - Continue with state B.\n  If the current value is 1:\n    - Write the value 0.\n    - Move one slot to the left.\n    - Continue with state F.\n\nIn state B:\n  If the current value is 0:\n    - Write the value 0.\n    - Move one slot to the right.\n    - Continue with state C.\n  If the current value is 1:\n    - Write the value 0.\n    - Move one slot to the right.\n    - Continue with state D.\n\nIn state C:\n  If the current value is 0:\n    - Write the value 1.\n    - Move one slot to the left.\n    - Continue with state D.\n  If the current value is 1:\n    - Write the value 1.\n    - Move one slot to the right.\n    - Continue with state E.\n\nIn state D:\n  If the current value is 0:\n    - Write the value 0.\n    - Move one slot to the left.\n    - Continue with state E.\n  If the current value is 1:\n    - Write the value 0.\n    - Move one slot to the left.\n    - Continue with state D.\n\nIn state E:\n  If the current value is 0:\n    - Write the value 0.\n    - Move one slot to the right.\n    - Continue with state A.\n  If the current value is 1:\n    - Write the value 1.\n    - Move one slot to the right.\n    - Continue with state C.\n\nIn state F:\n  If the current value is 0:\n    - Write the value 1.\n    - Move one slot to the left.\n    - Continue with state A.\n  If the current value is 1:\n    - Write the value 1.\n    - Move one slot to the right.\n    - Continue with state A.")

(def parser (insta/parser (slurp "resources/day-25.bnf")))

(defn parse-states
  [input]
  (s/conform :turing/states
             (insta/transform
               {:write     (fn [v]
                             [:rule/write (Integer/parseInt v)])
                :move      (fn [m]
                             [:rule/move (if (= m "right") 1 -1)])
                :next      (fn [n]
                             [:rule/next (keyword n)])
                :RULE-CASE (fn [id & r]
                             {(Integer/parseInt id) (into {} r)})
                :STATE     (fn [k & rules]
                             {(keyword k) (apply merge rules)})
                :STATES    (fn [& rules]
                             (apply merge rules))}
               (parser input))))

(defn init
  [input]
  #:turing{:tape    {0 0}
           :cursor  0
           :rule-id :A
           :states  (parse-states input)})

(defn solution
  [runs input]
  (->> input
       init
       (iterate step)
       (take (inc runs))
       (last)
       (:turing/tape)
       (vals)
       (reduce +)))



