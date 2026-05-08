;; @file    pages/contacts.cljs
;; @author  <wakaranakattari@gmail.com>
;; @info    <contacts page>
;; @version 1.3

;; @secstart->@secname <nsrq>
(ns bio-site.ui.pages.contacts
  (:require [bio-site.ui.components.header :as header]))
;; @secend->@secname   <nsrq>

;; @secstart->@secname <contactspage>
 ;; @funcinfo <contacts page implementation, basic contacts => github, telegram, discord>
(defn page []
  [:div

   [header/header]

   ;; @secstart->@secname <contactscontainer>
    ;; @info <main container which contains all the sections & content>
   [:main.contacts-container

    ;; @secstart->@secname <contacts>
     ;; @info <contactinformation>
    [:section.contacts
     [:h1 "contacts"]
     [:p "you can contact me at any convenient time, my private messages are always open for you"]]
    ;; @secend->@secname   <contacts>

    ;; @secstart->@secname <buttons>
     ;; @info <buttons link for socials>
    [:section.buttons]
    [:a {:href "https://github.com/wakaranakattari"} "my github"]
    [:a {:href "https://t.me/wakaranakattari"} "my telegram"]
    [:a {:href "https://discord.com/users/1466086440499941689"} "my discord"]]])
   ;; @secend->@secname    <buttons>
  ;; @secend->@secname    <contactscontainer>
;; @secend->@secname   <contactspage>
