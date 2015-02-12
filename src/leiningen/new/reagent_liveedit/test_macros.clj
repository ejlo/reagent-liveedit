(ns {{ns-name}}.test-macros
  (:require [cemerick.cljs.test :as t :refer-macros [is deftest]]
            #_[reagent.core :as r]
            #_[reagent.cursor :as rc]
            #_[{{ns-name}}.state :as state]))

;; from clojurescript source code
(defmacro assert-args [fnname & pairs]
  `(do (when-not ~(first pairs)
         (throw (IllegalArgumentException.
                  ~(clojure.core/str fnname " requires " (second pairs)))))
     ~(clojure.core/let [more (nnext pairs)]
        (when more
          (list* `assert-args fnname more)))))

(defmacro defdomtest [name value-map & body]
  (assert-args defdomtest
               (map? value-map) "a map of cursor and values")
 `(cemerick.cljs.test/deftest ~name
    (let [old-state# @{{ns-name}}.state/app-state]
      ~@(for [[path v] value-map]
          (list 'reset! `(reagent.core/cursor {{ns-name}}.state/app-state ~path) v))
      (reagent.core/force-update-all)
      ~@body
      ({{ns-name}}.state/reset-state! old-state#)
      (reagent.core/force-update-all))))

(defmacro defdom [name]
  `(def ~name nil))
