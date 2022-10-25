package no.it1901.groups2022.gr2227.timemaster.rest;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

public class RestTest extends JerseyTest {

  @Override
  protected Application configure() {
      return new ResourceConfig(Rest.class);
  }

  @Test
  public void helloWorldTest() {
    String responseMsg = target("api/test").request().get(String.class);
    assertEquals("Hello, World!", responseMsg);
  }

  @Test
  public void getEmployeesTest() {
  Response response = target("api/employees").request().get();
  assertEquals(Status.OK.getStatusCode(), response.getStatus());
  }

}
