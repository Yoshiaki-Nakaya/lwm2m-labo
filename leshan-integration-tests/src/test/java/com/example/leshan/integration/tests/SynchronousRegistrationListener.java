package com.example.leshan.integration.tests;

import java.util.Collection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.eclipse.leshan.core.observation.Observation;
import org.eclipse.leshan.core.request.exception.TimeoutException;
import org.eclipse.leshan.server.registration.Registration;
import org.eclipse.leshan.server.registration.RegistrationListener;
import org.eclipse.leshan.server.registration.RegistrationUpdate;

public class SynchronousRegistrationListener implements RegistrationListener {
  private CountDownLatch registerLatch = new CountDownLatch(1);
  private CountDownLatch updateLatch = new CountDownLatch(1);
  private CountDownLatch deregisterLatch = new CountDownLatch(1);
  private Registration lastRegistration;
  
  @Override
  public void registered(Registration reg, Registration previousReg, Collection<Observation> previousObsersations) {
  }

  @Override
  public void updated(RegistrationUpdate update, Registration updatedReg, Registration previousReg) {
  }

  @Override
  public void unregistered(Registration reg, Collection<Observation> observations, boolean expired, Registration newReg) {
  }

  public void waitForRegister(long timeout, TimeUnit timeUnit) throws InterruptedException, TimeoutException {
  }

  public void waitForUpdate(long timeout, TimeUnit timeUnit) throws InterruptedException, TimeoutException {
  }

  public void waitForDeregister(long timeout, TimeUnit timeUnit) throws InterruptedException, TimeoutException {
  }

  public boolean accept(Registration registration) {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

  public Registration getLastRegistration() {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

}
