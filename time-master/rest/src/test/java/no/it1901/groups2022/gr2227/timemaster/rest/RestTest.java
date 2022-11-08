package no.it1901.groups2022.gr2227.timemaster.rest;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

public class RestTest extends JerseyTest {

  FileHandler fileHandler;
  String employeeJson;
  File file;

  @Override
  protected Application configure() {
    return new ResourceConfig(Rest.class);
  }

  @BeforeEach
  public void init() {
    fileHandler = new FileHandler("employeesTest.json");
    employeeJson = "{\"id\":\"0\",\"name\":\"Anne\",\"workdays\":[],\"atWork\":false}";
    file = new File(Paths.get(System.getProperty("user.dir"), "../rest/timeMasterSaveFiles").toString(), "employeesTest.json");
  }

  @AfterEach
  public void cleanUp() {
    file.delete();
  }

  @Test
  public void systemStatusTest() {
    Response res = target("api").request().get();
    assertEquals(Status.OK.getStatusCode(), res.getStatus());
  }

  @Test
  public void getEmployeesTest() {
    Response res = target("api/employees").request().get();
    assertEquals(Status.OK.getStatusCode(), res.getStatus());
  }

  @Test
  public void createEmployeeTest() {
    Response res = target("api/employees").request().post(Entity.json(employeeJson));
    assertEquals(Status.CREATED.getStatusCode(), res.getStatus());
  }
  
  @Test
  public void getEmployeeByIdTest() {
    target("api/employees").request().post(Entity.json(employeeJson));
    String res1 = target("api/employees/0").request().get(String.class);
    assertEquals(employeeJson, res1);
    Response res2 = target("api/employees/NaN").request().get();
    assertEquals(Status.NOT_FOUND.getStatusCode(), res2.getStatus());

  }

  @Test
  public void updateEmployeeTest() {
    target("api/employees").request().post(Entity.json(employeeJson));
    Response res1 = target("api/employees/0").request().put(Entity.json(employeeJson));
    assertEquals(Status.OK.getStatusCode(), res1.getStatus());
    Response res2 = target("api/employees/NaN").request().put(Entity.json(employeeJson));
    assertEquals(Status.NOT_FOUND.getStatusCode(), res2.getStatus());
  }

  @Test
  public void deleteEmployeeTest() {
    target("api/employees").request().post(Entity.json(employeeJson));
    Response res1 = target("/api/employees/0").request().delete();
    assertEquals(Status.OK.getStatusCode(), res1.getStatus());

    target("api/employees").request().post(Entity.json(employeeJson));
    Response res2 = target("/api/employees/NaN").request().delete();
    assertEquals(Status.NOT_FOUND.getStatusCode(), res2.getStatus());
  }

}
