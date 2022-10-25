package no.it1901.groups2022.gr2227.timemaster.rest;

import no.it1901.groups2022.gr2227.timemaster.core.Employee;
import no.it1901.groups2022.gr2227.timemaster.core.TimeMaster;
import no.it1901.groups2022.gr2227.timemaster.mixin.Mixin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

/**
 * Root resource (exposed at "api" path)
 */
@Path("api")
public class Rest {

  TimeMaster timeMaster = new TimeMaster("employees.json");
  ObjectMapper mapper = new ObjectMapper();

  /**
   * Method handling HTTP GET requests. The returned object will be sent
   * to the client as "text/plain" media type.
   *
   * @return String that will be returned as a text/plain response.
   */
  @Path("test")
  @GET
  // @Produces("application/json")
  public String helloWorld() {
    return "Hello, World!";
  }

  @Path("employees")
  @GET
  @Produces("application/json")
  public String getEmployees() {
    String json = "ERROR\n";
    timeMaster.readEmployees();
    mapper.addMixIn(Employee.class, Mixin.class);
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
    mapper.registerModule(new JavaTimeModule());

    try {
      json = mapper.writeValueAsString(timeMaster.getEmployees());
    } catch (Exception e) {
      e.printStackTrace();
    }

    return json;
  }
}
