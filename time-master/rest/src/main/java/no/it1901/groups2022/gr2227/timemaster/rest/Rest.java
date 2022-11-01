package no.it1901.groups2022.gr2227.timemaster.rest;

import java.io.File;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
@Path("api")
public class Rest {

  FileHandler fileHandler;

  public Rest() {
    if (new File(Paths.get(System.getProperty("user.dir"), "../rest/timeMasterSaveFiles").toString(), "employeesTest.json").exists()) {
      fileHandler = new FileHandler("employeesTest.json");
    } else {
      fileHandler = new FileHandler("employees.json");
    }
  }

  @Path("test")
  @GET
  public String helloWorld() {
    return "Hello, World!";
  }

  @Path("employees")
  @Produces("application/json")
  @GET
  public JsonNode getEmployees() {
      return fileHandler.readFile();
  }

  @Path("employees")
  @Consumes("application/json")
  @POST
  public Response createEmployee(JsonNode req) {
      ArrayNode file = (ArrayNode) fileHandler.readFile();
      file.add(req);
      fileHandler.write(file);
      return Response.status(Response.Status.CREATED).entity("Created employee with id:" + req.get("id")).build();
  }

  @Path("employees/{id}")
  @Consumes("application/json")
  @PUT
  public Response updateEmployee(JsonNode req, @PathParam("id") String id) {
    ArrayNode file = (ArrayNode) fileHandler.readFile();
    for (int i = 0; i < file.size(); i++) {
      if (file.get(i).get("id").textValue().equals(id)) {
        file.remove(i);
        file.insert(i, req);
        fileHandler.write(file);
        return Response.status(Status.OK).entity("Updated employee with id:" + req.get("id")).build();
      }
    }
    return Response.status(Status.NOT_FOUND).entity(Status.NOT_FOUND.getReasonPhrase()).build();
  }

  @Path("employees/{name}")
  @Produces("application/json")
  @GET
  public JsonNode getEmployeeByName(@PathParam("name") String name) {
      for (JsonNode node : fileHandler.readFile()) {
        if (node.get("name").textValue().toLowerCase().equals(name.toLowerCase())) {
          return node;
        }
      }
    return null;
  }

  @Path("employees/{id}")
  @Consumes("application/json")
  @DELETE
  public Response deleteEmployee(@PathParam("id") String id) {
    ArrayNode file = (ArrayNode)fileHandler.readFile();
    for (int i = 0; i < file.size(); i++) {
      System.out.println(file.get(i).get("id").textValue());
      System.out.println(id);
      if (file.get(i).get("id").textValue().equals(id)) {
        file.remove(i);
        fileHandler.write(file);
        return Response.status(Status.OK).entity("Deleted employee with id:" + id).build();
      }
    }
    return Response.status(Status.NOT_FOUND).entity(Status.NOT_FOUND.getReasonPhrase()).build();
    }
  }
