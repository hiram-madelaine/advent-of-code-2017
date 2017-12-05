(ns advent-of-code.day-1-golf)

(defn golf
  [digits]
  (->> (str digits (first digits))
       (partition 2 1)
       (reduce (fn [acc [f s]]
                 (if (= f s)
                   (let [value (Integer/parseInt (str f))]
                     (+ acc value))
                   acc))
               0)))