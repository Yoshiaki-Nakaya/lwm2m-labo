package com.example.leshan.integration.tests.helper;

import java.security.PrivateKey;
import java.security.PublicKey;

import org.eclipse.leshan.client.object.Security;
import org.eclipse.leshan.client.resource.ObjectsInitializer;
import org.eclipse.leshan.core.SecurityMode;
import org.eclipse.leshan.server.bootstrap.BootstrapConfigStore;
import org.eclipse.leshan.server.californium.bootstrap.LeshanBootstrapServer;
import org.eclipse.leshan.server.security.BootstrapSecurityStore;
import org.eclipse.leshan.server.security.SecurityInfo;

public class BootstrapIntegrationTestHelper extends SecureIntegrationTestHelper {

  public LeshanBootstrapServer bootstrapServer;
  public final PublicKey bootstrapServerPublicKey;
  public final PrivateKey bootstrapServerPrivateKey;

  public BootstrapIntegrationTestHelper() {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

  public void createBootstrapServer(BootstrapSecurityStore securityStore, BootstrapConfigStore bootstrapStore) {
  }

  public void createBootstrapServer(BootstrapSecurityStore securityStore) {
  }

  public Security withoutSecurity() {
    throw new UnsupportedOperationException("Not Impl yet.");
  }

  public void createClient() {
  }

  public void createPSKClient(String pskIdentity, byte[] pskKey) {
  }

  public void createRPKClient() {
  }

  public void createClient(Security security, ObjectsInitializer initializer) {
  }

  public void createClient(ObjectsInitializer initializer) {
  }

  public BootstrapSecurityStore bsSecurityStore(final SecurityMode mode) {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

  public SecurityInfo pskSecurityInfo() {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

  public SecurityInfo rpkSecurityInfo() {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

  private BootstrapSecurityStore dummyBsSecurityStore() {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

  public BootstrapConfigStore unsecuredBootstrapStore() {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

  public BootstrapConfigStore deleteSecurityStore(Integer... objectToDelete) {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

  public BootstrapConfigStore deleteSecurityStore(final String... pathToDelete) {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

  public BootstrapConfigStore unsecuredWithAclBootstrapStore() {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

  public BootstrapConfigStore pskBootstrapStore() {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

  public BootstrapConfigStore rpkBootstrapStore() {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

  public void dispose() {
  }

}
