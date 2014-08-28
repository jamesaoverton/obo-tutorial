# Introduction

In this tutorial we use a running example: histology assays of mice and rats. The example is small and its fictional but it's based on real datasets that I've worked with using these same tools and techniques. Biology and biomedicine are vast fields, and this example won't be a close fit to every reader's field of research. The advantage is that the running example keeps the tutorial concrete and practical. Once you learn the various techniques for working with this example data, I hope you'll see how you can apply the same techniques to your work in your own field.

## Data Before

Take a look at the [data-before.csv][] file. It's just a spreadsheet in comma-separated-values format. What information does it contain? It's hard to say without some context.

Each row of the spreadsheet describes an "observation" made by an investigator of a study subject -- a mouse or a rat. The columns divide the information up into different fields. What does each column mean? Lab computers are stuffed with spreadsheets with cryptic headers, or even lacking any headers. Those spreadsheets hang around long after the memory of what the columns mean has faded. And columns can become strange mixtures of special cases, especially when data about different sorts of things gets crammed into the same table. It's often more effective to ignore the headers, and try to generalize from the values in the column instead.

What about the values? These are often more cryptic than the headers. What does "B6C3F1" mean? Worse, familiar names can be very misleading. Does "histology" mean a general type of assay or a specific protocol for this study? I personally find many common date formats completely frustrating: do they mean "MM/DD/YY" or "DD/MM/YY"?

During the study, as it's being designed and the data is being collected, the meanings of the columns and the values will be clear to the investigators -- more or less. The problem is communicating this information: to other researchers in the same lab, to researchers in other labs, and even to the original study authors at some time in the future. The solution to the communication problem is to be clear from the outset.

This tutorial is about some practical techniques that you can use to communicate your data better. Some of these techniques also improve search and analysis of your data. And most of these techniques are really very simple. There are really just two hard parts: applying them consistently yourself, and coordinating with others.

[data-before.csv]: https://github.com/jamesaoverton/obo-tutorial/blob/master/examples/data-before.csv


## Outline

The plan for this tutorial is to build from better names, to systems of names, to using these systems with your data, and finally to updating and maintaining those systems.

1. [Introduction](https://github.com/jamesaoverton/obo-tutorial/blob/master/docs/introduction.md) (you are here!)
2. [Names and Naming](https://github.com/jamesaoverton/obo-tutorial/blob/master/docs/names.md): anything we want to talk about can be given a globally unique name, an IRI
3. [Open Biological and Biomedical Ontologies (OBO)](https://github.com/jamesaoverton/obo-tutorial/blob/master/docs/obo.md): carefully constructed, standard systems of names (and statements)
4. [Using and Reusing Ontologies](https://github.com/jamesaoverton/obo-tutorial/blob/master/docs/using-and-reusing.md)
    - the difference between reference and application ontologies
    - finding the right ontology term
    - importing reference ontology terms into your application ontology
5. [Processing Data with Ontologies](https://github.com/jamesaoverton/obo-tutorial/blob/master/docs/processing-data.md): using RDF, OWL, and SPARQL to work with your data
6. [Updating, Testing, and Releasing Ontologies](https://github.com/jamesaoverton/obo-tutorial/blob/master/docs/ontology-development.md)
    - contributing to reference ontologies
    - updating and maintaining your ontology

Ontologies are more than just systems of names. They express statements about the things that they name: statements about things in the world. Your data also make statements about the world. We work hard to make sure that all these statements are true. We also work hard to make sure that they're useful. By using OBO ontologies and the techniques in this tutorial, we can link our data together into a larger system of statements about the world. By communicating clearly, and sharing what we know, we make our data more valuable than ever before.


## Data After

Let's return to the [data-before.csv][] spreadsheet. Now we can spell out the meaning of each column, the current sorts of values that it contains, and how to make those values more clear.

- datetime: when the observation was made
- investigator: the initials of the person who made the observation
- subject: the identifier of the subject that was observed
- species: the name of the species of the subject
- strain: the name of the genetic variant that the subject belongs to
- sex: the biological sex of the subject
- group: the identifier of the group that the subject is assigned to in the study
- protocol: the name of the protocol used for the observation
- organ: the name of the subject's organ that was observed
- morphology: the name of the disease or other abnormal state of the organ
- qualifier: a keyword to futher describe the morphology
- comment: unstructured notes made by the investigator about the observation


Column       |Current Format            |Better Format             |Technique
-------------|--------------------------|--------------------------|----------
datetime     |idiosyncratic date string |ISO standard              |SPARQL
investigator |investigator initials     |ORCID                     |replace
subject      |string                    |application ontology IRI  |SPARQL
species      |English, mixed case       |NCBITaxon IRI             |MIREOT
strain       |string                    |application ontology IRI  |QTT
sex          |English, mixed case       |PATO IRI                  |MIREOT
group        |string                    |application ontology IRI  |SPARQL
protocol     |ambiguous string          |OBI IRI                   |MIREOT
organ        |English, mixed case       |UBERON IRI                |extract
disease      |English, mixed case       |MPATH IRI                 |MIREOT
qualifier    |English, mixed case       |PATO IRI                  |MIREOT
comment      |English unstructured text |--                        |--


The results of these changes are in [data-after.csv][] and [data.owl][]. To learn more about the techniques, [read on!](https://github.com/jamesaoverton/obo-tutorial/blob/master/docs/names.md)

[data-after.csv]: https://github.com/jamesaoverton/obo-tutorial/blob/master/examples/data-after.csv
[data.owl]: https://github.com/jamesaoverton/obo-tutorial/raw/master/examples/data.owl

