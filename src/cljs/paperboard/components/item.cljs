(ns paperboard.components.item
  (:require [om.core :as om]
            [om.dom :as dom]))

(defn item
  "Item contains the title and the action to browse the URL of the news item"
  [data owner]
  (reify
    om/IRender
    (render [this]
      (dom/li #js {:className "item"} 
              (dom/a #js {:href (:url data) :target "_blank" :title (:title data)} 
                     (:title data))))))
