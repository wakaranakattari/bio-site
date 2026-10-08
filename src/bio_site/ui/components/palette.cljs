;; @file    <components/palette.cljs>
;; @author  <wakaranakattari@gmail.com>
;; @info    <command palette, cmd+k quick navigation>
;; @version <1.0>

;; @secstart->@secname <nsrq>
(ns bio-site.ui.components.palette
  (:require [clojure.string :as str]
            [reagent.core :as r]
            [bio-site.router :as router]
            [bio-site.data.articles :as articles]))
;; @secend->@secname   <nsrq>

;; @secstart->@secname <state>
  ;; @funcinfo <palette open state and search query>
(defonce open? (r/atom false))
(defonce query (r/atom ""))
;; @secend->@secname   <state>

;; @secstart->@secname <items>
  ;; @funcinfo <all palette entries: pages plus writing articles>
(defn- all-items []
  (concat [{:label "home" :hint "page" :path "/"}
           {:label "about" :hint "page" :path "/about"}
           {:label "projects" :hint "page" :path "/projects"}
           {:label "writing" :hint "page" :path "/writing"}
           {:label "communication" :hint "page" :path "/communication"}
           {:label "contacts" :hint "page" :path "/contacts"}]
          (map (fn [{:keys [slug title]}]
                 {:label title :hint "article" :path (str "/writing/" slug)})
               articles/articles)))
;; @secend->@secname   <items>

;; @funcinfo <open palette and reset query>
(defn open! []
  (reset! query "")
  (reset! open? true))

;; @funcinfo <close palette>
(defn close! []
  (reset! open? false))

;; @secstart->@secname <palette>
  ;; @funcinfo <command palette overlay with filter and keyboard navigation>
(defn palette []
  (let [selected (r/atom 0)]
    (r/create-class
     {:component-did-mount
      (fn [_]
        ;; @info <global keys: cmd+k toggles, esc closes>
        (set! (.-paletteKeys js/window)
              (fn [e]
                (cond
                  (and (or (.-metaKey e) (.-ctrlKey e)) (= (.-key e) "k"))
                  (do (.preventDefault e)
                      (if @open? (close!) (open!)))
                  (and @open? (= (.-key e) "Escape"))
                  (close!))))
        (.addEventListener js/window "keydown" (.-paletteKeys js/window)))
      :component-will-unmount
      (fn [_]
        (.removeEventListener js/window "keydown" (.-paletteKeys js/window)))
      :reagent-render
      (fn []
        (when @open?
          (let [q       (str/lower-case @query)
                matches (vec (take 8 (if (empty? q)
                                       (all-items)
                                       (filter #(str/includes? (str/lower-case (:label %)) q)
                                               (all-items)))))
                ;; @info <navigate and close on pick>
                go!     (fn [path]
                          (router/navigate! path)
                          (close!))]
            [:div.palette-overlay
             {:on-click (fn [e]
                          (when (= (.-target e) (.-currentTarget e))
                            (close!)))}
             [:div.palette-box
              [:input.palette-input
               {:type        "text"
                :placeholder "type a page or article..."
                :auto-focus  true
                :value       @query
                :on-change   (fn [e]
                               (reset! query (.. e -target -value))
                               (reset! selected 0))
                :on-key-down (fn [e]
                               (case (.-key e)
                                 "ArrowDown" (do (.preventDefault e)
                                                 (swap! selected #(min (dec (count matches)) (inc %))))
                                 "ArrowUp"   (do (.preventDefault e)
                                                 (swap! selected #(max 0 (dec %))))
                                 "Enter"     (when-let [item (nth matches @selected nil)]
                                               (go! (:path item)))
                                 nil))}]
              [:div.palette-list
               (if (seq matches)
                 (map-indexed
                  (fn [i {:keys [label hint path]}]
                    [:button.palette-item
                     {:key             (str label path)
                      :type            "button"
                      :class           (when (= i @selected) "selected")
                      :on-click        #(go! path)
                      :on-pointer-move #(reset! selected i)}
                     [:span.palette-label label]
                     [:span.palette-hint hint]])
                  matches)
                 [:div.palette-empty "nothing found"])]]]))) })))
;; @secend->@secname   <palette>
