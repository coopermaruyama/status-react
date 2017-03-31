(ns status-im.transactions.styles.list-item
  (:require-macros [status-im.utils.styles :refer [defstyle]])
  (:require [status-im.components.styles :as st]))

(def item
  {:padding-vertical   20
   :padding-horizontal 16
   :flex               1
   :flex-direction     :row
   :align-items        :center})

(def item-photo
  {:width            86
   :height           48
   :border-radius    100
   :background-color st/color-dark-blue-3
   :flex-direction   :row
   :align-items      :center})

(def item-info
  {:margin-left 16
   :flex        1})

(def item-info-recipient
  {:color       st/color-light-blue
   :font-size   15
   :flex-shrink 1})

(def item-info-amount
  {:color     st/color-white
   :font-size 19})

(def item-deny-btn
  {:margin-left 16})

(def item-deny-btn-icon
  {:width  24
   :height 24})

(def photo
  {:borderRadius 100
   :width        48
   :height       48})

(def item-photo-icon
  {:margin-left 4
   :width       24
   :height      24})
