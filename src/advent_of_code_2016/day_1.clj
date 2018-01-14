(ns advent-of-code-2016.day-1
  "The Document indicates that you should start at the given coordinates (where you just landed) and face North.
   Then, follow the provided sequence:
   * either turn left (L)
   * or right (R) 90 degrees,
   then walk forward the given number of blocks, ending at a new intersection.
   Given that you can only walk on the street grid of the city,
   How far is the shortest path to the destination ?

   -- Part Two
   Easter Bunny HQ is actually at the first location you visit twice.
   How many blocks away is Easter Bunny HQ?"
  (:require
    [clojure.string :as str]
    [cartesian.utils :refer [left right add manhattan-distance]]))

(def input "R4, R5, L5, L5, L3, R2, R1, R1, L5, R5, R2, L1, L3, L4, R3, L1, L1, R2, R3, R3, R1, L3, L5, R3, R1, L1, R1, R2, L1, L4, L5, R4, R2, L192, R5, L2, R53, R1, L5, R73, R5, L5, R186, L3, L2, R1, R3, L3, L3, R1, L4, L2, R3, L5, R4, R3, R1, L1, R5, R2, R1, R1, R1, R3, R2, L1, R5, R1, L5, R2, L2, L4, R3, L1, R4, L5, R4, R3, L5, L3, R4, R2, L5, L5, R2, R3, R5, R4, R2, R1, L1, L5, L2, L3, L4, L5, L4, L5, L1, R3, R4, R5, R3, L5, L4, L3, L1, L4, R2, R5, R5, R4, L2, L4, R3, R1, L2, R5, L5, R1, R1, L1, L5, L5, L2, L1, R5, R2, L4, L1, R4, R3, L3, R1, R5, L1, L4, R2, L3, R5, R3, R1, L3")

(defn ->model
  [input]
  (map (fn [[turn & distance]]
         [(keyword (str turn))
          (Integer/parseInt (str/join distance))]) (str/split input #",\s")))

(comment
  (->model input)
  )

(def init
  {:heading  [-1 0]
   :position [0 0]
   :visited []})

(def rotations {:L left
                :R right})


(defn turn
  [state rotation]
  (update state :heading (rotations rotation)))

(defn walk
  [state]
  (update state :position #(add % (:heading state))))

(defn move
  [state [rotation distance]]
  (let [state' (turn state rotation)
        steps (take (inc distance) (iterate walk state'))]
    (last steps)))

(defn solution-1
  [state input]
  (let [final (reduce move init (->model input))]
    (apply manhattan-distance (map :position [state final]))))


(defn steps
  [state [rotation distance]]
  (let [state' (turn state rotation)]
    (take (inc distance) (iterate walk state'))))

(defn solution-2
  [state input]
  (let [result (reduce
                 (fn [{:as acc visited? :visited} pos]
                   (if (visited? pos)
                     (-> acc
                         (assoc :found pos)
                         (reduced))
                     (update acc :visited conj pos)))
                 {:visited #{}}
                 (map :position (reductions move state (->model input))))]
    (manhattan-distance (:found result)
                        (:position state))))



#_(reduce (fn [{:as acc :keys [state visited]} cmd]
          (let [positions (steps state cmd)]
            (-> acc
                (update-in [:state ]))))
        {:state   init
         :visited []}
        (->model input))


(comment
  (solution-1 init input)
  (solution-2 init input)

  (fn [acc cmd] (steps init [:L 3]))

  (filter (fn [[p f]]
            (= 2 f)) (frequencies (map :position (reductions move init (->model input)))))



  (reduce steps init (->model "R8, R4, R4, R8"))

  (manhattan-distance [128 141] [0 0]))