package com.example.leshan.integration.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteTest {

  private static final Logger LOG = LoggerFactory.getLogger(DeleteTest.class);
  private final IntegrationTestHelper helper = new IntegrationTestHelper();

  @BeforeEach
  public void start() {

  }

  @AfterEach
  public void stop() {

  }

  @Test
  public void delete_create_object_instance() throws Exception {
    LOG.info("test");
    Assertions.fail("Test Not Impl yet.");

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
