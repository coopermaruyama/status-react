(ns status-im.components.text-input-with-label.styles
  (:require-macros [status-im.utils.styles :refer [defstyle defnstyle]])
  (:require [status-im.utils.platform :refer [ios?]]))

(defstyle text-input
  {:placeholder  ""
   :ios          {:height         26
                  :margin-top     24
                  :margin-bottom  6
                  :font-size      17
                  :letter-spacing -0.2}})

(defstyle component-container
  {:padding-left 16
   :ios          {:min-height 78}})

(defnstyle label-animated-text-style [label-top label-font-size]
  {:position  :absolute
   :top       label-top
   :font-size label-font-size
   :color     "#939ba1"
   :ios       {:letter-spacing -0.2}})

(defn label-animated-text [{:keys [label-top label-font-size]}]
  {:style (label-animated-text-style label-top label-font-size)})

(defstyle description-text
  {:style
   {:ios {:color          "#939ba1"
          :margin-top     4
          :height         16
          :font-size      14
          :letter-spacing -0.2}}})

(defstyle underline-blured
  {:background-color "#ececf0"
   :align-items      :center})

(defnstyle underline-focused [underline-width underline-height]
  {:style
   {:height           underline-height
    :width            underline-width
    :background-color "#628fe3"}})

(def label-top-top (if ios? 6 0))

(def label-top-bottom (if ios? 26 0))

(def label-font-size-top (if ios? 14 0))

(def label-font-size-bottom (if ios? 17 0))

(def underline-max-height (if ios? 1 2))
