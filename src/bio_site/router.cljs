;; @file    <router.cljs>
;; @author  <wakaranakattari@gmail.com>
;; @info    <client-side router>
;; @version <1.0>

;; @secstart->@secname <nsrq>
(ns bio-site.router
  (:require [reagent.core :as r]))
;; @secend->@secname   <nsrq>

;; @definfo <reactive current path atom>
(defonce current-path (r/atom (.-pathname js/location)))

;; @funcinfo <navigate without page reload via pushState>
(defn navigate! [path]
  (.pushState js/history nil "" path)
  (reset! current-path path))

;; @funcinfo <handle browser back/forward buttons>
(defn init-popstate! []
  (.addEventListener js/window "popstate"
    (fn [_] (reset! current-path (.-pathname js/location)))))