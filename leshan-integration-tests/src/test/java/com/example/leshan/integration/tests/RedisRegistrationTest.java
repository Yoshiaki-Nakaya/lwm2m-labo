package com.example.leshan.integration.tests;

import com.example.leshan.integration.tests.helper.RedisIntegrationTestHelper;

public class RedisRegistrationTest extends RegistrationTest {

  public RedisRegistrationTest() {
    helper = new RedisIntegrationTestHelper();
  }
}
