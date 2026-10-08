;; @file    <pages/about.cljs>
;; @author  <wakaranakattari@gmail.com>
;; @info    <about me page>
;; @version <2.2>

;; @secstart->@secname <nsrq>
(ns bio-site.ui.pages.about
  (:require [bio-site.router :as router]
            [bio-site.utils.reveal :refer [reveal-props]]
            [bio-site.utils.tilt :as tilt]))
;; @secend->@secname   <nsrq>

;; @secstart->@secname <techchips>
  ;; @funcinfo <renders a group of technology tags as chips>
(defn tech-chips [items]
  [:div.tech-chips
   (for [item items]
     ^{:key item}
     [:span.tech-chip item])])
;; @secend->@secname   <techchips>

;; @secstart->@secname <aboutpage>
  ;; @funcinfo <about page implementation, this contains all the basic information about me>
(defn page []
  [:div

   ;; @secstart->@secname <maincontainer>
    ;; @info <main container which contains all the sections & content>
   [:main.about-container

    ;; @secstart->@secname <aboutme>
      ;; @info <main information for me>
    [:section.about-hero (reveal-props 0)
     [:h1 "about me"]

     [:p.about-tagline "nikita · backend engineer · 18 years · infj-a"]

     [:p "building backend systems with rust and go. "
      "exploring r&d, functional programming, and programming language design with haskell."]]
    ;; @secend->@secname   <aboutme>

    ;; @secstart->@secname <myphilosophy>
      ;; @info <my personal philosophy>
    [:section.about-block (reveal-props 60)
     [:h2 "my philosophy"]

     [:p "programming was never about standard solutions for me, or sitting "
      "with the same comfortable tool forever. its a canvas for creativity. "
      "stepping outside the mainstream and exploring the strange corners "
      "of tech shapes how you think - it makes you versatile, innovative "
      "and open-minded"]

     [:p "as an infj, i naturally want to share this feeling with others. "
      "i love expanding peoples horizons and showing them that the world "
      "of code is much wider and more beautiful than they think. "
      "if a language is considered dead or too niche, it usually just means "
      "people havent truly understood its soul yet"]]
    ;; @secend->@secname   <myphilosophy>

    ;; @secstart->@secname <behindthecode>
      ;; @info <my most basics interests>
    [:section.about-block (reveal-props 60)
     [:h2 "behind the code"]

     [:p "books - im an avid reader, i simply cannot imagine my life without them. "
      "they are my way of understanding human nature and finding new perspectives"]

     [:h3 "favorite authors"]
     [tech-chips ["kafka" "camus" "sartre" "dostoevsky" "strugatsky" "dan brown" "chuck palahniuk"]]

     [:p "music - something deeply personal to me. its a sanctuary, a place "
      "i always return to when i need to live through my emotions and find peace"]

     [:p "creation - programming is my panacea. it might sound silly, "
      "but its how i show the world who i am. its proof that tech isnt just "
      "cold logic - in the right hands, its a pure form of art"]]
    ;; @secend->@secname   <behindthecode>

    ;; @secstart->@secname <mystack>
      ;; @info <my main stack: haskell, rust, go>
    [:section.about-block (reveal-props 60)
     [:h2 "what i use"]

     [:h3 "main languages"]
     [tech-chips ["haskell" "rust" "go"]]

     [:h3 "focus areas"]
     [tech-chips ["backend engineering" "systems programming" "functional programming" "r&d"]]]
    ;; @secend->@secname   <mystack>

    ;; @secstart->@secname <uses>
      ;; @info <my environment: editor, os, and infra>
    [:section.about-block (reveal-props 60)
     [:h2 "uses"]

     [:h3 "environment"]
     [tech-chips ["neovim" "emacs" "arch linux" "docker" "nginx"]]

     [:h3 "data"]
     [tech-chips ["postgres" "mongodb" "redis"]]]
    ;; @secend->@secname   <uses>

    ;; @secstart->@secname <whatido>
      ;; @info <what i actually do with my stack>
    [:section.about-block (reveal-props 60)
     [:h2 "what i do"]

     [:h3 "rust & go"]
     [:p "backend systems and architecture - apis, services, and developer tooling "
      "that stay simple, fast, and easy to maintain."]

     [:h3 "haskell"]
     [:p "r&d, functional programming, and programming language design - "
      "where i explore ideas that later make my backend work better."]]
    ;; @secend->@secname   <whatido>

    ;; @secstart->@secname <now>
      ;; @info <what i am focused on right now>
    [:section.about-block (reveal-props 60)
     [:h2 "now"]

     [:p "backend architecture, systems programming, programming languages, "
      "developer tooling, and deeper exploration of software engineering."]

     [:p "most of my code lives on github - "
      "see the projects page for what im building."]

     ;; @secstart->@secname <commcard> :: @secinfo <card linking to the full communication guide>
     [:a (merge {:href "/communication"
                 :class "comm-card"
                 :on-click (fn [e]
                             (.preventDefault e)
                             (router/navigate! "/communication"))}
                (tilt/tilt-props))
      [:span.comm-card-text
       [:span.comm-card-title "how i communicate"]
       [:span.comm-card-sub "no meta-questions, no trolling - read the short guide"]]
      [:span.comm-card-arrow "→"]]]]])
     ;; @secend->@secname   <commcard>
    ;; @secend->@secname   <now>
   ;; @secend->@secname <maincontainer>
;; @secend->@secname   <aboutpage>
