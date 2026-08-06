;; @file    <pages/contacts.cljs>
;; @author  <wakaranakattari@gmail.com>
;; @info    <contacts page>
;; @version <1.5>

;; @secstart->@secname <nsrq>
(ns bio-site.ui.pages.contacts)
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
    [:section.contacts
     [:h1 "contacts"]
     [:p "you can contact me at any convenient time, my private messages are always open for you"]]
    ;; @secend->@secname   <contacts>

    ;; @secstart->@secname <contactlist>
     ;; @info <contact links as cards>
    [:section.contact-list
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
