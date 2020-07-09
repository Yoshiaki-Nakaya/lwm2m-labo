package com.example.leshan.integration.tests;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.leshan.core.ResponseCode;
import org.eclipse.leshan.core.response.ErrorCallback;
import org.eclipse.leshan.core.response.LwM2mResponse;
import org.eclipse.leshan.core.response.ResponseCallback;

public class Callback<T extends LwM2mResponse> implements ResponseCallback<T>, ErrorCallback {

  private final CountDownLatch latch;
  private final AtomicBoolean called;
  private T response;
  private Exception exception;

  public Callback() {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

  @Override
  public void onResponse(T response) {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

  @Override
  public void onError(Exception e) {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

  public AtomicBoolean isCalled() {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

  public boolean waitForResponse(long timeout) throws InterruptedException {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

  public ResponseCode getResponseCode() {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

  public void reset() {
  }

  public T getResponse() {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

  public Exception getException() {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

}
