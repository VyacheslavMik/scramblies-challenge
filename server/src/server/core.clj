(ns server.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.util.response :refer [response content-type redirect]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.content-type :refer [wrap-content-type]]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.not-modified :refer [wrap-not-modified]]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.json :refer [wrap-json-response]]
            [clojure.java.io :as io]
            [clojure.string :as str])
  (:gen-class))

;; convert string to map where char is
;; the key and number of the chars in
;; string is the value
(defn string-to-map [s]
  (->> s
       (group-by identity)
       (map (fn [[k v]]
              [k (count v)]))
       (into {})))

;; here we validate input string
(defn valid? [s]
  (re-find #"^[a-z]+$" s))

;; this is not fastest algorithm
;; but I think it give decent performance
(defn scramble? [str1 str2]
  (cond
    ;; if first string have less characters
    ;; then second it obvious that result is false
    (> (count str2) (count str1))
    false

    (and (valid? str1) (valid? str2))
    (let [map1 (string-to-map str1)]
      (every? (fn [[k v]]
                (when-let [char-count (get map1 k)]
                  (>= (- char-count v) 0)))
              (string-to-map str2)))

    :else
    false))

;; for such small project this is enough
;; but for larger projects this need to
;; be abstracted away. And possibly invent
;; some DSL with parameters validation
;; and function composition
(defroutes app-routes
  (GET "/"         []
       (redirect "index.html"))
  (GET "/scramble" [str1 str2]
       (-> {:body {:result (scramble? str1 str2)}}
           (response)
           (content-type "application/json")))
  (route/not-found "<h1>Page not found</h1>"))

(def app (-> app-routes
             (wrap-reload)
             (wrap-defaults site-defaults)
             (wrap-resource "public")
             (wrap-content-type)
             (wrap-not-modified)
             (wrap-json-response)))

(defonce start (future (jetty/run-jetty app {:port 2019})))
