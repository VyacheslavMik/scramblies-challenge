(ns client.fetch
  (:require [clojure.string :as str]
            [re-frame.core :as re-frame]))

(defn to-query [params]
  (->> params
       (mapv (fn [[k v]] (str (name k) "=" v)))
       (str/join "&")))

;; This is very simple wrapper around built-in
;; fetch. For larger projects need to check
;; http result status and content-type header.
;; Also need to enchance this wrapper for sending
;; post data and files.
(defn fetch [{:keys [uri params success error]}]
  (let [opts {:headers {"Accept" "application/json"}
              :method "get"}]
    (->
     (js/fetch (str uri (when params (str "?" (to-query params))))
               (clj->js opts))
     (.then (fn [resp]
              (if (.-ok resp)
                (.json resp)
                (re-frame/dispatch (conj error (js->clj resp :keywordize-keys true))))))
     (.then (fn [json]
              (re-frame/dispatch (conj success (-> json
                                                   (js->clj :keywordize-keys true)
                                                   (:body))))))
     (.catch (fn [err]
               (re-frame/dispatch (conj error (.-message err))))))))
