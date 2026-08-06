;; @file    <services/writing.cljs>
;; @author  <wakaranakattari@gmail.com>
;; @info    <writing service: fetch articles manifest and markdown content>
;; @version <1.0>

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

;; @secstart->@secname <fetchmanifest>
  ;; @funcinfo <fetch the list of articles from the static manifest>
(defn fetch-articles!
  [on-success on-error]
  (-> (js/fetch "/writing/index.json")
      (.then #(.json %))
      (.then (fn [data]
               (on-success (js->clj data :keywordize-keys true))))
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