package com.example.leshan.integration.tests;

import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.leshan.server.queue.PresenceListener;
import org.eclipse.leshan.server.registration.Registration;

public class PresenceCounter implements PresenceListener {

  private AtomicInteger nbAwake = new AtomicInteger(0);
  private AtomicInteger nbSleeping = new AtomicInteger(0);

  @Override
  public void onAwake(Registration registration) {
  }

  @Override
  public void onSleeping(Registration registration) {
  }

  public void resetCounter() {
  }

  public int getNbAwake() {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

  public int getNbSleeping() {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

  public boolean accept(Registration registration) {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

}
