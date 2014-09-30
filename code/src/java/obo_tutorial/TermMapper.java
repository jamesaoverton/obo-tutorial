package obo_tutorial;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.text.SimpleDateFormat;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;


/**
 * Load a CSV file that specifies a mapping from terms to CURIEs,
 * then apply the mapping to all the cells of an input CSV file,
 * and write to an output CSV file.
 * 
 * @author <a href="mailto:james@overton.ca">James A. Overton</a>
 */
public class TermMapper {
  /**
   * Given a CSV file path,
   * with terms in the first column and CURIEs in the fourth column,
   * load an input CSV file from a path,
   * apply the mapping to each cell,
   * and write an output CSV file to a path.
   * The first row of the input data (the header row) is not mapped.
   *
   * @param args Three strings:
   *   1. the path of the term mapping file
   *   2. the path of the input file to map
   *   3. the path of the output file
   */
  public static void main(String[] args) {
    map(args[0], args[1], args[2]);
  }

  /**
   * Given a CSV file path,
   * with terms in the first column and CURIEs in the fourth column,
   * load an input CSV file from a path,
   * apply the mapping to each cell,
   * and write an output CSV file to a path.
   * The first row of the input data (the header row) is not mapped.
   *
   * @param args Three strings:
   *   1. the path of the term mapping file
   *   2. the path of the input file to map
   *   3. the path of the output file
   * @return the result of applying the map to the input data
   */
  public static List<List<String>> map(List<String> args) {
    return map(args.get(0), args.get(1), args.get(2));
  }

  /**
   * Given a CSV file path,
   * with terms in the first column and CURIEs in the fourth column,
   * load an input CSV file from a path,
   * apply the mapping to each cell,
   * and write an output CSV file to a path.
   * The first row of the input data (the header row) is not mapped.
   *
   * @param termMapPath the path to the term mapping file
   * @param inputPath the path to the input file to map
   * @param outputPath the path of the output file
   * @return the result of applying the map to the input data
   */
  public static List<List<String>> map(String termMapPath, String inputPath,
      String outputPath) {
    Map<String,String> termMap = new HashMap<String,String>();
    List<List<String>> rows = new ArrayList<List<String>>();

    // read the term mapping CSV file
    try {
      CSVReader reader = new CSVReader(new FileReader(termMapPath));
      List<String[]> termRows = reader.readAll();
      reader.close();
      termRows.remove(0);

      for(String[] termRow: termRows) {
        if(!termRow[0].equals("") && !termRow[3].equals("")) {
          termMap.put(termRow[0], termRow[3]);
        }
      }
    } catch (IOException e) {
      System.out.println("Could not read assay CSV file at " + termMapPath);
    }

    // read the input CSV file
    try {
      CSVReader reader = new CSVReader(new FileReader(inputPath));
      List<String[]> dataRows = reader.readAll();
      reader.close();
      for(String[] dataRow: dataRows) {
        rows.add(Arrays.asList(dataRow));
      }
    } catch (IOException e) {
      System.out.println("Could not read assay CSV file at " + inputPath);
    }

    return map(termMap, rows, outputPath);
  }

  /**
   * Given a map from terms to CURIE strings,
   * a List of Lists of strings with the input data,
   * and a path to the output file,
   * apply the mapping to each input data string,
   * and write to the output file.
   * The first row of the input data (the header row) is not mapped.
   *
   * @param termMap a map from term strings to CURIE strings
   * @param inputData a list of rows, which are lists of strings
   * @param outputPath the path of the output file
   * @return the result of applying the map to the input data
   */
  public static List<List<String>> map(Map<String,String> termMap,
      List<List<String>> inputData, String outputPath) {
    List<List<String>> results = new ArrayList<List<String>>();
    List<String> headers = inputData.remove(0);
    // 1/1/14 10:21 AM
    SimpleDateFormat crazyFormat = new SimpleDateFormat("d/M/y hh:mm a");
    SimpleDateFormat saneFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

    // iterate over rows
    for(List<String> row: inputData) {
      List<String> result = new ArrayList<String>();
      int column = 0;
      for(String cell: row) {

        // do the mapping!
        String header = headers.get(column);
        if(termMap.containsKey(cell)) {         // term mapping
          result.add(termMap.get(cell));
        } else if (header.equals("datetime")) { // datetime column
          try {
            Date date = crazyFormat.parse(cell);
            result.add(saneFormat.format(date));
          } catch(Exception e) {
            result.add(cell);
          }
        } else if (header.equals("subject")) {  // subject column
          result.add("study:subject-" + cell);
        } else if (header.equals("group")) {    // group column
          result.add("study:group-" + cell);
        } else {                                // do nothing
          result.add(cell);
        }
        column = column + 1;

      }
      results.add(result);
    }

    // write
    try {
      CSVWriter writer = new CSVWriter(new FileWriter(outputPath));
      writer.writeNext((String[]) headers.toArray(new String[0]));
      for(List<String> result: results) {
        writer.writeNext((String[]) result.toArray(new String[0]));
      }
      writer.close();
    } catch (IOException e) {
      System.out.println("Could not write assay CSV file to " + outputPath);
    }

    return results;
  }

}

