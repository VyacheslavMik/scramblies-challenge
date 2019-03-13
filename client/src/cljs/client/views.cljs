(ns client.views
  (:require
   [re-frame.core :as re-frame]
   [client.subs :as subs]
   [client.events :as events]
   [client.style :as style]
   ))

;; Actually here we need to put error messages
;; related with this input, but for simplicity
;; I put it in one place below all inputs.
;; Also this is redundant for this project
;; to create such component.:)
(defn string-input [placeholder event sub]
  [:input.str
   {:value @(re-frame/subscribe [sub])
    :placeholder placeholder
    :on-change (fn [ev]
                 (re-frame/dispatch [event (.. ev -target -value)]))}])

;; for this simple form it's ok to use this approach
;; but for more complex forms it will be better to
;; use something like this https://github.com/HealthSamurai/zenform
;; or this https://github.com/HealthSamurai/re-form
;; for more declarative form description
(defn main-panel []
  (let [error  @(re-frame/subscribe [::subs/error])
        result @(re-frame/subscribe [::subs/result])]
    [:div.main
     style/main
     [:div.inputs
      [string-input "Enter first string"  ::events/set-str1 ::subs/str1]
      [string-input "Enter second string" ::events/set-str2 ::subs/str2]
      [:button.scramble
       {:on-click (fn [_]
                    (re-frame/dispatch [::events/scramble]))}
       "Scramble"]]
     (if error
       [:div.error error]
       [:div.result result])]))
