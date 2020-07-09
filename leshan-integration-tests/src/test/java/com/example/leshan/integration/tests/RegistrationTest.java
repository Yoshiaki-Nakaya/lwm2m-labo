package com.example.leshan.integration.tests;

import java.io.IOException;

import org.eclipse.leshan.server.security.NonUniqueSecurityInfoException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationTest {

  protected IntegrationTestHelper helper = new IntegrationTestHelper();

  @BeforeEach
  public void start() {
    Assertions.fail("Test Not Impl yet.");
  }

  @AfterEach
  public void stop() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void register_update_deregister() {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void deregister_cancel_multiple_pending_request() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void register_update_deregister_reregister() throws NonUniqueSecurityInfoException, InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void register_update_reregister() throws NonUniqueSecurityInfoException, InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void register_observe_deregister_observe() throws NonUniqueSecurityInfoException, InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void register_with_additional_attributes() throws InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void register_with_invalid_request() throws InterruptedException, IOException {
    Assertions.fail("Test Not Impl yet.");
  }

}
