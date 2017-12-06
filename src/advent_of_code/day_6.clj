(ns advent-of-code.day-6
  "The reallocation routine operates in cycles.
   In each cycle :
   * it finds the memory bank with the most blocks (ties won by the lowest-numbered memory bank)
   * redistributes those blocks among the banks.

   To do this :
   * it removes all of the blocks from the selected bank,
   * then moves to the next (by index) memory bank
   * and inserts one of the blocks.
   * It continues doing this until it runs out of blocks;
   * if it reaches the last memory bank, it wraps around to the first one.

   The debugger would like to know how many redistributions can be done before a blocks-in-banks configuration is produced that has been seen before.

   For example, imagine a scenario with only four memory banks:
   The banks start with 0, 2, 7, and 0 blocks.
   The third bank has the most blocks, so it is chosen for redistribution.
   Starting with the next bank (the fourth bank) and then continuing to the first bank,
   the second bank, and so on, the 7 blocks are spread out over the memory banks.
   The fourth, first, and second banks get two blocks each,
   and the third bank gets one back.
   The final result looks like this: 2 4 1 2.
   Next, the second bank is chosen because it contains the most blocks (four).
   Because there are four memory banks, each gets one block.
   The result is: 3 1 2 3.
   Now, there is a tie between the first and fourth memory banks, both of which have three blocks.
   The first bank wins the tie, and its three blocks are distributed evenly over the other three banks, leaving it with none: 0 2 3 4.
   The fourth bank is chosen, and its four blocks are distributed such that each of the four banks receives one: 1 3 4 1.
   The third bank is chosen, and the same thing happens: 2 4 1 2.
   At this point, we've reached a state we've seen before: 2 4 1 2 was already seen.
   The infinite loop is detected after the fifth block redistribution cycle, and so the answer in this example is 5.
   Given the initial block counts in your puzzle input, how many redistribution cycles must be completed before a configuration is produced that has been seen before?"
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.test.alpha :as stest]))

(s/def :mem/blocks nat-int?)
(s/def :mem/bank (s/int-in 0 16))
(s/def :mem/banks (s/coll-of :mem/blocks :count 16 :type vector?))
(s/def :mem/seen (s/coll-of :mem/banks :type set?))

(s/def :mem/memory (s/keys :req [:mem/banks
                                 :mem/seen
                                 :mem/blocks
                                 :mem/bank]))


(defn max-at
  "Trouve le max bank ainsi que la position"
  [banks]
  (let [high (apply max banks)]
    [high (.indexOf banks high)]))

(defn block-redistribution-cycle
  [{:as state :mem/keys [blocks bank banks]}]
  (let [next-bank (mod (inc bank) (count banks))]
    (-> state
        (update-in [:mem/banks next-bank] inc)
        (update :mem/blocks dec)
        (assoc :mem/bank next-bank))))

(defn cycle-mem
  [state]
  (->> state
       (iterate block-redistribution-cycle)
       (take-while #(<= 0 (:mem/blocks %)))
       last))

(defn redistribution-cycle
  "* it removes all of the blocks from the selected bank,
   * then moves to the next (by index) memory bank
   * and inserts one of the blocks.
   * It continues doing this until it runs out of blocks    ;
   * if it reaches the last memory bank, it wraps around to the first one."
  [{:as state :mem/keys [banks seen]}]
  (let [[high index] (max-at banks)]
    (-> state
        (assoc-in [:mem/banks index] 0)
        (assoc :mem/blocks high)
        (assoc :mem/bank index)
        (update :mem/seen conj banks)
        cycle-mem)))

(defn solution
  [stop-pred state]
  (->> state
       (iterate redistribution-cycle)
       (take-while stop-pred)
       count))

(def solution-day-6-part-1
  (partial solution (fn [{:as state :mem/keys [banks seen]}]
                      (not (seen banks)))))


(comment


  (solution-day-6-part-1 #:mem{:banks [10 3 15 10 5 15 5 15 9 2 5 8 5 2 3 6]
                               :init  [10 3 15 10 5 15 5 15 9 2 5 8 5 2 3 6]
                               :seen  #{}})

  (solution-day-6-part-1 #:mem{:banks [1 1 0 15 14 13 12 10 10 9 8 7 6 4 3 5]
                               :init  [1 1 0 15 14 13 12 10 10 9 8 7 6 4 3 5]
                               :seen  #{}})

  (solution-day-6-part-1 #:mem{:banks [0 2 7 0]
                               :seen  #{}})

  (solution-day-6-part-1 #:mem{:banks [2 4 1 2]
                               :seen  #{}})


  )

(defn mem-cycle
  [state]

  )

