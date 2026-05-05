(ns bio-site.ui.components.header)

(defn header []
  (let [path (.-pathname js/location)]
    (fn []
      [:header.header
       [:a.nav-item {:href "/" :class
                     (when (= path "/") "active")} "home"]

       [:a.nav-item {:href "/about" :class
                     (when (= path "/about") "active")} "about"]

       [:a.nav-item {:href "/projects" :class
                     (when (= path "/projects") "active")} "projects"]

       [:a.nav-item {:href "/writing" :class
                     (when (= path "/writing") "active")} "writing"]

       [:a.nav-item {:href "/contacts" :class
                     (when (= path "/contacts") "active")} "contacts"]])))
