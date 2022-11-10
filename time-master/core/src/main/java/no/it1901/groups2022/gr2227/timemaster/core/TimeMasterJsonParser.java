package no.it1901.groups2022.gr2227.timemaster.core;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.ArrayList;
import no.it1901.groups2022.gr2227.timemaster.mixin.EmployeeMixin;
import no.it1901.groups2022.gr2227.timemaster.mixin.WorkdayMixin;

/**
 * Object used in the TimeMaster application to serialize objects to Json-strings.
 *
 * @author Håvard Solberg Nybøe
 * @author Amalie Mansåker
 * @version 1.0
 * @since 1.0
 */
public class TimeMasterJsonParser {
  
  private final ObjectMapper mapper;

  /**
   * Creates a Json-parser object made for
   * the TimeMaster application.
   */
  public TimeMasterJsonParser() {
    this.mapper = new ObjectMapper();
    this.mapper.configure(Feature.AUTO_CLOSE_SOURCE, true);
    this.mapper.addMixIn(Employee.class, EmployeeMixin.class);
    this.mapper.addMixIn(Workday.class, WorkdayMixin.class);
    this.mapper.enable(SerializationFeature.INDENT_OUTPUT);
    this.mapper.registerModule(new JavaTimeModule());
  }
 
  /**
   * Converts the provided Object to a String in JSON-format.
   *
   * @param value   Object to be converted.
   * @return        Converted object
   *                <code>null</code> if the string couldn't be parsed.
   * @see <a href="https://github.com/FasterXML/jackson">com.fasterxml.jackson</a>
   */
  public String write(Object value) {
    try {
      return this.mapper.writeValueAsString(value);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
  
  /**
   * Creates an Employee-object from the provided String-object.
   * The string has to follow the same format as the one 
   * being parsed from an Employee-object to a String.
   *
   * @param input   String represented JSON-format of an Employee-object.
   * @return        Employee-object
   *                <code>null</code> if the string couldn't be parsed.
   * @see Employee
   * @see <a href="https://github.com/FasterXML/jackson">com.fasterxml.jackson</a>
   */
  public Employee readEmployee(String input) {
    try {
      return this.mapper.readValue(input, Employee.class);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
  
  /**
   * Creates an ArrayList with Employees from the provided String-object.
   * The string has to follow the same format as the one 
   * being parsed from an Employee-object to a String.
   *
   * @param input   String represented JSON-format of Employee-objects.
   * @return        List of Employee-objects.
   *                <code>null</code> if the string couldn't be parsed.
   * @see Employee
   * @see <a href="https://github.com/FasterXML/jackson">com.fasterxml.jackson</a>
   */
  public ArrayList<Employee> readEmployees(String input) {
    try {
      return this.mapper.readValue(input, new TypeReference<ArrayList<Employee>>() {});
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Creates an ArrayList with workdays from the provided String-object.
   * The string has to follow the same format as the one 
   * being parsed from a Workday-object to a String.
   *
   * @param input   String represented JSON-format of Workday-objects.
   * @return        List of Workday-objects.
   *                <code>null</code> if the string couldn't be parsed.
   * @see Workday
   * @see <a href="https://github.com/FasterXML/jackson">com.fasterxml.jackson</a>
   */
  public ArrayList<Workday> readWorkdays(String input) {
    try {
      return this.mapper.readValue(input, new TypeReference<ArrayList<Workday>>() {});
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
  
}
