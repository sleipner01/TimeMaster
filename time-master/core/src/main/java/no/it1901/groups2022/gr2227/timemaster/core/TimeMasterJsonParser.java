package no.it1901.groups2022.gr2227.timemaster.core;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.ArrayList;
import no.it1901.groups2022.gr2227.timemaster.mixin.EmployeeMixin;
import no.it1901.groups2022.gr2227.timemaster.mixin.WorkdayMixin;

public class TimeMasterJsonParser {
  
  final ObjectMapper mapper;

  public TimeMasterJsonParser() {
    this.mapper = new ObjectMapper();
    this.mapper.configure(Feature.AUTO_CLOSE_SOURCE, true);
    this.mapper.addMixIn(Employee.class, EmployeeMixin.class);
    this.mapper.addMixIn(Workday.class, WorkdayMixin.class);
    this.mapper.enable(SerializationFeature.INDENT_OUTPUT);
    this.mapper.registerModule(new JavaTimeModule());
  }
 

  public String write(Object value) {
    try {
      return this.mapper.writeValueAsString(value);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
  
  public Employee readEmployee(String input) {
    try {
      return this.mapper.readValue(input, Employee.class);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
  
  public ArrayList<Employee> readEmployees(String input) {
    try {
      return this.mapper.readValue(input, new TypeReference<ArrayList<Employee>>() {});
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public ArrayList<Workday> readWorkdays(String input) {
    try {
      return this.mapper.readValue(input, new TypeReference<ArrayList<Workday>>() {});
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
  
}
