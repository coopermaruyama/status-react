(ns status-im.transactions.screen
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
            [status-im.components.styles :refer [icon-ok
                                                 icon-close]]
            [status-im.components.confirm-button :refer [confirm-button]]
            [status-im.components.status-bar :refer [status-bar]]
            [status-im.components.toolbar-new.actions :as act]
            [status-im.components.toolbar-new.view :refer [toolbar]]
            [status-im.transactions.views.list-item :as transactions-list-item]
            [status-im.transactions.views.password-form :as password-form]
            [status-im.transactions.styles :as st]
            [status-im.utils.listview :as lw]
            [status-im.i18n :refer [label label-pluralize]]
            [clojure.string :as s]))

(defn toolbar-view [transactions]
  [toolbar
   {:background-color st/transactions-toolbar-background
    :nav-action       (act/close-white #(dispatch [:deny-transactions]))
    :custom-content   [view {:style st/toolbar-title-container}
                       [text {:style st/toolbar-title-text}
                        (label :t/pending-transactions)]
                       [text {:style st/toolbar-title-count}
                        (count transactions)]]}])

(defn render-row-fn [row _ _]
  (list-item
   [touchable-highlight {:on-press #(dispatch [:navigate-to-modal :transaction-details row])}
    [view
     [transactions-list-item/view row]]]))

(defview confirm []
  [transactions [:transactions]
   {:keys [password]} [:get :confirm-transactions]
   confirmed?        [:get-in [:transactions-list-ui-props :confirmed?]]]
  {:component-will-unmount #(dispatch [:set-in [:transactions-list-ui-props :confirmed?] false])}
  [view st/transactions-screen
   [status-bar {:type :transparent}]
   [toolbar-view transactions]
   [view {:style st/transactions-screen-content-container}
    [list-view {:style      st/transactions-list
                :dataSource (lw/to-datasource transactions)
                :renderRow  render-row-fn}]
    (when confirmed?
      [password-form/view (count transactions)])]
   (let [confirm-text (if confirmed?
                        (label :t/confirm)
                        (label-pluralize (count transactions) :t/confirm-transactions))
         confirm-fn   (if confirmed?
                        #(dispatch [:accept-transactions password])
                        #(dispatch [:set-in [:transactions-list-ui-props :confirmed?] true]))]
     [confirm-button confirm-text confirm-fn])])
