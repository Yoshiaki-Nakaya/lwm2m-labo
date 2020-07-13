package com.example.leshan.integration.tests;

import static org.assertj.core.api.Assertions.assertThat;

import org.eclipse.californium.core.coap.Response;
import org.eclipse.leshan.core.ResponseCode;
import org.eclipse.leshan.core.node.LwM2mObjectInstance;
import org.eclipse.leshan.core.node.LwM2mSingleResource;
import org.eclipse.leshan.core.request.CreateRequest;
import org.eclipse.leshan.core.request.DeleteRequest;
import org.eclipse.leshan.core.response.DeleteResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.leshan.integration.tests.helper.IntegrationTestHelper;

public class DeleteTest {

  private static final Logger LOG = LoggerFactory.getLogger(DeleteTest.class);
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
  public void delete_create_object_instance() throws Exception {
    // Create ACL instance
    helper.server.send(
        helper.getCurrentRegistration(),
        new CreateRequest(2, new LwM2mObjectInstance(0, LwM2mSingleResource.newIntegerResource(3, 33))));
    
    // try to delete this instance
    DeleteResponse response = helper.server.send(helper.getCurrentRegistration(), new DeleteRequest(2, 0));
    
    // verify result
    assertThat(response.getCode()).isEqualTo(ResponseCode.DELETED);
    assertThat(response.getCoapResponse()).isNotNull();
    assertThat(response.getCoapResponse()).isInstanceOf(Response.class);

  }

  @Test
  public void cannot_delete_resource() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void cannot_delete_unknown_object_instance() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void cannot_delete_device_object_instance() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void cannot_delete_security_object_instance() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");

  }

}
