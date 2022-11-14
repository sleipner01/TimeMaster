package no.it1901.groups2022.gr2227.timemaster.core;

import org.junit.jupiter.api.Test;
import com.github.tomakehurst.wiremock.WireMockServer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;


public class ApiHandlerTest {
  
  private WireMockServer wireMockServer;
  private ApiHandler apiHandler;
  private Employee testEmployee1;
  final private TimeMasterJsonParser jsonParser = new TimeMasterJsonParser();

  final private static int PORT = 8081;
  final private static int STATUSOK = 200;
  final private static int STATUSCREATED = 201;

  @BeforeEach
  public void createApiHandler() {
    apiHandler = new ApiHandler("http://localhost:8081/");
    testEmployee1 = new Employee("Anna");
    this.wireMockServer = new WireMockServer(PORT); // Listens to port 8081
    this.wireMockServer.start();
    configureFor(PORT);
  }

  @AfterEach
  public void stopWireMockServer() {
    this.wireMockServer.stop();
  }

  @Test
  public void testCreateEmployee() throws IOException {
    stubFor(post(urlEqualTo("/api/employees"))
        .willReturn(status(STATUSCREATED)));

    assertEquals(STATUSCREATED, apiHandler.createEmployee(testEmployee1));
  }

  @Test
  public void testGetEmployees() throws IOException {
    stubFor(get(urlEqualTo("/api/employees"))
        .willReturn(okJson("[]")));

    assertEquals(apiHandler.getEmployees(), new ArrayList<>());
  }

  @Test
  public void testDeleteEmployees() throws IOException {
    stubFor(delete(urlEqualTo("/api/employees/" + testEmployee1.getId()))
        .willReturn(status(STATUSOK)));

    assertEquals(
      STATUSOK, apiHandler.deleteEmployee(testEmployee1));
  }

  @Test
  public void testGetWorkdays() throws IOException {
    stubFor(get(urlEqualTo("/api/employees/" + testEmployee1.getId()))
        .willReturn(okJson("[]")));

    // assertEquals(apiHandler.getWorkdays(testEmployee1), new ArrayList<>());

  }

  @Test
  public void testGetEmployee() throws IOException {
    stubFor(get(urlEqualTo("/api/employees/" + testEmployee1.getId()))
        .willReturn(okJson(jsonParser.write(testEmployee1))));
    
    assertEquals(testEmployee1.getId(),
        apiHandler.getEmployee(testEmployee1).getId());
  }

  @Test
  public void testUpdateEmployee() throws IOException {
    stubFor(put(urlEqualTo("/api/employees/" + testEmployee1.getId()))
        .willReturn(status(STATUSOK)));
    
    assertEquals(STATUSOK, apiHandler.updateEmployee(testEmployee1));

  }

}
