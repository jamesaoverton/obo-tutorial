package obo_tutorial;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;

import obo_tutorial.Extractor;

/**
 * A command-line interface for the OBO tutorial tools.
 *
 * @author <a href="mailto:james@overton.ca">James A. Overton</a>
 */
public class CommandLineInterface {
  /**
   * Parse the command-line arguments and trigger the appropriate actions.
   *
   * @param args command-line arguments
   */
  public static void main(String[] args) {
    CommandLineParser parser = new PosixParser();
    Options options = new Options();
    options.addOption(new Option("h", "help", false, "print this message"));

    try {
      CommandLine line = parser.parse(options, args);
      List<String> arguments = new ArrayList<String>(Arrays.asList(args));
      String command = null;
      if(arguments.size() > 0) {
        command = arguments.remove(0);
      }

      if(line.hasOption("h")) {
        printUsage(options);
      } else if (args.length == 0) {
        System.out.println("No command provided.\n");
        printUsage(options);
      } else if (command.equals("help")) {
        printUsage(options);
      } else if (command.equals("extract")) {
        Extractor.extract(arguments);
      } else {
        System.out.println("Unknown command: " + command + "\n");
        printUsage(options);
      }
    }
    catch(ParseException e) {
      System.out.println("Error while parsing command-line arguments:");
      printUsage(options);
    }
    catch(Exception e) {
      System.out.println("Error while processing command:");
      printUsage(options);
    }
  }

  /**
   * Print command-line usage information.
   *
   * @param options all options for the command-line parser
   */
  public static void printUsage(Options options) {
    HelpFormatter formatter = new HelpFormatter();
    formatter.printHelp("obo-tutorial [options] <command> <arguments...>",
        options);
    System.out.println("commands:");
    String fmt = " %-10s %s";

    System.out.println(String.format(fmt, "help", "print this message"));
    System.out.println(String.format(fmt, "extract", "<source-path> <terms-path> <target-path> <target-iri> extract a list of terms from the source ontology to a target ontology"));
  }
}
