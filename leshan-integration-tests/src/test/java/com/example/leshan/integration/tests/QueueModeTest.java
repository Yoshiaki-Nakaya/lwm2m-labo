package com.example.leshan.integration.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueueModeTest {

  private static final Logger LOG = LoggerFactory.getLogger(QueueModeTest.class);
  protected QueueModeIntegrationTestHelper queueModeHelper = new QueueModeIntegrationTestHelper();
  private final long awaketime = 1;

  @BeforeEach
  public void start() {
    Assertions.fail("Test Not Impl yet.");

  }

  @AfterEach
  public void stop() {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void awake_sleeping_awake_sleeping() {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void one_awake_notification() {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void sleeping_if_timeout() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void correct_sending_when_awake() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

}
