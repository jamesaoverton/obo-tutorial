# Open Biological and Biomedical Ontologies

One of the things that ontologies do is provide systems of names. The Open Biological and Biomedical Ontologies (OBO) is a community of ontology projects for science with shared principles and practises. Most OBO ontologies[^1] provide standardized names for the things that are general within a scientific domain. The term `obo:OBI_0000070` is an example: it names the class of all assays in general. The observation described in the first row of [data-before.csv][] is a *particular* assay that happened at a certain place and time. The class of all assays is *general* (or *universal*) -- it does not exist in space and time, but we can give it a name and talk about it.

[^1]: It seems redundant to say "an OBO ontology" -- "an Open Biological and Biomedical Ontologies *ontology*" -- but I use the phrase to mean "an ontology that belongs to the OBO library and follows OBO principles".

Much of the work in science involves general classes. For example, the [Gene Ontology (GO)](http://geneontology.org/) provides terms for universals such as [apoptotic process](http://purl.obolibrary.org/obo/GO_0006915), which is a kind of [biological process](http://purl.obolibrary.org/obo/GO_0008150), and for [host cell membrane](http://purl.obolibrary.org/obo/GO_0033644), which is a kind of [cellular component](http://purl.obolibrary.org/obo/GO_0005575). The [Uberon](http://uberon.org) cross-species anatomy ontology provides terms for universals such as [lung](http://purl.obolibrary.org/obo/UBERON_0002048), which is a kind of [anatomical entity](http://purl.obolibrary.org/obo/UBERON_0001062). The [NCBI Taxonomy](http://www.ncbi.nlm.nih.gov/taxonomy) is not an ontology (strictly speaking), but an OBO-compatible resource is availble that provides terms for biological taxa such as [Mus musculus](http://purl.obolibrary.org/obo/NCBITaxon_10090).

We can give a name (an IRI) to the subject of the first row of data-before.csv, a particular mouse, and assign it to the class of all mice. We can name its particular lung that was used in the experiment and assign it to the class of all lungs (across species), even if we don't know whether the data refers to the right or the left lung.

In addition to names, ontologies also encode information about relationships between classes and facts that hold for all members of a class. This lets us draw additional inferences about our particular mouse and its particular lung, multiplying the value of our data.

[data-before.csv]: https://github.com/jamesaoverton/obo-tutorial/blob/master/examples/data-before.csv


## Reference Ontologies and Application Ontologies

We should distinguish between *reference ontologies* and *application ontologies*. A reference ontology is meant to serve a general purpose, providing terms for some domain of science. We can compare it to a textbook. An application ontology is meant to serve a specific purpose, limited to a particular project or institution, but often crossing several domains. An application ontology is likely to include terms from several reference ontologies. Reference ontologies focus almost exclusively on universals, while applications ontologies are more likely to include terms for particulars.

OBO ontologies are reference ontologies -- they focus on terms for the universals within a scientific domain. In our running example we will be using terms from several reference ontologies, such as Uberon and OBI. In this tutorial we will be building an application ontology to support our running example, importing these reference ontology terms and adding other terms that don't exist in (or belong in) any reference ontology.


## Shared Best Practises

OBO projects all share a commitment to a set of principles and best practises.

TODO: Write a summary of the shared principles and practises. In the meantime, you can peruse these links.

- The [OBO Foundry](http://obofoundry.org)
- [OBO Foundry Principles](http://obofoundry.org/wiki/index.php/OBO_Foundry_Principles)
- [OBO Foundry Operations Committee](https://code.google.com/p/obo-foundry-operations-committee)
    - [Editorial Working Group](https://code.google.com/p/obo-foundry-operations-committee/wiki/EditorialWG)
    - [Technical Working Group](https://code.google.com/p/obo-foundry-operations-committee/wiki/TechnicalWG)
    - [Outreach Working Group](https://code.google.com/p/obo-foundry-operations-committee/wiki/OutreachWG)


Now let's see how to [use and reuse ontology terms](https://github.com/jamesaoverton/obo-tutorial/blob/master/docs/using-and-reusing.md).

