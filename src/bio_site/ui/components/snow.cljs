;; @file    <components/snow.cljs>
;; @author  <wakaranakattari@gmail.com>
;; @info    <lightweight canvas snowfall overlay>
;; @version <1.1>

;; @secstart->@secname <nsrq>
(ns bio-site.ui.components.snow
  (:require [clojure.string :as str]
            [reagent.core :as r]))
;; @secend->@secname   <nsrq>

;; @secstart->@secname <reducedmotion>
  ;; @funcinfo <true when the user prefers reduced motion>
(defn- reduced-motion? []
  (and (exists? js/window)
       (.-matchMedia js/window)
       (.-matches (.matchMedia js/window "(prefers-reduced-motion: reduce)"))))
;; @secend->@secname   <reducedmotion>

;; @secstart->@secname <snowcolor>
  ;; @funcinfo <read snow color from css var, white fallback>
(defn- snow-color []
  (try
    (let [c (str/trim (-> js/window
                           (.getComputedStyle (.-documentElement js/document))
                           (.getPropertyValue "--snow-color")))]
      (if (empty? c) "#ffffff" c))
    (catch js/Error _ "#ffffff")))
;; @secend->@secname   <snowcolor>

;; @secstart->@secname <snow>
  ;; @funcinfo <fixed canvas snowfall, pauses offscreen, off with reduced motion>
(defn snow []
  (let [raf        (atom nil)
        on-resize  (atom nil)
        canvas-ref (atom nil)]
    (r/create-class
     {:component-did-mount
      (fn [_]
        (let [canvas @canvas-ref]
          (when canvas
            (let [ctx    (.getContext canvas "2d")
                  flakes (atom [])
                  color  (atom "#ffffff")
                  frame  (atom 0)]
              ;; @info <flake field sized to viewport>
              (letfn [(seed! []
                        (let [w   (.-innerWidth js/window)
                              h   (.-innerHeight js/window)
                              dpr (or (.-devicePixelRatio js/window) 1)]
                          (set! (.-width canvas) (* w dpr))
                          (set! (.-height canvas) (* h dpr))
                          (reset! flakes
                                  (vec (for [_ (range (min 90 (max 30 (quot w 14))))]
                                         {:x  (rand w)
                                          :y  (rand h)
                                          :r  (+ 0.8 (rand 2.2))
                                          :s  (+ 0.25 (rand 0.75))
                                          :ph (rand (* 2 js/Math.PI))
                                          :o  (+ 0.35 (rand 0.45))})))))]
                (seed!)
                (reset! color (snow-color))
                (reset! on-resize (fn [] (seed!)))
                (.addEventListener js/window "resize" @on-resize)
                ;; @info <fall with sine drift, wrap at the bottom>
                (letfn [(tick []
                          (let [w   (.-innerWidth js/window)
                                h   (.-innerHeight js/window)
                                dpr (or (.-devicePixelRatio js/window) 1)]
                            (.clearRect ctx 0 0 (* w dpr) (* h dpr))
                            (.save ctx)
                            (.scale ctx dpr dpr)
                            ;; @info <refresh theme color twice a second>
                            (when (zero? (mod (swap! frame inc) 30))
                              (reset! color (snow-color)))
                            (set! (.-fillStyle ctx) @color)
                            (swap! flakes
                                   (fn [fs]
                                     (mapv (fn [{:keys [x y r s ph o]}]
                                             (let [ny (+ y s)
                                                   nx (+ x (* 0.4 (js/Math.sin (+ ph (/ ny 60)))))]
                                               (set! (.-globalAlpha ctx) o)
                                               (.beginPath ctx)
                                               (.arc ctx nx (mod ny (+ h 8)) r 0 (* 2 js/Math.PI))
                                               (.fill ctx)
                                               {:x nx :y (if (> ny (+ h 8)) -8 ny)
                                                :r r :s s :ph ph :o o}))
                                           fs)))
                            (.restore ctx)
                            (reset! raf (js/requestAnimationFrame tick))))]
                  (reset! raf (js/requestAnimationFrame tick))))))))
      :component-will-unmount
      (fn [_]
        (when @raf (js/cancelAnimationFrame @raf))
        (when @on-resize (.removeEventListener js/window "resize" @on-resize)))
      :reagent-render
      (fn []
        (when-not (reduced-motion?)
          [:canvas.snow-canvas {:ref (fn [el] (reset! canvas-ref el))}]))})))
;; @secend->@secname   <snow>
