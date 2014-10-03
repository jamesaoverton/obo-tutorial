package obo_tutorial;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.util.OWLOntologyMerger;
import org.semanticweb.owlapi.util.AutoIRIMapper;

/**
 * Merge ontologies.
 * WARNING: checks for imports in the parent directory, but
 * does not read the catalog.xml file!
 * 
 * @author <a href="mailto:james@overton.ca">James A. Overton</a>
 */
public class Merger {
  /**
   * Given a list of input ontology file paths,
   * an output file path, and an output ontology IRI,
   * merge the inputs into the output with that IRI and write it.
   *
   * @param args At least three strings:
   *   - one or more paths to the input ontology files
   *   - the path of the merged ontology (output) file
   *   - the IRI of the merged ontology
   */
  public static void main(String[] args) {
    merge(Arrays.asList(args));
  }

  /**
   * Given a list of input ontology file paths,
   * an output file path, and an output ontology IRI,
   * merge the inputs into the output with that IRI and write it.
   *
   * @param args At least three strings:
   *   - one or more paths to the input ontology files
   *   - the path of the merged ontology (output) file
   *   - the IRI of the merged ontology
   * @return the merged ontology
   */
  public static OWLOntology merge(List<String> args) {
    String outputIRI = args.remove(args.size() - 1);
    String outputPath = args.remove(args.size() - 1);
    return merge(args, outputPath, outputIRI);
  }

  /**
   * Given a list of input ontology file paths,
   * an output file path, and an output ontology IRI,
   * merge the inputs into the output with that IRI and write it.
   *
   * @param paths a list of paths to input ontologies
   * @param outputPathString the path to save the resulting ontology file
   * @param outputIRIString the IRI string of the resulting ontology
   * @return the merged ontology
   */
  public static OWLOntology merge(List<String> paths,
      String outputPathString, String outputIRIString) {
    OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

    // load ontologies into manager
    // checks the directory of the file for imports
    for(String path: paths) {
      try {
        File file = new File(path);
        File parent = file.getCanonicalFile().getParentFile();
        manager.addIRIMapper(new AutoIRIMapper(parent, false));
        manager.loadOntologyFromOntologyDocument(file);
      } catch (Exception e) {
        System.out.println("ERROR: Could not load ontology at: " + path);
        System.out.println(e.getMessage());
        e.printStackTrace();
      }
    }

    IRI outputPath = IRI.create(new File(outputPathString));
    IRI outputIRI = IRI.create(outputIRIString);

    return merge(manager, outputPath, outputIRI);
  }

  /**
   * Given a source ontology and a file with a list of IRIs,
   * extract those IRIs as a module from the source ontology,
   * and save a new ontology with the given IRI to the given target file.
   *
   * @param manager a manager with all the ontologies to merge
   * @param outputPath the path to save the resulting ontology file
   * @param outputIRI the IRI string of the resulting ontology
   * @return the merged ontology
   */
  public static OWLOntology merge(OWLOntologyManager manager,
      IRI outputPath, IRI outputIRI) {
    OWLOntology merged = null;
    
    // merge
    try {
      OWLOntologyMerger merger = new OWLOntologyMerger(manager);
      merged = merger.createMergedOntology(manager, outputIRI);
    } catch (OWLOntologyCreationException e) {
      System.out.println("ERROR: Could not merge ontologyies");
      System.out.println(e.getMessage());
    }

    // write
    try {
      manager.saveOntology(merged, outputPath);
    } catch (Exception e) {
      System.out.println("ERROR: Could not save ontology to: " + outputPath);
    }

    return merged;
  }

}
