;; @file    <core.cljs>
;; @author  <wakaranakattari@gmail.com>
;; @info    <core site>
;; @version <1.4>

;; @secstart->@secname <nsrq>
(ns bio-site.core
  (:require [reagent.dom.client :as rdc]
            [bio-site.ui.pages.home :as home]
            [bio-site.ui.pages.about :as about]
            [bio-site.ui.pages.projects :as projects]
            [bio-site.ui.pages.contacts :as contacts]
            [bio-site.ui.pages.writing :as writing]))
;; @secend->@secname   <nsrq>

;; @funcinfo <main site routing>
(defn current-page []
  (let [path (.-pathname js/location)]
    (cond
      (.startsWith path "/about")    [about/page]
      (.startsWith path "/projects") [projects/page]
      (.startsWith path "/contacts") [contacts/page]
      (.startsWith path "/writing")  [writing/page]
      :else [home/page])))

;; @definfo <react root node>
(defonce root (rdc/create-root (js/document.getElementById "app")))

;; @funcinfo <application entry point>
;; @export   true
(defn ^:export init []
  (rdc/render root [current-page]))

;; @funcinfo <hot reload callback>
;; @dev-only true
(defn ^:dev/after-load start []
  (init))
