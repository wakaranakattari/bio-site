(ns bio-site.ui.pages.home
  (:require [bio-site.ui.components.header :as header]))

(defn page []
  [:div
   [header/header]

   [:main.home-container
    [:section.hero
     [:h1 "hi, im nikita!"]
     [:h2 "software engineer"]
     [:div.button-group
      [:a {:href "/projects" :class "button-projects"}
       "view projects"]
      [:a {:href "/contacts" :class "button-contacts"}
       "contact Me"]]]]])
