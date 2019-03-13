#!/bin/bash

cd client && lein clean &&
lein with-profile prod cljsbuild once min &&
cd ../server &&
lein repl
