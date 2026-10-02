# Learning guide — from Hello World to a small product

GreetingsApp deliberately stays small. It demonstrates how a classroom-sized Java idea can gain structure, tests and distribution without hiding the fundamentals.

## 1. Follow one greeting

1. `GreetingsApp.main` selects terminal or graphical mode.
2. The Swing frame reads the name when Enter or the button is pressed.
3. `GreetingService.greet` trims input, selects a time-of-day message and localizes it.
4. The frame displays the returned string and asks the custom gate panel to pulse.

The service does not know about buttons or labels. That separation is why tests can call it without opening a window.

## 2. Run and experiment

```bash
mvn test
mvn package
java -jar target/GreetingsApp.jar
java -jar target/GreetingsApp.jar --greet "Ada"
```

Try adding a language: add its phrases in `GreetingService`, expose it in `Frame.settings`, then mirror the translation in `web/app.js`. Add a test before changing the interface.

## 3. What the browser version teaches

The PWA uses semantic HTML, responsive CSS and plain JavaScript. `manifest.webmanifest` makes it installable; `sw.js` caches its small application shell so it can start offline. Preferences stay locally in the browser and are never transmitted.

## 4. Packaging

`jpackage` combines the JAR with a trimmed Java runtime. Users therefore do not need to install or configure a JRE. GitHub Actions runs the command on each target operating system because native launchers must be built on that OS.

