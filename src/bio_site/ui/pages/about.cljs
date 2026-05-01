(ns bio-site.ui.pages.about
  (:require [bio-site.ui.components.header :as header]))

(defn page []
  [:div
   ;; header call
   [header/header]

   ;; about page container
   [:main.about-container

    ;; about me section
    [:section.about-me

     [:h1 "about me"]

     [:p "nikita · 18 years · gay · infj-a"]

     [:p "im a software engineer who loves exotic and strange languages"]]

    ;; my philosophy section
    [:section.my-philosophy

     [:h1 "my philosophy"]

     [:p "im convinced that exotic tools in the right hands can work wonders "
      "and make people into versatile and innovative developers"]

     [:p "i share this kind of knowledge with people and expand their horizons "
      "in the world of programming. programming is great, but you cant sit "
      "at the same tool your whole life, right?"]

     [:p (str "i tried many technologies and languages, my first programming language was c++, "
              "it gave me a very strong base in programming, computer architecture "
              "and many other things, but i got tired of constantly sitting at the same tool, "
              "so i started trying new things for myself, and now i am at the point of no return, "
              "i saw all the beauty and elegance of other tools, many people think that many languages "
              "are dead or rarely used, but i can say one thing - you just didnt understand them")]]

    ;; my interests section
    [:section.my-interests

     [:h1 "my interests"]

     [:p "i love reading books, i cant imagine my life without them"]

     [:p "music is something special for me, its the place where i want to return and live it"]

     [:p (str "i love programming and everything related to it. for me, programming is a way "
              "to show myself and the world that imm a creative and interesting person. "
              "i believe programming is a panacea, as silly as that may sound")]]

    ;; my stack section
    [:section.my-stack
     [:h1 "what i use"]
     [:h2 "languages"]
     [:p "clojure · java · d · perl · c++ · js & ts"]
     [:h2 "frameworks"]
     [:p "react · vue.js · next.js · reagent"]
     [:h2 "tools"]
     [:p "neovim · emacs · docker · nginx · linux"]]

    ;; my indignation section
    [:section.my-indignation
     [:h1 "*1 my indignation"]

     [:p (str "i dont really understand why the vast majority of programmers "
              "have such tunnel vision. there are so many great tools and programming languages "
              "in the world, and because theyre not mainstream, people simply bury them. "
              "theyre so full of stereotypes that they bury them without even trying them. "
              "thats why i dont particularly like communicating with such people. "
              "i believe they wont go beyond their comfort zone of one or two such tools "
              "and will continue to live in their own rosy world")]

     [:p (str "people are too stereotypical, and itss this imposed stereotype "
              "that prevents them from discovering something new. ive encountered this "
              "very stereotype myself. i used to think that non-mainstream tools were disgusting "
              "and created for fun. take pascal, for example. the imposed stereotypes about the tool "
              "mostly come from school years with blue screens and turbo pascal. "
              "but the language is incredibly powerful for its tasks and is still improving "
              "and evolving. with this example, i want to express my indignation "
              "at the stereotypes imposed on developers and people in general. "
              "after all, isnt it better to live in your own echo chamber and not let anyone in?)")]

     [:p (str "unpopular tools and languages are unpopular for a reason: "
              "theyve found their way into companies and relatively small communities. "
              "its in these communities that truly cool and intelligent questions are asked, "
              "rather than in huge, toxic communities where asking questions like "
              "\"how do i properly dereference a pointer in c++?\" will get you beaten "
              "to the punch and told youll never become a programmer. "
              "its precisely because of this experience that ive had that "
              "i prefer to remain in a small, friendly, and truly valuable community "
              "rather than in huge, toxic, and inadequate ones")]

     [:p (str "developers turn tools into philosophies, like linux, emacs, or vim, "
              "and i call people like that schizophrenics. for me, a tool should remain a tool. "
              "why do i use nvim & emacs? because its a very convenient text printer, nothing more. "
              "why do i use arch linux and not customize it? because its a minimalist tool "
              "fine-tuned for me without unnecessary clutter. i dont make a philosophy out of this, "
              "i use what i truly need and find convenient, and i dont try to promote these tools "
              "to others. when people are interested, i try to support them in their endeavors. "
              "and most developers, especially amateurs of a wide range of school-age people, "
              "say something like, \"what, you dont use hyperland on arch? youre not a real linux user\" "
              "this kind of nonsense often reveals an inferiority complex in these guys. "
              "they fight for their imaginary tool by insulting others and filling their own insecurities. "
              "guys, dont do that. people dont really care about your opinions. "
              "they dont suffer from your loud takeovers, but youll have mental health problems in the future")]]]

   [:p {:class "disclaimer-1"}
    " *1 - this is my personal opinion. "
    "im not here to argue. "
    "either you resonate with it or not - i genuinely dont care."]])
