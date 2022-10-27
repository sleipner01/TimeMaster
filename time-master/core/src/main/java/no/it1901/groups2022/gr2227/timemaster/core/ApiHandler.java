package no.it1901.groups2022.gr2227.timemaster.core;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;


public class ApiHandler {

  final String baseURL = "http://localhost:8080/api/";
  private TimeMasterJsonParser jsonParser = new TimeMasterJsonParser();
  
  private String getResponse(String path) throws IOException {
    HttpURLConnection connection = (HttpURLConnection) new URL(baseURL + path).openConnection();

    connection.setRequestMethod("GET");
    int responseCode = connection.getResponseCode();
    String response = "";
    if (responseCode == HttpURLConnection.HTTP_OK) {
      Scanner scanner = new Scanner(connection.getInputStream());
      while (scanner.hasNextLine()) {
        response += scanner.nextLine();
        response += "\n";
      }
      scanner.close();
      }
      return response;
    }

  public Employee getEmployee(String name) throws IOException {
    return this.jsonParser.readEmployee(this.getResponse(name));
  }

  public ArrayList<Employee> getEmployees() throws IOException {
    return this.jsonParser.readEmployees(this.getResponse("employees"));
  }

  public ArrayList<Workday> getWorkdays(String name) throws IOException {
    return this.jsonParser.readWorkdays(this.getResponse(name));
  }

}
