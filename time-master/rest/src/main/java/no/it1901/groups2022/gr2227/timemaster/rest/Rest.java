package no.it1901.groups2022.gr2227.timemaster.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
/**
 * Root resource (exposed at "api" path)
 */
@Path("api")
public class Rest {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces("application/json")
    public String getIt() {

        return "[{\"id\" : \"bec36f58-2797-45aa-8d11-7522ff34de1d\",\"name\" : \"Test\",\"workdays\" : [ ],\"atWork\" : false}, {\"id\" : \"03ffaa9e-4a78-4008-b63c-b90b48c375c8\",\"name\" : \"Test2\",\"workdays\" : [ {\"date\" : [ 2022, 10, 20 ],\"timeIn\" : [ 20, 38, 41, 267835000 ],\"timeOut\" : null} ],\"atWork\" : true}]";
    }
}
