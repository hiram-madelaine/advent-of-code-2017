(ns advent-of-code.day-8
  "Each instruction consists of several parts:

   * the register to modify,
   * whether to increase or decrease that register's value,
   * the amount by which to increase or decrease it,
   * a condition.

   If the condition fails, skip the instruction without modifying the register.
   The registers all start at 0.
   The instructions look like this:
   b inc 5 if a > 1
   a inc 1 if b < 5
   c dec -10 if a >= 1
   c inc -20 if c == 10

   These instructions would be processed as follows:
   Because a starts at 0, it is not greater than 1, and so b is not modified.
   a is increased by 1 (to 1) because b is less than 5 (it is 0).
   c is decreased by -10 (to 10) because a is now greater than or equal to 1 (it is 1).
   c is increased by -20 (to -10) because c is equal to 10.
   After this process, the largest value in any register is 1.
   You might also encounter <= (less than or equal to) or != (not equal to).
   However, the CPU doesn't have the bandwidth to tell you what all the registers are named, and leaves that to you to determine.

   What is the largest value in any register after completing the instructions in your puzzle input?"
  (:require [clojure.string :as string]
            [clojure.string :as str]))




(defn eval-expr
  [ctx {:keys [register operator operand comparator condition-register comparator-operand]}]
  (if (comparator (ctx condition-register) comparator-operand)
    (update ctx register operator operand)
    ctx))

(defn solution
  [input]
  (let [expressions (->> input
                         string/split-lines
                         (map #(string/split % #"\s"))
                         (map (fn [[register operation amount _ register-condition comparator value]]
                                {:register           (keyword register)
                                 :operator           ({"inc" + "dec" -} operation)
                                 :operand            (read-string amount)
                                 :comparator         (eval (read-string (get {"!=" "not="} comparator comparator)))
                                 :condition-register (keyword register-condition)
                                 :comparator-operand (read-string value)}))
                         )
        registry (zipmap (map :register expressions ) (repeat 0))]
    (->> expressions
         (reduce eval-expr registry)
         vals
         (apply max))))
