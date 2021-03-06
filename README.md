# Paperboard

Paperboard is a self-hosted tool to keep up with the headlines of the sites you are interested in. 
The tool consists in a Kanban-like structure, with a column for every site you add. The columns can be of two types: a reddit subreddit or an RSS feed.

**Note:** it is in a very early stage and errors may —and will— appear.

## Development

Open a terminal and type `lein repl` to start a Clojure REPL
(interactive prompt).

In the REPL, type

```clojure
(run)
(browser-repl)
```

To start the backend server run:

```bash
lein run -m paperboard.api
```

## Build

First, edit the file `env/prod/cljs/paperboard/main.cljs` and set the URL of your backend and other configuration variables.

Then, build the `jar` file with leiningen.

```bash
lein uberjar
```

To build the frontend run this command:
```bash
lein with-profile uberjar cljsbuild once
```

You can copy everything under the `resources` directory and serve it statically.

## Run the service

You can serve the frontend statically as mentioned before, but if you really want to you can do it like this:
```bash
java -cp target/paperboard.jar clojure.main -m paperboard.server
```

To run the backend use this command:
```bash
java -cp target/paperboard.jar clojure.main -m paperboard.api
```

