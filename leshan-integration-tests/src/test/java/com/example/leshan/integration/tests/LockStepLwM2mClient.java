package com.example.leshan.integration.tests;

import java.net.InetSocketAddress;
import java.util.Random;

import org.eclipse.californium.core.test.lockstep.LockstepEndpoint;
import org.eclipse.leshan.core.request.UplinkRequest;
import org.eclipse.leshan.core.response.LwM2mResponse;

public class LockStepLwM2mClient extends LockstepEndpoint {
  private static final Random r = new Random();
  private InetSocketAddress destination;

  public LockStepLwM2mClient(final InetSocketAddress destination) {
  }

  public void sendLwM2mRequest(UplinkRequest<? extends LwM2mResponse> lwm2mReq) {
  }

}
