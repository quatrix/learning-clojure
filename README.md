# Learning Clojure - YAY

## Roadmap (WIP)

What I've done so far that seems effective (I'm just starting out, will revisit this as I go):

1. Setup your dev env, get used to REPL-driven-development inside of the editor's buffer.
1. Start reading `Clojure For The Brave And True` - type out and try all the examples, it seems like a good general introduction
1. Start watching talks from Clojure confs to get yourself immersed/indoctrinated in Clojure, especially the Rich Hickey talks.
1. Koans/4clojure is probably a good way to get to know the core library a bit better
1. Finally start working on some small project like a simple game (`play-clj`) or a CRUD service (`reitit` seems pretty nice) 
1. ...
1. Profit!


## Development Environment

### NVIM (use nvim, it has better Clojure plugins)
* [Clojure in VIM 2020](https://blog.djy.io/conjuring-clojure-in-vim-2020-edition/)
* [Clojure in VIM - more](https://thoughtbot.com/blog/writing-clojure-in-vim)
* [Conjure demo](https://www.youtube.com/watch?v=lR2vbwuzrIM)

#### The plugins I use:
* `neoclide/coc.nvim` - LSP code completion, integration with linter, etc.
* `Olical/conjure` - For REPL driven development, let's you eval `forms` from the buffer
* `luochen1990/rainbow` - Colors matching ()
* `calebsmith/vim-lambdify` - Replaces `defn` and `fn` with λ
* `bhurlow/vim-parinfer` - Simpler LISP editing

## Links

* [Cheatsheet](https://clojure.org/api/cheatsheet) - for reference
* [Enterprise Clojure Training](https://enterpriseclojure.com/) - pretty much a Clojure in 24 hours.
* [Clojure by example](https://kimh.github.io/clojure-by-example/#about)
* [Clojure Animated](https://markm208.github.io/cljbook/) - examples are written and explained incrementally
* [Koans](https://github.com/functional-koans/clojure-koans)
* [Fork of 4clojure](https://4clojure.oxal.org/)
* [Gist of onboarding links](https://gist.github.com/yogthos/be323be0361c589570a6da4ccc85f58f)
* [Alternative getting started links](https://www.reddit.com/r/Clojure/comments/fpp9r8/how_hard_is_it_to_learn_clojure/flo1lyi/?utm_source=reddit&utm_medium=web2x&context=3)
* [How lazy-seq works](https://stackoverflow.com/questions/44095400/how-to-understand-clojures-lazy-seq)
* [Clojure Tool Box](https://www.clojure-toolbox.com/) - seems like a curated package index
* [Threading with style](https://stuartsierra.com/2018/07/06/threading-with-style) - how to use threading macros 
* [Code Smells](https://bsless.github.io/code-smells/)


## Books
* [Clojure For The Brave And True](https://www.braveclojure.com/) - covers all the basics, people usually start with this one.

_Didn't read yet! Top Reddit recommendations_
* [The Joy Of Clojure](https://www.manning.com/books/the-joy-of-clojure-second-edition) - more of a deep dive
* [Getting Clojure](https://pragprog.com/titles/roclojure/getting-clojure/)
* [Living Clojure](https://www.oreilly.com/library/view/living-clojure/9781491909270/)

## Blog Posts
* [Post about managing side effects](https://bsless.github.io/side-effects/)
* [Mostly web related](https://yogthos.net/archives.html)
* [FizzBuzz - life goals](https://aphyr.com/posts/353-rewriting-the-technical-interview)


## Podcasts
* [Design in clojure](https://clojuredesign.club/)
* [The REPL](https://www.therepl.net/)


## Lectures
* [Simple made easy - Rich Hickey](https://www.youtube.com/watch?v=SxdOUGdseq4) - indoctrination
* [Clojure, made simple - Rich Hickey](https://www.youtube.com/watch?v=VSdnJDO-xdg)
* [Amperity lecture about how they learn and teach clojure internally](https://www.youtube.com/watch?v=QBsjYyg9bLE)
* [Implementing core functions](https://www.youtube.com/watch?v=csH4ZEtq2Tg)
* [Every clojure talk ever](https://www.youtube.com/watch?v=jlPaby7suOc) - LOL

## Testing??
TBD - need to find some resources on testing culture / tools
