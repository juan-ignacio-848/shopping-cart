(ns shopping-app-web.products
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-event-db
  ::init-products
  (fn [db]
    (assoc db :products {1 {:id 1 :name "iPhone 12"}
                         2 {:id 2 :name "Samsung Galaxy S20"}
                         3 {:id 3 :name "Xiaomi Mi9T"}})))

(re-frame/dispatch-sync [::init-products])

(re-frame/reg-sub
  :products/all
  (fn [db]
    (:products db)))

#_(re-frame/reg-sub
    :shopping-cart/items
    (fn [db]
      (mapv (:products db) (get-in db [:cart :items]))))

#_(re-frame/reg-sub
    :shopping-cart/items
    (fn [db]
      (let [freq (frequencies (get-in db [:cart :items]))]
        (mapv (fn [[id qty]]
                [((:products db) id) qty])
              freq))))

(re-frame/reg-sub
  :shopping-cart/items
  (fn [db]
    (println db)
    (let [items (get-in db [:cart :items])]
      (mapv (fn [[id qty]]
              [((:products db) id) qty])
            items))))

(re-frame/reg-sub
  :shopping-cart/db
  (fn [db]
    (:cart db)))

#_(re-frame/reg-event-db
    :add-to-cart
    (fn [db [_ product]]
      (update-in db [:cart :items] (fnil conj []) (:id product))))

(re-frame/reg-event-db
  :add-to-cart
  (fn [db [_ product]]
    (update-in db [:cart :items (:id product)] (fnil inc 0))))

(comment

  )
