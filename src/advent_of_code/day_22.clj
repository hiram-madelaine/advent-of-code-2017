(ns advent-of-code.day-22
  (:require [clojure.string :as string]))


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

(defn right
  [[x y]]
  [y (- x)])

(defn left
  [[x y]]
  [(- y) x])

(def turn {\# right
           \. left})


(defn init
  [input]
  (let [carte (->model input)
        center (int (/ (Math/sqrt (count carte)) 2))]
    {:carte      carte
     :heading    [-1 0]
     :current    [center center]
     :infections 0}))

(defn add
  [[x y] [dx dy]]
  [(+ x dx) (+ y dy)])


(def infect {\#  \.
             \.  \#
             nil \#})

(defn step
  [{:keys [heading current] :as state}]
  (let [status (get-in state [:carte current])
        move (get turn status left)
        heading' (move heading)
        status' (infect status)
        cnt (if (= \# status') 1 0)]
    (-> state
        (assoc-in [:carte current] status')
        (assoc :heading heading')
        (update :infections + cnt)
        (update :current add heading'))))

(defn solution
  [input]
  (->> input
       init
       (iterate step)
       (take 10000)
       (last)
       (:infections)))

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




