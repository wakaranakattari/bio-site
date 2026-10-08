;; @file    <utils/reveal.cljs>
;; @author  <wakaranakattari@gmail.com>
;; @info    <scroll reveal helper via intersectionobserver>
;; @version <1.0>

;; @secstart->@secname <ns>
(ns bio-site.utils.reveal)
;; @secend->@secname   <ns>

;; @secstart->@secname <observer>
  ;; @funcinfo <shared observer singleton, adds reveal-visible once per element>
(defonce ^:private observer-ref (atom nil))

(defonce ^:private observer
  (delay
    (when (and (exists? js/IntersectionObserver)
               (exists? js/window))
      (let [obs (js/IntersectionObserver.
                 (fn [entries]
                   (doseq [entry entries]
                     (when (.-isIntersecting entry)
                       (let [el (.-target entry)]
                         ;; @info <revealed once, stop watching the element>
                         (.add (.-classList el) "reveal-visible")
                         (.unobserve @observer-ref el)))))
                 #js {:threshold 0.1})]
        (reset! observer-ref obs)
        obs))))
;; @secend->@secname   <observer>

;; @secstart->@secname <revealprops>
  ;; @funcinfo <hiccup props for scroll reveal, delay-ms staggers, extra-class merges>
(defn reveal-props
  ([] (reveal-props 0 nil))
  ([delay-ms] (reveal-props delay-ms nil))
  ([delay-ms extra-class]
   {:class (str "reveal" (when extra-class (str " " extra-class)))
    :style {:--reveal-delay (str delay-ms "ms")}
    :ref   (fn [el]
             (when el
               ;; @info <no observer support, show immediately>
               (if @observer
                 (.observe @observer el)
                 (.add (.-classList el) "reveal-visible"))))}))
;; @secend->@secname   <revealprops>
