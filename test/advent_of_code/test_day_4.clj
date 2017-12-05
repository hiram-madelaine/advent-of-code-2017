(ns advent-of-code.test-day-4
  (:require [clojure.test :refer :all]
            [advent-of-code.day-4 :as day-4 :refer [check-part-1 input]]))


(deftest example
  (are [expected phrase]
    (= expected (day-4/check-passphrase day-4/day-4-check phrase))
    true "aa bb cc dd ee"
    false "aa bb cc dd aa"
    true "aa bb cc dd aaa"))

(deftest anwser-day-4
  (is (= 455 (count (check-part-1 input)))))
