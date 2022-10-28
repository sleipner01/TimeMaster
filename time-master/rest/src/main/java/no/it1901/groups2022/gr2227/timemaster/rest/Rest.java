package no.it1901.groups2022.gr2227.timemaster.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
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
  @Consumes("application/json")
  @PUT
  public Response createEmployee(JsonNode req) {
      ArrayNode file = (ArrayNode) fileHandler.readFile();
      file.add(req);
      fileHandler.write(file);
      return Response.status(Response.Status.CREATED).entity("Created employee with id:" + req.get("id")).build();
  }

  @Path("employees/{id}")
  @Consumes("application/json")
  @POST
  public Response updateEmployee(JsonNode req, @PathParam("id") String id) {
    ArrayNode file = (ArrayNode) fileHandler.readFile();
    for (int i = 0; i < file.size(); i++) {
      String employeeId = file.get(i).get("id").toString();
      employeeId = employeeId.substring(1, employeeId.length()-1); //Dette er smålig hacky men we dont care. please fix
      System.out.println(employeeId);
      System.out.println(id);
      System.out.println(employeeId.equals(id) );
      if (employeeId.equals(id)) {
        file.remove(i);
        file.insert(i, req);
        fileHandler.write(file);
      }
    }
    return Response.status(Response.Status.OK).entity("Updated employee with id:" + req.get("id")).build();
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
