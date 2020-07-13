package com.example.leshan.integration.tests.parts;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.eclipse.leshan.core.request.exception.TimeoutException;
import org.eclipse.leshan.server.queue.PresenceListener;
import org.eclipse.leshan.server.registration.Registration;

public class SynchronousPresenceListener implements PresenceListener {
  private CountDownLatch awakeLatch = new CountDownLatch(1);
  private CountDownLatch sleepingLatch = new CountDownLatch(1);

  @Override
  public void onAwake(Registration registration) {
  }

  @Override
  public void onSleeping(Registration registration) {
  }

  public void waitForAwake(long timeout, TimeUnit timeUnit) throws InterruptedException, TimeoutException {
  }

  public void waitForSleep(long timeout, TimeUnit timeUnit) throws InterruptedException, TimeoutException {
  }

  public boolean accept(Registration registration) {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

}
