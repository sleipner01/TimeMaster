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

  public Employee getEmployee(Employee employee) throws IOException {
    return this.jsonParser.readEmployee(this.getResponse(employee.getId()));
  }

  public ArrayList<Employee> getEmployees() throws IOException {
    return this.jsonParser.readEmployees(this.getResponse("employees"));
  }

  public ArrayList<Workday> getWorkdays(String name) throws IOException {
    return this.jsonParser.readWorkdays(this.getResponse(name));
  }

  public void createEmployee(Employee employee) throws IOException{
    request("employees", this.jsonParser.write(employee),"POST");
  }

  public void updateEmployee(Employee employee) throws IOException {
    request("employees/" + employee.getId(), this.jsonParser.write(employee),"PUT");
  }

  public void deleteEmployee(Employee employee) throws IOException {
    request("employees/" + employee.getId(), "", "DELETE");
  }

  /**
   * Check server status. 
   *
   * @return  <code>true</code> if server is running and responding.
   *          <code>false</code> if the server is off or isn't responding properly.
   */
  public boolean checkServerStatus() {
    try {
      HttpURLConnection connection = setConnection("", "GET");
      int responseCode = connection.getResponseCode();
      if(200 <= responseCode && responseCode < 300) {
        System.out.println("********************" + "\n");
        String response = "";
          Scanner scanner = new Scanner(connection.getInputStream());
          while (scanner.hasNextLine()) {
            response += scanner.nextLine();
            response += "\n";
          }
          scanner.close();
        
          System.out.println(response);
        System.out.println("********************" + "\n");
        return true;
      }
      else {
        System.err.println( "********************" + "\n" +
          "The server isn't responding properly.\n" + 
          "Reponse code: " + connection.getResponseCode() + "\n" +
          "Response message: " + connection.getResponseMessage() + "\n" +
          "********************"
          );
        return false;
      }
    } catch (IOException e) {
      System.err.println("Connection to the server failed. It might not be running.");
      return false;
    }
  }

}
