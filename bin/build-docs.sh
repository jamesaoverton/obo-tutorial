#!/bin/bash 
# Use Pandoc to build a PDF version of the OBO tutorial.
# Run this from the `bin` directory:
#     ./build-docs.sh

pandoc --toc --toc-depth=2 --listings \
  --from markdown_github+raw_tex+yaml_metadata_block \
  --output ../obo-tutorial.pdf \
  ../docs/metadata.yaml \
  ../docs/introduction.md \
  ../docs/names.md \
  ../docs/obo.md \
  ../docs/using-and-reusing.md \
  ../docs/processing-data.md \
  ../docs/ontology-development.md




