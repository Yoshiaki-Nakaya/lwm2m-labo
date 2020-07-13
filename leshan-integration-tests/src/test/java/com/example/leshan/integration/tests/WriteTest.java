package com.example.leshan.integration.tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import org.eclipse.californium.core.coap.Response;
import org.eclipse.leshan.core.ResponseCode;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.node.codec.CodecException;
import org.eclipse.leshan.core.request.ContentFormat;
import org.eclipse.leshan.core.request.ReadRequest;
import org.eclipse.leshan.core.request.WriteRequest;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.leshan.integration.tests.helper.IntegrationTestHelper;

public class WriteTest {

  private static final Logger LOG = LoggerFactory.getLogger(WriteTest.class);
  private IntegrationTestHelper helper = new IntegrationTestHelper();

  @BeforeEach
  public void init() {
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
  public void can_write_string_resource_in_text() throws InterruptedException {
    write_string_resource(ContentFormat.TEXT);
  }

  @Test
  public void can_write_string_resource_in_tlv() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void can_write_string_resource_in__old_tlv() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void can_write_string_resource_in_json() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void can_write_string_resource_in__old_json() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void can_write_boolean_resource_in_text() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void can_write_boolean_resource_in_tlv() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void can_write_boolean_resource_in_old_tlv() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void can_write_boolean_resource_in_json() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void can_write_boolean_resource_in_old_json() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void can_write_integer_resource_in_text() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void can_write_integer_resource_in_tlv() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void can_write_integer_resource_in_old_tlv() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void can_write_integer_resource_in_json() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void can_write_integer_resource_in_old_json() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void can_write_float_resource_in_text() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void can_write_float_resource_in_tlv() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void can_write_float_resource_in_old_tlv() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void can_write_float_resource_in_json() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void can_write_float_resource_in_old_json() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void can_write_time_resource_in_text() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void can_write_time_resource_in_tlv() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void can_write_time_resource_in_old_tlv() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void can_write_time_resource_in_json() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void can_write_time_resource_in_old_json() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void can_write_opaque_resource_in_opaque() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void can_write_opaque_resource_in_tlv() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void can_write_opaque_resource_in_old_tlv() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void can_write_opaque_resource_in_json() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void can_write_opaque_resource_in_text() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void can_write_opaque_resource_in_old_json() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void cannot_write_non_writable_resource() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void cannot_write_security_resource() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void can_write_object_instance_in_tlv() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void can_write_object_instance_in_old_tlv() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void can_write_object_instance_in_json() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void can_write_object_instance_in_old_json() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");

  }

  public void can_write_object_instance(ContentFormat format) throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void can_write_replacing_object_instance() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void cannot_write_replacing_incomplete_object_instance() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void can_write_updating_object_instance() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void can_write_multi_instance_objlnk_resource_in_tlv() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void can_write_single_instance_objlnk_resource_in_tlv() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void can_write_single_instance_objlnk_resource_in_text() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void send_writerequest_synchronously_with_bad_payload_raises_codeexception() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void send_writerequest_asynchronously_with_bad_payload_raises_codeexception() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");

  }

  private void write_string_resource(ContentFormat format) throws InterruptedException {
    // write resource
    String expectedvalue = "stringvakue";
    WriteResponse response = helper.server.send(helper.getCurrentRegistration(),
        new WriteRequest(format, IntegrationTestHelper.TEST_OBJECT_ID, 0, IntegrationTestHelper.STRING_RESOURCE_ID, expectedvalue));
    
    // verify result
    assertThat(response.getCode()).isEqualTo(ResponseCode.CHANGED);
    assertThat(response.getCoapResponse()).isNotNull();
    assertThat(response.getCoapResponse()).isInstanceOf(Response.class);

    // read resource to check the value changed
    ReadResponse readResponse = helper.server.send(
        helper.getCurrentRegistration(),
        new ReadRequest(IntegrationTestHelper.TEST_OBJECT_ID, 0, IntegrationTestHelper.STRING_RESOURCE_ID));
    LwM2mResource resource = (LwM2mResource) readResponse.getContent();
    assertThat(resource.getValue()).isEqualTo(expectedvalue);
  }

  private void write_boolean_resource(ContentFormat format) throws InterruptedException {
    throw new UnsupportedOperationException("Not Impl yet.");
  }

  private void write_integer_resource(ContentFormat format) throws InterruptedException {
    throw new UnsupportedOperationException("Not Impl yet.");
  }

  private void write_float_resource(ContentFormat format) throws InterruptedException {
    throw new UnsupportedOperationException("Not Impl yet.");
  }

  private void write_time_resource(ContentFormat format) throws InterruptedException {
    throw new UnsupportedOperationException("Not Impl yet.");
  }

  private void write_opaque_resource(ContentFormat format) throws InterruptedException {
    throw new UnsupportedOperationException("Not Impl yet.");
  }
}
