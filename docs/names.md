# Names and Naming

The [data-before.csv][] file contains terse, ambiguous, and misleading names. This isn't unusual! How can we be be more explicit and communicate more clearly? The fist step is to use better names.

[data-before.csv]: https://github.com/jamesaoverton/obo-tutorial/blob/master/examples/data-before.csv


## All the Names We Need

Why do people use terse, ambiguous, cryptic names for things? Many reasons. In our day-to-day language we tend to use short names and abbreviations to save time and effort, relying on context to remove ambiguity. Unfortunately, context can fade with time and distance. There are also practical limitations: short names fit better into dense columns, and on legacy computer systems memory and storage were precious. But today storage is cheap, and better software can autocomplete values and display short names while storing longer, unambiguous names.

You're already familiar with a simple yet powerful technology that can provide us with a practically unlimited number of unambiguous names. Here's an example: `<https://github.com/jamesaoverton/obo-tutorial/>`. It's a [URL][], also known as a Uniform Resource Locator or a web address, and it's one of the building blocks of the World Wide Web.

A URL has three main parts:

1. protocol, e.g. `https`
2. host, e.g. `github.com`
3. path, e.g. `jamesaoverton/obo-tutorial/`

The protocol tells you how to get the resource. Common protocols for web pages are [`http`][HTTP] (HyperText Transfer Protocol) and [`https`][HTTPS] (HTTP Secure). The host is the name of the server to contact, which can be a numeric [IP address][IP], but is more often a [domain name][DNS]. The path is the name of the resource on that server.

URLs are combine three very important features: they're consistent enough to be shared across the world, almost anyone can make their own, but they're flexible enough to provide a practically unlimited number of names. The consistency comes from (1) the standardized protocols, such as HTTP. DNS names and IP addresses (2) are allocated to organizations and people, and there's a lot of them, allowing almost anyone to have their own "namespace". Between (1) and (2) we have standard ways to find the server that hosts that namespace and connect to it across the Internet. And within each namespace, the path (3) can be almost anything, allowing for each namespace to contain a vast number of names.

[URL]: https://en.wikipedia.org/wiki/Url
[HTTP]: https://en.wikipedia.org/wiki/HTTP
[HTTPS]: https://en.wikipedia.org/wiki/HTTPS
[IP]: https://en.wikipedia.org/wiki/IP_address
[DNS]: https://en.wikipedia.org/wiki/Domain_name

What can we do with all these names? We can give a globally unique name to anything we care to talk about. We can give our study a globally unique name. We can name each subject in our study. We can name each row of our table of data (each observation). We can name the relationships that our columns express.We can give a name to the values that we reuse. We don't have to assign a name to everything, but we have more than enough names for everything that we *want* to name.


## Names for Rules for Names

There's actually a family of related standards for these names. Related to the URL is the [URN][] (Universal Resource Name) which has a somewhat different structure. A [URI][] (Universal Resource Identifier) is either a URL or a URN. URIs are limited to [ASCII][] characters (for the English alphabet), but an [IRI][] (Internationalized Resource Identifier) can have [Unicode][] characters (for almost every language). Because "IRI" is the most general term, that's the one I'll use in what follows, but pretty much every example mentioned is just a humble URL.

Because IRIs can be long and redundant, there are standard ways for shortening them without loosing information. A [CURIE][] (Compact URI) consists of a prefix and a suffix, where the prefix stands in for a longer base IRI. By converting the prefix and appending the suffix we get back to the full IRI. For example, if we let the `obo` prefix stand in for the IRI <http://purl.obolibrary.org/obo/>, then the CURIE `obo:OBI_0000070` can be expanded unambiguously to <http://purl.obolibrary.org/obo/OBI_0000070>. Any file that contains CURIEs should also define the prefixes. Here are some very common prefixes for our work:

- `rdf` <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
- `rdfs` <http://www.w3.org/2000/01/rdf-schema#>
- `xsd` <http://www.w3.org/2001/XMLSchema#>
- `owl` <http://www.w3.org/2002/07/owl#>
- `obo` <http://purl.obolibrary.org/obo/>

The part after the prefix is technically known as the "reference".

This tutorial is about OBO ontologies, and we'll have more to say about them in the next section. But OBO has its own [policy](http://obofoundry.org/id-policy.shtml) for naming terms that's worth discussing now. Every OBO ontology has its own "ID space", which is a string of a few letters, e.g. "OBI" for the Ontology for Biomedical Investigations. Every term has a unique "Local ID", which is a string of digits, e.g. "0000070" for OBI's "assay" term. The combination is the term's identifier, e.g. "OBI:0000070". This is what we mean by the ID of an OBO term. Add the <http://purl.obolibrary.org/obo/> prefix (and use an underscore) and you get the term's unique IRI: <http://purl.obolibrary.org/obo/OBI_0000070>.

Using numeric IDs for terms has advantages and disadvantages. The biggest and most obvious disadvantage is that the term ID is *opaque*: it carries no meaning for a human reader. There's nothing about the ID that tells you that it's the ID for "assay" -- at this point I've just memorized that connection. But the opacity of the ID becomes an advantage if we ever have to replace the OBI "assay" term with a better term. The new "assay" term will get a new ID and the old term can keep its ID to support legacy data -- we try our best not to break legacy systems! If the term ID were not opaque but used a word, something like "OBI_assay", what would we call the new term? "OBI_assay_new"? And what if we had to replace it, in turn? "OBI_assay_new_2"? My bet is that people would keep using the old "OBI_assay" ID just because it looks right, and never update to the improved terms.

Opaque IDs make versioning easier, and versioning is crucial. We can overcome the disadvantages of opacity with better tools. It's trivial for software to go from a unique ID to a human-readable label. But trying to go from an ambiguous, human-readable label to a unique ID is a recipe for disaster. Better to use globally unique IRIs and improve our tools.

One other acronym you might come across is "[PURL][]" (Persistent URL). A PURL is just a URL, but with additional expectations that it will always point to the same resource. PURLs usually work by transparently redirecting a request to another address. If a resource is moved to a new server, for example, the PURL doesn't change, but it will redirect you to the new address instead of the old one. (In general, [cool URIs don't change][cool], and every IRI should be persistent, but people use PURLs to make their intentions clear.)

It's important to know that there are some differences, but for pretty much all our purposes we can treat these as the same: URL, URI, IRI, and PURL for full names; CURIE or OBO term ID for abbreviations. I'll try to stick to "IRI".

[URN]: https://en.wikipedia.org/wiki/Uniform_resource_name
[URI]: https://en.wikipedia.org/wiki/Uniform_resource_identifier
[IRI]: https://en.wikipedia.org/wiki/Internationalized_Resource_Identifier
[ASCII]: https://en.wikipedia.org/wiki/ASCII
[Unicode]: https://en.wikipedia.org/wiki/Universal_Character_Set
[CURIE]: https://en.wikipedia.org/wiki/CURIE
[PURL]: https://en.wikipedia.org/wiki/PURL
[cool]: http://www.w3.org/Provider/Style/URI.html


## No More than One Name

IRIs let us assign as many names as we want. But it's important to step back and ask: how many names *do* we want?

Names are most useful when everything we want to talk about has only one name. When you and I both use the same name for the same thing, then we're communicating. If we use different names for the same thing, then it takes extra work to figure out that we both mean the same thing.

Coordinating names can be a challenge. The challenge only increases when the community of communicators is large, and our knowledge of the things we're talking about is changing quickly. I'm sure you'll recognize this situation in your field of research: new terminology is introduced, revised and revised again to fit new facts and usage, and then perhaps settles into some stable textbook meaning or disappears all together.

[Ontologies](https://github.com/jamesaoverton/obo-tutorial/blob/master/docs/obo.md) are part of the solution.

