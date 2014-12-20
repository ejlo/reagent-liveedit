# reagent-liveedit

A leiningen template based on [reagent-template](https://github.com/reagent-project/reagent-template) for live editing in clojurescript web app.

## About

This is a collections of tools for getting started developing web apps with clojurescript. It is mostly an example, since there are so many way to set it up, so you will likely need to do a bit of configuring to get it how you like it.

## Features

* uses [reagent](http://holmsand.github.io/reagent/) as react framework 
* uses [garden](https://github.com/noprompt/garden) to create stylesheets with clojure 
* live reloading of css and js files using lein-figwheel and weasel
* automatically run tests and display the results directly in the web app
* emacs brower-repl

## Dependencies

* [leiningen](leiningen.org) and [phantomjs](http://phantomjs.org)


## Install

Clone the repo and in its root directory, run

```bash
lein install
```

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

