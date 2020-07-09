package com.example.leshan.integration.tests;

import org.eclipse.californium.core.coap.Response;
import org.eclipse.californium.elements.Connector;
import org.eclipse.leshan.client.californium.LeshanClient;
import org.eclipse.leshan.client.servers.ServerIdentity;
import org.eclipse.leshan.core.observation.Observation;
import org.eclipse.leshan.core.response.ObserveResponse;
import org.eclipse.leshan.server.observation.ObservationListener;
import org.eclipse.leshan.server.registration.Registration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ObserveTest {

  protected IntegrationTestHelper helper = new IntegrationTestHelper();

  @BeforeEach
  public void start() {
    Assertions.fail("Test Not Impl yet.");
  }

  @AfterEach
  public void stop() {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void can_observe_resource() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void can_observe_resource_then_passive_cancel() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void can_observe_resource_then_active_cancel() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void can_observe_instance() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void can_observe_object() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void can_handle_error_on_notification() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void can_observe_timestamped_resource() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void can_observe_timestamped_instance() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void can_observe_timestamped_object() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  private void sendNotification(Connector connector, byte[] payload, Response firstCoapResponse, int contentFormat) {
    Assertions.fail("Test Not Impl yet.");
  }

  private Connector getConnector(LeshanClient client, ServerIdentity server) {
    Assertions.fail("Test Not Impl yet.");
    throw new UnsupportedOperationException("Not Impl yet.");

  }

  private final class TestObservationListener implements ObservationListener {

    @Override
    public void newObservation(Observation observation, Registration registration) {
      // TODO Auto-generated method stub

    }

    @Override
    public void cancelled(Observation observation) {
      // TODO Auto-generated method stub

    }

    @Override
    public void onResponse(Observation observation, Registration registration, ObserveResponse response) {
      // TODO Auto-generated method stub

    }

    @Override
    public void onError(Observation observation, Registration registration, Exception error) {
      // TODO Auto-generated method stub

    }


  }

}
