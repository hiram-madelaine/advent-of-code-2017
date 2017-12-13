(ns advent-of-code.day-10
  (:require [com.rpl.specter :refer [setval srange]]))

(defn replace-at
  "Replace in input at position by sub
  [0 1 2 3 4] 0 [2 1 0]  -> [2 1 0 3 4]
  [2 1 0 3 4] 3 [1 2 3 4] -> [4 3 0 1 2]
       - ^
  [2 1 0 3 4 2 1 0 3 4 2 1 0 3 4]
         ^
  "
  [input position sub]
  (let [input-length (count input)
        sub-length (count sub)]
    (if (> input-length (+ position sub-length))
      (setval (srange position (+ position sub-length)) sub input)
      (let [in (- input-length position)
            [start end] (split-at in sub)]
        (->> input
             (setval (srange position (+ position in)) start)
             (setval (srange 0 (count end)) end))))

    ))


(defn rotate
  "
  [(0 1 2) 3 4] -> [(2 1 0) 3 4]
    ^
  [2 1) 0 (3 4] -> [4 3) 0 ([1] 2]
           ^
  "
  [input position length]
  (let [reversed (reverse (take length (drop position (cycle input))))]
    (replace-at input position reversed)))

(defn solution
  [lengths acc]
  (reduce (fn [{:keys [position skip input]} length]
            {:position (mod (+ position length skip) (count input))
             :skip     (inc skip)
             :input    (rotate input position length)})
          acc lengths))

(defn solution-1
  [input]
  (->> (solution input
                {:input    (range 256)
                 :skip     0
                 :position 0})
      :input
      (take 2)
      (apply *)))


(defn solution-2
  [lengths]
  (let [lengths (concat (->> lengths
                             (map char)
                             (map int)) [17, 31, 73, 47, 23])
        solution' (partial solution lengths)]
    (->> (last (take 65 (iterate solution' {:input    (range 256)
                                            :skip     0
                                            :position 0})))
         (:input)
         (partition 16)
         (map #(reduce bit-xor %))
         (map #(Integer/toHexString %))
         (map #(if (= 2 (count %)) % (str "0" %)))
         clojure.string/join)))


(count "a2582a3ae66e6e86e3812dcb672a272")
