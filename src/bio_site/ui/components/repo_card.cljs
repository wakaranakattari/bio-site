;; @file    <components/repo-card.cljs>
;; @author  <wakaranakattari@gmail.com>
;; @info    <repos card component>
;; @version <1.9>

;; @secstart->@secname <ns>
(ns bio-site.ui.components.repo-card)
;; @secend->@secname   <ns>

;; @secstart->@secname <repocard>
  ;; @funcinfo <repos card component which includes repo stars & repo lang & repo description & repo forks count & repo name & license>
(defn repo-card [{:keys [name description html_url stargazers_count
                         forks_count license languages lang-bar-fn]}]
  ;; @info <clicking on a repository takes u to the next page>
  [:a {:href html_url :target "_blank" :class "repo-card"}

   ;; @secstart->@secname <repomaster> :: @secinfo <gets name repos & stargazers count & forks count>
   [:div.repo-master
    [:h3 name]
    [:div.repo-stats
     [:span "★ " stargazers_count]
     [:span "⇅ " forks_count]]]
   ;; @secend->@secname  <repomaster>

   ;; @info <gets description repos>
   [:p.repo-description (or description "no description")]

   ;; @info <language breakdown bar with percentages>
   (when lang-bar-fn
     [lang-bar-fn languages])

   ;; @secstart->@secname <repofooter> :: @secinfo <gets license>
   [:div.repo-footer
    [:span.repo-license
     (if-let [spdx (:spdx_id license)]
       (if (not-any? #(= spdx %) ["NOASSERTION" "OMIT" ""])
         (.toLowerCase spdx)
         "no license")
       "no license")]]
   ;; @secend->@secname  <repofooter>
   ])
;; @secend->@secname   <repocard>