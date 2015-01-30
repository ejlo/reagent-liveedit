(ns {{ns-name}}.state
    (:require [reagent.core :as reagent]))

(defonce init-state {:text "Hello, this is: "})

(defonce app-state (reagent/atom init-state))

(defn get-state
  ([cursor]
     (get-in @app-state cursor))
  ([cursor default]
     (get-in @app-state cursor default)))

(defn put! [cursor v]
  (swap! app-state assoc-in cursor v))

(defn update! [cursor f & args]
  (apply swap! app-state update-in cursor f args))

(defn reset-state! [s]
  (reset! app-state s))

(defn clean-state! []
  (reset-state! init-state))
