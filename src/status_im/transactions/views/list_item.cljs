(ns status-im.transactions.views.list-item
  (:require-macros [status-im.utils.views :refer [defview]])
  (:require [re-frame.core :refer [subscribe dispatch]]
            [clojure.string :as string]
            [status-im.utils.identicon :as identicon]
            [status-im.components.react :as rn]
            [status-im.transactions.styles.list-item :as st]
            [status-im.i18n :as i18n]))

(defn item-image [recipient]
  (let [photo-path (:photo-path recipient)]
    [rn/view {:style st/item-photo}
     [rn/image {:source {:uri (if (string/blank? photo-path)
                                (identicon/identicon (:identity recipient))
                                photo-path)}
                :style  st/photo}]
     [rn/image {:source {:uri :icon_arrow_left_white}
                :style  st/item-photo-icon}]]))

(defn item-info [recipient-name value]
  [rn/view {:style st/item-info}
   [rn/text {:style           st/item-info-recipient
             :number-of-lines 1}
    recipient-name]
   [rn/text {:style st/item-info-amount} value]])

(defn deny-btn []
  [rn/view {:style st/item-deny-btn}
   [rn/image {:source {:uri :icon_close_white}
              :style st/item-deny-btn-icon}]])

(defview view [{:keys [to value] :as transaction}]
  [recipient [:contact-by-address to]]
  (let [eth-value      (.fromWei js/Web3.prototype value "ether")
        value          (str (i18n/label-number eth-value) " ETH")
        recipient-name (or (:name recipient) to)]
    [rn/touchable-highlight {:on-press #(dispatch [:navigate-to-modal :transaction-details transaction])}
     [rn/view {:style st/item}
      [item-image recipient]
      [item-info recipient-name value]
      [deny-btn]]]))
