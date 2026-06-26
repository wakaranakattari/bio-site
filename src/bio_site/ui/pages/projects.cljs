;; @file    <pages/projects.cljs>
;; @author  <wakaranakattari@gmail.com>
;; @info    <projects page, displays github repositories with loading skeleton>
;; @version <1.6>

;; @secstart->@secname <nsrq>
(ns bio-site.ui.pages.projects
  (:require [reagent.core :as r]
            [bio-site.ui.components.header :as header]
            [bio-site.services.github :as github]))
;; @secend->@secname   <nsrq>

;; @secstart->@secname <langcolors>
  ;; @info <language color map, matches github colors>
(def lang-colors
  {"Clojure"       "#e8a0a0"
   "ClojureScript" "#b8d9a0"
   "JavaScript"    "#f5e56b"
   "TypeScript"    "#7fb5d9"
   "Python"        "#8ab8d4"
   "HTML"          "#e8b4a0"
   "CSS"           "#b8a0d4"
   "SCSS"          "#d4a0b8"
   "Shell"         "#b8d4a0"
   "Rust"          "#e8c8a0"
   "Go"            "#80d4d4"
   "Java"          "#d4b880"
   "Perl"          "#80c8d4"
   "Zig"           "#f0c8a0"
   "Ruby"          "#e8a0a8"
   "PHP"           "#a8b8d4"
   "Swift"         "#e8a8a0"
   "Kotlin"        "#c8b0f0"
   "C"             "#b0b0b0"
   "C++"           "#e8b0c0"
   "C#"            "#80c8a0"
   "Dart"          "#80d4cc"
   "Elixir"        "#c8a8d4"
   "Lua"           "#9090c0"
   "Vue"           "#a0d4b0"
   "Svelte"        "#f0b0a0"
   "Crystal"       "#b0b8d4"
   "PowerShell"    "#90a8c8"
   "Emacs Lisp"    "#d0a8d4"
   "Makefile"      "#b0c8a0"
   "CMake"         "#e0b0b0"
   "Dockerfile"    "#a0b8c0"
   "Node.js"       "#90c8a0"
   "GraphQL"       "#e8a8d0"
   "Markdown"      "#90b8d4"
   "YAML"          "#d8a8a0"
   "JSON"          "#b0b0b0"
   "TOML"          "#d4b8a0"
   "XML"           "#90b8c8"})
;; @secend->@secname   <langcolors>

;; @secstart->@secname <langbar>
  ;; @funcinfo <renders language breakdown bar + legend, like github>
(defn lang-bar
  [languages]

  (let [percents (github/calc-lang-percents languages)]
    (when (seq percents)
      [:div.lang-breakdown

       ;; @info <colored bar with segments per language>
       [:div.lang-bar
        (for [{:keys [lang percent]} percents]
          ^{:key lang}
          [:div.lang-segment
           {:style {:width            (str (.toFixed percent 1) "%")
                    :background-color (get lang-colors lang "#888")}}])]

       ;; @info <legend: color dot + name + percent>
       [:div.lang-legend
        (for [{:keys [lang percent]} percents]
          ^{:key lang}
          [:span.lang-item
           [:span.lang-dot {:style {:background-color (get lang-colors lang "#888")}}]
           [:span.lang-name lang]
           [:span.lang-percent (str (.toFixed percent 1) "%")]])]])))
;; @secend->@secname   <langbar>

;; @secstart->@secname <projectspage>
  ;; @funcinfo <projects page, fetches repos from github api, shows skeleton while loading>
(defn page []
  ;; @info <state: list of repositories>
  (let [repos   (r/atom [])
        ;; @info <state: loading flag>
        loading (r/atom true)
        ;; @info <state: visibility flag for fade-in animation>
        visible (r/atom false)
        ;; @info <state: track if initial load is done>
        initialized (r/atom false)]

    ;; @info <fetch repos with requestAnimationFrame for smooth rendering>
    (when-not @initialized
      (reset! initialized true)
      
      ;; @info <use requestAnimationFrame to ensure skeleton renders first>
      (js/requestAnimationFrame
       (fn []
         (github/fetch-repos!
          (fn [data]
            ;; @info <on success: update repos and stop loading>
            (reset! repos data)
            (reset! loading false)
            ;; @info <use requestAnimationFrame for smooth paint>
            (js/requestAnimationFrame
             (fn []
               ;; @info <tiny delay for browser to paint the grid>
               (js/setTimeout #(reset! visible true) 16))))
          (fn [_]
            ;; @info <on error: stop loading, show empty state>
            (reset! loading false)
            (js/requestAnimationFrame
             (fn []
               (js/setTimeout #(reset! visible true) 16))))))))

    (fn []
      [:div
       [header/header]
       [:main.projects-container
        [:h1 "projects"]

        ;; @info <if loading, show skeleton grid>
        (if @loading
          [:div.repos-grid.skeleton-grid
           (doall (for [_ (range 6)]
                    ^{:key (random-uuid)}
                    [:div.skeleton-card
                     [:div.skeleton-title]
                     [:div.skeleton-line]
                     [:div.skeleton-line.short]]))]

          ;; @info <if loaded, show actual repos with fade-in animation>
          [:div.repos-grid {:class (when @visible "repos-visible")}
           (for [repo @repos]
             ^{:key (:name repo)}
             [:a.repo-card {:href (:html_url repo) :target "_blank"}
              [:h3 (:name repo)]
              ;; @info <repo description, fallback to "no description">
              [:p (or (:description repo) "no description")]
              ;; @info <repo stars count>
              [:span.repo-stars "★  " (:stargazers_count repo)]
              ;; @info <language breakdown bar with percentages>
              [lang-bar (:languages repo)]])])]])))
;; @secend->@secname   <projectspage>