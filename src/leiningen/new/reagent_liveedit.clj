(ns leiningen.new.reagent-liveedit
  (:use [leiningen.new.templates :only [renderer name-to-path sanitize-ns ->files]]))

(def render (renderer "reagent-liveedit"))

(defn reagent-liveedit
  [name]
  (let [data {:name name
              :ns-name (sanitize-ns name)
              :sanitized (name-to-path name)}]
    (->files
     data
     ["project.clj" (render "project.clj" data)]
     ["README.md" (render "README.md")]
     ["LICENSE" (render "LICENSE")]
     [".gitignore" (render ".gitignore")]
     ["src/cljs/{{sanitized}}/core.cljs" (render "core.cljs" data)]
     ["src/cljs/{{sanitized}}/state.cljs" (render "state.cljs" data)]
     ["src/cljs/{{sanitized}}/test.cljs" (render "test.cljs" data)]
     ["src/clj/{{sanitized}}/server/handler.clj" (render "handler.clj" data)]
     ["src/clj/{{sanitized}}/server/services.clj" (render "services.clj" data)]
     ["src/env/dev/cljs/{{sanitized}}/dev.cljs" (render "dev.cljs" data)]
     ["src/env/dev/cljs/{{sanitized}}/testrunner.cljs" (render "testrunner.cljs" data)]
     ["src/env/prod/cljs/{{sanitized}}/prod.cljs" (render "prod.cljs" data)]
     ["src/styles/{{sanitized}}/styles/dev.clj" (render "dev.clj" data)]
     ["src/styles/{{sanitized}}/styles/site.clj" (render "site.clj" data)]
     ["test/{{sanitized}}/core_test.cljs" (render "core_test.cljs" data)]
     ["test/{{sanitized}}/test_macros.clj" (render "test_macros.clj" data)]
     ["resources/public/index.html" (render "index.html" data)]

     ["resources/templates/js/phantomjs_polyfills.js" (render "phantomjs_polyfills.js")]
     )))
