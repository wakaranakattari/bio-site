;; @file    pages/writing.cljs
;; @author  <wakaranakattari@gmail.com>
;; @info    <writing page, personal thoughts and articles>
;; @version 1.3

;; @secstart->@secname <nsrq>
(ns bio-site.ui.pages.writing
  (:require [bio-site.ui.components.header :as header]))
;; @secend->@secname   <nsrq>

;; @secstart->@secname <writingpage>
  ;; @funcinfo <writing page, explains why indignation section moved here>
(defn page []
  [:div

   [header/header]

   ;; @secstart->@secname <writingsection>
   ;; @info <main writing section>
   [:section.writing

    [:h1 "writing"]

    [:p (str "this will be a page of my personal thoughts and articles, "
             "a kind of personal forum. "
             "i realized that the \"my indignation\" section in \"about me\" "
             "is turning out to be too loud, "
             "and its better to put such things in separate short articles")]]])
   ;; @secend->@secname   <writingsection>
;; @secend->@secname   <writingpage>
