# Updating, Testing, and Releasing Ontologies


## Tracking Changes with Ontology Diff Tools

TODO: Finish writing this section.

- Protégé has an "Ontology Differences" tab
- [Bubastis](http://www.ebi.ac.uk/fgpt/sw/bubastis/index.html) online and downloadable tools for finding differences between ontologies or their versions


## Filing Effective Term Requests and Bug Reports

Ontologies grow over time as the state of the art advances and as their usage increases. OBO ontologies are open, community projects that depend on contributions from researchers in their domains. You can help the ontologies that you use to grow by contributing new terms.

Most OBO projects accept submissions of new terms via a "tracker". The Gene Ontology has a powerful system called "TermGenie" that some other projects are starting to use.


### Ontology Domains

OBO ontology developers work hard to coordinate with other OBO projects. Each ontology has a specific scope, and it's important that you submit your term request or bug report to the right ontology project.

The [OBO home page](http://obofoundry.org) lists all the Foundry and Library ontologies with a note about their domains. The information page and home page for each ontology will have more information about their scope.

When searching for the right ontology for your new term, also keep in mind some of the topics we discussed above in the [Assessing Ontologies and Terms for Reuse](https://github.com/jamesaoverton/obo-tutorial/blob/master/docs/using-and-reusing.md#assessing-ontologies-and-terms-for-reuse) section, such as community support.


### Trackers

Open source software development makes heavy use of the Internet for collaboration. Public version control systems manage source code, and email lists help developers and users coordinate. You'll often see bug reports and feature requests on mailing lists, but for a project of any size these can quickly get out of control. So most open source projects have a "tracker" for handling bug reports and feature requests in a more structured way. GitHub, Google Code, SourceForge, and other software hosting sites all provide trackers, with different names and slightly different features.

Each tracker "ticket" (aka "issue", "item", etc.) will have this information:

1. status: e.g. open, accepted, closed, fixed, wontfix
2. creator: the person who opened the ticket
3. owner: the developer that the ticket is assigned to
4. category, milestone, priority: used for sorting
5. discussion thread: the most important part

There will often be instructions for the tracker, saying what information should be included with new tracker tickets. Please follow those project-specific instructions. The next three sections have some general guidelines that apply to pretty much any OBO project.

If you're submitting a term to an ontology that uses TermGenie, such as the Gene Ontology, then you should use it! See the section on TermGenie below.


### Existing Terms

Before you submit a new term, please search [OntoBee](http://www.ontobee.org) or [BioPortal](http://bioportal.bioontology.org) for the term you want and its synonyms. An OBO ontology might already include the term you want under a different name. Check the [OBO home page](http://obofoundry.org) to find appropriate ontologies.

If you find a term that's close enough, but would like additions or changes made to it, please submit a new ticket to the ontology project's issue tracker and be sure to include:

1. the name of the term in the title of the ticket
2. the [IRI](https://en.wikipedia.org/wiki/Internationalized_resource_identifier) that identifies the term (something like `http://purl.obolibrary.org/obo/OBI_0000070`)
3. a description of the changes you would like to see and reasons for the changes


### New Terms

If you don't find the term you're looking for, you should request a new term from the appropriate OBO project. The developers will need the following information before they can add your term:

1. editor preferred term: a unique, unambiguous label for the term in American English
2. alternative terms: common synonyms or translations
3. textual definition
4. definition source for the textual definition
5. logical definition (or parent term)
6. example of usage
7. term editor: your name, and that of any collaborators, as it should appear in the ontology

We covered most of these [Assessing Ontologies and Terms for Reuse](https://github.com/jamesaoverton/obo-tutorial/blob/master/docs/using-and-reusing.md#assessing-ontologies-and-terms-for-reuse), but submitting a term isn't quite the same thing as using a term. Let's look at these again from the perspective of a new term submission.

1. The [editor preferred term](http://purl.obolibrary.org/obo/IAO_0000111) is an unambiguous label that uniquely identifies the term within the OBO Foundry. (Some ontologies just use `rdfs:label` for this.) There must be one and only one editor preferred term, and it must be in American English. It should not contain any acronyms or jargon words, and it should make sense when read outside the context of a scientific investigation. As you might expect, all these requirements combine to make many of OBO's editor preferred terms long and ugly. But the most important thing is that they're clear! (See also the [OBO Foundry naming conventions](http://www.obofoundry.org/wiki/index.php/FP_012_naming_conventions).)

2. There can be many [alternative term](http://purl.obolibrary.org/obo/IAO_0000118) annotations for a given term. (Some ontologies use different properties for synonyms.) They can include acronyms and jargon -- in fact, if the editor preferred term spells out an acronym then it's a good idea to put the abbreviated form in an alternative term annotation. They can be in any language, but make sure to specify which language if it is not American English so we can note that. Alternative term annotations do not have to be unique across OBO.

3. The [textual definition](http://purl.obolibrary.org/obo/IAO_0000115) is the most important annotation because it expresses the meaning of your term. Definitions should have Aristotelian form: "An A is a B that C" where A is your new term, B is the parent term, and C spells out how A is a special kind of B. Every term needs one and only one textual definition, in American English, and definitions must be unique across the OBO Foundry. (See also the OBO Foundry Principle on [textual definitions](http://www.obofoundry.org/wiki/index.php/FP_006_textual_definitions).)

4. Every textual definition should have a [definition source](http://purl.obolibrary.org/obo/IAO_0000119). If you created the definition, then that source would be you. If the definition was adapted from Wikipedia or some other website, then this annotation should contain the permanent URL. If it was adapted from a paper, use its [DOI](https://en.wikipedia.org/wiki/Digital_object_identifier) or PubMed URL.

5. The logical definition of a term is almost as important as the textual definition. I say "almost" because, in case of misunderstandings, the textual definition takes precedence. The logical definition expresses the meaning of the term in a machine-readable way using the [Web Ontology Language (OWL) version 2](http://www.w3.org/TR/owl2-overview/). Most OBO terms are OWL Classes (i.e. general nouns) and every one of these requires a single parent class. Many of them have more complex logical definitions. If you aren't comfortable specifying a logical definition, that's fine. Ontology developers will be happy to help create one based on your textual definition.

6. One or more [example of usage](http://purl.obolibrary.org/obo/IAO_0000112) annotations can really clarify the meaning of a term. While the textual definition must be general enough to cover all cases, the examples provide specific cases that show the term in action. Examples of usage can be common cases that demonstrate the prototypical usage, or uncommon edge cases that delimit the scope of the term. While not strictly required, every new term should have one or more examples of usage.

7. Finally, OBO developers want to give you credit for your work. They will add one or more [term editor](http://purl.obolibrary.org/obo/IAO_0000117) annotations with your name, and the names of others who have collaborated on creating the term.


### TermGenie

Trackers work well enough for handling term submissions, but that's not what they were designed for. Submitting, reviewing, and adding terms using a tracker requires many manual steps. Ontology terms often in fall into groups with a strong pattern, in which case it would be nice to automate the process as much as possible.

TermGenie was specifically designed to streamline ontology term submission and review. Take a look at the [Gene Ontology's version of TermGenie](http://go.termgenie.org), and see the [project home page](http://termgenie.org) for more details.

Once you go to the TermGenie page for an ontology project, the next step is to select a term template. Authorized users can use TermGenie in "free-form" mode to submit any term they want, but the system is designed for terms that follow strong patterns and that's where it really shines. The TermGenie templates share many of the ideas behind the Quick Term Templates discussed previously, and you can do similar things with [Ontorat](http://ontorat.hegroup.org/). But TermGenie provides a much more friendly interface, smart autocomplete, powerful validation tools, end-to-end workflows for users and reviewers, and integration with version control systems.

Select a template and fill in the fields. You can add multiple terms and you can use multiple templates. When you're ready, click "Verify Input", and TermGenie will run a reasoner and apply a series of validation checks to make sure that your term submissions follow all the rules and don't duplicate any existing terms.

Once any validation problems have been fixed, click "Submit". Your terms will be assigned official identifiers and added to the ontology. The ontology files will be updated in the project's version control system. You can use your new terms right away! Meanwhile, the terms will be added to a queue for review by ontology developers. For each term they can decide to approve it, contact you to discuss required modifications, or decide that there are critical problems and mark the term as obsolete. Even if it is marked obsolete, the term will not just be deleted: it has been assigned an identifier and added to the ontology. You should not use obsolete terms in new data, but obsolete terms used in old data will still be accessible.


TODO: Finish writing these sections.

## Best Practices for Committing Changes to an Ontology
## Quality Checking for Ontologies
## Unit Testing for Ontologies
## Continuous Integration with Jenkins

- http://bio-ontologies.knowledgeblog.org/405
- http://douroucouli.wordpress.com/2012/02/16/ontologies-and-continuous-integration/

## Publishing Ontologies
## Managing PURLs

- http://purl.obofoundry.org

## Release Workflows

- [owltools](https://owltools.googlecode.com)
- [OBO Ontology Release Tool (Oort)](https://code.google.com/p/owltools/wiki/Oort)
