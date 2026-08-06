;; @file    <pages/home.cljs>
;; @author  <wakaranakattari@gmail.com>
;; @info    <home page>
;; @version <1.5>

;; @secstart->@secname <nsrq>
(ns bio-site.ui.pages.home)
;; @secend->@secname   <nsrq>

;; @secstart->@secname <homepage>
;; @funcinfo <home page implementation, displays hero section and navigation buttons>
(defn page []
  [:div

   ;; @secstart->@secname <homecontainer>
   ;; @info <main container with hero and CTA buttons>
   [:main.home-container

    ;; @secstart->@secname <herosection>
    ;; @info <hero section with greeting and role>
    [:section.hero
     [:h1 "hi, im " [:span.hero-name "nikita aka wkrn"] "!"]
     [:h2 "software engineer"]
     [:p.hero-intro "a personal corner of the internet - "
      "thoughts, code and experiments"]]
    ;; @secend->@secname <herosection>

    ;; @secstart->@secname <buttongroup>
    ;; @info <button group for navigation to projects and contacts>
    [:div.button-group
     [:a.button-link {:href "/projects"}
      [:span.button-label "view projects"]
      [:span.button-arrow "→"]]
     [:a.button-link {:href "/contacts"}
      [:span.button-label "contact me"]
      [:span.button-arrow "→"]]]]])
;; @secend->@secname   <homecontainer>
;; @secend->@secname   <homepage>
