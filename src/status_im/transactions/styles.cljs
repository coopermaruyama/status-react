(ns status-im.transactions.styles
  (:require-macros [status-im.utils.styles :refer [defstyle]])
  (:require [status-im.components.styles :as st]))

(def transactions-toolbar-background st/color-dark-blue-1)

(def transactions-screen
  {:flex 1
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

(def form-container
  {:flex 1
   :paddingLeft 16})

(def password-style
  {:color     :white
   :font-size 12})

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
