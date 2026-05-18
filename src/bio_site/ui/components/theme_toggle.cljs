;; @file    <components/theme_toggle.cljs>
;; @author  <wakaranakattari@gmail.com>
;; @info    <theme toggle button component>
;; @version <1.4>

;; @secstart->@secname <nsrc>
(ns bio-site.ui.components.theme-toggle
  (:require [bio-site.utils.theme :as theme]))
;; @secend->@secname   <nsrc>

;; @funcinfo <theme toggle button component implementation>
(defn theme-toggle []
  [:button.theme-btn
   {:on-click theme/toggle!}
   (if (= @theme/theme-state :dark)
     "d"
     "l")])
