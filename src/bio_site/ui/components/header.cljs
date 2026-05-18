;; @file    <components/header.cljs>
;; @author  <wakaranakattari@gmail.com>
;; @info    <header component>
;; @version <1.4>

;; @secstart->@secname <nsrc>
(ns bio-site.ui.components.header
  (:require [bio-site.ui.components.theme-toggle :refer [theme-toggle]]
            [bio-site.utils.theme :as theme]))

;; @secend->@secname   <nsrc>

;; @secstart->@secname <headerrouting>
  ;; @funcinfo <header routing to highlight the active page>
(defn header []
  (let [path (.-pathname js/location)]
    (fn []

      (let [current-theme @theme/theme-state]
        (theme/apply-theme current-theme))

      [:header.header

       ;; @secstart->@secname <navigation> :: @secinfo <navigation section & basic routing header>
       ;; @info <routing in "/" home page, if active => highlight>
       [:a.nav-item  {:href "/" :class
                      (when (= path "/") "active")} "home"]

      ;; @info <routing in "/about" about page, if active => highlight>
       [:a.nav-item  {:href "/about" :class
                      (when (= path "/about") "active")} "about"]

       ;; @info <routing in "/projects" projects page, if active => highlight>
       [:a.nav-item  {:href "/projects" :class
                      (when (= path "/projects") "active")} "projects"]

       ;; @info <routing in "/writing" writing page, if active => highlight>
       [:a.nav-item  {:href "/writing" :class
                      (when (= path "/writing") "active")} "writing"]

       ;; @info <routing in "/contacts" contacts page, if active => highlight>
       [:a.nav-item  {:href "/contacts" :class
                      (when (= path "/contacts") "active")} "contacts"]

       ;; @info <theme toggle button>
       [theme-toggle]])))
      ;; @secend->@secname    <navigation>
;; @secend->@secname   <headerrouting>
