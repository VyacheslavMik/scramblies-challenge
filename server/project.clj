(defproject server "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [compojure "1.6.1"]
                 [ring/ring-defaults "0.3.2"]
                 [ring/ring-jetty-adapter "1.6.3"]
                 [ring/ring-devel "1.6.3"]
                 [ring/ring-json "0.4.0"]]
  :main ^:skip-aot server.core
  :target-path "target/%s"
  :plugins [[lein-ring "0.12.5"]]
  :resource-paths ["../client/resources"]
  :ring {:handler server.core/app}
  :profiles {:dev {:dependencies [[cider/piggieback "0.4.0"]
                                  [nrepl "0.6.0"]]}
             :uberjar {:aot :all}})
