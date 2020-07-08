package com.example.leshan.server.demo.servlet;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.servlets.EventSource;
import org.eclipse.jetty.servlets.EventSourceServlet;
import org.eclipse.leshan.server.californium.LeshanServer;

public class EventServlet extends EventSourceServlet {

  public EventServlet(LeshanServer sever, int securePort) {

  }

  @Override
  protected EventSource newEventSource(HttpServletRequest request) {
    // TODO Auto-generated method stub
    return null;
  }

}
