;; @file    <services/github.cljs>
;; @author  <wakaranakattari@gmail.com>
;; @info    <github fetch api for dynamic loading repos>
;; @version <1.8>

;; @secstart->@secname <ns>
(ns bio-site.services.github)
;; @secend->@secname   <ns>

;; @secstart->@secname <reposcache>
  ;; @funcinfo <localStorage cache for github repos with 1h ttl>
(def ^:private cache-key "github-repos-cache")
(def ^:private cache-ttl-ms (* 60 60 1000))

  ;; @funcinfo <keep only fields needed by the ui>
(defn- slim-repo
  [repo]
  (select-keys repo [:name :description :html_url :stargazers_count
                     :forks_count :license :language :languages :pushed_at :updated_at]))

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
  ;; @funcinfo <throw if github answers with non-ok status>
(defn- check-res!
  [res]
  (if (.-ok res)
    res
    (let [status (.-status res)]
      (throw (js/Error. (if (= status 403)
                          "github rate limit, try later"
                          (str "github error " status)))))))

  ;; @funcinfo <fetch languages for one repo, empty map on failure>
(defn- fetch-one-langs!
  [repo]
  (-> (js/fetch (:languages_url repo)
                #js {:cache "force-cache"
                     :headers #js {"Accept" "application/vnd.github+json"}})
      ;; @info <if langs fetch fails, keep repo without languages>
      (.then (fn [res] (if (.-ok res) (.json res) (js/Promise.resolve #js {}))))
      (.then (fn [langs] (assoc repo :languages (js->clj langs :keywordize-keys true))))
      (.catch (fn [_] (assoc repo :languages {})))))

  ;; @funcinfo <fetch languages in chunks of 5 to save rate limit>
(defn- fetch-langs-limited!
  [repos]
  (let [chunks (partition-all 5 repos)]
    (reduce (fn [acc chunk]
              (.then acc
                (fn [done]
                  (-> (js/Promise.all (into-array (map fetch-one-langs! chunk)))
                      ;; @info <if chunk succeeds, append to accumulator>
                      (.then (fn [res] (into done (array-seq res))))))))
            (js/Promise.resolve [])
            chunks)))

  ;; @funcinfo <fetch github repos, async, on-success (repos) and on-error (err-msg)>
(defn- fetch-fresh!
  [on-success on-error]

  (let [stale-by-name (into {} (map (fn [r] [(:name r) r])
                                    (or (read-cache) [])))]
    (-> (js/fetch "https://api.github.com/users/wakaranakattari/repos?sort=pushed&per_page=100"
                  #js {:cache "no-store"
                       :headers #js {"Accept" "application/vnd.github+json"}})
      ;; @info <if getting fetch is successfully, check status and parse to json>
      (.then check-res!)
      (.then #(.json %))
      ;; @info <if parse to json is successfully, convert to clj map and filter out forked repos>
      (.then (fn [data]
               (let [repos    (js->clj data :keywordize-keys true)
                     filtered (filter #(not (:fork %)) repos)
                     sorted   (sort-by :pushed_at #(compare %2 %1) filtered)
                     ;; @info <reuse cached languages when pushed_at is unchanged>
                     reuse    (into {} (keep (fn [r]
                                               (let [old (get stale-by-name (:name r))]
                                                 (when (and old
                                                              (= (:pushed_at old) (:pushed_at r))
                                                              (seq (:languages old)))
                                                   [(:name r) (:languages old)])))
                                             sorted))
                     to-fetch (remove #(contains? reuse (:name %)) sorted)]
                 (if (empty? to-fetch)
                   (js/Promise.resolve
                    (map #(assoc % :languages (get reuse (:name %))) sorted))
                   (-> (fetch-langs-limited! to-fetch)
                       ;; @info <if langs are loaded, merge reused and fallback to stale on empty>
                       (.then (fn [fetched]
                                (let [by-name (into reuse (map (fn [r] [(:name r) (:languages r)]) fetched))]
                                  (map (fn [r]
                                         (let [langs (get by-name (:name r))]
                                           (if (seq langs)
                                             (assoc r :languages langs)
                                             (if-let [old (get stale-by-name (:name r))]
                                               (assoc r :languages (:languages old))
                                               r))))
                                       sorted)))))))))
      ;; @info <if langs are ready, slim repos, write cache and return>
      (.then (fn [repos-with-langs]
               (let [slim (map slim-repo repos-with-langs)]
                 (write-cache! slim)
                 (on-success slim))))

      ;; @info <if fetch via api || parse to json || convert to clj map is failed>
      (.catch (fn [err]
                (on-error (.-message err)))))))
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