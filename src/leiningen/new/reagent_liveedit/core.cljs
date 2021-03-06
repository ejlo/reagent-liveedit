(ns {{ns-name}}.core
  (:require [reagent.core :as reagent]
            [secretary.core :as secretary :include-macros true]
            [goog.events :as events]
            [goog.history.EventType :as EventType]
            [{{ns-name}}.state :as state :refer [cur]]
            [{{ns-name}}.test :as test])
  (:import goog.History))

;; -------------------------
;; Views

(defmulti page identity)

(defmethod page :page1 [_]
  [:div [:h2 @(cur [:text]) "Page 1"]
   [:div [:a {:href "#/page2"} "go to page 2 →"]]])

(defmethod page :page2 [_]
  [:div [:h2 @(cur [:text]) "Page 2"]
   [:div [:a {:href "#/"} "← go to page 1"]]])

(defmethod page :default [_]
  [:div "Invalid/Unknown route"])

(defn main-page []
  [:div#main [page @(cur [:current-page])]
   [test/test-component]])

;; -------------------------
;; Routes
(secretary/set-config! :prefix "#")

(secretary/defroute "/" []
  (reset! (cur [:current-page]) :page1))

(secretary/defroute "/page2" []
  (reset! (cur [:current-page]) :page2))

;; -------------------------
;; Initialize app
(defn render []
  (let [app (.getElementById js/document "app")]
    (reagent/render [main-page] app)))

(defn init! []
  (render))

;; -------------------------
;; History
(defn hook-browser-navigation! []
  (doto (History.)
    (events/listen
     EventType/NAVIGATE
     (fn [event]
       (secretary/dispatch! (.-token event))))
    (.setEnabled true)))
;; need to run this after routes have been defined
(hook-browser-navigation!)
