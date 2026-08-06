;; @file    <components/theme_toggle.cljs>
;; @author  <wakaranakattari@gmail.com>
;; @info    <theme toggle button component>
;; @version <1.5>

;; @secstart->@secname <nsrc>
(ns bio-site.ui.components.theme-toggle
  (:require [bio-site.utils.theme :as theme]))
;; @secend->@secname   <nsrc>

;; @funcinfo <theme toggle button component>
(defn theme-toggle []
  [:button.theme-btn
   {:type "button"
    :aria-label (if (= @theme/theme-state :dark)
                  "switch to light theme"
                  "switch to dark theme")
    :on-click theme/toggle!}
   "d/l"])
