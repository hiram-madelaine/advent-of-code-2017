(ns advent-of-code-2016.day-5
  "
  The eight-character password for the door is generated one character at a time
  by finding the MD5 hash of some Door ID (your puzzle input)
  and an increasing integer index (starting with 0).

  A hash indicates the next character in the password
  if its hexadecimal representation starts with five zeroes.
  If it does, the sixth character in the hash is the next character of the password.

  For example, if the Door ID is abc:

  The first index which produces a hash that starts with five zeroes is 3231929,
  which we find by hashing abc3231929; the sixth character of the hash, and thus the first character of the password, is 1.
  5017308 produces the next interesting hash, which starts with 000008f82..., so the second character of the password is 8.
  The third time a hash starts with five zeroes is for abc5278568, discovering the character f.
  In this example, after continuing this search a total of eight times, the password is 18f47a30.

  Given the actual Door ID, what is the password?

  Your puzzle input is wtnhxymk.

  --- Part Two ---
  As the door slides open, you are presented with a second door that uses a slightly more inspired security mechanism. Clearly unimpressed by the last version (in what movie is the password decrypted in order?!), the Easter Bunny engineers have worked out a better solution.

  Instead of simply filling in the password from left to right, the hash now also indicates the position within the password to fill. You still look for hashes that begin with five zeroes; however, now, the sixth character represents the position (0-7), and the seventh character is the character to put in that position.

  A hash result of 000001f means that f is the second character in the password. Use only the first result for each position, and ignore invalid positions.

  For example, if the Door ID is abc:

  The first interesting hash is from abc3231929, which produces 0000015...;
  so, 5 goes in position 1: _5______.

  In the previous method, 5017308 produced an interesting hash;
  however, it is ignored, because it specifies an invalid position (8).

  The second interesting hash is at index 5357525, which produces 000004e...;
  so, e goes in position 4: _5__e___.

  You almost choke on your popcorn as the final character falls into place, producing the password 05ace8e3.

  Given the actual Door ID and this new method, what is the password? Be extra proud of your solution if it uses a cinematic decrypting animation.

  Your puzzle input is still wtnhxymk.
  "
  (:require [pandect.algo.md5 :refer [md5]]
            [clojure.string :as str]))

(def example "abc")

(defn find-nonce
  [difficulty input]
  (filter #(-> (str input %) md5 (.startsWith difficulty)) (range)))

(defn solution-1
  [input]
  (let [nonces (take 8 (find-nonce "00000" input))
        hashes (map #(md5 (str input %)) nonces)
        digits (map #(nth % 5) hashes)]
    digits))

(defn solution-2
  [input]
  (let [nonces (find-nonce "00000" input)
        hashes (map #(md5 (str input %)) nonces)
        digits (map #(.substring % 5 7) hashes)
        digits' (filter (fn [digit]
                          (let [p (str (first digit))]
                            (and (number? (read-string p))
                                 (<= 0 (Integer/parseInt (str p)) 7)))) digits)]
    digits'))


(let [input "wtnhxymk"
      result (atom {})]
  (->> input
       (find-nonce "00000")
       (map #(md5 (str input %)))
       (map #(.substring % 5 7))
       (filter (fn [digit]
                 (let [position (str (first digit))]
                   (prn position)
                   (and (number? (read-string position))
                        (<= 0 (Integer/parseInt (str position)) 7)))))
       (map (fn [[p d]]
              [(Integer/parseInt (str p)) d]))
       (take-while (fn [[p d]]
                     (swap! result #(merge {p d} %))
                     (prn @result)
                     (not (= (set (range 8)) (set (keys @result))))))))

(comp
  (reduce
    (fn [acc [p d]]
      (merge {p d} acc))
    {})
  sort
  (map val)
  str/join)

{2 \f, 4 \f, 1 \5, 7 \4, 5 \3, 6 \a, 3 \7}

{2 \7, 1 \3, 7 \c, 6 \f, 3 \e, 5 \0, 4 \6}

(str/join (map val (into (sorted-map) {0 \4, 2 \7, 1 \3, 7 \c, 6 \f, 3 \e, 5 \0, 4 \6})))

(into {} '([2 \7] [4 \6] [1 \3] [4 \0] [7 \c] [7 \0] [5 \0] [6 \f] [2 \d] [4 \f]))

(sort (reduce
       (fn [acc [p d]]
         (merge {p d} acc))
       {}
       [[2 \7]
        [4 \6]
        [1 \3]
        [4 \0]
        [7 \c]
        [7 \0]
        [5 \0]
        [6 \f]
        [2 \d]
        [4 \f]
        [2 \f]
        [2 \2]
        [6 \f]
        [3 \e]
        [5 \3]
        [1 \9]
        [3 \7]
        [6 \a]
        [7 \4]
        [1 \5]
        [2 \f]]))


(comment

  (find-nonce "00000" "abc" 8)

  (md5 (str 3422296 "abc"))

  (solution-1 "abc")

  (solution-2 "abc")

  (solution-2 "wtnhxymk")


  (clojure.string/join '(\2 \4 \1 \4 \b \c \7 \7)))
