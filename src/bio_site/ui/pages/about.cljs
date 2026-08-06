;; @file    <pages/about.cljs>
;; @author  <wakaranakattari@gmail.com>
;; @info    <about me page>
;; @version <1.5>

;; @secstart->@secname <nsrq>
(ns bio-site.ui.pages.about)
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
  ;; @funcinfo <about page implementation, this contains all the basic information about me and my indignation>
(defn page []
  [:div

   ;; @secstart->@secname <maincontainer>
    ;; @info <main container which contains all the sections & content>
   [:main.about-container

    ;; @secstart->@secname <aboutme>
      ;; @info <main information for me>
    [:section.about-hero
     [:h1 "about me"]

     [:p.about-tagline "nikita · gay · 18 years · infj-a"]

     [:p "an explorer at heart, i express myself through software engineering. "
      "i dont just write code - i look for stories, depth and elegance "
      "in places most people overlook. whether its an obscure language, "
      "a hidden track in an album, or a page in a book, im always chasing "
      "that spark of genuine inspiration"]]
    ;; @secend->@secname   <aboutme>

    ;; @secstart->@secname <myphilosophy>
      ;; @info <my personal philosophy>
    [:section.about-block
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
    [:section.about-block
     [:h2 "behind the code"]

     [:p "books - im an avid reader, i simply cannot imagine my life without them. "
      "they are my way of understanding human nature and finding new perspectives"]

     [:p "music - something deeply personal to me. its a sanctuary, a place "
      "i always return to when i need to live through my emotions and find peace"]

     [:p "creation - programming is my panacea. it might sound silly, "
      "but its how i show the world who i am. its proof that tech isnt just "
      "cold logic - in the right hands, its a pure form of art"]]
    ;; @secend->@secname   <behindthecode>

    ;; @secstart->@secname <mystack>
      ;; @info <my main stack, p.s... i very luv perl & clj..>
    [:section.about-block
     [:h2 "what i use"]

     [:h3 "main languages"]
     [tech-chips ["go" "elixir" "rust"]]

     [:h3 "second languages"]
     [tech-chips ["typescript" "clojure" "perl"]]

     [:h3 "frontend"]
     [tech-chips ["solidjs" "next.js" "reagent" "elysia" "bun" "javascript" "typescript" "wasm"]]

     [:h3 "tools & db"]
     [tech-chips ["neovim" "vscode" "docker" "nginx" "linux" "postgres" "mongodb" "redis"]]]
    ;; @secend->@secname   <mystack>

    ;; @secstart->@secname <myindignation>
      ;; @info <these are my personal indignations and thoughts, please take it easy)>
    [:section.about-block
     ;; @info <*1 etc this is a footnote or a disclaimer, read below>
     [:h2 "*1 my indignation"]

     [:h3 "tunnel vision"]
     [:p (str "i dont really understand why the vast majority of programmers "
              "have such tunnel vision. there are so many great tools and programming languages "
              "in the world, and because theyre not mainstream, people simply bury them. "
              "theyre so full of stereotypes that they bury them without even trying them. "
              "thats why i dont particularly like communicating with such people. "
              "i believe they wont go beyond their comfort zone of one or two such tools "
              "and will continue to live in their own rosy world")]

     [:h3 "imposed stereotypes"]
     [:p (str "people are too stereotypical, and its this imposed stereotype "
              "that prevents them from discovering something new. ive encountered this "
              "very stereotype myself. i used to think that non-mainstream tools were disgusting "
              "and created for fun. take pascal, for example. the imposed stereotypes about the tool "
              "mostly come from school years with blue screens and turbo pascal. "
              "but the language is incredibly powerful for its tasks and is still improving "
              "and evolving. with this example, i want to express my indignation "
              "at the stereotypes imposed on developers and people in general. "
              "after all, isnt it better to live in your own echo chamber and not let anyone in?)")]

     [:h3 "toxic communities"]
     [:p (str "unpopular tools and languages are unpopular for a reason: "
              "theyve found their way into companies and relatively small communities. "
              "its in these communities that truly cool and intelligent questions are asked, "
              "rather than in huge, toxic communities where asking questions like "
              "\"how do i properly dereference a pointer in c++?\" will get you beaten "
              "to the punch and told youll never become a programmer. "
              "its precisely because of this experience that ive had that "
              "i prefer to remain in a small, friendly, and truly valuable community "
              "rather than in huge, toxic, and inadequate ones")]

     [:h3 "tool philosophies"]
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
              "they dont suffer from your loud takeovers, but youll have mental health problems in the future")]]
    ;; @secend->@secname   <myindignation>

    ;; @secstart->@secname <disclaimer1>
      ;; @info <the disclaimer is there so that when people express dissatisfaction with my indignation, they can scroll down and read it>
     [:p.disclaimer-1
      " *1 - this is my personal opinion. "
      "im not here to argue. "
      "either you resonate with it or not - i genuinely dont care."]]])
   ;; @secend->@secname <disclaimer1>
   ;; @secend->@secname <maincontainer>
;; @secend->@secname   <aboutpage>
