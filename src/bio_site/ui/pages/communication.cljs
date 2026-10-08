;; @file    <pages/communication.cljs>
;; @author  <wakaranakattari@gmail.com>
;; @info    <communication guide page, not in header, linked from about>
;; @version <1.1>

;; @secstart->@secname <nsrq>
(ns bio-site.ui.pages.communication
  (:require [bio-site.router :as router]
            [bio-site.utils.reveal :refer [reveal-props]]))
;; @secend->@secname   <nsrq>

;; @secstart->@secname <chatbubble>
  ;; @funcinfo <renders a chat message bubble, kind is either bad or good>
(defn chat-bubble [kind text]
  [:div.bubble {:class (str "bubble-" (name kind))} text])
;; @secend->@secname   <chatbubble>

;; @secstart->@secname <commpage>
  ;; @funcinfo <communication guide page implementation>
(defn page []
  [:div

   ;; @secstart->@secname <maincontainer>
    ;; @info <main container which contains all the sections & content>
   [:main.comm-container

    ;; @secstart->@secname <back>
     ;; @info <back link to about page>
    [:nav.comm-back
     [:a {:href "/about"
          :on-click (fn [e]
                      (.preventDefault e)
                      (router/navigate! "/about"))}
      "← about"]]
    ;; @secend->@secname   <back>

    ;; @secstart->@secname <commhero>
      ;; @info <page heading and short intro>
    [:section.comm-hero (reveal-props 0)
     [:h1 "how to communicate"]
     [:p "im all for chill, comfortable communication that feels adequate for everyone. "
      "this page is a short guide on how to write me so we dont waste each others time."]]
    ;; @secend->@secname   <commhero>

    ;; @secstart->@secname <metaquestions>
      ;; @info <dont ask meta-questions, with bad examples as chat bubbles>
    [:section.comm-block (reveal-props 60)
     [:h2 "dont ask meta-questions"]

     [:p "a meta-question is a question that implies other questions instead of just asking:"]

     ;; @secstart->@secname <badexamples> :: @secinfo <bad first messages>
     [:div.chat
      [chat-bubble :bad "may i ask a question?"]
      [chat-bubble :bad "hi. are you here?"]
      [chat-bubble :bad "do you have a minute??"]
      [chat-bubble :bad "i have a question about %topic%..."]]
     ;; @secend->@secname   <badexamples>

     [:p "people type much slower than they speak. "
      "asking right away opens up async communication - "
      "if im offline, ill answer when im back "
      "instead of staring at your \"hi\" and wondering what happened."]]
    ;; @secend->@secname   <metaquestions>

    ;; @secstart->@secname <goodmessage>
      ;; @info <what a good first message looks like, with a good example bubble>
    [:section.comm-block (reveal-props 80)
     [:h2 "a good first message"]

     [:p "three things: context, what you already tried, and the actual question. for example:"]

     [:div.chat
      [chat-bubble :good "hi! im trying to do x with y, already tried a and b, got error c - any ideas?"]]

     [:p "languages - ru/en. code, languages, and tooling are always welcome. "
      "i usually reply within a day or two."]]
    ;; @secend->@secname   <goodmessage>

    ;; @secstart->@secname <boundaries>
      ;; @info <what i dont tolerate in communication>
    [:section.comm-block (reveal-props 100)
     [:h2 "boundaries"]

     [:p "what i dont tolerate: trolling, insults, bullying, "
      "and messages with a trolling subtext - passive-aggressive jabs included."]

     [:p "im here for vibe, adequate communication thats comfortable for everyone. "
      "cross the line and i just stop replying. no drama, no second chances lecture."]]]])
   ;; @secend->@secname <maincontainer>
;; @secend->@secname   <commpage>
