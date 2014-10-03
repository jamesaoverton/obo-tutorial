package obo_tutorial;

import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import com.hp.hpl.jena.tdb.TDBFactory;
import com.hp.hpl.jena.update.GraphStore;
import com.hp.hpl.jena.sparql.modify.GraphStoreBasic;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.NodeFactory;
import com.hp.hpl.jena.update.UpdateExecutionFactory;
import com.hp.hpl.jena.update.UpdateFactory;
import com.hp.hpl.jena.update.UpdateRequest;
import com.hp.hpl.jena.update.UpdateProcessor;

/**
 *
 * @author <a href="mailto:james@overton.ca">James A. Overton</a>
 */
public class Modeller {
  /**
   * Given a path to a Turtle file with instance data,
   * a path to an application ontology file,
   * a path to a SPARQL Update file,
   * and an output file path,
   * Load the instance data and ontology, run the queries,
   * and save the resulting "tutorial:data" graph to the output Turtle file.
   *
   * @param args Four strings:
   *   1. the path of the input Turtle file
   *   2. the path of the application ontology file
   *   3. the path of a SPARQL files
   *   4. the path of the output Turtle file
   */
  public static void main(String[] args) {
    convert(args[0], args[1], args[2], args[3]);
  }

  /**
   * Given a path to a Turtle file with instance data,
   * a path to an application ontology file,
   * a path to a SPARQL Update file,
   * and an output file path,
   * Load the instance data and ontology, run the queries,
   * and save the resulting "tutorial:data" graph to the output Turtle file.
   *
   * @param args Four strings:
   *   1. the path of the input Turtle file
   *   2. the path of the application ontology file
   *   3. the path to a directory of SPARQL files
   *   4. the path of the output Turtle file
   * @return a Jena Model containing for "tutorial:data"
   */
  public static Model convert(List<String> args) {
    return convert(args.get(0), args.get(1), args.get(2), args.get(3));
  }

  /**
   * Given a path to a Turtle file with instance data,
   * a path to an application ontology file,
   * a path to a SPARQL Update file,
   * and an output file path,
   * Load the instance data and ontology, run the queries,
   * and save the resulting "tutorial:data" graph to the output Turtle file.
   *
   * @param dataPath the path of the input Turtle file
   * @param ontologyPath the path of the application ontology file
   * @param sparqlPath the path to a SPARQL UPDATE files
   * @param outputPath the path of the output Turtle file
   * @return a Jena Model for "tutorial:data"
   */
  public static Model convert(String dataPath, String ontologyPath,
      String sparqlPath, String outputPath) {
    // load the data and get the tutorial URI
    Model data = RDFDataMgr.loadModel(dataPath);
    Map<String,String> prefixMap = data.getNsPrefixMap();
    String tutorialIRI = prefixMap.get("tutorial");

    // load ontology with imports
    OntModel ont = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
    ont.read(ontologyPath);

    // create the graph store and load data
    GraphStore gs = new GraphStoreBasic(TDBFactory.createDatasetGraph());
    gs.addGraph(NodeFactory.createURI(tutorialIRI + "raw"),
                data.getGraph());
    gs.addGraph(NodeFactory.createURI(tutorialIRI + "ontology"),
                ont.getGraph());

    // load the SPARQL file
    FileInputStream sparqlFile = null;
    try {
      sparqlFile = new FileInputStream(sparqlPath);
    } catch (IOException e) {
      System.out.println("Could not read SPARQL file at " + sparqlPath);
    }

    return convert(gs, prefixMap, sparqlFile, outputPath);
  }

  /**
   * Given a Jena graph store, a map of prefixes to IRIs,
   * a SPARQL Update file, and an output file path,
   * Load the instance data and ontology, run the queries,
   * and save the resulting "tutorial:data" graph to the output Turtle file.
   *
   * @param gs a collection of graphs containing the instance and ontology data
   * @param prefixMap the prefixes to use
   * @param sparqlFile a file with a SPARQL Update query
   * @param outputPath the path of the output Turtle file
   * @return a Jena Model for "tutorial:data"
   */
  public static Model convert(GraphStore gs, Map<String,String> prefixMap,
      FileInputStream sparqlFile, String outputPath) {
    String tutorialIRI = prefixMap.get("tutorial");

    // run update
    UpdateRequest ur = UpdateFactory.read(sparqlFile);
    UpdateProcessor up = UpdateExecutionFactory.create(ur, gs);
    up.execute();
    
    // write
    Node dataNode = NodeFactory.createURI(tutorialIRI + "data");
    Model m = ModelFactory.createDefaultModel();
    try {
      m = ModelFactory.createModelForGraph(gs.getGraph(dataNode));
      m.setNsPrefixes(prefixMap);
      FileOutputStream output = new FileOutputStream(outputPath);
      RDFDataMgr.write(output, m, RDFFormat.TURTLE_PRETTY);
    } catch (IOException e) {
      System.out.println("Could not write output Turtle file to " + outputPath);
    }

    return m;
  }

}
