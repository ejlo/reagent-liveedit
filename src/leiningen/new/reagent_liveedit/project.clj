(defproject {{ns-name}} "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :source-paths ["src/clj" "src/cljs"]
  :template-additions ["README.md"
                       "LICENSE"
                       ".gitignore"]

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [reagent "0.5.0-alpha3"]
                 [reagent-utils "0.1.2"]
                 [garden "1.2.5"]
                 [secretary "1.2.1"]
                 [org.clojure/clojurescript "0.0-2913" :scope "provided"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [com.cemerick/piggieback "0.1.5"]
                 [weasel "0.6.0"]
                 [ring "1.3.2"]
                 [ring/ring-defaults "0.1.4"]
                 [prone "0.8.1"]
                 [compojure "1.3.2"]
                 [environ "1.0.0"]
                 [leiningen "2.5.1"]
                 [figwheel "0.2.3-SNAPSHOT"]
                 [ring-mock "0.1.5"]
                 [ring/ring-devel "1.3.2"]
                 [pjstadig/humane-test-output "0.6.0"]
                 [prismatic/dommy "1.0.0"]]

  :plugins [[lein-cljsbuild "1.0.5"]
            [lein-garden "0.2.5"]
            [lein-environ "1.0.0"]
            [lein-ring "0.9.1"]
            [lein-exec "0.3.4"]
            [lein-pdo "0.1.1"]
            [com.cemerick/clojurescript.test "0.3.3"]
            [lein-figwheel "0.2.3-SNAPSHOT"]
            [cider/cider-nrepl "0.9.0-SNAPSHOT"]]

  :ring {:handler {{ns-name}}.server.handler/app}

  :min-lein-version "2.5.0"

  :clean-targets  ^{:protect false} ["target/"
                                     "resources/public/js/"
                                     "resources/public/css/"]


  :aliases {"server"   ["ring" "server"]
            "css"      ["garden" "auto" "dev"]
            "autotest" ["cljsbuild" "auto" "test"]
            "test"     ["cljsbuild" "once" "test"]
            "web"      ["with-profile" "production" "trampoline" "ring" "server"]
            "prod"     ["with-profile" "production" "do"
                        "clean,"
                        "garden" "once" "prod,"
                        "cljsbuild" "once" "app"]
            "live"     ["pdo" "css," "figwheel," "server"]
            "once"     ["do" "garden" "once" "dev,"
                        "cljsbuild" "once" "app"]
            "dev"      ["do" "cljsbuild" "once" "app," "live"]}

  :garden {:builds [{:id "prod"
                     :source-paths ["src/styles"]
                     :stylesheet {{ns-name}}.styles.site/site
                     :compiler {:output-to "resources/public/css/site.css"
                                :vendors ["webkit"]
                                :pretty-print? false}}
                    {:id "dev"
                     :source-paths ["src/styles"]
                     :stylesheet {{ns-name}}.styles.site/site
                     :env {:dev? true}
                     :compiler {:output-to "resources/public/css/site.css"
                                :vendors ["webkit"]
                                :pretty-print? true}}]}

  :cljsbuild {:builds {:app {:source-paths ["src/cljs"]
                             :compiler {:output-to "resources/public/js/app.js"}}
                       :test {:source-paths ["src/cljs" "src/env/test" "test"]
                              :notify-command ["phantomjs" "target/test/test.js"]
                              :compiler {:output-to "target/test/test.js"
                                         :optimizations :whitespace
                                         :pretty-print true
                                         :preamble ["templates/js/phantomjs_polyfills.js"]}}}
              :test-commands {"unit-tests" ["phantomjs" "target/test/test.js"]}}

  :profiles {:dev {:repl-options {:init-ns {{ns-name}}.server.handler
                                  :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}

                   :injections [(require 'pjstadig.humane-test-output)
                                (pjstadig.humane-test-output/activate!)]

                   :figwheel {:http-server-root "public"
                              :server-port 3449
                              :css-dirs ["resources/public/css"]
                              :server-logfile "logs/figwheel.log"
                              :repl false}

                   :env {:dev? true}

                   :cljsbuild
                   {:builds
                    {:app {:source-paths ["src/env/dev" "test"]
                           :compiler
                           {:main                 "{{ns-name}}.dev-env"
                            :asset-path           "js/out"
                            :output-dir           "resources/public/js/out"
                            :optimizations        :none
                            :cache-analysis       true
                            :source-map           "resources/public/js/out.js.map"
                            :source-map-timestamp true
                            :pretty-print         true}}}}}

             :production {:ring {:open-browser? false
                                 :stacktraces?  false
                                 :auto-reload?  false}
                          :env {:production true}
                          :cljsbuild
                          {:builds
                           {:app
                            {:source-paths ["src/env/prod"]
                             :compiler
                             {:main          "{{ns-name}}.prod-env"
                              :optimizations :advanced
                              :pretty-print  false}}}}}})
