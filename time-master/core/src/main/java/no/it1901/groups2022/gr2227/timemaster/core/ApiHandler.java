package no.it1901.groups2022.gr2227.timemaster.core;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/** 
 * ApiHandler is the client side of the REST API, and makes requests to the server. 
 * It encapsulates 
 * <ul>
 *  <li> String baseURL which is the base URL for all requests
 *  <li> @see TimeMasterJsonPaser
 * </ul>
 * 
*/
public class ApiHandler {

  private final String baseUrl;
  private TimeMasterJsonParser jsonParser;

  /**
   * Creates an ApiHandler object.
   */
  public ApiHandler() {
    this.baseUrl = "http://localhost:8080/api/";
    this.jsonParser = new TimeMasterJsonParser();
  }
  
  /**
   * Creates the connection needed for requests.
   *
   * @param path          the path to set the connection
   * @param requestMethod the type of request
   * @return              a HttpURL Connection
   * @throws IOException  throws exception if can't connect to server
   */
  private HttpURLConnection setConnection(String path, String requestMethod) throws IOException { 
    HttpURLConnection connection = (HttpURLConnection) new URL(baseUrl + path).openConnection();
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
    System.out.println(response);
    return response;
  }

  private int request(String path, String req, String reqMethod) throws IOException {
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
    return responseCode;
  }

  /**
   * Creates an HTTP-request with method "GET" to endpoint "employees/".
   *
   * @param employee      Employee to retrieve from the API.
   * @return              The employee stored at the API.
   * @throws IOException  If API-call failed.
   * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/net/HttpURLConnection.html">
   *      java.net.HttpURLConnection
   *      </a>
   */
  public Employee getEmployee(Employee employee) throws IOException {
    return this.jsonParser.readEmployee(this.getResponse("employees/" + employee.getId()));
  }

  /**
   * Creates an HTTP-request with method "GET" to endpoint "employees".
   *
   * @return              List of employees.
   * @throws IOException  If API-call failed.
   * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/net/HttpURLConnection.html">
   *      java.net.HttpURLConnection
   *      </a>
   */
  public ArrayList<Employee> getEmployees() throws IOException {
    return this.jsonParser.readEmployees(this.getResponse("employees"));
  }

  /**
   * Creates an HTTP-request with method "GET" to endpoint "employees/".
   *
   * @param employee      Employee to retrieve workdays from.
   * @return              List of workdays.
   * @throws IOException  If API-call failed.
   * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/net/HttpURLConnection.html">
   *      java.net.HttpURLConnection
   *      </a>
   */
  public ArrayList<Workday> getWorkdays(Employee employee) throws IOException {
    return this.jsonParser.readWorkdays(this.getResponse("employees/" + employee.getId()));
  }

  /**
   * Creates an HTTP-request with method "POST" to endpoint "employees".
   *
   * @param employee      Employee to be created and stored.
   * @return              Responsecode.
   * @throws IOException  If API-call failed.
   * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/net/HttpURLConnection.html">
   *      java.net.HttpURLConnection
   *      </a>
   */
  public int createEmployee(Employee employee) throws IOException {
    return request("employees", this.jsonParser.write(employee), "POST");
  }

  /**
   * Creates an HTTP-request with method "PUT to endpoint "employees/".
   *
   * @param employee      Employee to be updated.
   * @return              Responsecode.
   * @throws IOException  If API-call failed.
   * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/net/HttpURLConnection.html">
   *      java.net.HttpURLConnection
   *      </a>
   */
  public int updateEmployee(Employee employee) throws IOException {
    return request("employees/" + employee.getId(), this.jsonParser.write(employee), "PUT");
  }

  /**
   * Creates an HTTP-request with method "DELETE" to endpoint "employees/".
   *
   * @param employee      Employee to be deleted.
   * @return              Responsecode.
   * @throws IOException  If API-call failed.
   * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/net/HttpURLConnection.html">
   *      java.net.HttpURLConnection
   *      </a>
   */
  public int deleteEmployee(Employee employee) throws IOException {
    return request("employees/" + employee.getId(), "", "DELETE");
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
      if (200 <= responseCode && responseCode < 300) {
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
      } else {
        System.err.println("********************" 
            + "\n" 
            + "The server isn't responding properly.\n" 
            + "Reponse code: " 
            + connection.getResponseCode() 
            + "\n" 
            + "Response message: " 
            + connection.getResponseMessage() 
            + "\n" 
            + "********************"
        );
        return false;
      }
    } catch (IOException e) {
      System.err.println("Connection to the server failed. It might not be running.");
      return false;
    }
  }

}
