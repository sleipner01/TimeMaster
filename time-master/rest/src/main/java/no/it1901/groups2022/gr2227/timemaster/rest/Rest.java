package no.it1901.groups2022.gr2227.timemaster.rest;

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
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** 
 * Rest is the server side of the REST API, and responds to requests from the client. 
 * It encapsulates a filehandler object, which writes to and reads from a file.
 * 
*/
@Path("api")
public class Rest {

  private FileHandler fileHandler;

  /**
   * Creates a Rest object.
   * The fileHandler object is depending on if a file already exists or not.
   * 
   */
  public Rest() {
    if (new File(Paths.get(System.getProperty("user.dir"),
        "../rest/timeMasterSaveFiles").toString(),
        "employeesTest.json").exists()) {
      fileHandler = new FileHandler("employeesTest.json");
    } else {
      fileHandler = new FileHandler("employees.json");
    }
  }

  /**
   * Gets a list of all employees.
   *
   * @return Json node of all employees.
   */
  @Path("employees")
  @Produces("application/json")
  @GET
  public JsonNode getEmployees() {
    return fileHandler.readFile();
  }

  /**
   * Creates a new employee.
   *
   * @param req the employee object as JsonNode to be added.
   *
   * @return 201 Created response.
   */
  @Path("employees")
  @Consumes("application/json")
  @POST
  public Response createEmployee(JsonNode req) {
    ArrayNode file = (ArrayNode) fileHandler.readFile();
    file.add(req);
    fileHandler.write(file);
    return Response.status(Response.Status.CREATED)
        .entity("Created employee with id:" + req.get("id")).build();
  }

  /**
   * Updates an employee object.
   *
   * @param req the employee object to be updated as JsonNode.
   *
   * @param id  the ID of the employee, works as path.
   *
   * @return either a 200 OK or 404 Not Found response.
   */
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
        return Response.status(Status.OK)
            .entity("Updated employee with id:" + req.get("id")).build();
      }
    }
    return Response.status(Status.NOT_FOUND).entity(Status.NOT_FOUND.getReasonPhrase()).build();
  }

  /**
   * Gets the given employee.
   *
   * @param id ID of employee, works as path.
   *
   * @return employee by ID as node.
   */
  @Path("employees/{id}")
  @Produces("application/json")
  @GET
  public Response getEmployeeById(@PathParam("id") String id) {
    for (JsonNode node : fileHandler.readFile()) {
      if (node.get("id").textValue().equals(id)) {
        return Response.status(Status.OK).entity(node).build();
      }
    }
    return Response.status(Status.NOT_FOUND).build();
  }

  /**
   * Checks if server is running.
   *
   * @return 202 OK respons.
   */
  @Path("")
  @Produces({ MediaType.TEXT_PLAIN })
  @GET
  public Response systemStatus() {
    return Response.status(Response.Status.OK)
        .entity("** System running **\n"
            + "Status code: " + Response.Status.OK + "\n"
            + "DateTime: " + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME) + "\n"
            + "Ready for requests...")
        .build();
  }

  /**
   * Deletes the given employee.
   *
   * @param id the ID of the employee, works as path.
   *
   * @return either a 200 OK or 404 Not Found response.
   */
  @Path("employees/{id}")
  @Consumes("application/json")
  @DELETE
  public Response deleteEmployee(@PathParam("id") String id) {
    ArrayNode file = (ArrayNode) fileHandler.readFile();
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
