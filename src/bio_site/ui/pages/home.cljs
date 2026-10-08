;; @file    <pages/home.cljs>
;; @author  <wakaranakattari@gmail.com>
;; @info    <home page>
;; @version <1.8>

;; @secstart->@secname <nsrq>
(ns bio-site.ui.pages.home
  (:require [reagent.core :as r]
            [bio-site.router :as router]
            [bio-site.services.github :as github]
            [bio-site.ui.components.typing :refer [typing-roles]]
            [bio-site.utils.reveal :refer [reveal-props]]))
;; @secend->@secname   <nsrq>

;; @secstart->@secname <navbutton>
  ;; @funcinfo <spa nav button with label and arrow>
(defn- nav-button [href label]
  [:a.button-link {:href href
                   :on-click (fn [e]
                               (.preventDefault e)
                               (router/navigate! href))}
   [:span.button-label label]
   [:span.button-arrow "→"]])
;; @secend->@secname   <navbutton>

;; @secstart->@secname <herostats>
  ;; @funcinfo <live github stats strip, renders nothing until loaded>
(defn- hero-stats [stats]
  (when @stats
    [:div.hero-stats (reveal-props 250)
     [:span.hero-stat "★ " (:stars @stats)]
     [:span.hero-stat-dot "·"]
     [:span.hero-stat (:repos @stats) " repos on github"]]))
;; @secend->@secname   <herostats>

;; @secstart->@secname <homepage>
;; @funcinfo <home page implementation, displays hero section and navigation buttons>
(defn page []
  (let [stats (r/atom nil)]
    ;; @info <fetch stats once for the hero strip>
    (github/fetch-stats! #(reset! stats %))
    (fn []
      [:div

       ;; @secstart->@secname <homecontainer>
       ;; @info <main container with hero and CTA buttons>
       [:main.home-container

        ;; @secstart->@secname <herosection>
        ;; @info <hero section with greeting, typing roles and live stats>
        [:section.hero (reveal-props 0)
         [:h1 "hi, im " [:span.hero-name "nikita aka laowai"] "!"]
         [:h2 [typing-roles ["backend engineer" "rust & go" "haskell · r&d"]]]
         [:p.hero-intro "a personal corner of the internet - "
          "thoughts, code and experiments"]
         [hero-stats stats]]
        ;; @secend->@secname <herosection>

        ;; @secstart->@secname <buttongroup>
        ;; @info <button group for navigation to projects and contacts>
        [:div.button-group (reveal-props 150)
         [nav-button "/projects" "view projects"]
         [nav-button "/contacts" "contact me"]]]])))
;; @secend->@secname   <homecontainer>
;; @secend->@secname   <homepage>
