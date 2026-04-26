(ns bio-site.ui.pages.projects
  (:require [reagent.core :as r]
            [bio-site.ui.components.header :as header]
            [bio-site.services.github :as github]))

(defn page []
  (let [repos (r/atom [])
        loading (r/atom true)]

    (github/fetch-repos!
     (fn [data]
       (reset! repos data)
       (reset! loading false))
     (fn [_]
       (reset! loading false)))

    (fn []
      [:div
       [header/header]
       [:main.projects-container
        [:h1 "projects"]

        (if @loading
          [:div.repos-grid.sekeleton-grid
           (doall (for [_ (range 6)]
                    ^{:key (random-uuid)}
                    [:div.skeleton-card
                     [:div.skeleton-title]
                     [:div.skeleton-line]
                     [:div.skelet-line.short]]))]
          [:div.repos-grid
           (for [repo @repos]
             ^{:key (:name repo)}
             [:a.repo-card {:href (:html_url repo) :target "_blank"}
              [:h3 (:name repo)]
              [:p (or (:description repo) "no description")]
              [:span "★  " (:stargazers_count repo)]
              (if (:language repo)
                [:span " · " (:language repo)]
                [:span " · no languages or empty project"])])])]])))
