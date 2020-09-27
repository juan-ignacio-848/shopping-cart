(ns shopping-app-web.subs
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
  ::all
  (fn [db]
    db))

(re-frame/reg-sub
  ::name
  (fn [db]
    (:name db)))

(re-frame/reg-sub
 ::active-panel
 (fn [db _]
   (:active-panel db)))
