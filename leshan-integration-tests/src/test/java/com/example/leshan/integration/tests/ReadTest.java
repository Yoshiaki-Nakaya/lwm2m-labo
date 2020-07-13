package com.example.leshan.integration.tests;


import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.instanceOf;

import org.eclipse.californium.core.coap.Response;
import org.eclipse.leshan.core.ResponseCode;
import org.eclipse.leshan.core.node.LwM2mObject;
import org.eclipse.leshan.core.node.LwM2mObjectInstance;
import org.eclipse.leshan.core.request.ReadRequest;
import org.eclipse.leshan.core.response.ReadResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.leshan.integration.tests.helper.IntegrationTestHelper;

public class ReadTest {

  private static final Logger LOG = LoggerFactory.getLogger(ReadTest.class);
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
  public void can_read_empty_object() throws InterruptedException {
    // read ACL object
    ReadResponse response = helper.server.send(helper.getCurrentRegistration(), new ReadRequest(2));

    // verify result
    assertThat(response.getCode()).isEqualTo(ResponseCode.CONTENT);
    assertThat(response.getCoapResponse()).isNotNull();
    assertThat(response.getCoapResponse()).isInstanceOf(Response.class);
    
    LwM2mObject object = (LwM2mObject) response.getContent();
    assertThat(object.getId()).isEqualTo(2);
    assertThat(object.getInstances()).isEmpty();
  }

  @Test
  public void can_read_object() throws InterruptedException {
    // read device object
    ReadResponse response = helper.server.send(helper.getCurrentRegistration(), new ReadRequest(3));
    
    // verify result
    assertThat(response.getCode()).isEqualTo(ResponseCode.CONTENT);
    assertThat(response.getCoapResponse()).isNotNull();
    assertThat(response.getCoapResponse()).isInstanceOf(Response.class);
    
    LwM2mObject object = (LwM2mObject) response.getContent();
    assertThat(object.getId()).isEqualTo(3);
    
    LwM2mObjectInstance instance = object.getInstance(0);
    assertThat(instance.getId()).isEqualTo(0);

  }

  @Test
  public void can_read_object_instance() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void can_read_resource() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void can_read_empty_opaque_resource() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void cannot_read_non_readable_resource() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void cannot_read_non_existent_object() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void cannot_read_non_existent_instance() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void cannot_read_non_existent_resource() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void cannot_read_security_resource() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

}
