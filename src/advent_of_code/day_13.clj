(ns advent-of-code.day-13
  (:require [clojure.string :as str]))

(defn at-zero?
  "Tells if we are caught by the scanner at depth for range
   Given a range, a scanner will be at position 0 every (* 2 (dec range))"
  [depth range]
  (mod depth (* 2 (dec range))))

(defn caught?
  [depth range]
  (zero? (at-zero? depth range)))

(def not-caught?
  (complement caught?))

(defn ->model
  [input]
  (->> (str/split-lines input)
       (map #(str/split % #":\s"))
       (map #(map (fn [s] (Integer/parseInt s)) %))))

(defn solution
  [input]
  (reduce (fn [score [depth range]]
            (if (caught? depth range)
              (+ score (* depth range))
              score))
          0
          (->model input)))

(defn solution-2
  [input]
  (let [firewall (->model input)]
    (some (fn [wait]
            (when (every?
                    (fn [[depth range]]
                      (not-caught? (+ wait depth) range))
                    firewall)
              wait))
          (range))))


(def example "0: 3\n1: 2\n4: 4\n6: 4")
(def input-day-13 "0: 3\n1: 2\n2: 4\n4: 6\n6: 4\n8: 6\n10: 5\n12: 8\n14: 8\n16: 6\n18: 8\n20: 6\n22: 10\n24: 8\n26: 12\n28: 12\n30: 8\n32: 12\n34: 8\n36: 14\n38: 12\n40: 18\n42: 12\n44: 12\n46: 9\n48: 14\n50: 18\n52: 10\n54: 14\n56: 12\n58: 12\n60: 14\n64: 14\n68: 12\n70: 17\n72: 14\n74: 12\n76: 14\n78: 14\n82: 14\n84: 14\n94: 14\n96: 14")



