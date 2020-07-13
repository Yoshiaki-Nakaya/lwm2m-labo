package com.example.leshan.integration.tests;

import static org.assertj.core.api.Assertions.assertThat;

import org.eclipse.californium.core.coap.Response;
import org.eclipse.leshan.core.ResponseCode;
import org.eclipse.leshan.core.request.ExecuteRequest;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.leshan.integration.tests.helper.IntegrationTestHelper;

public class ExecuteTest {

  private static final Logger LOG = LoggerFactory.getLogger(ExecuteTest.class);
  private final IntegrationTestHelper helper = new IntegrationTestHelper();

  @BeforeEach
  public void start() {
    helper.initialize();
    helper.createServer();
    helper.server.start();
    helper.createClient();
    helper.client.start();
    helper.waitForRegistrationAtServerSide(1);

  }

  @AfterEach
  public void stop() {
    helper.client.destroy(false);
    helper.server.destroy();
    helper.dispose();
  }

  @Test
  public void cannot_execute_read_only_resource() throws InterruptedException {
    // execute manufacturer resource on device
    ExecuteResponse response = helper.server.send(helper.getCurrentRegistration(), new ExecuteRequest(3, 0, 0));
    
    // verify result
    assertThat(response.getCode()).isEqualTo(ResponseCode.METHOD_NOT_ALLOWED);
    assertThat(response.getCoapResponse()).isNotNull();
    assertThat(response.getCoapResponse()).isInstanceOf(Response.class);
    
  }

  @Test
  public void cannot_execute_read_write_resource() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void cannot_execute_nonexisting_resource_on_existing_object() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void cannot_execute_nonexisting_resource_on_non_existing_object() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void cannot_execute_security_object() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void can_execute_resource() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void can_execute_resource_with_parameters() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

}
