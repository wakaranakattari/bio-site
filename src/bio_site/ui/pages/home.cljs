;; @file    pages/home.cljs
;; @author  <wakaranakattari@gmail.com>
;; @info    <home page>
;; @version 1.3

;; @secstart->@secname <nsrq>
(ns bio-site.ui.pages.home
  (:require [bio-site.ui.components.header :as header]))
;; @secend->@secname   <nsrq>

;; @secstart->@secname <homepage>
;; @funcinfo <home page implementation, displays hero section and navigation buttons>
(defn page []
  [:div

   [header/header]

   ;; @secstart->@secname <homecontainer>
   ;; @info <main container with hero and CTA buttons>
   [:main.home-container

    ;; @secstart->@secname <herosection>
    ;; @info <hero section with greeting and role>
    [:section.hero
     [:h1 "hi, im nikita aka wkrn!"]
     [:h2 "software engineer"]
    ;; @secend->@secname <herosection>

    ;; @secstart->@secname <buttongroup>
    ;; @info <button group for navigation to projects and contacts>
     [:div.button-group
      [:a {:href "/projects" :class "button-projects"}
       "view projects"]
      [:a {:href "/contacts" :class "button-contacts"}
       "contact Me"]]]]])
    ;; @secend->@secname   <buttongroup>
   ;; @secend->@secname   <homecontainer>
;; @secend->@secname   <homepage>
