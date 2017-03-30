(ns status-im.transactions.styles
  (:require-macros [status-im.utils.styles :refer [defstyle]])
  (:require [status-im.components.styles :as st]))

(def transactions-toolbar-background st/color-dark-blue-1)

(def transactions-screen
  {:flex             1
   :background-color st/color-dark-blue-2})

(defstyle toolbar-title-container
  {:flex           1
   :flex-direction :row
   :align-self     :stretch
   :android        {:padding-left 30}
   :ios            {:align-items :center}})

(def toolbar-title-text
  {:color     st/color-white
   :font-size 17})

(def toolbar-title-count
  {:color       st/color-white
   :font-size   17
   :opacity     0.2
   :margin-left 8})

(def transactions-list
  {:flex 1})

(def password-container
  {:margin-bottom 24
   :padding-left  16})

(def password-title
  {:color         st/color-white
   :font-size     15
   :margin-bottom 16})

(def password-input-wrapper
  {:position       :relative
   :height         56
   :padding-top    0
   :padding-bottom 0
   :margin-bottom  4})

(def password-input
  {:color          :white
   :height         52
   :padding-top    16
   :padding-bottom 16
   :font-size      16})

;; details

(def details-item
  {:margin-top   10
   :padding-left 16
   :font-size    15})

(def details-item-title
  {:width        80
   :margin-right 24
   :color        st/color-white
   :opacity      0.2})

(def details-item-name-content
  {:color st/color-light-blue})

(def details-data
  {:margin-top       10
   :padding          16
   :background-color st/color-dark-blue-3})

;; success

(def success-screen
  {:flex             1
   :background-color st/color-dark-blue-2})

(def success-screen-content-container
  {:flex            1
   :align-items     :center
   :justify-content :center})

(def success-icon-container
  {:background-color st/color-light-blue
   :border-radius    100
   :height           133
   :width            133
   :justify-content  :center
   :align-items      :center})

(def success-icon
  {:height 40
   :width  54})

(def success-text
  {:font-size  17
   :color      st/color-light-blue3
   :margin-top 26})
