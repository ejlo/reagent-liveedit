(ns {{ns-name}}.dev-env
  (:require [figwheel.client :as figwheel :include-macros true]
            [weasel.repl :as weasel]
            [reagent.core :as r]
            [{{ns-name}}.core :as core]
            [{{ns-name}}.testrunner :as testrunner]))

(enable-console-print!)

(figwheel/start
 {:websocket-url "ws://localhost:3449/figwheel-ws"
  :on-jsload (fn []
               (core/render)
               (testrunner/run-tests))})

(defn run []
  (weasel/connect "ws://localhost:9001" :verbose true)
  (core/init!)
  (testrunner/run-tests))

(set! (.-onload js/window) run)
