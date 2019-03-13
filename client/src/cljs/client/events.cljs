(ns client.events
  (:require
   [re-frame.core :as re-frame]
   [clojure.string :as str]
   [client.db :as db]
   [client.dict :as dict]
   [client.fetch :as fetch]))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

(re-frame/reg-event-db
 ::set-str1
 (fn [db [_ value]]
   (assoc db :str1 value)))

;; this is code duplication, but it not worth
;; to extract function for this, because it
;; may confuse people. When code base will
;; be increasing we need to abstract away
;; such things.
(re-frame/reg-event-db
 ::set-str2
 (fn [db [_ value]]
   (assoc db :str2 value)))

(re-frame/reg-event-db
 ::on-success
 (fn [db [_ data]]
   (assoc db :result (dict/results (:result data)))))

(re-frame/reg-event-db
 ::on-error
 (fn [db [_ error]]
   (assoc db :error error)))

(defn invalid? [db path]
  (not (re-find #"^[a-z]+$" (or (path db) ""))))

;; validate before sending request to the server
(re-frame/reg-event-fx
 ::validate
 (fn [{db :db} _]
   (let [error (cond-> ""
                 (invalid? db :str1) (str (dict/errors :first-string))
                 (invalid? db :str2) (str (dict/errors :second-string)))]
     (if (str/blank? error)
       {::fetch {:uri "/scramble"
                 :params {:str1 (:str1 db)
                          :str2 (:str2 db)}
                 :success [::on-success]
                 :error   [::on-error]}}
       {:db (assoc db :error error)}))))

(re-frame/reg-event-fx
 ::scramble
 (fn [{db :db} _]
   {:db (dissoc db :result :error)
    :dispatch [::validate]}))

(re-frame/reg-fx
 ::fetch
 (fn [opts]
   (fetch/fetch opts)))
