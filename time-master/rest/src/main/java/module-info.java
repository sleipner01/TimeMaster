module no.it1901.groups2022.gr2227.timemaster.rest {
  requires jakarta.ws.rs;
  requires jakarta.json;

  requires com.fasterxml.jackson.core;
  requires com.fasterxml.jackson.databind;
  requires com.fasterxml.jackson.datatype.jsr310;

  requires jersey.common;
  requires jersey.server;
  requires jersey.media.json.jackson;
  requires jersey.container.grizzly2.http;
  requires jersey.container.servlet;
  requires jersey.container.servlet.core;
  requires jersey.hk2;
  requires grizzly.http.server;

  requires org.glassfish.hk2.api;
  requires org.slf4j;

  opens no.it1901.groups2022.gr2227.timemaster.rest to jersey.server;
}