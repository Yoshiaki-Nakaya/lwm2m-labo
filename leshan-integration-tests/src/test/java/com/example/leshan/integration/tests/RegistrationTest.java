package com.example.leshan.integration.tests;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.eclipse.leshan.core.Link;
import org.eclipse.leshan.server.security.NonUniqueSecurityInfoException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.leshan.integration.tests.helper.IntegrationTestHelper;

public class RegistrationTest {

  protected IntegrationTestHelper helper = new IntegrationTestHelper();

  @BeforeEach
  public void start() {
    helper.initialize();
    helper.createServer();
    helper.server.start();
    helper.createClient();
  }

  @AfterEach
  public void stop() throws InterruptedException {
    helper.client.destroy(true);
    helper.server.destroy();
    helper.dispose();
  }

  @Test
  public void register_update_deregister() {
    // Check client is not registered
    helper.assertClientNotRegisterered();
    
    // Start it and wait for registration
    helper.client.start();
    helper.waitForRegistrationAtServerSide(1);
    
    // Check client is well registered
    helper.assertClientRegisterered();
    assertThat(helper.getCurrentRegistration().getObjectLinks()).containsExactly(Link.parse("</>;rt=\"oma.lwm2m\",</1/0>,</2>,</3/0>,</2000/0>,</2000/1>".getBytes()));
    
    // Check for update 
    helper.waitForUpdateAtClientSide(IntegrationTestHelper.LIFETIME);
    helper.assertClientRegisterered();
    
    // Check deregistration
    helper.client.stop(true);
    helper.waitForDeregistrationAtServerSide(1);
    helper.assertClientNotRegisterered();
  }

  @Test
  public void deregister_cancel_multiple_pending_request() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void register_update_deregister_reregister() throws NonUniqueSecurityInfoException, InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void register_update_reregister() throws NonUniqueSecurityInfoException, InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void register_observe_deregister_observe() throws NonUniqueSecurityInfoException, InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void register_with_additional_attributes() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void register_with_invalid_request() throws InterruptedException, IOException {
    Assertions.fail("Test Not Impl yet.");
  }

}
