(ns server.core-test
  (:require [clojure.test :refer :all]
            [server.core :refer :all]))

(deftest scramble-test
  (testing "Scramble"
    (is (scramble? "rekqodlw" "world"))
    (is (scramble? "cedewaraaossoqqyt" "codewars"))
    (is (not (scramble? "katas" "steak")))

    ;; I assume that if one of the strings empty (or both)
    ;; this is false
    (is (not (scramble? "abcd" "")))
    (is (not (scramble? "" "abcd")))
    (is (not (scramble? "" "")))
    (is (not (scramble? "" "")))

    ;; Check uppercase
    (is (not (scramble? "Abcd" "abcd")))
    (is (not (scramble? "abcd" "Abcd")))

    ;; Check punctuation
    (is (not (scramble? "abcd" "ab:cd")))
    (is (not (scramble? "ab,cd" "abcd")))))
