;; @file    <components/repo-card.cljs>
;; @author  <wakaranakattari@gmail.com>
;; @info    <repos card component>
;; @version <1.16>

;; @secstart->@secname <ns>
(ns bio-site.ui.components.repo-card
  (:require [reagent.core :as r]
            [bio-site.utils.tilt :as tilt]
            [bio-site.utils.reveal :refer [reveal-props]]))
;; @secend->@secname   <ns>

;; @secstart->@secname <updatedago>
  ;; @funcinfo <relative pushed time like updated 3d ago, nil on bad input>
(defn- updated-ago [iso]
  (try
    (when (seq iso)
      (let [mins (max 1 (quot (- (.now js/Date) (.parse js/Date iso)) 60000))]
        (cond (< mins 60) (str "updated " mins "m ago")
              (< mins 1440) (str "updated " (quot mins 60) "h ago")
              (< mins 43200) (str "updated " (quot mins 1440) "d ago")
              :else (str "updated " (quot mins 43200) "mo ago"))))
    (catch js/Error _ nil)))
;; @secend->@secname   <updatedago>

;; @secstart->@secname <freshbadge>
  ;; @funcinfo <freshness badge from pushed date, new under 7d and active under 30d>
(defn- fresh-badge [pushed-at]
  (try
    (when (seq pushed-at)
      (let [days (quot (- (.now js/Date) (.parse js/Date pushed-at)) 86400000)]
        (cond (< days 7) [:span.repo-badge.repo-badge-new "new"]
              (< days 30) [:span.repo-badge.repo-badge-active "active"])))
    (catch js/Error _ nil)))
;; @secend->@secname   <freshbadge>

;; @secstart->@secname <copiedurl>
  ;; @funcinfo <last copied clone url, drives copied checkmark>
(defonce copied-url (r/atom nil))
;; @secend->@secname   <copiedurl>

;; @secstart->@secname <cardactions>
  ;; @funcinfo <open github subpage in new tab without triggering card link>
(defn- open-sub [base sub]
  (fn [e]
    (.preventDefault e)
    (.stopPropagation e)
    (.open js/window (str base "/" sub) "_blank")))

  ;; @funcinfo <copy git clone url, flips copied checkmark for a moment>
(defn- copy-clone! [html-url]
  (-> (.. js/navigator -clipboard (writeText (str "git clone " html-url ".git")))
      (.then (fn []
               (reset! copied-url html-url)
               (js/setTimeout #(when (= @copied-url html-url)
                                 (reset! copied-url nil))
                              1500)))
      (.catch (fn [_] nil))))
;; @secend->@secname   <cardactions>

;; @secstart->@secname <repocard>
  ;; @funcinfo <repos card component which includes repo stars & repo lang & repo description & repo forks count & repo name & license>
(defn repo-card [{:keys [name description html_url stargazers_count
                         forks_count license languages lang-bar-fn reveal-delay
                         topics pushed_at featured?]}]
  ;; @info <clicking on a repository takes u to the next page>
  [:a (merge {:href html_url :target "_blank" :rel "noopener noreferrer"}
             (tilt/tilt-props)
             ;; @info <reveal only for entrance stagger, plain visible when filtering>
             (when reveal-delay
               (reveal-props reveal-delay (str "repo-card" (when featured? " featured")))))

   ;; @secstart->@secname <repomaster> :: @secinfo <gets name repos & stargazers count & forks count>
   [:div.repo-master
    [:div.repo-title
     [:h3 name]
     (fresh-badge pushed_at)]
    [:div.repo-stats
     [:span.repo-stat.repo-stat-link {:title "stargazers"
                                      :on-click (open-sub html_url "stargazers")}
      "★ " stargazers_count]
     [:span.repo-stat.repo-stat-link {:title "forks"
                                      :on-click (open-sub html_url "forks")}
      "⇅ " forks_count]
     [:span.repo-stat.repo-clone {:title "copy clone url"
                                  :on-click (fn [e]
                                              (.preventDefault e)
                                              (.stopPropagation e)
                                              (copy-clone! html_url))}
      (if (= @copied-url html_url) "✓" "⧉")]]]
   ;; @secend->@secname  <repomaster>

   ;; @info <gets description repos>
   [:p.repo-description (or description "no description")]

   ;; @info <repo topics, first three>
   (when (seq topics)
     [:div.repo-topics
      (for [t (take 3 topics)]
        ^{:key t}
        [:span.repo-topic t])])

   (when lang-bar-fn
     [lang-bar-fn languages])

   ;; @secstart->@secname <repofooter> :: @secinfo <gets license>
   [:div.repo-footer
    [:span.repo-license
     (if-let [spdx (:spdx_id license)]
       (if (not-any? #(= spdx %) ["NOASSERTION" "OMIT" ""])
         (.toLowerCase spdx)
         "no license")
       "no license")]
    (when-let [ago (updated-ago pushed_at)]
      [:span.repo-updated ago])
    [:span.repo-open "↗"]]
   ;; @secend->@secname  <repofooter>
   ])
;; @secend->@secname   <repocard>
