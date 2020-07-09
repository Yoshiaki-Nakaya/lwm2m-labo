package com.example.leshan.integration.tests;

import org.eclipse.leshan.core.response.LwM2mResponse;
import org.eclipse.leshan.server.californium.LeshanServerBuilder;

public class QueueModeIntegrationTestHelper extends IntegrationTestHelper {

  public static final long LIFETIME = 3600; // Updates are manually triggered with a timer
  protected SynchronousPresenceListener presenceListener = new SynchronousPresenceListener() {
  };
  protected PresenceCounter presenceCounter = new PresenceCounter() {
  };

  public void createClient() {
  }

  public void createServer(int clientAwakeTime) {
  }

  protected LeshanServerBuilder createServerBuilder(int clientAwakeTime) {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

  public void waitToSleep(long timeInMilliseconds) {
  }

  public void ensureAwakeFor(long awaketimeInSeconds, long margeInMs) {
  }

  public void waitToGetAwake(long timeInMilliseconds) {
  }

  public void ensureClientAwake() {
  }

  public void ensureClientSleeping() {
  }

  public void ensureReceivedRequest(LwM2mResponse response) {
  }

  public void ensureTimeoutException(LwM2mResponse response) {
  }

}
