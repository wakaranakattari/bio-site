;; @file    <pages/projects.cljs>
;; @author  <wakaranakattari@gmail.com>
;; @info    <projects page, displays github repositories>
;; @version <1.8>

;; @secstart->@secname <nsrq>
(ns bio-site.ui.pages.projects
  (:require [reagent.core :as r]
            [bio-site.ui.components.repo-card :as repo-card]
            [bio-site.services.github :as github]))
;; @secend->@secname   <nsrq>

;; @secstart->@secname <langcolors>
  ;; @info <language color map, matches github colors>
(def lang-colors
  {"Haskell"       "#9e98b1"
   "OCaml"         "#d5a87c"
   "Clojure"       "#e8a0a0"
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
   "XML"           "#90b8c8"
   "Scala"         "#c68a91"
   "Nix"           "#9393e0"
   "Nim"           "#d9c27b"
   "F#"            "#bc89db"
   "Racket"        "#8f9dbe"
   "Common Lisp"   "#91bfaf"
   "Scheme"        "#8495d2"
   "Erlang"        "#c18fb4"
   "Gleam"         "#e29cd8"
   "Elm"           "#99bcc6"
   "PureScript"    "#868ea0"
   "Julia"         "#b4a0be"
   "Haxe"          "#d7aa74"
   "D"             "#bf999b"
   "Dune"          "#c1937d"
   "Ada"           "#7ad8af"
   "Fortran"       "#9792be"
   "Prolog"        "#b78391"
   "Agda"          "#8aa4af"
   "R"             "#84aed0"
   "Vim Script"    "#7bc697"
   "Assembly"      "#c1a473"
   "Batchfile"     "#c1d586"
   "Standard ML"   "#cd939d"
   "Objective-C"   "#88a9dc"
   "Smalltalk"     "#bbca65"
   "Groovy"        "#93b3bf"
   "Hack"          "#ababab"
   "Less"          "#7c93b6"
   "CoffeeScript"  "#8099ba"
   "Astro"         "#d99b7b"
   "Solidity"      "#bca194"
   "Hy"            "#a3adbb"
   "GDScript"      "#8ba0b1"
   "Raku"          "#7a7ad8"
   "Tape"          "#888"})
;; @secend->@secname   <langcolors>

;; @secstart->@secname <langbar>
  ;; @funcinfo <renders language breakdown bar + legend, like github>
(defn lang-bar
  [languages]
  (let [percents (github/calc-lang-percents languages)]
    (when (seq percents)
      [:div.lang-breakdown

       [:div.lang-bar
        (for [{:keys [lang percent]} percents]
          ^{:key lang}
          [:div.lang-segment
           {:style {:width            (str (.toFixed percent 1) "%")
                    :background-color (get lang-colors lang "#888")}}])]

       [:div.lang-legend
        (for [{:keys [lang percent]} percents]
          ^{:key lang}
          [:span.lang-item
           [:span.lang-dot {:style {:background-color (get lang-colors lang "#888")}}]
           [:span.lang-name lang]
           [:span.lang-percent (str (.toFixed percent 1) "%")]])]])))
;; @secend->@secname   <langbar>

;; @secstart->@secname <projectspage>
  ;; @funcinfo <projects page, fetches repos from github api, fades in on load>
(defn page []
  (let [repos   (r/atom [])
        visible (r/atom false)
        error   (r/atom nil)]

    ;; @funcinfo <fetch repos, resets state and can be reused for retry>
    (letfn [(fetch! []
              (reset! error nil)
              (reset! visible false)
              (github/fetch-repos!
               (fn [data]
                 (reset! repos data)
                 (js/requestAnimationFrame
                  (fn []
                    (js/setTimeout #(reset! visible true) 16))))
               (fn [msg]
                 (reset! error msg)
                 (js/requestAnimationFrame
                  (fn []
                    (js/setTimeout #(reset! visible true) 16))))))]

      (fetch!)

      (fn []
        [:div
         [:main.projects-container
          [:h1 "projects"]
          [:p.projects-intro "some things i built and keep on github"]

          (if @error
            [:div.projects-status.repos-visible
             [:p "failed to load repositories"]
             [:p.projects-error-msg @error]
             [:button.retry-btn {:type "button" :on-click fetch!} "try again"]]

            (if (and @visible (empty? @repos))
              [:div.projects-status.repos-visible
               [:p "no repositories yet"]]

              [:div.repos-grid {:class (when @visible "repos-visible")}
               (for [repo @repos]
                 ^{:key (:name repo)}
                 [repo-card/repo-card (assoc repo :lang-bar-fn lang-bar)])]))]]))))
;; @secend->@secname   <projectspage>