(ns advent-of-code.test-day-4-part-2
  (:require [clojure.test :refer :all]
            [advent-of-code.day-4-part-2 :refer [check-day-4-part-2]]
            [advent-of-code.day-4 :as day-4]))


(deftest example
  (are [expected phrase]
    (= expected (day-4/check-passphrase check-day-4-part-2 phrase))
    true "abcde fghij"
    false "abcde xyz ecdab"
    true "a ab abc abd abf abj"
    true "iiii oiii ooii oooi oooo"
    false "oiii ioii iioi iiio"))