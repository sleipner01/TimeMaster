package no.it1901.groups2022.gr2227.timemaster.rest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FileHandler {

  final ObjectMapper mapper;
  final String fileName;
  final String dir;
  final File file;

  public FileHandler(String name) {
    this.mapper = new ObjectMapper();
    this.fileName = name;
    this.dir = Paths.get(System.getProperty("user.dir"), "../rest/timeMasterSaveFiles").toString();
    this.file = new File(Paths.get(this.dir.toString(), this.fileName).toString());
    try {
      file.createNewFile();
      if (file.length() == 0) {
        this.mapper.writeValue(file, new ArrayList<>());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public JsonNode readFile() {
    try {
      return this.mapper.readTree(this.file);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public JsonNode parseString(String val) {
    try {
      return this.mapper.readTree(val);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public void write(Object val) {
    try {
      this.mapper.writeValue(file, val);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
}
