package com.example.leshan.integration.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateTest {

  private static final Logger LOG = LoggerFactory.getLogger(DeleteTest.class);
  private final IntegrationTestHelper helper = new IntegrationTestHelper();

  @BeforeEach
  public void start() {

  }

  @AfterEach
  public void stop() {

  }

  @Test
  public void sync_send_without_acknowleged() throws Exception {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void sync_send_with_acknowleged_request_without_response() throws Exception {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void async_send_without_acknowleged() throws Exception {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void async_send_with_acknowleged_request_without_response() throws Exception {
    Assertions.fail("Test Not Impl yet.");

  }

}