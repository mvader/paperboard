(ns paperboard.services.news
  (:require [paperboard.services.http :as http]
            [cljs.core.async :refer [chan close! <!]])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(defn- paged-reddit-url
  "Returns the given URL of a subreddit with the proper pagination"
  [url after]
  (str url "?limit=25&after=" after))

(defn- paged-rss-url
  "Returns the given URL of a RSS feed with the proper pagination"
  [url page]
  "not implemented yet")

(defn- paged-url
  "Returns the given URL with the proper pagination added to it"
  [col-type url page]
  (case col-type
    :reddit (paged-reddit-url url page)
    :rss    (paged-rss-url url page)))

(defn- reddit-post-to-item
  "Converts the JSON result of a reddit post to the item format"
  [post]
  (let [data (get post "data")]
    {:id       (get data "id")
     :type     :reddit
     :comments (str "http://reddit.com" (get data "permalink"))
     :url      (get data "url")
     :title    (get data "title")}))

(defn- get-reddit
  "Retrieves the feed with the latest news of a subreddit"
  [url]
  (let [ch (chan 1)]
    (go (let [data (get (<! (http/get-json url)) "data")]
          (>! ch {:next-page (get data "after")
                  :items     (map reddit-post-to-item (get data "children"))})
          (close! ch)))
    ch))

(defn- get-rss
  "Retrieves the feed with the latest news of a RSS feed"
  [url]
  "not implemented yet")

(defn get-news
  "Retrieves the items for the news column in a JSON format"
  ([col-type url] (case col-type
                    :reddit (get-reddit url)
                    :rss    (get-rss url)))
  ([col-type url page] (let [req-url (paged-url col-type url page)]
                         (get-news col-type url))))
