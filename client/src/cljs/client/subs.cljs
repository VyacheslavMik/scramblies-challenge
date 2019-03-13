(ns client.subs
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::str1
 (fn [db]
   (:str1 db)))

(re-frame/reg-sub
 ::str2
 (fn [db]
   (:str2 db)))

(re-frame/reg-sub
 ::error
 (fn [db]
   (:error db)))

(re-frame/reg-sub
 ::result
 (fn [db]
   (:result db)))
