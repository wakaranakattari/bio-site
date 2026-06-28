;; @file    <components/header.cljs>
;; @author  <wakaranakattari@gmail.com>
;; @info    <header component>
;; @version <1.5>

;; @secstart->@secname <nsrc>
(ns bio-site.ui.components.header
  (:require [bio-site.ui.components.theme-toggle :refer [theme-toggle]]
            [bio-site.utils.theme :as theme]
            [bio-site.router :as router]))
;; @secend->@secname   <nsrc>

;; @funcinfo <nav link with spa navigation and active highlight>
(defn- nav-link [href label]
  [:a.nav-item
   {:href href
    :class (when (= @router/current-path href) "active")
    :on-click (fn [e]
                (.preventDefault e)
                (router/navigate! href))}
   label])

;; @secstart->@secname <headerrouting>
  ;; @funcinfo <header routing to highlight the active page>
(defn header []
  (fn []

    (let [current-theme @theme/theme-state]
      (theme/apply-theme current-theme))

    [:header.header

     ;; @secstart->@secname <navigation> :: @secinfo <navigation section & basic routing header>
     ;; @info <routing in "/" home page, if active => highlight>
     [nav-link "/" "home"]

     ;; @info <routing in "/about" about page, if active => highlight>
     [nav-link "/about" "about"]

     ;; @info <routing in "/projects" projects page, if active => highlight>
     [nav-link "/projects" "projects"]

     ;; @info <routing in "/writing" writing page, if active => highlight>
     [nav-link "/writing" "writing"]

     ;; @info <routing in "/contacts" contacts page, if active => highlight>
     [nav-link "/contacts" "contacts"]

     ;; @info <theme toggle button>
     [theme-toggle]]))
     ;; @secend->@secname    <navigation>
;; @secend->@secname   <headerrouting>