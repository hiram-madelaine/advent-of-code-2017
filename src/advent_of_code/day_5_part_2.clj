(ns advent-of-code.day-5-part-2
  "Now, the jumps are even stranger: after each jump,
  if the offset was three or more, instead decrease it by 1.
  Otherwise, increase it by 1 as before.
  Using this rule with the above example, the process now takes 10 steps,
  and the offset values after finding the exit are left as 2 3 2 3 -1.
  How many steps does it now take to reach the exit?"
  (:require [advent-of-code.day-5 :as day-5]))

(def jump-part-2 (partial day-5/jump (fn [j]
                                       (if (<= 3 j)
                                         (dec j)
                                         (inc j)))))

(def solution-part-2 (partial day-5/solution jump-part-2))

(solution-part-2 day-5/input)
