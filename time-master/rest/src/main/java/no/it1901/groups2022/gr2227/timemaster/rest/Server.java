package no.it1901.groups2022.gr2227.timemaster.rest;

import java.io.IOException;
import java.net.URI;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Main class.
 *
 */
public class Server {

  /** 
   * Base URI the Grizzly HTTP server will listen on
   */
  public static final String BASE_URI = "http://localhost:8080/";

  /**
   * Starts Grizzly HTTP server exposing JAX-RS resources defined in this
   * application.
   *
   * @return Grizzly HTTP server.
   */
  public static HttpServer startServer() {
    // create a resource config that scans for JAX-RS resources and providers
    // in no.it1901.groups2022.gr2227.timemaster.rest package
    FileHandler fileHandler = new FileHandler("employees.json");
    fileHandler.readFile();
    final ResourceConfig rc = new ResourceConfig(Rest.class);

    // create and start a new instance of grizzly http server
    // exposing the Jersey application at BASE_URI
    return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
  }

  /**
   * Main method.
   *
   * @param args arguments for main method, program takes no args
   * @throws IOException throws IOException if server failed
   */
  public static void main(String[] args) throws IOException {
    final HttpServer server = startServer();
    System.out.println(String.format("Jersey app started with endpoints available at "
        + "%s%nPress Enter to stop it...", BASE_URI));
    System.in.read();
    server.shutdownNow();
  }
}
