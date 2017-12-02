(ns advent-of-code.test-day-2
  (:require [clojure.test :refer :all]
            [advent-of-code.day-2 :refer [checksum day-2-input]]))



(deftest example
  (is (= 18 (checksum "5 1 9 5\n7 5 3\n2 4 6 8"))))

(deftest reponse-1
  (is (= 36174 (checksum day-2-input))))