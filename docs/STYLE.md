# WPDCS for Clojure

WPDCS is a documentation and code style standard that is portable across languages.  
This document describes how it applies to **Clojure**.

---

> An important note for readers: the title says it's a Perl standard, but the code in this repository is written exclusively in Clojure. Why is that? Because my coding style and formatting are fully portable to other programming languages, but I only use it in my own stack, namely Perl, Clojure, and D.
Historically, when I started writing in Perl, I decided to create a convenient commenting style and code formatting style (similar to GNU from C and C++). Since I found it so comfortable, I decided to use these comments throughout my projects.
This document is intended to provide a brief introduction to commenting and writing code.

---

## File Header

Every file starts with a header

```clojure
;; @file    services/github.cljs
;; @author  <wakaranakattari@gmail.com>
;; @info    <github fetch api for dynamic loading repos>
;; @version 1.3
```

| Tag | Description |
| :--- | :--- |
| @file | Path to file |
| @author | Contact email |
| @info | Brief description of what the file does |
| @version | Current version |

## Namespace Section & Namespace and Require section

### Namespace
The `ns` form is wrapped a section:

```clojure
;; @secstart->@secname <ns>
(ns bio-site.services.github)
;; @secend->@secname   <ns>
```

### Namespace & require

The `nsrq` form is wrapped a section

```clojure
;; @secstart->@secname <nsrq>
(ns bio-site.ui.pages.about
  (:require [bio-site.ui.components.header :as header]))
;; @secend->@secname   <nsrq>
```

## Function Documentation

Before each function:

```clojure
;; @secstart->@secname <fetchrepos>
  ;; @funcinfo <fetch github repos, async, takes two callbacks: on-success (repos) and on-error (err-msg)>
(defn fetch-repos! [on-success on-error]
  ...)
;; @secend->@secname <fetchrepos>
```

| Tag | Description |
| :--- | :--- |
| @secstart/@secend | Function boundaries |
| @funcinfo | What the function does and its parameters |

## Inline Comments

For steps inside a function

```clojure
;; @info <getting fetch via api>
(-> (js/fetch ...)
    ;; @info <if fetch succeeds, parse to json>
    (.then #(.json %))
    ;; @info <if parse succeeds, convert to clj map and filter out forks>
    (.then (fn [data] ...))
    ;; @info <if any step fails>
    (.catch (fn [err] ...)))
```

| Tag | Description |
| :--- | :--- |
| @info | Description of what a line or block does |

## Component Sections (UI)

For Reagent/React components:

```clojure
;; @secstart->@secname <repocard>
(defn repo-card [{:keys [name description ...]}]
  ;; @info <clicking takes you to the repo page>
  [:a {:href html_url}

   ;; @secstart->@secname <repomaster> :: @secinfo <gets name & stars>
   [:div.repo-master
    [:h3 name]
    [:span stargazers_count]]
   ;; @secend->@secname <repomaster>

   ;; @info <gets description>
   [:p description]

   ;; @secstart->@secname <repofooter> :: @secinfo <gets language>
   [:div.repo-footer
    (when language [:span language])]
   ;; @secend->@secname <repofooter>

   [:span forks_count]])
;; @secend->@secname <repocard>
```

| Tag | Description |
| :--- | :--- |
| :: @secinfo | Additional description for a section |
| @info | Description of a specific UI element |


## Summary of Tags

| Tag | Purpose |
| :--- | :--- |
| @file | File header |
| @author | Contact |
| @info | Description of file or code block |
| @version | Version |
| @secstart / @secend | Boundaries of a section (ns, function, component) |
| @funcinfo | Function documentation |
| :: @secinfo | Section description (attached to @secstart) |

## Why This Works

- You can open any file in your stack and immediately understand its structure
- Section boundaries are explicit, not dependent on indentation
- Documentation is part of the code, not a separate file
- The same habits work across Perl, Clojure, and D
