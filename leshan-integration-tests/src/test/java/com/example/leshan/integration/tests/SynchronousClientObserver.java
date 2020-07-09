package com.example.leshan.integration.tests;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.leshan.client.observer.LwM2mClientObserverAdapter;
import org.eclipse.leshan.client.servers.ServerIdentity;
import org.eclipse.leshan.core.ResponseCode;
import org.eclipse.leshan.core.request.BootstrapRequest;
import org.eclipse.leshan.core.request.DeregisterRequest;
import org.eclipse.leshan.core.request.RegisterRequest;
import org.eclipse.leshan.core.request.UpdateRequest;
import org.eclipse.leshan.core.request.exception.TimeoutException;

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
  }

  @Override
  public void onBootstrapFailure(ServerIdentity bsserver, BootstrapRequest request, ResponseCode responseCode,
      String errorMessage, Exception cause) {
    // TODO Auto-generated method stub
    super.onBootstrapFailure(bsserver, request, responseCode, errorMessage, cause);
  }

  @Override
  public void onBootstrapTimeout(ServerIdentity bsserver, BootstrapRequest request) {
  }

  @Override
  public void onRegistrationSuccess(ServerIdentity server, RegisterRequest request, String registrationID) {
  }

  @Override
  public void onRegistrationFailure(ServerIdentity server, RegisterRequest request, ResponseCode responseCode,
      String errorMessage, Exception cause) {
    // TODO Auto-generated method stub
    super.onRegistrationFailure(server, request, responseCode, errorMessage, cause);
  }

  @Override
  public void onRegistrationTimeout(ServerIdentity server, RegisterRequest request) {
  }

  @Override
  public void onUpdateSuccess(ServerIdentity server, UpdateRequest request) {
  }

  @Override
  public void onUpdateFailure(ServerIdentity server, UpdateRequest request, ResponseCode responseCode,
      String errorMessage, Exception cause) {
    // TODO Auto-generated method stub
    super.onUpdateFailure(server, request, responseCode, errorMessage, cause);
  }

  @Override
  public void onUpdateTimeout(ServerIdentity server, UpdateRequest request) {
  }

  @Override
  public void onDeregistrationSuccess(ServerIdentity server, DeregisterRequest request) {
  }

  @Override
  public void onDeregistrationFailure(ServerIdentity server, DeregisterRequest request, ResponseCode responseCode,
      String errorMessage, Exception cause) {
    // TODO Auto-generated method stub
    super.onDeregistrationFailure(server, request, responseCode, errorMessage, cause);
  }

  @Override
  public void onDeregistrationTimeout(ServerIdentity server, DeregisterRequest request) {
  }

  public boolean waitForRegistration(long timeout, TimeUnit timeUnit) throws InterruptedException, TimeoutException {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

  public boolean waitForUpdate(long timeout, TimeUnit timeUnit) throws InterruptedException, TimeoutException {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

  public boolean waitForDeregistration(long timeout, TimeUnit timeUnit) throws InterruptedException, TimeoutException {
    throw new UnsupportedOperationException("Not Impl yet.");

    
  }

  public boolean waitForBootstrap(long timeout, TimeUnit timeUnit) throws InterruptedException, TimeoutException {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

}
