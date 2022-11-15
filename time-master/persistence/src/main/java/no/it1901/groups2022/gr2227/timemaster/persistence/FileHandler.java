package no.it1901.groups2022.gr2227.timemaster.persistence;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * FileHandler is a class that reads to and from file.
 * It encapulates 
 * <ul>
 *  <li> ObjectMapper reads to and writes from file. 
 *  <li> String dir is the path to where write and read.
 *  <li> String fileName name of the file that is read or written.
 * </ul>
 */
public class FileHandler implements TimeMasterFileHandler {

  private final ObjectMapper mapper;
  private final String dir;
  private final String fileName;

  /**
   * Makes a FileHandler object.
   *
   * @param name name of the file being written to or read from.
   */
  public FileHandler(String name) {
    this.mapper = new ObjectMapper();
    this.mapper.enable(SerializationFeature.INDENT_OUTPUT);
    this.fileName = name;
    this.dir = Paths.get(System.getProperty("user.dir"), "../timeMasterSaveFiles").toString();
    File file = new File(Paths.get(this.dir, this.fileName).toString());
    try {
      file.createNewFile();
      if (file.length() == 0) {
        this.mapper.writeValue(file, new ArrayList<>());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  @Override
  public JsonNode readFile() {
    try {
      return this.mapper.readTree(
          new File(Paths.get(this.dir,
              this.fileName).toString()));
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Parses a String to JsonNode.
   *
   * @param val the string being parsed to JsonNode
   *
   * @return JsonNode of the input string
   */
  public JsonNode parseString(String val) {
    try {
      return this.mapper.readTree(val);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public void write(Object val) {
    try {
      this.mapper.writeValue(
          new File(Paths.get(this.dir,
              this.fileName).toString()),
          val);
    } catch (RuntimeException e) {
        e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
}
