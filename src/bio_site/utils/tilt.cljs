;; @file    <utils/tilt.cljs>
;; @author  <wakaranakattari@gmail.com>
;; @info    <subtle 3d tilt on cards, fine pointers only>
;; @version <1.0>

;; @secstart->@secname <ns>
(ns bio-site.utils.tilt)
;; @secend->@secname   <ns>

;; @secstart->@secname <tiltenabled>
  ;; @funcinfo <tilt only with a fine pointer and without reduced motion>
(defn- tilt-enabled? []
  (and (exists? js/window)
       (.-matchMedia js/window)
       (.-matches (.matchMedia js/window "(pointer: fine)"))
       (not (.-matches (.matchMedia js/window "(prefers-reduced-motion: reduce)")))))
;; @secend->@secname   <tiltenabled>

;; @secstart->@secname <tiltprops>
  ;; @funcinfo <hiccup handlers for subtle card tilt, max-deg caps the angle>
(defn tilt-props
  ([] (tilt-props 6))
  ([max-deg]
   (if-not (tilt-enabled?)
     {}
     {:on-pointer-move
      (fn [e]
        (let [el   (.-currentTarget e)
              rect (.getBoundingClientRect el)
              px   (/ (- (.-clientX e) (.-left rect)) (.-width rect))
              py   (/ (- (.-clientY e) (.-top rect)) (.-height rect))
              rx   (* max-deg (- 0.5 py) 2)
              ry   (* max-deg (- px 0.5) 2)]
          ;; @info <inline transform overrides the css hover lift>
          (set! (.. el -style -transform)
                (str "perspective(700px) rotateX(" rx "deg) rotateY(" ry "deg) translateY(-2px)"))))
      :on-pointer-leave
      (fn [e]
        ;; @info <clear inline style, css hover takes over again>
        (let [el (.-currentTarget e)]
          (set! (.. el -style -transform) "")))})))
;; @secend->@secname   <tiltprops>
