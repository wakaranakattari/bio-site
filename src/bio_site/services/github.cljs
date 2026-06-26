;; @file    <services/github.cljs>
;; @author  <wakaranakattari@gmail.com>
;; @info    <github fetch api for dynamic loading repos>
;; @version <1.5>

;; @secstart->@secname <ns>
(ns bio-site.services.github)
;; @secend->@secname   <ns>

;; @secstart->@secname <fetchrepos>
  ;; @funcinfo <fetch github repos, async, takes two callbacks: on-success (repos) and on-error (err-msg)>
(defn fetch-repos!
  [on-success on-error]

  ;; @info <use AbortController for cleanup, but keep it simple for speed>
  ;; @info <fetch with cache: force-cache for faster subsequent loads>
  (-> (js/fetch "https://api.github.com/users/wakaranakattari/repos?sort=updated&per_page=100" 
                #js {:cache "force-cache"})
      ;; @info <if getting fetch is successfully, trying parse to json>
      (.then #(.json %))
      ;; @info <if parse to json is successfully, convert to clj map and filter out forked repos>
      (.then (fn [data]
               (let [repos    (js->clj data :keywordize-keys true)
                     filtered (filter #(not (:fork %)) repos)]

                 ;; @info <fetch languages for each repo in parallel with Promise.all>
                 (-> (js/Promise.all
                      (clj->js
                       (map (fn [repo]
                              (-> (js/fetch (:languages_url repo) #js {:cache "force-cache"})
                                  (.then #(.json %))
                                  ;; @info <attach languages map to repo>
                                  (.then (fn [langs]
                                           (assoc repo :languages (js->clj langs))))))
                            filtered)))
                     ;; @info <on success: pass repos with languages to callback>
                     (.then (fn [repos-with-langs]
                              (on-success (js->clj repos-with-langs :keywordize-keys true))))))))

      ;; @info <if fetch via api || parse to json || convert to clj map is failed>
      (.catch (fn [err]
                (on-error (.-message err))))))
;; @secend->@secname   <fetchrepos>

;; @secstart->@secname <calcpercents>
  ;; @funcinfo <calculate language percentages from raw bytes map>
(defn calc-lang-percents
  [languages]

  ;; @info <sum all bytes>
  (let [total (reduce + (vals languages))]
    (if (zero? total)
      []
      ;; @info <map each language to name + percentage, sort descending>
      (->> languages
           (map (fn [[lang bytes]]
                  {:lang    (name lang)
                   :percent (/ (* bytes 100.0) total)}))
           (sort-by :percent >)))))
;; @secend->@secname   <calcpercents>