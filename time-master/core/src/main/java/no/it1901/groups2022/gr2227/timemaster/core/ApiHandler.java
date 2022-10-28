package no.it1901.groups2022.gr2227.timemaster.core;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class ApiHandler {

  final String baseURL = "http://localhost:8080/api/";
  private TimeMasterJsonParser jsonParser = new TimeMasterJsonParser();
  
  // https://happycoding.io/tutorials/java-server/rest-api
  private HttpURLConnection setConnection(String path, String requestMethod) throws IOException { 
    HttpURLConnection connection = (HttpURLConnection) new URL(baseURL + path).openConnection();
    connection.setRequestMethod(requestMethod);
    return connection;
  }

  private String getResponse(String path) throws IOException {
    HttpURLConnection connection = setConnection(path, "GET");
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

  private void request(String path, String req, String reqMethod) throws IOException{
    HttpURLConnection connection = setConnection(path, reqMethod);
    connection.setDoOutput(true);
    connection.setRequestProperty("Content-Type", "application/json");
    OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
    writer.write(req);
    System.out.println(req);
    writer.flush();

    int responseCode = connection.getResponseCode();
    System.out.println(responseCode);
    switch (responseCode) {
      case HttpURLConnection.HTTP_OK:
      case HttpURLConnection.HTTP_CREATED:
        System.out.println(reqMethod + " request was successful.");
        break;
      default:
        System.out.println(reqMethod + " request was not successful.");
        break;
    }
  }

  public void postRequest(String path, String req) throws IOException {
    HttpURLConnection connection = setConnection(path, "POST");
    connection.setDoOutput(true);
    connection.setRequestProperty("Content-Type", "application/json");

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

  public void createEmployee(Employee employee) throws IOException{
    request("employees", this.jsonParser.write(employee),"PUT");
  }

    public void updateEmployee(Employee employee) throws IOException {
      request("employees/" + employee.getId(), this.jsonParser.write(employee),"POST");
  }

}
