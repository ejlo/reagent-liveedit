(ns {{ns-name}}.state
    (:require [reagent.core :as reagent]))

(defonce init-state {:text "Hello, this is: "})

(defonce app-state (reagent/atom init-state))

(def -cur
  (memoize
   (fn [atom path]
     (reagent/cursor atom path))))

(defn cur
  ([path]
   (-cur app-state path))
  ([atom path]
   (-cur atom path)))

(defn reset-state! [s]
  (reset! app-state s))

(defn clean-state! []
  (reset-state! init-state))
