(ns status-im.transactions.screens.confirmation-success
  (:require [re-frame.core :as rf]
            [status-im.components.react :as rn]
            [status-im.components.confirm-button :as confirm-button]
            [status-im.components.status-bar :as status-bar]
            [status-im.transactions.views.list-item :as transactions-list-item]
            [status-im.transactions.styles :as st]
            [status-im.i18n :as i18n]))

(defn confirmation-success [quantity]
  [rn/view st/success-screen
   [status-bar/status-bar {:type :transparent}]
   [rn/view {:style st/success-screen-content-container}
    [rn/view {:style st/success-icon-container}
     [rn/image {:source {:uri :icon_ok_white}
                :style  st/success-icon}]]
    [rn/view
     [rn/text {:style st/success-text}
      (i18n/label-pluralize quantity :t/transactions-confirmed)]]]
   [confirm-button/confirm-button
    (i18n/label :t/got-it)
    #(rf/dispatch [:navigate-back])]])
