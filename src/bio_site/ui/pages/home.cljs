(ns bio-site.ui.pages.home
  (:require [bio-site.ui.components.header :as header]))

(defn page []
  [:div

   ;;header call
   [header/header]

   ;; home page container
   [:main.home-container

    ;; hero section
    [:section.hero
     [:h1 "hi, im nikita aka wkrn!"]
     [:h2 "software engineer"]

     ;; buttons group
     [:div.button-group
      [:a {:href "/projects" :class "button-projects"}
       "view projects"]
      [:a {:href "/contacts" :class "button-contacts"}
       "contact Me"]]]]])
