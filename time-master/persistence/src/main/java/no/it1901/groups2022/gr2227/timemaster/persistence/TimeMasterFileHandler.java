package no.it1901.groups2022.gr2227.timemaster.persistence;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * TimeMasterFileHandler is an interface that describes 
 * what a filehandler-implementation for the TimeMaster application
 * must contain.
 */
public interface TimeMasterFileHandler {

  /**
   * Reads from a file.
   *
   * @return JsonNode of the information from mapper
   */
  JsonNode readFile();

  /**
   * Writes an object to the file.
   *
   * @param val the object being written to file.
   */
  void write(Object val);

}
