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

(defview confirm []
  [transactions [:transactions]
   {:keys [password]} [:get :confirm-transactions]
   wrong-password? [:wrong-password?]]
  [view st/transactions-screen
   [status-bar {:type :transparent}]
   [toolbar-view transactions]
   [list-view {:dataSource (lw/to-datasource transactions)
               :renderRow  (fn [row _ _]
                             (list-item [transactions-list-item/view row]))}]
   [view st/form-container
    [text-field
     {:editable          true
      :error             (when wrong-password? (label :t/wrong-password))
      :error-color       :#ffffff80 #_:#7099e6
      :label             (label :t/password)
      :secure-text-entry true
      :label-color       :#ffffff80
      :line-color        :white
      :input-style       st/password-style
      :on-change-text    #(dispatch [:set-in [:confirm-transactions :password] %])}]]])
