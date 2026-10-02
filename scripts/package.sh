#!/usr/bin/env bash
set -euo pipefail
mvn -B package -DskipTests
rm -rf dist/GreetingsApp && mkdir -p dist
jpackage --type app-image --name GreetingsApp --app-version 2.0.0 --vendor sofoste93 --description "Event Horizon first-contact station" --input target --main-jar GreetingsApp.jar --main-class com.sofoste.greetings.GreetingsApp --dest dist

