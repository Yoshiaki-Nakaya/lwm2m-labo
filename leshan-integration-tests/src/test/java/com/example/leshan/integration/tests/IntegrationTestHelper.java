package com.example.leshan.integration.tests;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.eclipse.leshan.client.object.Device;
import org.eclipse.leshan.client.servers.ServerIdentity;
import org.eclipse.leshan.core.model.ObjectModel;
import org.eclipse.leshan.server.californium.LeshanServerBuilder;
import org.eclipse.leshan.server.registration.Registration;

public class IntegrationTestHelper {
  public static final Random r = new Random();
  public static final long LIFETIME = 2;
  public static final int TEST_OBJECT_ID = 2000;
  public static final int STRING_RESOURCE_ID = 0;
  public static final int BOOLEAN_RESOURCE_ID = 1;
  public static final int INTEGER_RESOURCE_ID = 2;
  public static final int FLOAT_RESOURCE_ID = 3;
  public static final int TIME_RESOURCE_ID = 4;
  public static final int OPAQUE_RESOURCE_ID = 5;
  public static final int OBJLNK_MULTI_INSTANCE_RESOURCE_ID = 6;
  public static final int OBJLNK_SINGLE_INSTANCE_RESOURCE_ID = 7;
  public static final int INTEGER_MANDATORY_RESOURCE_ID = 8;
  public static final int STRING_MANDATORY_RESOURCE_ID = 9;
  private SynchronousClientObserver clientObserver = new SynchronousClientObserver();
  private SynchronousRegistrationListener registrationListener = new SynchronousRegistrationListener() {
    
  };

  protected List<ObjectModel> createObjectModels() {
    throw new UnsupportedOperationException("Not Impl yet.");
    
  }

  public void initialize() {
  }

  public String getCurrentEndpoint() {
    throw new UnsupportedOperationException("Not Impl yet.");
  }

  public void createClient() {
  }

  public static class TestDevice extends Device {
  }

  public void createClient(Map<String, String> additionalAttributes) {
  }

  public void createServer() {
  }

  protected LeshanServerBuilder createServerBuilder() {
    throw new UnsupportedOperationException("Not Impl yet.");
  }

  protected void setupServerMonitoring() {
  }

  protected void setupClientMonitoring() {
  }

  public void waitForRegistrationAtClientSide(long timeInSeconds) {
  }

  public void waitForRegistrationAtServerSide(long timeInSeconds) {
  }

  public void ensureNoRegistration(long timeInSeconds) {
  }

  public void waitForUpdateAtClientSide(long timeInSeconds) {
  }

  public void waitForBootstrapFinishedAtClientSide(long timeInSeconds) {
  }

  public void ensureNoUpdate(long timeInSeconds) {
  }

  public void waitForDeregistrationAtServerSide(long timeInSeconds) {
  }

  public void waitForDeregistrationAtClientSide(long timeInSeconds) {
  }

  public void ensureNoDeregistration(long timeInSeconds) {
  }

  public Registration getCurrentRegistration() {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

  public ServerIdentity getCurrentRegisteredServer() {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

  public void deregisterClient() {
  }

  public void dispose() {
  }

  public void assertClientRegisterered() {
  }

  public void assertClientNotRegisterered() {
  }

  public Registration getLastRegistration() {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

}
