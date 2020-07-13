package com.example.leshan.integration.tests;

import com.example.leshan.integration.tests.helper.RedisSecureIntegrationTestHelper;

public class RedisSecurityTest extends SecurityTest {

  public RedisSecurityTest() {
    helper = new RedisSecureIntegrationTestHelper();
  }
}
