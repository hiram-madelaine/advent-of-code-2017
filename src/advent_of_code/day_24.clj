(ns advent-of-code.day-24
  (:require [clojure.string :as string]))


(defn line->connector
  [s]
  (let [[p1 p2] (string/split s #"/")]
    [(Integer/parseInt p1)
     (Integer/parseInt p2)]))

(defn ->inventory
  [input]
  (->> input
       string/split-lines
       (map line->connector)
       set))

(defn compatible?
  "Find a component compatible with the end pins of the last bridge component"
  [inventory bridge]
  (let [[_ p2] (last bridge)]
    (filter (fn [candidate]
              (some #(= p2 %) candidate)) inventory)))

(defn grow
  "Connect a new component to the bridge respecting the pins."
  [bridge [s' e']]
  (let [[_ e] (last bridge)]
    (conj bridge (if (= e s')
                   [s' e']
                   [e' s']))))

(defn bridge-strength
  [bridge]
  (reduce + (flatten bridge)))

(defn choose-strongest
  [bridges]
  (last (sort-by bridge-strength bridges)))

(defn solution
  "Find the longuest bridge."
  [choice-fn bridge inventory]
  (let [comps (compatible? inventory bridge)]
    (if (seq comps)
      (choice-fn
        (map
          #(solution choice-fn
                     (grow bridge %)
                     (disj inventory %))
          comps))
      bridge)))

(defn solution-1
  [input]
  (->> input
       ->inventory
       (solution choose-strongest [[0 0]])
       bridge-strength))


(defn choose-longuest-then-strongest
  [bridges]
  (let [grouped (group-by count bridges)
        mx (apply max (keys grouped))]
    (choose-strongest (get grouped mx))))

(defn solution-2
  [input]
  (->> input
       ->inventory
       (solution choose-longuest-then-strongest [[0 0]])
       bridge-strength))

(def input-day-24 "48/5\n25/10\n35/49\n34/41\n35/35\n47/35\n34/46\n47/23\n28/8\n27/21\n40/11\n22/50\n48/42\n38/17\n50/33\n13/13\n22/33\n17/29\n50/0\n20/47\n28/0\n42/4\n46/22\n19/35\n17/22\n33/37\n47/7\n35/20\n8/36\n24/34\n6/7\n7/43\n45/37\n21/31\n37/26\n16/5\n11/14\n7/23\n2/23\n3/25\n20/20\n18/20\n19/34\n25/46\n41/24\n0/33\n3/7\n49/38\n47/22\n44/15\n24/21\n10/35\n6/21\n14/50")

(def example "0/2\n2/2\n2/3\n3/4\n3/5\n0/1\n10/1\n9/10")








