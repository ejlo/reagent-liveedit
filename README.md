# reagent-liveedit

A leiningen template based on [reagent-template](https://github.com/reagent-project/reagent-template) for live editing a clojurescript web app.

## Added Features

* Uses [garden](https://github.com/noprompt/garden) to create stylesheets with clojure
* Runs tests automatically and displays the results directly in the web app

## Dependencies

* [leiningen](leiningen.org) and [phantomjs](http://phantomjs.org)

## Usage

```bash
lein new reagent-liveedit myapp
cd myapp
lein dev
```

### Emacs browser repl

* Start the repl with ``M-x cider-jack-in``
* Start the browser repl with ``(broswer-repl)``
* Reload the broswer page

