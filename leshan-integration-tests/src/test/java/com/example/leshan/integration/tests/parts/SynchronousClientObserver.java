package com.example.leshan.integration.tests.parts;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.leshan.client.observer.LwM2mClientObserverAdapter;
import org.eclipse.leshan.client.servers.ServerIdentity;
import org.eclipse.leshan.core.ResponseCode;
import org.eclipse.leshan.core.request.BootstrapRequest;
import org.eclipse.leshan.core.request.DeregisterRequest;
import org.eclipse.leshan.core.request.RegisterRequest;
import org.eclipse.leshan.core.request.UpdateRequest;

public class SynchronousClientObserver extends LwM2mClientObserverAdapter {
  private CountDownLatch registerLatch = new CountDownLatch(1);
  private AtomicBoolean registerSucceed = new AtomicBoolean(false);
  private AtomicBoolean registerFailed = new AtomicBoolean(false);;
  private CountDownLatch updateLatch = new CountDownLatch(1);
  private AtomicBoolean updateSucceed = new AtomicBoolean(false);
  private AtomicBoolean updateFailed = new AtomicBoolean(false);
  private CountDownLatch deregisterLatch = new CountDownLatch(1);
  private AtomicBoolean deregisterSucceed = new AtomicBoolean(false);
  private AtomicBoolean deregisterFailed = new AtomicBoolean(false);
  private CountDownLatch bootstrapLatch = new CountDownLatch(1);
  private AtomicBoolean bootstrapSucceed = new AtomicBoolean(false);
  private AtomicBoolean bootstrapFailed = new AtomicBoolean(false);

  @Override
  public void onBootstrapSuccess(ServerIdentity bsserver, BootstrapRequest request) {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

  @Override
  public void onBootstrapFailure(ServerIdentity bsserver, BootstrapRequest request, ResponseCode responseCode,
      String errorMessage, Exception cause) {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

  @Override
  public void onBootstrapTimeout(ServerIdentity bsserver, BootstrapRequest request) {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

  @Override
  public void onRegistrationSuccess(ServerIdentity server, RegisterRequest request, String registrationID) {
    registerSucceed.set(true);
    registerLatch.countDown();
  }

  @Override
  public void onRegistrationFailure(ServerIdentity server, RegisterRequest request, ResponseCode responseCode,
      String errorMessage, Exception cause) {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

  @Override
  public void onRegistrationTimeout(ServerIdentity server, RegisterRequest request) {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

  @Override
  public void onUpdateSuccess(ServerIdentity server, UpdateRequest request) {
    updateSucceed.set(true);
    updateLatch.countDown();
  }

  @Override
  public void onUpdateFailure(ServerIdentity server, UpdateRequest request, ResponseCode responseCode,
      String errorMessage, Exception cause) {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

  @Override
  public void onUpdateTimeout(ServerIdentity server, UpdateRequest request) {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

  @Override
  public void onDeregistrationSuccess(ServerIdentity server, DeregisterRequest request) {
    deregisterSucceed.set(true);
    deregisterLatch.countDown();
  }

  @Override
  public void onDeregistrationFailure(ServerIdentity server, DeregisterRequest request, ResponseCode responseCode,
      String errorMessage, Exception cause) {
    deregisterFailed.set(true);
    deregisterLatch.countDown();
  }

  @Override
  public void onDeregistrationTimeout(ServerIdentity server, DeregisterRequest request) {
    throw new UnsupportedOperationException("Not Impl yet.");
  }

  public boolean waitForRegistration(long timeout, TimeUnit timeUnit) throws InterruptedException, TimeoutException {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

  public boolean waitForUpdate(long timeout, TimeUnit timeUnit) throws InterruptedException, TimeoutException {
    try {
      if (updateLatch.await(timeout, timeUnit)) {
        if (updateSucceed.get()) {
          return true;
        }
        if (updateFailed.get()) {
          return false;
        }
      }
      throw new TimeoutException("client registration update latch timeout");
    } finally {
      updateLatch = new CountDownLatch(1);
    }

  }

  public boolean waitForDeregistration(long timeout, TimeUnit timeUnit) throws InterruptedException, TimeoutException {
    throw new UnsupportedOperationException("Not Impl yet.");

    
  }

  public boolean waitForBootstrap(long timeout, TimeUnit timeUnit) throws InterruptedException, TimeoutException {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

}
