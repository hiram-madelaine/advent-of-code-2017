(ns advent-of-code.day-18
  "It seems like the assembly is meant to operate on a set of registers that are each named with a single letter and that can each hold a single integer.
   You suppose each register should start with a value of 0.
   There aren't that many instructions, so it shouldn't be hard to figure out what they do.
   Here's what you determine:
   snd X plays a sound with a frequency equal to the value of X.
   set X Y sets register X to the value of Y.
   add X Y increases register X by the value of Y.
   mul X Y sets register X to the result of multiplying the value contained in register X by the value of Y.
   mod X Y sets register X to the remainder of dividing the value contained in register X by the value of Y (that is, it sets X to the result of X modulo Y).
   rcv X recovers the frequency of the last sound played, but only when the value of X is not zero.
   (If it is zero, the command does nothing.)
   jgz X Y jumps with an offset of the value of Y, but only if the value of X is greater than zero.
   (An offset of 2 skips the next instruction, an offset of -1 jumps to the previous instruction, and so on.)
   Many of the instructions can take either a register (a single letter) or a number.
   The value of a register is the integer it contains;
   the value of a number is that number.
   After each jump instruction, the program continues with the instruction to which the jump jumped.
   After any other instruction, the program continues with the next instruction.
   Continuing (or jumping) off either end of the program terminates it."
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as gen]
            [clojure.string :as string]
            )
  (:refer-clojure :exclude [set]))

(def input-day-18 "set i 31\nset a 1\nmul p 17\njgz p p\nmul a 2\nadd i -1\njgz i -2\nadd a -1\nset i 127\nset p 618\nmul p 8505\nmod p a\nmul p 129749\nadd p 12345\nmod p a\nset b p\nmod b 10000\nsnd b\nadd i -1\njgz i -9\njgz a 3\nrcv b\njgz b -1\nset f 0\nset i 126\nrcv a\nrcv b\nset p a\nmul p -1\nadd p b\njgz p 4\nsnd a\nset a b\njgz 1 3\nsnd b\nset f 1\nadd i -1\njgz i -11\nsnd a\njgz f -16\njgz a -19")


(s/def :duet/operation #{"snd" "set" "add" "mul" "mod" "rcv" "jgz"})

(s/def :duet/register (s/and
                        (s/conformer keyword identity)
                        keyword?))

(s/def :duet/parameter (s/and
                         (s/conformer #(try (Integer/parseInt %)
                                            (catch Exception e
                                              ::s/invalid))
                                      identity)
                         int?))

(s/def :duet/argument (s/or :integer :duet/parameter :register :duet/register))


(s/def :duet/instruction (s/and
                           (s/conformer #(string/split % #"\s")
                                        identity)
                           (s/cat :op :duet/operation :reg :duet/register :arg (s/? :duet/argument))))

(s/def :duet/input (s/coll-of :duet/instruction))

(s/conform :duet/register "a")


(defn ->model
  [input]
  (->> input
       string/split-lines
       (s/conform :duet/input)))

(defn init-state
  [instructions]
  (into {:position 0}
        (for [{reg :reg} instructions]
          [reg 0])))

(defn move
  [state]
  (update state :position inc))

(defn get-arg
  [state [t v]]
  (if (= :register t)
    (state v)
    v))

(defmulti duet-ops (fn [state instruction registry value]
                     instruction))

(defmethod duet-ops "set"
  [state _ reg value]
  (-> state
      (assoc reg value)
      move))

(defmethod duet-ops "add"
  [state _ reg value]
  (-> state
      (update reg + value)
      move))

(defmethod duet-ops "mul"
  [state _ reg value]
  (-> state
      (update reg * value)
      move))

(defmethod duet-ops "jgz"
  [state _ reg value]
  (let [reg-value (get state reg)
        step (if (pos? reg-value)
               value
               1)]
    (update state :position + step)))

(defmethod duet-ops "mod"
  [state _ reg value]
  (-> state
      (update reg mod value)
      move))

(defmethod duet-ops "snd"
  [state _ reg value]
  (-> state
      (assoc :sound (state reg))
      move))

(defmethod duet-ops "rcv"
  [state _ reg _]
  (let [value (state reg)]
    (if (not= 0 value)
      (-> state
          (assoc reg (state :sound))
          (assoc :received true)
          (move))
      (move state))))

(defn duet-machine
  [{:as state position :position} instructions]
  (if-not (:received state)
    (let [instruction (get instructions position)
          {:keys [op reg arg]} instruction
          value (get-arg state arg)]
      (recur (duet-ops state op reg value) instructions))
    state))

(defn solution
  [input]
  (let [instructions (->model input)
        state (init-state instructions)]
    (duet-machine state instructions)))


(def example "set a 1\nadd a 2\nmul a a\nmod a 5\nsnd a\nset a 0\nrcv a\njgz a -1\nset a 1\njgz a -2")


(comment
  (solution "set a 2\nset b 5\nmul a a\nmod a b\nsnd a")
  (solution "set a 1\nadd a 2\nmul a a\nmod a 5\nsnd a\nset a 0")
  (solution "set a 1\nadd a 2\nmul a a\nmod a 5\nsnd a\nset a 0\nrcv a\njgz a -1")
  (solution "set a 1\nadd a 2\nmul a a\nmod a 5\nsnd a\nset a 0\nrcv a\njgz a -1\nset a 1")
  (solution "set a 1\nadd a 2\nmul a a\nmod a 5\nsnd a\nset a 0\nrcv a\njgz a -1\nset a 1\njgz a -2")
  (solution input-day-18)
  (let [instructions (->model example)
        state (init-state instructions)]
    (duet-machine state instructions)))