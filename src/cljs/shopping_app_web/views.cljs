(ns shopping-app-web.views
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   [re-com.core :as re-com]
   [shopping-app-web.subs :as subs]
   [shopping-app-web.products :as products]
   ))


;; home

(defn home-title []
  (let [name (re-frame/subscribe [::subs/name])]
    [re-com/title
     :label (str "Hello from " @name ". This is the Home Page.")
     :level :level1]))

(defn link-to-products-page []
  [re-com/hyperlink-href
   :label "go to Products"
   :href "#/products"])

(defn link-to-about-page []
  [re-com/hyperlink-href
   :label "go to About Page"
   :href "#/about"])

(defn home-panel []
  [re-com/v-box
   :gap "1em"
   :children [[home-title]
              [link-to-about-page]
              [link-to-products-page]
              ]])


;; about

(defn about-title []
  [re-com/title
   :label "This is the About Page." :level :level1])

(defn link-to-home-page []
  [:a {:href "#/"} "go to Home Page"])

(defn about-panel []
  [re-com/v-box
   :gap "1em"
   :children [[about-title]
              [link-to-home-page]]])

;; products
(defn shopping-cart []
  [:div
   [:h2 "Shopping Cart"]
   (let [products @(re-frame/subscribe [:shopping-cart/items])]
     [:div
      [:ol
       (for [[{:keys [id name]} qty] products]
         [:li {:key id} (str name " x" qty)])]])
   [:div (pr-str @(re-frame/subscribe [:shopping-cart/db]))]])

(defn products []
  [:div
   [:h2 "Product List"]
   (let [products @(re-frame/subscribe [:products/all])]
     [:div
      [:ol
       (for [[id product] products]
         [:li {:key id}
          (:name product)
          [re-com/button
           :label "Add to cart"
           :on-click #(re-frame/dispatch [:add-to-cart product])]])]])
   [shopping-cart]
   [:div [link-to-home-page]]])



;; main

(defn- panels [panel-name]
  (case panel-name
    :home-panel  [home-panel]
    :about-panel [about-panel]
    :products    [products]
    [:div]))

(defn show-panel [panel-name]
  [panels panel-name])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [::subs/active-panel])]
    [re-com/v-box
     :height "100%"
     :children [[panels @active-panel]]]))

(comment

  (def prods '({:id 1, :name "iPhone 12"} {:id 3, :name "Xiaomi Mi9T"}))
  (def prods2 [{:id 1, :name "iPhone 12"} {:id 3, :name "Xiaomi Mi9T"}])
  (for [{:keys [id name]} prods2]
    name)

  (def mapa {:k1 1 :k2 2})
  (for [[a b] mapa]
    b)
  (def mapa {:1 "uno" :2 "dos"})
  (mapa :1)
  (get mapa :1)
  (:1 mapa)
  )
