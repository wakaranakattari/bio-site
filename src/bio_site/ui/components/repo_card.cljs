(ns bio-site.ui.components.repo-card)

(defn repo-card [{:keys [name description html_url stargazers_count language forks_count]}]
  [:a {:href html_url :target "_blank" :class "repo-card"}
   [:div.repo-master
    [:h3 name]
    [:span.repo-stats stargazers_count]]
   [:p.repo-description description]
   [:div.repo-footer
    (when language
      [:span.repo-language language])
    [:span.repo-forks forks_count]]])
