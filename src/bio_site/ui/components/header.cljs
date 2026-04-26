(ns bio-site.ui.components.header)

(defn header []
  [:header {:class "header"}
   [:a {:href "/" :class "nav-item"} "home"]
   [:a {:href "/about" :class "nav-item"} "about"]
   [:a {:class "nickname"} "wkrn."]
   [:a {:href "/projects" :class "nav-item"} "projects"]
   [:a {:href "/contacts" :class "nav-item"} "contacts"]])
