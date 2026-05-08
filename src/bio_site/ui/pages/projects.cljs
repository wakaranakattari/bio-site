;; @file    pages/projects.cljs
;; @author  <wakaranakattari@gmail.com>
;; @info    <projects page, displays github repositories with loading skeleton>
;; @version 1.3

;; @secstart->@secname <nsrq>
(ns bio-site.ui.pages.projects
  (:require [reagent.core :as r]
            [bio-site.ui.components.header :as header]
            [bio-site.services.github :as github]))
;; @secend->@secname   <nsrq>

;; @secstart->@secname <projectspage>
  ;; @funcinfo <projects page, fetches repos from github api, shows skeleton while loading>
(defn page []
  ;; @info <state: list of repositories>
  (let [repos (r/atom [])
        ;; @info <state: loading flag>
        loading (r/atom true)]

    ;; @info <fetch repos on component mount>
    (github/fetch-repos!
     (fn [data]
       ;; @info <on success: update repos and stop loading>
       (reset! repos data)
       (reset! loading false))
     (fn [_]
       ;; @info <on error: stop loading, show empty state>
       (reset! loading false)))

    (fn []
      [:div
       [header/header]
       [:main.projects-container
        [:h1 "projects"]

        ;; @info <if loading, show skeleton grid>
        (if @loading
          [:div.repos-grid.sekeleton-grid
           (doall (for [_ (range 6)]
                    ^{:key (random-uuid)}
                    [:div.skeleton-card
                     [:div.skeleton-title]
                     [:div.skeleton-line]
                     [:div.skelet-line.short]]))]
          ;; @info <if loaded, show actual repos>
          [:div.repos-grid
           (for [repo @repos]
             ^{:key (:name repo)}
             [:a.repo-card {:href (:html_url repo) :target "_blank"}
              [:h3 (:name repo)]
              ;; @info <repo description, fallback to "no description">
              [:p (or (:description repo) "no description")]
              ;; @info <repo stars count>
              [:span "★  " (:stargazers_count repo)]
              ;; @info <repo language, if present>
              (if (:language repo)
                [:span " · " (:language repo)]
                [:span " · no languages or empty project"])])])]])))
;; @secend->@secname   <projectspage>
