;; @file    <services/writing.cljs>
;; @author  <wakaranakattari@gmail.com>
;; @info    <writing service: fetch articles manifest and markdown content>
;; @version <1.1>

;; @secstart->@secname <ns>
(ns bio-site.services.writing
  (:require [clojure.string :as str]
            ["markdown-it" :as md-it]))
;; @secend->@secname   <ns>

;; @secstart->@secname <mdrenderer>
  ;; @info <markdown renderer instance, html is escaped by default>
(defonce ^:private md (md-it #js {:html false :linkify true}))
;; @secend->@secname   <mdrenderer>

;; @secstart->@secname <frontmatter>
  ;; @funcinfo <parse front matter block at the top of an article file>
(defn- parse-front-matter
  [raw]
  (if-let [[_ fm body] (re-find #"(?s)^---\r?\n(.*?)\r?\n---\r?\n?(.*)" raw)]
    {:meta (into {}
                 (keep (fn [line]
                         (when-let [[_ k v] (re-find #"^\s*([^:]+):\s*(.*?)\s*$" line)]
                           [(keyword (str/lower-case (str/trim k)))
                            (str/replace (str/trim v) #"^\"|\"$" "")])))
                 (str/split-lines fm))
     :body (or body "")}
    {:meta {} :body raw}))
;; @secend->@secname   <frontmatter>

;; @secstart->@secname <manifestcache>
  ;; @funcinfo <localStorage cache for articles manifest with 24h ttl>
(def ^:private cache-key "writing-articles-cache")
(def ^:private cache-ttl-ms (* 24 60 60 1000))

  ;; @funcinfo <read cached manifest from localStorage, nil if missing or expired>
(defn- read-cache []
  (try
    (when-let [raw (.getItem js/localStorage cache-key)]
      (let [cached (js->clj (js/JSON.parse raw) :keywordize-keys true)
            ts     (:ts cached)]
        (when (and ts (> (+ ts cache-ttl-ms) (.now js/Date)))
          (:articles cached))))
    (catch js/Error _ nil)))

  ;; @funcinfo <write manifest to localStorage with current timestamp>
(defn- write-cache!
  [articles]
  (try
    (.setItem js/localStorage cache-key
              (js/JSON.stringify (clj->js {:ts (.now js/Date) :articles articles})))
    (catch js/Error _ nil)))

  ;; @funcinfo <get cached manifest synchronously, nil if none>
(defn cached-articles
  []
  (read-cache))
;; @secend->@secname   <manifestcache>

;; @secstart->@secname <fetchmanifest>
  ;; @funcinfo <fetch the list of articles from the static manifest, updates cache>
(defn fetch-articles!
  [on-success on-error]
  (-> (js/fetch "/writing/index.json")
      (.then #(.json %))
      (.then (fn [data]
               (let [articles (js->clj data :keywordize-keys true)]
                 (write-cache! articles)
                 (on-success articles))))
      (.catch (fn [err]
                (on-error (.-message err))))))
;; @secend->@secname   <fetchmanifest>

;; @secstart->@secname <fetcharticle>
  ;; @funcinfo <fetch a single article by slug, returns parsed meta and rendered html>
(defn fetch-article!
  [slug on-success on-error]
  (-> (js/fetch (str "/writing/" slug ".md"))
      (.then #(.text %))
      (.then (fn [raw]
               (let [{:keys [meta body]} (parse-front-matter raw)]
                 (on-success {:meta  meta
                              :title (or (:title meta) slug)
                              :html  (.render md body)}))))
      (.catch (fn [err]
                (on-error (.-message err))))))
;; @secend->@secname   <fetcharticle>