;; @file    <pages/contacts.cljs>
;; @author  <wakaranakattari@gmail.com>
;; @info    <contacts page>
;; @version <1.8>

;; @secstart->@secname <nsrq>
(ns bio-site.ui.pages.contacts
  (:require [bio-site.router :as router]
            [bio-site.utils.reveal :refer [reveal-props]]
            [bio-site.utils.tilt :as tilt]))
;; @secend->@secname   <nsrq>

;; @secstart->@secname <contactspage>
 ;; @funcinfo <contacts page implementation, basic contacts => github, telegram, discord>
(defn page []
  [:div

   ;; @secstart->@secname <contactscontainer>
    ;; @info <main container which contains all the sections & content>
   [:main.contacts-container

    ;; @secstart->@secname <contacts>
     ;; @info <contactinformation>
    [:section.contacts (reveal-props 0)
     [:h1 "contacts"]
     [:p "you can contact me at any convenient time, my private messages are always open for you"]]
    ;; @secend->@secname   <contacts>

    ;; @secstart->@secname <howtowriteme>
      ;; @info <how to write me: no meta-questions, straight to the point>
    [:section.contacts-guide (reveal-props 80)
     [:h2 "how to write me"]

     [:p "dont ask meta-questions - no \"hi\", \"are you here?\", "
      "\"do you have a minute?\" or \"can i ask a question?\". "
      "just ask your question right away."]

     [:p "a good first message has three things: context, "
      "what you already tried, and the actual question. "
      "chat is async - if im offline, ill answer when im back "
      "instead of staring at your \"hi\" and wondering what happened."]

     [:p "languages - ru/en. code, languages, and tooling are welcome. "
      "i usually reply within a day or two."]

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
      [:span.comm-card-arrow "→"]]]
    ;; @secend->@secname   <commcard>
    ;; @secend->@secname   <howtowriteme>

    ;; @secstart->@secname <contactlist>
     ;; @info <contact links as cards>
    [:section.contact-list (reveal-props 120)
     [:a.contact-item {:href "https://github.com/wakaranakattari"
                       :target "_blank" :rel "noopener noreferrer"}
      [:span.contact-icon "gh"]
      [:span.contact-info
       [:span.contact-name "github"]
       [:span.contact-handle "@wakaranakattari"]]
      [:span.contact-arrow "→"]]
     [:a.contact-item {:href "https://t.me/wakaranakattari"
                       :target "_blank" :rel "noopener noreferrer"}
      [:span.contact-icon "tg"]
      [:span.contact-info
       [:span.contact-name "telegram"]
       [:span.contact-handle "@wakaranakattari"]]
      [:span.contact-arrow "→"]]
     [:a.contact-item {:href "https://discord.com/users/1466086440499941689"
                       :target "_blank" :rel "noopener noreferrer"}
      [:span.contact-icon "ds"]
      [:span.contact-info
       [:span.contact-name "discord"]
       [:span.contact-handle "@wkrn"]]
      [:span.contact-arrow "→"]]]]])
   ;; @secend->@secname    <contactlist>
  ;; @secend->@secname    <contactscontainer>
;; @secend->@secname   <contactspage>
