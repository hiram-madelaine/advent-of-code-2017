(ns advent-of-code.day-17)


(defn step
  [[steps buffer pos index]]
  (let [next-value (inc index)
        num-steps (mod steps next-value)
        insert-pos (inc (mod (+ pos num-steps) next-value))
        buffer' (let [[s e] (split-at insert-pos buffer)]
                  (vec (concat s [next-value] e)))]
    [steps buffer' insert-pos next-value]))

(defn solution-1
  []
  (let [buffer (second (last (take 2018 (iterate step [376 [0] 0 0]))))]
    (get buffer (inc (.indexOf buffer 2017)))))

(defn step-2
  "We do not need handle the buuffer for this one."
  [[steps pos index pos-1]]
  (let [next-value (inc index)
        num-steps (mod steps next-value)
        insert-pos (inc (mod (+ pos num-steps) next-value))
        pos-1 (if (zero? (dec insert-pos)) next-value pos-1)]
    [steps  insert-pos next-value pos-1]))

(defn solution-2
  []
  (last (last (take 50000001 (iterate step-2 [376 0 0])))))


