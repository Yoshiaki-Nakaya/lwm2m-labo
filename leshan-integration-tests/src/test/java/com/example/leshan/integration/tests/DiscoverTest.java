package com.example.leshan.integration.tests;

import static org.assertj.core.api.Assertions.assertThat;

import org.eclipse.californium.core.coap.Response;
import org.eclipse.leshan.core.Link;
import org.eclipse.leshan.core.ResponseCode;
import org.eclipse.leshan.core.request.DiscoverRequest;
import org.eclipse.leshan.core.response.DiscoverResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.leshan.integration.tests.helper.IntegrationTestHelper;

public class DiscoverTest {

  private static final Logger LOG = LoggerFactory.getLogger(DiscoverTest.class);
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
  public void can_discover_object() throws InterruptedException {
    // read ACL object
    DiscoverResponse response = helper.server.send(helper.getCurrentRegistration(), new DiscoverRequest(3));
    
    // verify result
    assertThat(response.getCode()).isEqualTo(ResponseCode.CONTENT);
    assertThat(response.getCoapResponse()).isNotNull();
    assertThat(response.getCoapResponse()).isInstanceOf(Response.class);
    
    Link[] payload = response.getObjectLinks();
    assertThat(Link.serialize(payload)).isEqualTo("</3>,</3/0>,</3/0/0>,</3/0/1>,</3/0/2>,</3/0/11>,</3/0/14>,</3/0/15>,</3/0/16>");

  }

  @Test
  public void cant_discover_non_existent_object() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void can_discover_object_instance() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void cant_discover_non_existent_instance() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void can_discover_resource() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void cant_discover_resource_of_non_existent_object() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void cant_discover_resource_of_non_existent_instance() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void cant_discover_resource_of_non_existent_instance_and_resource() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void cant_discover_resource_of_non_existent_resource() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

}
