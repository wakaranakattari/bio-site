;; @file    <services/github.cljs>
;; @author  <wakaranakattari@gmail.com>
;; @info    <github fetch api for dynamic loading repos>
;; @version <1.6>

;; @secstart->@secname <ns>
(ns bio-site.services.github)
;; @secend->@secname   <ns>

;; @secstart->@secname <reposcache>
  ;; @funcinfo <localStorage cache for github repos with 24h ttl>
(def ^:private cache-key "github-repos-cache")
(def ^:private cache-ttl-ms (* 24 60 60 1000))

  ;; @funcinfo <keep only fields needed by the ui>
(defn- slim-repo
  [repo]
  (select-keys repo [:name :description :html_url :stargazers_count
                     :forks_count :license :languages]))

  ;; @funcinfo <read cached repos from localStorage, nil if missing or expired>
(defn- read-cache []
  (try
    (when-let [raw (.getItem js/localStorage cache-key)]
      (let [cached (js->clj (js/JSON.parse raw) :keywordize-keys true)
            ts     (:ts cached)]
        (when (and ts (> (+ ts cache-ttl-ms) (.now js/Date)))
          (:repos cached))))
    (catch js/Error _ nil)))

  ;; @funcinfo <write repos to localStorage with current timestamp>
(defn- write-cache!
  [repos]
  (try
    (.setItem js/localStorage cache-key
              (js/JSON.stringify (clj->js {:ts (.now js/Date) :repos repos})))
    (catch js/Error _ nil)))
;; @secend->@secname   <reposcache>

;; @secstart->@secname <fetchfresh>
  ;; @funcinfo <fetch github repos, async, on-success (repos) and on-error (err-msg)>
(defn- fetch-fresh!
  [on-success on-error]

  (-> (js/fetch "https://api.github.com/users/wakaranakattari/repos?sort=updated&per_page=100" 
                #js {:cache "force-cache"})
      ;; @info <if getting fetch is successfully, trying parse to json>
      (.then #(.json %))
      ;; @info <if parse to json is successfully, convert to clj map and filter out forked repos>
      (.then (fn [data]
               (let [repos    (js->clj data :keywordize-keys true)
                     filtered (filter #(not (:fork %)) repos)]

                 (-> (js/Promise.all
                      (clj->js
                       (map (fn [repo]
                              (-> (js/fetch (:languages_url repo) #js {:cache "force-cache"})
                                  (.then #(.json %))
                                  (.then (fn [langs]
                                           (assoc repo :languages (js->clj langs))))))
                            filtered)))
                     (.then (fn [repos-with-langs]
                              (let [repos (js->clj repos-with-langs :keywordize-keys true)
                                    slim  (map slim-repo repos)]
                                (write-cache! slim)
                                (on-success slim))))))))

      ;; @info <if fetch via api || parse to json || convert to clj map is failed>
      (.catch (fn [err]
                (on-error (.-message err))))))
;; @secend->@secname   <fetchfresh>

;; @secstart->@secname <fetchrepos>
  ;; @funcinfo <public fetch, cached repos first, then fresh>
(defn fetch-repos!
  [on-success on-error]

  (let [cached (read-cache)]
    (when cached
      (on-success cached))

    (fetch-fresh!
     (fn [repos] (on-success repos))
     (fn [msg] (when-not cached (on-error msg))))))
;; @secend->@secname   <fetchrepos>

;; @secstart->@secname <calcpercents>
  ;; @funcinfo <calc language percentages>
(defn calc-lang-percents
  [languages]

  (let [total (reduce + (vals languages))]
    (if (zero? total)
      []
      (->> languages
           (map (fn [[lang bytes]]
                  {:lang    (name lang)
                   :percent (/ (* bytes 100.0) total)}))
           (sort-by :percent >)))))
;; @secend->@secname   <calcpercents>