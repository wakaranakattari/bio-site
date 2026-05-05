(ns bio-site.ui.pages.writing
  (:require [bio-site.ui.components.header :as header]))

(defn page []
  [:div

   ;; header call
   [header/header]

   ;; writing section
   [:section.writing

    [:h1 "writing"]

    [:p (str "this will be a page of my personal thoughts and articles, "
             "a kind of personal forum. "
             "i realized that the \"my indignation\" section in \"about me\" "
             "is turning out to be too loud, "
             "and its better to put such things in separate short articles")]]])
