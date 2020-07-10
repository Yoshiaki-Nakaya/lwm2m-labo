package com.example.leshan.integration.tests;

import java.util.Collection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.leshan.core.observation.Observation;
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
    if (accept(reg)) {
      lastRegistration = reg;
      registerLatch.countDown();
    }

  }

  @Override
  public void updated(RegistrationUpdate update, Registration updatedReg, Registration previousReg) {
    if (accept(updatedReg)) {
      updateLatch.countDown();
    }
  }

  @Override
  public void unregistered(Registration reg, Collection<Observation> observations, boolean expired, Registration newReg) {
    if (accept(reg)) {
      deregisterLatch.countDown();
    }
  }

  /**
   * 次の登録イベントまで待機する。
   * @param timeout
   * @param timeUnit
   * @throws InterruptedException
   * @throws TimeoutException
   */
  public void waitForRegister(long timeout, TimeUnit timeUnit) throws InterruptedException, TimeoutException {
    try {
      if (!registerLatch.await(timeout, timeUnit)) {
        throw new TimeoutException("wait for register timeout");
      }
    } finally {
      registerLatch = new CountDownLatch(1);
    }

  }

  /**
   * 次の更新イベントが来るまで待機
   * @param timeout
   * @param timeUnit
   * @throws InterruptedException
   * @throws TimeoutException
   */
  public void waitForUpdate(long timeout, TimeUnit timeUnit) throws InterruptedException, TimeoutException {
    try {
      if (!updateLatch.await(timeout, timeUnit)) {
        throw new TimeoutException("wait for update timeout");
      }
    } finally {
      updateLatch = new CountDownLatch(1);
    }

  }

  public void waitForDeregister(long timeout, TimeUnit timeUnit) throws InterruptedException, TimeoutException {
    try {
      if (!deregisterLatch.await(timeout, timeUnit)) {
        throw new TimeoutException("wait for deregister timeout");
      }
    } finally {
      deregisterLatch = new CountDownLatch(1);
    }

  }

  public boolean accept(Registration registration) {
    return true;
  }

  public Registration getLastRegistration() {
    return lastRegistration;
  }

}
