;; @file    <core.cljs>
;; @author  <wakaranakattari@gmail.com>
;; @info    <core site>
;; @version <1.6>

;; @secstart->@secname <nsrq>
(ns bio-site.core
  (:require [reagent.core :as r]
            [reagent.dom.client :as rdc]
            [shadow.lazy :as lazy]
            [bio-site.router :as router]
            [bio-site.utils.theme :as theme]
            [bio-site.ui.components.header :as header]
            [bio-site.ui.pages.home :as home]
            [bio-site.ui.pages.not-found :as not-found]))
;; @secend->@secname   <nsrq>

;; @secstart->@secname <lazypages>
  ;; @info <lazy references to page components>
(def about-lazy    (lazy/loadable bio-site.ui.pages.about/page))
(def projects-lazy (lazy/loadable bio-site.ui.pages.projects/page))
(def contacts-lazy (lazy/loadable bio-site.ui.pages.contacts/page))
(def writing-lazy  (lazy/loadable bio-site.ui.pages.writing/page))

  ;; @funcinfo <cache of loaded page components>
(defonce loaded-pages (r/atom {}))

  ;; @funcinfo <load lazy page and cache it>
(defn- load-page!
  [page-key page-lazy]
  (lazy/load page-lazy
             (fn [page]
               (swap! loaded-pages assoc page-key page))
             (fn [err]
               (js/console.warn "failed to load page" (name page-key) err)
               (swap! loaded-pages dissoc page-key))))

  ;; @funcinfo <render lazy page, loads on demand>
(defn- lazy-page
  [page-key page-lazy]
  (let [page (get @loaded-pages page-key)]
    (when (nil? page)
      (load-page! page-key page-lazy))
    (if page
      [page]
      [:div])))
;; @secend->@secname   <lazypages>

;; @funcinfo <main site routing>
(defn current-page []
  (let [path @router/current-path]
    (cond
      (or (= path "/") (= path "/index.html")) [home/page]
      (.startsWith path "/about")    [lazy-page :about about-lazy]
      (.startsWith path "/projects") [lazy-page :projects projects-lazy]
      (.startsWith path "/contacts") [lazy-page :contacts contacts-lazy]
      (.startsWith path "/writing")  [lazy-page :writing writing-lazy]
      :else                          [not-found/page])))

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
