package com.example.leshan.server.demo.servlet;

import java.security.PublicKey;
import java.security.cert.X509Certificate;

import javax.servlet.http.HttpServlet;

import org.eclipse.leshan.server.security.EditableSecurityStore;

public class SecurityServlet extends HttpServlet {


  public SecurityServlet(EditableSecurityStore store, X509Certificate serverCertificate) {

  }

  public SecurityServlet(EditableSecurityStore store, PublicKey serverPublicKey) {

  }

}
