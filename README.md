# vmfest-playground

A project to test [VMFest](https://github.com/tbatchelli/vmfest).

## Usage

Ensure that you have VirtualBox 4.1.x installed and start
`vboxwebsrvr` by issuing the following on the shell:

```bash
$ vboxwebsrvr -t0
```

and if you haven't done it already, disable login authorization in
VirtualBox:

``` 
$ VBoxManage setproperty websrvauthlibrary null
```

Clone this project. 

If you don't have Leiningen installed, [download
and install
it](https://github.com/technomancy/leiningen#installation). Then, at
the shell run:

```bash
$ lein deps, repl
```

Finally, open the file
[play.clj](https://github.com/pallet/vmfest-playground/blob/master/src/play.clj)
and follow the tutorial copying the code line by line to the Clojure
REPL.

## License

Copyright © 2012 Antoni Batchelli

Distributed under the Eclipse Public License, the same as Clojure.
