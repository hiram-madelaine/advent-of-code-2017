(ns advent-of-code.test-day-1
  (:require [clojure.test :refer :all]
            [advent-of-code.day-1 :refer [captcha inverse-captcha]]
            [advent-of-code.day-1-golf :refer [golf]]))


(deftest example
  (are [result digits]
    (= result (inverse-captcha digits))
    3 "1122"
    4 "1111"
    0 "1234"
    9 "91212129"))


(deftest problem
  (is (= 1341
         (inverse-captcha captcha))))


(deftest example'
  (are [result digits]
    (= result (golf digits))
    3 "1122"
    4 "1111"
    0 "1234"
    9 "91212129"))

(deftest problem'
  (is (= 1341
         (golf captcha))))

