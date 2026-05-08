;; @file    components/repo-card.cljs
;; @author  <wakaranakattari@gmail.com>
;; @info    <repos card component>
;; @version 1.3

;; @secstart->@secname <ns>
(ns bio-site.ui.components.repo-card)
;; @secend->@secname   <ns>

;; @secstart->@secname <repocard>
 ;; @funcinfo <repos card component which includes repo stars & repo lang & repo description & repo forks count & repo name>
(defn repo-card [{:keys [name description html_url stargazers_count language forks_count]}]
 ;; @info <clicking on a repository takes u to the next page>
  [:a {:href html_url :target "_blank" :class "repo-card"}

  ;; @secstart->@secname <repomaster> :: @secinfo <gets name repos & stargazers count>
   [:div.repo-master
    [:h3 name]
    [:span.repo-stats stargazers_count]]
   ;; @secend->@secname  <repomaster>

   ;; @info <gets description repos>
   [:p.repo-description description]

   ;; @secstart->@secname <repofooter> :: @secinfo <gets repos lang>
   [:div.repo-footer
    (when language
      [:span.repo-language language])
    ;; @secend->@secname  <repofooter>

    ;; @info <gets forks count>
    [:span.repo-forks forks_count]]])
;; @secend->@secname   <repocard>
