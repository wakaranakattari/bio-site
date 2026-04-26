(ns bio-site.core
  (:require [reagent.dom.client :as rdc]
            [bio-site.ui.pages.home :as home]
            [bio-site.ui.pages.about :as about]
            [bio-site.ui.pages.projects :as projects]
            [bio-site.ui.pages.contacts :as contacts]))

(defn current-page []
  (let [path (.-pathname js/location)]
    (cond
      (.startsWith path "/about") [about/page]
      (.startsWith path "/projects") [projects/page]
      (.startsWith path "/contacts") [contacts/page]
      :else [home/page])))

(defonce root (rdc/create-root (js/document.getElementById "app")))

(defn ^:export init []
  (rdc/render root [current-page]))

(defn ^:dev/after-load start []
  (init))
