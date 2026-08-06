;; @file    <core.cljs>
;; @author  <wakaranakattari@gmail.com>
;; @info    <core site>
;; @version <1.7>

;; @secstart->@secname <nsrq>
(ns bio-site.core
  (:require [reagent.core :as r]
            [reagent.dom.client :as rdc]
            [bio-site.router :as router]
            [bio-site.utils.theme :as theme]
            [bio-site.ui.components.header :as header]
            [bio-site.ui.pages.home :as home]
            [bio-site.ui.pages.about :as about]
            [bio-site.ui.pages.projects :as projects]
            [bio-site.ui.pages.contacts :as contacts]
            [bio-site.ui.pages.writing :as writing]
            [bio-site.ui.pages.not-found :as not-found]))
;; @secend->@secname   <nsrq>

;; @funcinfo <main site routing>
(defn current-page []
  (let [path @router/current-path]
    [:div.page-lazy
     (cond
       (or (= path "/") (= path "/index.html")) [home/page]
       (.startsWith path "/about")    [about/page]
       (.startsWith path "/projects") [projects/page]
       (.startsWith path "/contacts") [contacts/page]
       (.startsWith path "/writing")  [writing/page]
       :else                          [not-found/page])]))

;; @funcinfo <page shell, header is static, content remounts on route change>
(defn page-shell []
  (let [path @router/current-path]
    [:div
     [header/header]
     [:div.page-shell {:key path}
      [current-page]]]))

;; @definfo <react root node>
(defonce root (rdc/create-root (js/document.getElementById "app")))

;; @funcinfo <application entry point>
;; @export   true
(defn ^:export init []
  (theme/init)
  (router/init-popstate!)
  (rdc/render root [page-shell]))

;; @funcinfo <hot reload callback>
;; @dev-only true
(defn ^:dev/after-load start []
  (init))
