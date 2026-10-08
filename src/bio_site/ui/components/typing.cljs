;; @file    <components/typing.cljs>
;; @author  <wakaranakattari@gmail.com>
;; @info    <typewriter rotating roles component>
;; @version <1.0>

;; @secstart->@secname <nsrq>
(ns bio-site.ui.components.typing
  (:require [reagent.core :as r]))
;; @secend->@secname   <nsrq>

;; @secstart->@secname <reducedmotion>
  ;; @funcinfo <true when the user prefers reduced motion>
(defn- reduced-motion? []
  (and (exists? js/window)
       (.-matchMedia js/window)
       (.-matches (.matchMedia js/window "(prefers-reduced-motion: reduce)"))))
;; @secend->@secname   <reducedmotion>

;; @secstart->@secname <typingroles>
  ;; @funcinfo <typewriter cycling through roles, static first role without motion>
(defn typing-roles [roles]
  (let [state (r/atom {:text "" :idx 0 :pos 0 :mode :typing})
        timer (atom nil)
        ;; @info <clear pending timeout, used on unmount>
        cancel! (fn []
                  (when @timer
                    (js/clearTimeout @timer)
                    (reset! timer nil)))
        step  (fn step []
                (let [{:keys [idx pos mode]} @state
                      role (nth roles (mod idx (count roles)))]
                  (cond
                    ;; @info <typing forward, pause at the end>
                    (= mode :typing)
                    (if (< pos (count role))
                      (do (swap! state assoc
                                 :text (subs role 0 (inc pos))
                                 :pos (inc pos))
                          (reset! timer (js/setTimeout step 70)))
                      (do (swap! state assoc :mode :pausing)
                          (reset! timer (js/setTimeout step 1700))))
                    ;; @info <short pause, then start deleting>
                    (= mode :pausing)
                    (do (swap! state assoc :mode :deleting)
                        (reset! timer (js/setTimeout step 45)))
                    ;; @info <deleting backward, next role at zero>
                    :else
                    (if (> pos 0)
                      (do (swap! state assoc
                                 :text (subs role 0 (dec pos))
                                 :pos (dec pos))
                          (reset! timer (js/setTimeout step 35)))
                      (do (swap! state assoc :idx (inc idx) :pos 0 :mode :typing)
                          (reset! timer (js/setTimeout step 400)))))))]
    (r/create-class
     {:component-did-mount
      (fn [_]
        (if (reduced-motion?)
          (swap! state assoc :text (first roles))
          (reset! timer (js/setTimeout step 600))))
      :component-will-unmount
      (fn [_] (cancel!))
      :reagent-render
      (fn [_]
        [:span.typing-text
         (:text @state)
         [:span.typing-caret]])})))
;; @secend->@secname   <typingroles>
