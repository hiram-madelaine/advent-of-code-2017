(ns advent-of-code.day-1-golf)

(defn golf
  [ds]
  (->> (str ds (first ds))
       (partition 2 1)
       (reduce (fn [r [f s]]
                 (if (= f s)
                   (+ r (-> f str Integer/parseInt))
                   r)) 0)))