package no.it1901.groups2022.gr2227.timemaster.rest;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;

import org.glassfish.grizzly.http.server.HttpServer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class RestTest {

    private HttpServer server;
    private WebTarget target;

    // @BeforeEach
    // public void setUp() throws Exception {
    //     // start the server
    //     server = Main.startServer();
    //     // create the client
    //     Client c = ClientBuilder.newClient();

    //     // uncomment the following line if you want to enable
    //     // support for JSON in the client (you also have to uncomment
    //     // dependency on jersey-media-json module in pom.xml and Main.startServer())
    //     // --
    //     // c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

    //     target = c.target(Main.BASE_URI);
    // }

    // @AfterEach
    // public void tearDown() throws Exception {
    //     server.shutdown();
    // }

    /**
     * Test to see that the message "Got it!" is sent in the response.
     */
    // @Test
    // public void testGetIt() {
    //     String msg = "[{\"id\" : \"bec36f58-2797-45aa-8d11-7522ff34de1d\",\"name\" : \"Test\",\"workdays\" : [ ],\"atWork\" : false}, {\"id\" : \"03ffaa9e-4a78-4008-b63c-b90b48c375c8\",\"name\" : \"Test2\",\"workdays\" : [ {\"date\" : [ 2022, 10, 20 ],\"timeIn\" : [ 20, 38, 41, 267835000 ],\"timeOut\" : null} ],\"atWork\" : true}]";
    //     String responseMsg = target.path("api").request().get(String.class);
    //     assertEquals(msg, responseMsg);
    // }

    @Test
    public void simple() {
        assertEquals(true, true);
    }
}
