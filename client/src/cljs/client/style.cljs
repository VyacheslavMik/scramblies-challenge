(ns client.style
  (:require [garden.core :refer [css]]))

;; simple style for the form
(def main
  [:style
   (css
    [:body {:font-size "18px"}
     [:input {:font-size "18px"}]
     [:button {:font-size "18px"}]
     [:div {:display "flex"}]
     [:.main {:margin-left "auto"
              :margin-right "auto"
              :margin-top "10px"
              :height "200px"
              :padding "40px"
              :flex-direction "column"
              :box-shadow "6px 9px 33px -1px rgba(0,0,0,0.38)"
              :border-radius "8px"}
      [:.result :.error {:margin-top "20px"}]
      [:.error {:color "red"
                :white-space "pre"}]
      [:.str {:padding "10px 20px"
              :border-radius "8px"
              :border "1px solid lightgray"
              :margin-right "10px"}
       [:&:focus {:outline "none"
                  :border "1px solid #4823d0"}]]
      [:.scramble {:border-radius "8px"
                   :background "rgb(72,35,208)"
                   :padding "10px 20px"
                   :text-transform "uppercase"
                   :color "white"
                   :cursor "pointer"}
       [:&:active {:background "white"
                   :color "black"}]
       [:&:focus {:outline "none"
                  :border "1px solid #4823d0"}]]]])])

