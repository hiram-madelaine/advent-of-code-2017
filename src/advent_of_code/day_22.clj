(ns advent-of-code.day-22
  (:require
    [clojure.string :as string]
    [cartesian.utils :as coords :refer [add right left opposite]]))


(def input-day-22 "#.##.###.#.#.##.###.##.##\n.##.#.#.#..####.###.....#\n...##.....#..###.#..#.##.\n##.###.#...###.#.##..##.#\n###.#.###..#.#.##.#.###.#\n.###..#.#.####..##..#..##\n..###.##..###.#..#...###.\n........##..##..###......\n######...###...###...#...\n.######.##.###.#.#...###.\n###.##.###..##..#..##.##.\n.#.....#.#.#.#.##........\n#..#..#.#...##......#.###\n#######.#...#..###..#..##\n#..#.###...#.#.#.#.#....#\n#.#####...#.##.##..###.##\n..#..#..#.....#...#.#...#\n###.###.#...###.#.##.####\n.....###.#..##.##.#.###.#\n#..#...######.....##.##.#\n###.#.#.#.#.###.##..###.#\n..####.###.##.#.###..#.##\n#.#....###....##...#.##.#\n###..##.##.#.#.##..##...#\n#.####.###.#...#.#.....##")
(def example "..#\n#..\n...")

(defn ->model
  [input]
  (into {} (apply concat
                  (map-indexed
                    (fn [i line]
                      (map-indexed (fn [j c]
                                     [[i j] c]) line))
                    (string/split-lines input)))))
(defn init
  [input]
  (let [carte (->model input)
        center (int (/ (Math/sqrt (count carte)) 2))]
    {:carte      carte
     :heading    [-1 0]
     :current    [center center]
     :infections 0}))

(def infect-1 {\#  {:next \.
                    :move right}
               \.  {:next \#
                    :move left}
               nil {:next \#
                    :move left}})

(defn step
  [infect-fn {:keys [heading current] :as state}]
  (let [status (get-in state [:carte current])
        move (get-in infect-fn [status :move])
        heading' (move heading)
        status' (get-in infect-fn [status :next] status)
        cnt (if (= \# status') 1 0)]
    (-> state
        (assoc-in [:carte current] status')
        (assoc :heading heading')
        (update :infections + cnt)
        (update :current add heading'))))

(defn solution
  ([infect-fn input runs]
   (let [step-1 (partial step infect-fn)]
     (->> input
          init
          (iterate step-1)
          (take runs)
          (last)
          (:infections))))
  ([infect-fn input]
    (solution infect-fn input 10000)))

(def solution-1 (partial solution infect-1))


(def infect-2
  "Clean nodes become weakened.
   Weakened nodes become infected.
   Infected nodes become flagged.
   Flagged nodes become clean.

  If it is clean, it turns left.
  If it is weakened, it does not turn, and will continue moving in the same direction.
  If it is infected, it turns right.
  If it is flagged, it reverses direction, and will go back the way it came."
  {\# {:next \F
       :move right}
   \. {:next \W
       :move left}
   nil {:next \W
        :move left}
   \F {:next \.
       :move opposite}
   \W {:next \#
       :move identity}})

(def solution-2 (partial solution infect-2))


(comment
  (let [state state #_{:carte      (->model example)
                       :current    [1 1]
                       :heading    [-1 0]
                       :infections 0}]
    (reduce + (filter neg? (for [binome (partition 2 1 (take 10000 (iterate step state)))
                                 ]
                             (->> binome
                                  (map :carte)
                                  (map vals)
                                  (map frequencies)
                                  (map #(get % \#))
                                  (reduce -)))))))




