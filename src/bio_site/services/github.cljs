(ns bio-site.services.github)

(defn fetch-repos!
  [on-success on-error]
  (-> (js/fetch "https://api.github.com/users/wakaranakattari/repos?sort=updated&per_page=100")
      (.then #(.json %))
      (.then (fn [data]
               (let [repos (js->clj data :keywordize-keys true)
                     filtered (filter #(not (:fork %)) repos)]
                 (on-success filtered))))
      (.catch (fn [err]
                (on-error (.-message err))))))
