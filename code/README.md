# OBO Tutorial Code

The `src` directory contains Java code to support the tutorial. If you download a [release](https://github.com/jamesaoverton/obo-tutorial/releases) version of the tutorial, you can just run the `bin/obo-tutorial.jar` file like this:

    cd examples
    java -jar ../bin/obo-tutorial.jar

This command will print more information about using the tool, and the tutorial includes several examples. Here's a summary of all the build steps in the tutorial:

    cd examples
    java -jar ../bin/obo-tutorial.jar map terms.csv data-before.csv data-after.csv
    java -jar ../bin/obo-tutorial.jar extract \
      uberon.owl \
      uberon-terms.txt \
      uberon-module.owl \
      "https://github.com/jamesaoverton/obo-tutorial/raw/master/examples/uberon-module.owl"
    java -jar ../bin/obo-tutorial.jar convert prefixes.ttl data-after.csv data-raw.ttl
    java -jar ../bin/obo-tutorial.jar model data-raw.ttl application.owl model.rq data-after.ttl
    java -jar ../bin/obo-tutorial.jar merge \
      data-after.ttl \
      obo-tutorial.owl \
      "https://github.com/jamesaoverton/obo-tutorial/raw/master/examples/obo-tutorial.owl"


## Requirements

To run this code you will need a Java Runtime Environment. You probably already have one installed on your computer, but if not you can get it here:

http://www.oracle.com/technetwork/es/java/javase/downloads/index.html


## Building

To build this code you need Java plus [Apache Ant](http://ant.apache.org) and [Apache Ivy](http://ant.apache.org/ivy/). Please follow their respective installation instructions. Then:

1. change to the `code` directory: `cd code`
2. download required libraries: `ant deps`
3. build and package the code: `ant package`

The result is the `bin/obo-tutorial.jar` file, including all dependencies.

See the [build.xml](https://github.com/jamesaoverton/obo-tutorial/blob/master/code/build.xml) and [ivy.xml](https://github.com/jamesaoverton/obo-tutorial/blob/master/code/ivy.xml) configuration files for build details.
