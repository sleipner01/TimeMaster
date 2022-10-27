package no.it1901.groups2022.gr2227.timemaster.rest;

import com.fasterxml.jackson.databind.JsonNode;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

@Path("api")
public class Rest {

  FileHandler fileHandler = new FileHandler("employees.json");

  @Path("test")
  @GET
  public String helloWorld() {
    return "Hello, World!";
  }

  @Path("employees")
  @Produces("application/json")
  @GET
  public JsonNode getEmployees() {
    try {
      return fileHandler.readFile();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  @Path("employees")
  @Produces("text/plain")
  @Consumes("application/json")
  @PUT
  public void createEmployee(String req) {
    try {
      fileHandler.write(fileHandler.parseString(req));;
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Path("employees/{name}")
  @Produces("application/json")
  @GET
  public JsonNode getEmployeeByName(@PathParam("name") String name) {
    try {
      for (JsonNode node : fileHandler.readFile()) {
        if (node.get("name").textValue().toLowerCase().equals(name.toLowerCase())) {
          return node;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
  
}
