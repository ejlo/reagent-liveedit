(ns {{ns-name}}.core-test
  (:require [cemerick.cljs.test :refer-macros [is deftest done use-fixtures]]
            [dommy.core :as dommy :refer-macros [sel sel1]]
            [reagent.core :as reagent]
            [{{ns-name}}.core :as {{ns-name}}]
            [{{ns-name}}.state :as state :refer [cur]]))

(def saved-app-state (atom nil))

(defn save-app-state [f]
  (reset! saved-app-state @state/app-state)
  (f)
  (reset! state/app-state @saved-app-state))

(use-fixtures :once save-app-state)

(deftest trivial-pass
  (is (= 1 1)))

(deftest trivial-fail
  #_(is (= 2 1)))

(deftest trivial-error
  #_(is (1 = 1) "Don't do this!!!"))

(deftest ^:async trivial-async-test
  (let [now #(.getTime (js/Date.))
        t (now)]
    (js/setTimeout
      (fn []
        (is (>= (now) (+ t 10)))
        (done))
      10)))

(deftest page1
  (reset! (cur [:current-page]) :page1)
  (reagent/force-update-all)
  (is (= (some-> [:#main :h2] sel1 dommy/text)
         "Hello, this is: Page 1")))

(deftest page2
  (reset! (cur [:current-page]) :page2)
  (reagent/force-update-all)
  (is (= (some-> [:#main :h2] sel1 dommy/text)
         "Hello, this is: Page 2")))

(deftest test-component
  (reset! (cur [:test :pass]) 2)
  (reagent/force-update-all)
  (is (= "2" (some-> [".test" ".pass" :span] sel second dommy/text))))
