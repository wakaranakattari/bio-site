;; @file    <utils/theme.cljs>
;; @author  <wakaranakattari@gmail.com>
;; @info    <theme switcher logic>
;; @version <1.4>

;; @secstart->@secname <nsrc>
(ns bio-site.utils.theme
  (:require [reagent.core :as r]))
;; @secend->@secname   <nsrc>

;; @secstart->@secname <state>
  ;; @funcinfo <global theme state atom, initialized from localStorage>
(defonce theme-state
  (let [saved (.getItem js/localStorage "theme")]
    (r/atom (or (some-> saved keyword) :dark))))
;; @secend->@secname <state>

;; @funcinfo <apply theme class to <html> element>
(defn apply-theme [theme]
  (let [html (.-documentElement js/document)]
    (if (= theme :light)
      (.add (.-classList html) "light-theme")
      (.remove (.-classList html) "light-theme"))))

;; @funcinfo <toggle theme and persist to localStorage>
(defn toggle! []
  (swap! theme-state #(if (= % :dark) :light :dark))
  (.setItem js/localStorage "theme" (name @theme-state))
  (apply-theme @theme-state))

;; @funcinfo <initialize theme on app startup>
(defn init []
  (apply-theme @theme-state))
