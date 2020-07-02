package com.example.leshan.server.demo;

import java.net.InetSocketAddress;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.leshan.core.node.codec.DefaultLwM2mNodeDecoder;
import org.eclipse.leshan.core.node.codec.DefaultLwM2mNodeEncoder;
import org.eclipse.leshan.core.node.codec.LwM2mNodeDecoder;
import org.eclipse.leshan.server.californium.LeshanServer;
import org.eclipse.leshan.server.californium.LeshanServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LeshanServerController {

  private static final Logger LOG = LoggerFactory.getLogger(LeshanServerController.class);

  private Server server;
  private LeshanServer lwServer;

  private LeshanServerController(final ServerOptions serverOptions) {
    createServer(serverOptions);
  }

  private void createServer(ServerOptions serverOptions) {
    // Prepare LWM2M server
    LeshanServerBuilder builder = new LeshanServerBuilder();
    builder.setEncoder(new DefaultLwM2mNodeEncoder());
    LwM2mNodeDecoder decoder = new DefaultLwM2mNodeDecoder();
    builder.setDecoder(decoder);

    // Create and start LWM2M server
    LeshanServer lwServer = builder.build();

    // Now prepare Jetty
    InetSocketAddress jettyAddr;
    if (serverOptions.getWebAddress() == null) {
      jettyAddr = new InetSocketAddress(serverOptions.getWebPort());
    } else {
      jettyAddr = new InetSocketAddress(serverOptions.getWebAddress(), serverOptions.getWebPort());
    }
    Server server = new Server(jettyAddr);
    WebAppContext root = new WebAppContext();
    root.setContextPath("/");
    root.setResourceBase(LeshanServerDemo.class.getClassLoader().getResource("webapp").toExternalForm());
    root.setParentLoaderPriority(true);
    server.setHandler(root);
  }

  public void start() throws Exception {
    // Start Jetty & Leshan
    lwServer.start();
    server.start();
    LOG.info("Web Server started at {}.", server.getURI());
  }

  public static final LeshanServerController createWith(ServerOptions options) {
    return new LeshanServerController(options);
  }
}
