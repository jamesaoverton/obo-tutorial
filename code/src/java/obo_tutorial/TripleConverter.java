package obo_tutorial;

import java.io.FileReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

/**
 * Load a CSV and do a "naive" conversion to triples
 * base on the headers.
 * 
 * @author <a href="mailto:james@overton.ca">James A. Overton</a>
 */
public class TripleConverter {
  /**
   * Given a path to a Turtle file with prefixes to use,
   * a path to an input CSV file with a header row,
   * and an output file path,
   * convert the table to triples in a very simple way,
   * and save to the output file.
   * The first row of the input data must be a header row.
   *
   * @param args Three strings:
   *   1. the path of the Turtle file with prefixes to use
   *   2. the tutorial IRI string
   *   3. the path of the output Turtle file
   */
  public static void main(String[] args) {
    convert(args[0], args[1], args[2]);
  }

  /**
   * Given a path to a Turtle file with prefixes to use,
   * a path to an input CSV file with a header row,
   * and an output file path,
   * convert the table to triples in a very simple way,
   * and save to the output file.
   * The first row of the input data must be a header row.
   *
   * @param args Three strings:
   *   1. the path of the Turtle file with prefixes to use
   *   2. the path of the input CSV file
   *   3. the path of the output Turtle file
   * @return a Jena Model containing all the triples
   */
  public static Model convert(List<String> args) {
    return convert(args.get(0), args.get(1), args.get(2));
  }

  /**
   * Given a path to a Turtle file with prefixes to use,
   * a path to an input CSV file with a header row,
   * and an output file path,
   * convert the table to triples in a very simple way,
   * and save to the output file.
   * The first row of the input data must be a header row.
   *
   * @param prefixPath the path of a Turtle file to use for prefixes
   * @param inputPath the path of the input CSV file
   * @param outputPath the path of the output Turtle file
   * @return a Jena Model containing all the triples
   */
  public static Model convert(String prefixPath, String inputPath,
      String outputPath) {
    List<List<String>> inputData = new ArrayList<List<String>>();

    // read the prefixes
    Model m = RDFDataMgr.loadModel(prefixPath);
    Map<String,String> prefixMap = m.getNsPrefixMap();

    // read the input CSV file
    try {
      CSVReader reader = new CSVReader(new FileReader(inputPath));
      List<String[]> dataRows = reader.readAll();
      reader.close();
      for(String[] dataRow: dataRows) {
        inputData.add(Arrays.asList(dataRow));
      }
    } catch (IOException e) {
      System.out.println("Could not read input CSV file at " + inputPath);
    }

    return convert(prefixMap, inputData, outputPath);
  }

  /**
   * Given a path to a Turtle file with prefixes to use,
   * a path to an input CSV file with a header row,
   * and an output file path,
   * convert the table to triples in a very simple way,
   * and save to the output file.
   * The first row of the input data must be a header row.
   *
   * @param prefixMap the prefixes to use
   * @param inputPath the path of the input CSV file
   * @param outputPath the path of the output Turtle file
   * @return a Jena Model containing all the triples
   */
  public static Model convert(Map<String,String> prefixMap,
      List<List<String>> inputData, String outputPath) {
    List<String> headers = inputData.remove(0);

    Model m = ModelFactory.createDefaultModel();
    m.setNsPrefixes(prefixMap);

    // iterate over rows
    int rowNumber = 1;
    for(List<String> row: inputData) {
      String rowIRI = m.expandPrefix("tutorial:row-" + rowNumber);
      m.add(
        m.createStatement(
          m.createResource(rowIRI),
          m.createProperty(m.expandPrefix("rdf:type")),
          m.createResource(m.expandPrefix("tutorial:row"))));
      int columnNumber = 0;

      // convert row-column-cell to subject-predicate-object
      for(String cell: row) {
        String header = headers.get(columnNumber);
        String headerIRI = m.expandPrefix("tutorial:column-" + header);
        if(cell.length() == 0) {
          // do nothing
        } else if (header.equals("datetime") ||
                   header.equals("comment")) {
          // create a statement where the object is a literal
          m.add(
            m.createStatement(
              m.createResource(rowIRI),
              m.createProperty(headerIRI),
              m.createLiteral(cell, null)));
        } else {
          // create a statement where the object is a URI
          m.add(
            m.createStatement(
              m.createResource(rowIRI),
              m.createProperty(headerIRI),
              m.createResource(m.expandPrefix(cell))));
        }
        columnNumber = columnNumber + 1;

      }
      rowNumber = rowNumber + 1;
    }

    // write
    try {
      FileOutputStream output = new FileOutputStream(outputPath);
      RDFDataMgr.write(output, m, RDFFormat.TURTLE_PRETTY);
    } catch (IOException e) {
      System.out.println("Could not write output Turtle file to " + outputPath);
    }

    return m;
  }

}

