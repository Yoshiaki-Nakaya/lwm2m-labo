package com.example.leshan.integration.tests;

import com.example.leshan.integration.tests.helper.RedisIntegrationTestHelper;

public class RedisObserveTest extends ObserveTest {

  public RedisObserveTest() {
    helper = new RedisIntegrationTestHelper();
  }
}
