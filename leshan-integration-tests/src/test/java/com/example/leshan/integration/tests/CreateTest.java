package com.example.leshan.integration.tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.eclipse.californium.core.coap.Response;
import org.eclipse.leshan.core.ResponseCode;
import org.eclipse.leshan.core.node.LwM2mObject;
import org.eclipse.leshan.core.node.LwM2mSingleResource;
import org.eclipse.leshan.core.request.ContentFormat;
import org.eclipse.leshan.core.request.CreateRequest;
import org.eclipse.leshan.core.request.ReadRequest;
import org.eclipse.leshan.core.request.exception.InvalidRequestException;
import org.eclipse.leshan.core.response.CreateResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.leshan.integration.tests.helper.IntegrationTestHelper;

public class CreateTest {

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
  public void can_create_instance_without_instance_id_tlv() throws InterruptedException {
    can_create_instance_without_instance_id(ContentFormat.TLV);
  }

  @Test
  public void can_create_instance_without_instance_id_json() throws InterruptedException {
    
    assertThrows(InvalidRequestException.class, () -> {
      can_create_instance_without_instance_id(ContentFormat.JSON);
    });
  }

  public void can_create_instance_without_instance_id(ContentFormat format) throws InterruptedException {
    // create ACL instance
    CreateResponse response = helper.server.send(helper.getCurrentRegistration(),
        new CreateRequest(format, 2, LwM2mSingleResource.newIntegerResource(3, 33)));
    
    // verify result
    assertThat(response.getCode()).isEqualTo(ResponseCode.CREATED);
    assertThat(response.getLocation()).isEqualTo("2/0");
    assertThat(response.getCoapResponse()).isNotNull();
    assertThat(response.getCoapResponse()).isInstanceOf(Response.class);
    
    // create a second ACL instance
    response = helper.server.send(
        helper.getCurrentRegistration(),
        new CreateRequest(format, 2, LwM2mSingleResource.newIntegerResource(3, 34)));
        
    // verify result
    assertThat(response.getCode()).isEqualTo(ResponseCode.CREATED);
    assertThat(response.getLocation()).isEqualTo("2/1");
    assertThat(response.getCoapResponse()).isNotNull();
    
    // read object 2
    ReadResponse readResponse = helper.server.send(helper.getCurrentRegistration(), new ReadRequest(2));
    assertThat(readResponse.getCode()).isEqualTo(ResponseCode.CONTENT);
    LwM2mObject object = (LwM2mObject) readResponse.getContent();
    assertThat(object.getInstance(0).getResource(3).getValue()).isEqualTo(33l);
    assertThat(object.getInstance(1).getResource(3).getValue()).isEqualTo(34l);
    
  }

  @Test
  public void can_create_instance_with_id_tlv() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void can_create_instance_with_id_json() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  public void can_create_instance_with_id(ContentFormat format) throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void can_create_2_instances_of_object_tlv() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void can_create_2_instances_of_object_json() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  public void can_create_2_instances_of_object(ContentFormat format) throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void cannot_create_instance_of_absent_object() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void cannot_create_instance_without_all_required_resources() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void cannot_create_mandatory_single_object() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void cannot_create_instance_of_security_object() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

}
