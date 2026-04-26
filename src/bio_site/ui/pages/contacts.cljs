(ns bio-site.ui.pages.contacts
  (:require [bio-site.ui.components.header :as header]))

(defn page []
  [:div
   [header/header]
   [:main.contacts-container
    [:section.contacts
     [:h1 "contacts"]
     [:p "you can contact me at any convenient time, my private messages are always open for you"]]

    [:section.buttons]
    [:a {:href "https://github.com/wakaranakattari"} "my github"]
    [:a {:href "https://t.me/wakaranakattari"} "my telegram"]
    [:a {:href "https://discord.com/users/1466086440499941689"} "my discord"]]])
