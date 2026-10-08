;; @file    <pages/projects.cljs>
;; @author  <wakaranakattari@gmail.com>
;; @info    <projects page, displays github repositories>
;; @version <2.5>

;; @secstart->@secname <nsrq>
(ns bio-site.ui.pages.projects
  (:require [clojure.string :as str]
            [reagent.core :as r]
            [bio-site.ui.components.repo-card :as repo-card]
            [bio-site.services.github :as github]
            [bio-site.utils.theme :as theme]
            [bio-site.utils.reveal :refer [reveal-props]]))
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

;; @secstart->@secname <langicon>
  ;; @funcinfo <verified simpleicons slugs, generic transform as fallback>
(def lang-slugs
  {"Haskell" "haskell" "Rust" "rust" "Go" "go" "OCaml" "ocaml"
   "Elixir" "elixir" "Erlang" "erlang" "Clojure" "clojure"
   "JavaScript" "javascript" "TypeScript" "typescript" "Python" "python"
   "HTML" "html5" "CSS" "css" "SCSS" "sass" "Sass" "sass" "Shell" "gnubash"
   "Java" "openjdk" "Perl" "perl" "Zig" "zig" "Ruby" "ruby" "PHP" "php"
   "Swift" "swift" "Kotlin" "kotlin" "C" "c" "C++" "cplusplus" "C#" "dotnet"
   "Dart" "dart" "Lua" "lua" "Vue" "vuedotjs" "Svelte" "svelte"
   "Crystal" "crystal" "Dockerfile" "docker" "Emacs Lisp" "gnuemacs"
   "Scala" "scala" "Nix" "nixos" "Nim" "nim" "F#" "fsharp" "Racket" "racket"
   "Common Lisp" "commonlisp" "Gleam" "gleam" "Elm" "elm"
   "PureScript" "purescript" "Julia" "julia" "Haxe" "haxe" "D" "d"
   "Ada" "ada" "Fortran" "fortran" "R" "r" "Vim Script" "vim"
   "Groovy" "apachegroovy" "Less" "less" "CoffeeScript" "coffeescript"
   "Astro" "astro" "Solidity" "solidity" "GraphQL" "graphql"
   "Markdown" "markdown" "YAML" "yaml" "JSON" "json" "TOML" "toml"
   "XML" "xml" "Node.js" "nodedotjs" "CMake" "cmake"
   "Jupyter Notebook" "jupyter" "Nunjucks" "nunjucks" "Stylus" "stylus"
   "Handlebars" "handlebarsdotjs" "EJS" "ejs" "PostCSS" "postcss"
   "Babel" "babel" "ESLint" "eslint" "Vite" "vite" "Webpack" "webpack"
   "Gulp" "gulp" "Gradle" "gradle" "Maven" "apachemaven" "Jinja" "jinja"
   "Pug" "pug" "V" "v" "Odin" "odin" "Red" "red" "Ring" "ring"
   "GDScript" "godotengine"})

  ;; @funcinfo <language logo via simpleicons cdn, hides itself when missing>
(defn- lang-slug [lang]
  (or (get lang-slugs lang)
      (str/replace (str/lower-case (or lang "")) #"[^a-z0-9]" "")))

(defn lang-icon [lang]
  [:img.lang-icon {:src      (str "https://cdn.simpleicons.org/" (lang-slug lang) "/"
                                  (if (= @theme/theme-state :dark) "A8C89F" "4a7c59"))
                   :alt      ""
                   :loading  "lazy"
                   :on-error (fn [e]
                               (set! (.. e -target -style -display) "none"))}])
;; @secend->@secname   <langicon>

;; @secstart->@secname <langfilter>
  ;; @funcinfo <language filter chips, nil selected means all>
(defn filter-chips [languages selected on-pick]
  [:div.filter-chips
   [:button.filter-chip {:type     "button"
                         :class    (when (nil? @selected) "active")
                         :on-click (fn [_] (on-pick nil))}
    "all"]
   (doall
    (for [lang languages]
      ^{:key lang}
      [:button.filter-chip {:type     "button"
                            :class    (when (= lang @selected) "active")
                            :on-click (fn [_] (on-pick lang))}
       [lang-icon lang]
       lang]))])
;; @secend->@secname   <langfilter>

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
           [lang-icon lang]
           [:span.lang-name lang]
           [:span.lang-percent (str (.toFixed percent 1) "%")]])]])))
;; @secend->@secname   <langbar>

;; @secstart->@secname <projectspage>
  ;; @funcinfo <projects page, fetches repos from github api, fades in on load>
(defn page []
  (let [repos   (r/atom [])
        visible (r/atom false)
        error   (r/atom nil)
        lang-filter (r/atom nil)
        search (r/atom "")]

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
        (let [all       @repos
              top-langs (->> all (keep :language) (frequencies) (sort-by val >) (map key) (take 6) (vec))
              current   @lang-filter
              q         (str/lower-case @search)
              matches-q (fn [r]
                          (or (empty? q)
                              (str/includes? (str/lower-case (str (:name r) " "
                                                                 (:description r) " "
                                                                 (str/join " " (:topics r))))
                                             q)))
              shown     (vec (->> all
                                  (filter #(or (nil? current) (= (:language %) current)))
                                  (filter matches-q)))
              stars     (reduce + 0 (map :stargazers_count all))
              featured  (when (and (nil? current) (empty? q) (seq all))
                          (take 3 (sort-by :stargazers_count > all)))
              feat-names (set (map :name featured))
              rest-shown (vec (remove #(contains? feat-names (:name %)) shown))
              clear!    (fn []
                          (reset! lang-filter nil)
                          (reset! search ""))]
          (js/console.log "PAGEDBG" (pr-str {:all (count all)
                                             :shown (count shown)
                                             :featured (count featured)
                                             :rest (count rest-shown)
                                             :rest-types (mapv type rest-shown)
                                             :feat-types (mapv type featured)}))
          [:div
           [:main.projects-container
            [:h1 "projects"]
            [:p.projects-intro "some things i built and keep on github"]

            ;; @info <live totals from loaded repos>
            (when (seq all)
              [:p.projects-stats (count all) " repos · " stars " ★"
               (when (or current (seq q))
                 (str " · showing " (count rest-shown)))])

            ;; @info <language filter chips>
            (when (seq top-langs)
              [filter-chips top-langs lang-filter #(reset! lang-filter %)])

            ;; @info <search toolbar, matches name, description and topics>
            (when (seq all)
              [:div.projects-toolbar
               [:input.projects-search {:type      "text"
                                        :placeholder "search repos, topics..."
                                        :value     @search
                                        :on-change #(reset! search (.. % -target -value))}]])

            ;; @info <featured repos, top by stars>
            (when (seq featured)
              [:div.featured-block
               [:div.repos-grid.featured-grid {:class (when @visible "repos-visible")}
                (map-indexed
                 (fn [idx repo]
                   ^{:key (:name repo)}
                   [repo-card/repo-card (assoc repo
                                              :lang-bar-fn lang-bar
                                              :featured? true
                                              :reveal-delay (min 480 (* idx 60)))])
                 featured)]])

            (cond
              @error
              [:div.projects-status.repos-visible
               [:p "failed to load repositories"]
               [:p.projects-error-msg @error]
               [:button.retry-btn {:type "button" :on-click (fn [_] (fetch!))} "try again"]]

              ;; @info <skeletons while first load>
              (and (empty? all) (not @visible))
              [:div.repos-grid.skeleton-grid
               (for [i (range 6)]
                 ^{:key i}
                 [:div.repo-card.skeleton {:aria-hidden true}
                  [:div.skel.skel-title]
                  [:div.skel.skel-line]
                  [:div.skel.skel-line.short]
                  [:div.skel.skel-bar]])]

              (and @visible (empty? rest-shown))
              [:div.projects-status.repos-visible
               [:p (if (seq all) "nothing matches, try another search" "no repositories yet")]
               (when (or current (seq q))
                 [:button.retry-btn {:type "button" :on-click (fn [_] (clear!))} "clear filters"])]

              :else
              ;; @info <stable grid, no remount on filter>
              [:div.repos-grid {:class (when @visible "repos-visible")}
               (map-indexed
                (fn [idx repo]
                  ^{:key (:name repo)}
                  [repo-card/repo-card (assoc repo
                                              :lang-bar-fn lang-bar
                                              ;; @info <stagger only unfiltered, plain visible on search>
                                              :reveal-delay (when (and (nil? current) (empty? q))
                                                              (min 480 (* idx 60))))])
                rest-shown)])]])))))
;; @secend->@secname   <projectspage>