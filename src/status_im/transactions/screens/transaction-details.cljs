(ns status-im.transactions.screens.transaction-details
  (:require-macros [status-im.utils.views :refer [defview]])
  (:require [re-frame.core :refer [subscribe dispatch]]
            [status-im.components.react :refer [view
                                                list-view
                                                list-item
                                                text
                                                image
                                                icon
                                                touchable-highlight
                                                touchable-opacity]]
            [status-im.components.confirm-button :as confirm-button]
            [status-im.components.status-bar :as status-bar]
            [status-im.components.toolbar-new.actions :as act]
            [status-im.components.toolbar-new.view :as toolbar]
            [status-im.transactions.views.list-item :as transactions-list-item]
            [status-im.transactions.styles :as st]
            [status-im.i18n :as i18n]))

(defn toolbar-view []
  [toolbar/toolbar
   {:background-color st/transactions-toolbar-background
    :custom-content   [view {:style st/toolbar-title-container}
                       [text {:style st/toolbar-title-text}
                        (i18n/label :t/transaction)]]}])

(defn detail-item [title content name?]
  [view {:style st/details-item}
   [text {:style st/details-item-title} title]
   [text (when name? {:style st/details-item-name-content}) content]])

(defn detail-data [content]
  [view {:style st/details-data}
   [text {:style st/details-item-title} (i18n/label :t/data)]
   [text content]])

(defview details [{:keys [from to data gas gas-price] :as transaction}]
  [sender    [:contact-by-address from]
   recipient [:contact-by-address to]]
  [view
   [detail-item (i18n/label :t/to) (:name recipient) true]
   [detail-item (i18n/label :t/from) (:name sender) true]
   [detail-item (i18n/label :t/est-fee) "0.00232 ETH" false]
   [detail-item (i18n/label :t/max-fee) "0" false]
   [detail-item (i18n/label :t/gas-price) "0.02 per million gas" false]
   [detail-data data]])

(defview transaction-details [transaction]
  [{:keys [password]} [:get :confirm-transactions]
   confirmed?        [:get-in [:transaction-details-ui-props :confirmed?]]]
  {:component-will-unmount #(dispatch [:set-in [:transaction-details-ui-props :confirmed?] false])}
  [view st/transactions-screen
   [status-bar/status-bar {:type :transparent}]
   [toolbar-view]
   [transactions-list-item/view transaction]
   [details transaction]
   (let [confirm-text (if confirmed?
                        (i18n/label :t/confirm)
                        (i18n/label-pluralize 1 :t/confirm-transactions))
         confirm-fn   (if confirmed?
                        #(dispatch [:accept-transactions password])
                        #(dispatch [:set-in [:transaction-details-ui-props :confirmed?] true]))]
     [confirm-button/confirm-button confirm-text confirm-fn])])
