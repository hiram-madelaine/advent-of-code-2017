(ns advent-of-code.day-7
  "The root node :
  * has at least one child
  * Has not parent -> Is not a child of a node
  Exclude all nodes with no child
  Exclude all nodes appearing as a child of a node"
  (:require [clojure.spec.alpha :as s]
            [clojure.string :as string]))



(def alphabet "abcdefghijklmnopqrstuvwxyz")
(def numbers "0123456789")

(def letter (set alphabet))
(def digits (set numbers))

(s/def ::node (s/+ letter))
(s/def ::weight (s/+ digits))
(s/def ::par #{\( \)})
(s/def ::space #{\space})

(s/def ::parent-childrens
  (s/and string?
         (s/conformer seq)
         (s/+ (s/cat
                :node ::node
                :void (s/cat :space ::space :paren ::par)
                :weight ::weight
                :void ::par
                :void (s/? (s/cat :space ::space :dash #{\-} :arrow #{\>} :space ::space))
                :children (s/* (s/cat :node ::node :void (s/? (s/cat :paren #{\,} :space ::space))))))
         (s/conformer
           (fn [[{:keys [node children]}]]
             (let [value (->> children
                              (map :node)
                              (map string/join)
                              set)]
               [(string/join node) value])))))

(def line-reg #"(\w+)\s\((\d+)\)\s?-?>?\s?(.*)")

(defn ->input
  [_ node weight children]
  [node weight (when-not (string/blank? children)
                 (set (string/split children #", ")))])
(defn solution
  [input]
  (let [nodes (into [] (comp
                         (map #(re-matches line-reg %))
                         (map #(apply ->input %))) (string/split-lines input))
        children (reduce (fn [acc [_ _ cs]] (clojure.set/union acc cs)) #{} nodes)]
    (ffirst (filter (fn [[p _]] (not (children p))) nodes))))

