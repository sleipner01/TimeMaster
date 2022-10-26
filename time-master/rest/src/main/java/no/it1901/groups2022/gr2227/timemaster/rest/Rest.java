package no.it1901.groups2022.gr2227.timemaster.rest;

import no.it1901.groups2022.gr2227.timemaster.core.Employee;
import no.it1901.groups2022.gr2227.timemaster.core.TimeMaster;
import no.it1901.groups2022.gr2227.timemaster.mixin.Mixin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

@Path("api")
public class Rest {

  final TimeMaster timeMaster = new TimeMaster("employees.json");
  final ObjectMapper mapper = new ObjectMapper()
    .addMixIn(Employee.class, Mixin.class)
    .enable(SerializationFeature.INDENT_OUTPUT)
    .registerModule(new JavaTimeModule());

  @Path("test")
  @GET
  public String helloWorld() {
    return "Hello, World!";
  }

  @Path("employees")
  @Produces("application/json")
  @GET
  public String getEmployees() {
    String res = null;
    timeMaster.readEmployees();

    try {
      res = mapper.writeValueAsString(timeMaster.getEmployees());
    } catch (Exception e) {
      e.printStackTrace();
    }

    return res;
  }

  @Path("employees/{name}")
  @Produces("application/json")
  @GET
  public String getEmployeeByName(@PathParam("name") String name) {
    String res = null;
    timeMaster.readEmployees();

    try {
      for (Employee employee : timeMaster.getEmployees()) {
        if (employee.getName().toLowerCase().equals(name.toLowerCase())) {
          res = mapper.writeValueAsString(employee);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return res;
  }
}
