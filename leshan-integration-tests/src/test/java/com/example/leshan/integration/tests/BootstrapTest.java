package com.example.leshan.integration.tests;

import org.eclipse.leshan.server.security.NonUniqueSecurityInfoException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BootstrapTest {

  private static final Logger LOG = LoggerFactory.getLogger(DeleteTest.class);
  private final BootstrapIntegrationTestHelper helper = new BootstrapIntegrationTestHelper();

  @BeforeEach
  public void start() {

  }

  @AfterEach
  public void stop() {

  }

  @Test
  public void bootstrap() {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void bootstrapWithAdditionalAttributes() {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void bootstrapWithDiscoverOnRoot() {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void bootstrapWithDiscoverOnDevice() {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void bootstrapDeleteSecurity() {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void bootstrapDeleteAll() {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void bootstrapWithAcl() {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void bootstrapSecureWithPSK() {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void bootstrapSecureWithBadPSKKey() {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void bootstrapSecureWithRPK() {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void bootstrapToPSKServer() throws NonUniqueSecurityInfoException {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void bootstrapToRPKServer() throws NonUniqueSecurityInfoException {
    Assertions.fail("Test Not Impl yet.");

  }

}
