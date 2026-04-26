(ns bio-site.ui.pages.about
  (:require [bio-site.ui.components.header :as header]))

(defn page []
  [:div
   [header/header]

   [:main.about-container
    [:section.about-me
     [:h1 "about me"]
     [:p "nikita · 18 years · gay · infj-a"]
     [:p "im a software engineer who loves exotic and strange languages"]]

    [:section.my-philosophy
     [:h1 "my philosophy"]
     [:p "im convinced that exotic tools in the right hands can work wonders and make people into versatile and innovative developers"]
     [:p "i share this kind of knowledge with people and expand their horizons in the world of programming. programming is great, but you cant sit at the same tool your whole life, right?"]]

    [:section.my-interests
     [:h1 "my interests"]
     [:p "i love reading books, i cant imagine my life without them"]
     [:p "music is something special for me, its the place where i want to return and live it"]
     [:p "i love programming and everything related to it. for me, programming is a way to show myself and the world that imm a creative and interesting person. i believe programming is a panacea, as silly as that may sound"]]

    [:section.my-stack
     [:h1 "what i use"]
     [:h2 "languages"]
     [:p "clojure · java · d · perl · c++ · js · ts"]
     [:h2 "frameworks"]
     [:p "react · vue.js · next.js · reagent"]
     [:h2 "tools"]
     [:p "neovim · emacs · docker · nginx · linux"]]]])
