(ns status-im.transactions.screens.transaction-details
  (:require-macros [status-im.utils.views :refer [defview]])
  (:require [re-frame.core :as rf]
            [status-im.components.react :as rn]
            [status-im.components.common.common :as common]
            [status-im.components.confirm-button :as confirm-button]
            [status-im.components.status-bar :as status-bar]
            [status-im.components.toolbar-new.actions :as act]
            [status-im.components.toolbar-new.view :as toolbar]
            [status-im.transactions.styles.screens :as st]
            [status-im.transactions.views.list-item :as transactions-list-item]
            [status-im.transactions.views.password-form :as password-form]
            [status-im.i18n :as i18n]))

(defn toolbar-view []
  [toolbar/toolbar
   {:background-color st/transactions-toolbar-background
    :nav-action       (act/back-white #(rf/dispatch [:navigate-to-modal :pending-transactions]))
    :custom-content   [rn/view {:style st/toolbar-title-container}
                       [rn/text {:style st/toolbar-title-text}
                        (i18n/label :t/transaction)]]}])

(defn detail-item [title content name?]
  [rn/view {:style st/details-item}
   [rn/text {:style st/details-item-title} title]
   [rn/text {:style (st/details-item-content name?)} content]])

(defn detail-data [content]
  [rn/view {:style st/details-data}
   [rn/text {:style st/details-data-title} (i18n/label :t/data)]
   [rn/text {:style st/details-data-content} "0xf8sd9fsd98f9dsf98dsf90xf8sd9fsd98f9dsf98dsf90xf8sd9fsd98f9dsf98dsf90xf8sd9fsd98f9dsf98dsf90xf8sd9fsd98f9dsf98dsf90xf8sd9fsd98f9dsdsf9"]])

(defview details [{:keys [from to data gas gas-price] :as transaction}]
  [sender    [:contact-by-address from]
   recipient [:contact-by-address to]]
  [rn/view
   [detail-item (i18n/label :t/to) (:name recipient) true]
   [detail-item (i18n/label :t/from) (:name sender) true]
   [detail-item (i18n/label :t/est-fee) "0.00232 ETH" false]
   [detail-item (i18n/label :t/max-fee) "0" false]
   [detail-item (i18n/label :t/gas-price) "0.02 per million gas" false]
   [detail-data data]])

(defview transaction-details []
  [{:keys [id] :as transaction} [:get :selected-transaction]
   {:keys [password]}           [:get :confirm-transactions]
   confirmed?                   [:get-in [:transaction-details-ui-props :confirmed?]]]

  {:component-will-unmount #(rf/dispatch [:set-in [:transaction-details-ui-props :confirmed?] false])}

  [rn/view {:style st/transactions-screen}
   [status-bar/status-bar {:type :transparent}]
   [toolbar-view]
   [rn/scroll-view st/details-screen-content-container
    [transactions-list-item/view transaction #(rf/dispatch [:navigate-to-modal :pending-transactions])]
    [common/separator {} st/details-separator]
    [details transaction]]
   (when confirmed? [password-form/view 1])
   (let [confirm-text (if confirmed?
                        (i18n/label :t/confirm)
                        (i18n/label-pluralize 1 :t/confirm-transactions))
         confirm-fn   (if confirmed?
                        #(rf/dispatch [:accept-transaction password id])
                        #(rf/dispatch [:set-in [:transaction-details-ui-props :confirmed?] true]))]
     [confirm-button/confirm-button confirm-text confirm-fn])])
