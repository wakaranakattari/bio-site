;; @file    <pages/writing.cljs>
;; @author  <wakaranakattari@gmail.com>
;; @info    <writing page, personal forum with articles and ideas>
;; @version <2.0>

;; @secstart->@secname <nsrq>
(ns bio-site.ui.pages.writing
  (:require [reagent.core :as r]
            [bio-site.services.writing :as writing]
            [bio-site.router :as router]))
;; @secend->@secname   <nsrq>

;; @secstart->@secname <articlelist>
  ;; @funcinfo <article list component, fetches manifest on mount>
(defn article-list []
  (let [articles (r/atom [])
        error    (r/atom nil)]
    (r/create-class
     {:component-did-mount
      (fn []
        (writing/fetch-articles!
         (fn [data] (reset! articles data))
         (fn [msg] (reset! error msg))))
      :reagent-render
      (fn []
        (let [loaded (boolean (seq @articles))]
          [:main.writing-container
           [:h1 "writing"]
           [:p.writing-intro
            "my personal forum: ideas, articles and thoughts, sorted by category."]

           (if @error
             [:div.writing-status
              [:p "failed to load articles"]
              [:p.writing-error-msg @error]]

             (if (empty? @articles)
               [:div.writing-status {:key (if loaded "error" "loading")}
                [:p "no articles yet"]]

               [:div.writing-list {:key "loaded"}
                (for [{:keys [slug title category date summary]} @articles]
                  ^{:key slug}
                  [:a.writing-card {:href (str "/writing/" slug)
                                    :on-click (fn [e]
                                                (.preventDefault e)
                                                (router/navigate! (str "/writing/" slug)))}
                   [:div.writing-card-meta
                    [:span.writing-category category]
                    [:span.writing-date date]]
                   [:h2 title]
                   (when summary [:p.writing-summary summary])])]))]))})))
;; @secend->@secname   <articlelist>

;; @secstart->@secname <articleview>
  ;; @funcinfo <single article view, fetches markdown on mount>
(defn article-view
  [{:keys [slug]}]
  (let [state (r/atom {:status :loading
                       :meta   nil
                       :html   nil
                       :error  nil})]
    (r/create-class
     {:component-did-mount
      (fn []
        (writing/fetch-article!
         slug
      (fn [{:keys [meta html]}]
        (reset! state {:status :ok :meta meta :html html}))
         (fn [msg]
           (reset! state {:status :error :error msg}))))
      :reagent-render
      (fn [_]
        (let [{:keys [status meta html error]} @state]
          [:main.writing-container
           [:nav.writing-back
            [:a {:href "/writing"
                 :on-click (fn [e]
                             (.preventDefault e)
                             (router/navigate! "/writing"))}
             "← all articles"]]

           (cond
             (= status :loading)
             [:div.writing-status [:p "loading article..."]]

             (= status :error)
             [:div.writing-status
              [:p "failed to load article"]
              [:p.writing-error-msg error]]

             :else
             [:article.writing-article {:key "loaded"}
              [:div.writing-card-meta
               [:span.writing-category (:category meta)]
               [:span.writing-date (:date meta)]]
              [:div.article-body
               {:dangerouslySetInnerHTML (r/unsafe-html html)}]])]))})))
;; @secend->@secname   <articleview>

;; @secstart->@secname <writingpage>
  ;; @funcinfo <writing page router: list on /writing, article on /writing/:slug>
(defn page []
  (let [path @router/current-path]
    [:div
     (if-let [slug (second (re-find #"^/writing/(.+?)/?$" path))]
       [article-view {:slug slug :key slug}]
       [article-list])]))
;; @secend->@secname   <writingpage>