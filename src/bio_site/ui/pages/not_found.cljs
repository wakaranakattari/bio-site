;; @file    <pages/not_found.cljs>
;; @author  <wakaranakattari@gmail.com>
;; @info    <404 page>
;; @version <1.0>

;; @secstart->@secname <nsrq>
(ns bio-site.ui.pages.not-found
  (:require [bio-site.router :as router]))
;; @secend->@secname   <nsrq>

;; @secstart->@secname <notfoundpage>
  ;; @funcinfo <404 page, shown for unknown routes>
(defn page []
  [:div.page-lazy

   [:main.not-found-container
    [:h1 "404"]
    [:p "page not found"]
    [:a.button-home
     {:href "/"
      :on-click (fn [e]
                  (.preventDefault e)
                  (router/navigate! "/"))}
     "go home"]]])
;; @secend->@secname   <notfoundpage>
