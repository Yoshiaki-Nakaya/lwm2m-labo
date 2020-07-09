package com.example.leshan.integration.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExecuteTest {

  private static final Logger LOG = LoggerFactory.getLogger(ExecuteTest.class);
  private final IntegrationTestHelper helper = new IntegrationTestHelper();

  @BeforeEach
  public void start() {
    Assertions.fail("Test Not Impl yet.");

  }

  @AfterEach
  public void stop() {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void cannot_execute_read_only_resource() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");
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
