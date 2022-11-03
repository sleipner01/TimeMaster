package no.it1901.groups2022.gr2227.timemaster.core;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import no.it1901.groups2022.gr2227.timemaster.mixin.EmployeeMixin;
import no.it1901.groups2022.gr2227.timemaster.mixin.WorkdayMixin;

public class TimeMasterJsonParser {
  
  final ObjectMapper mapper;
  final String filePath;
  
  public TimeMasterJsonParser(Path dir, String fileName) {
    this.mapper = new ObjectMapper();
    this.mapper.configure(Feature.AUTO_CLOSE_SOURCE, true);
    this.mapper.addMixIn(Employee.class, EmployeeMixin.class);
    this.mapper.addMixIn(Workday.class, WorkdayMixin.class);
    this.mapper.enable(SerializationFeature.INDENT_OUTPUT);
    this.mapper.registerModule(new JavaTimeModule());
    this.filePath = Paths.get(dir.toString(), fileName).toString();
  }
  
  public void write(ArrayList<Employee> employees) {
    try {
      this.mapper.writeValue(new File(filePath), employees);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public ArrayList<Employee> read() {
    var employees = new ArrayList<Employee>();
    if (new File(filePath).length() == 0) {
      System.out.println("Savefile is empty");
      return new ArrayList<>();
    } 
    try {
      employees = this.mapper.readValue(
        new File(filePath),
        new TypeReference<ArrayList<Employee>>() {
        });
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    return employees;
  }
  
}
