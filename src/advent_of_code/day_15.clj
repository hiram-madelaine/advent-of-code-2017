(ns advent-of-code.day-15
  (:require [clojure.pprint :refer [cl-format]]))

(defn step
  [factor previous]
  (-> previous
      (* factor)
      (mod 2147483647)))

(def step-A (partial step 16807))
(def step-B (partial step 48271))

(defn lower-bits-too-slow
  [n]
  (drop 16 (cl-format nil "~32,'0b" n)))

(defn lower-bits [x]
  "Get the lowest n bits of x
  https://stackoverflow.com/questions/10492361/clojure-the-fastest-way-to-extract-the-last-n-bits-in-an-integer"
  (bit-and x (unchecked-dec (bit-shift-left 1 16))))

(defn same-lower-bits?
  [n n2]
  (== (lower-bits n) (lower-bits n2)))

(defn judge
  [[count prev-a prev-b]]
  (let [next-a (step-A prev-a)
        next-b (step-B prev-b)]
    [(if (same-lower-bits? next-a next-b) (inc count) count)
     next-a
     next-b]))

(defn solution
  [runs init]
  (->> init
       (iterate judge)
       (take (inc runs))
       (last)
       (first)))

(def solution-1 (partial solution 40000000))

; Part 2


(defn step-2
  [step-fn parity [count numbers prev-a]]
  (let [next-a (step-fn prev-a)
        match (zero? (mod next-a parity))
        numbers (if match (conj numbers next-a) numbers)
        count (if match (inc count) count)]
    [count numbers next-a]))

(def step-A-2 (partial step-2 step-A 4))

(def step-B-2 (partial step-2 step-B 8))

(defn run-while-count
  [goal step init]
  (second (last (take-while
                  (fn [[count]] (<= count goal))
                  (iterate step [0 [] init])))))

(defn solution-2
  [pairs init-a init-b]
  (let [res-1 (future (run-while-count pairs step-A-2 init-a))
        res-2 (future (run-while-count pairs step-B-2 init-b))]
    (-> (map same-lower-bits? @res-1 @res-2)
        (frequencies)
        (get true))))


