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
                                                 icon-close
                                                 color-light-blue
                                                 color-light-red2]]
            [status-im.components.confirm-button :refer [confirm-button]]
            [status-im.components.status-bar :refer [status-bar]]
            [status-im.components.toolbar-new.actions :as act]
            [status-im.components.toolbar-new.view :refer [toolbar]]
            [status-im.components.text-field.view :refer [text-field]]
            [status-im.transactions.views.list-item :as transactions-list-item]
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

(defview password-form []
  [wrong-password? [:wrong-password?]]
  [view st/password-container
   [text {:style st/password-title} (label :t/enter-password-transactions)]
   [text-field
    {:editable               true
     :secure-text-entry      true
     :label-hidden?          true
     :error                  (when wrong-password? (label :t/wrong-password))
     :error-color            color-light-red2
     :placeholder            (label :t/password)
     :placeholder-text-color "#ffffff33"
     :line-color             color-light-blue
     :focus-line-height      2
     :wrapper-style          st/password-input-wrapper
     :input-style            st/password-input
     :on-change-text         #(dispatch [:set-in [:confirm-transactions :password] %])}]])

(defview confirm []
  [transactions [:transactions]
   {:keys [password]} [:get :confirm-transactions]
   confirmed?        [:get-in [:transactions-list-ui-props :confirmed?]]]
  {:component-will-unmount #(dispatch [:set-in [:transactions-list-ui-props :confirmed?] false])}
  [view st/transactions-screen
   [status-bar {:type :transparent}]
   [toolbar-view transactions]
   [list-view {:style        st/transactions-list
               :dataSource   (lw/to-datasource transactions)
               :renderRow    (fn [row _ _] (list-item [transactions-list-item/view row]))
               :renderFooter (fn [] (when confirmed? (list-item [password-form])))}]
   (let [confirm-text (if confirmed?
                        (label :t/confirm)
                        (label-pluralize (count transactions) :t/confirm-transactions))
         confirm-fn   (if confirmed?
                        #(dispatch [:accept-transactions password])
                        #(dispatch [:set-in [:transactions-list-ui-props :confirmed?] true]))]
     [confirm-button confirm-text confirm-fn])])
