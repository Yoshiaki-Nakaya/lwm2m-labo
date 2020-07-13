package com.example.leshan.integration.tests;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.eclipse.californium.core.network.config.NetworkConfig;
import org.eclipse.leshan.core.Link;
import org.eclipse.leshan.core.request.BindingMode;
import org.eclipse.leshan.core.request.ReadRequest;
import org.eclipse.leshan.core.request.RegisterRequest;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.server.californium.LeshanServerBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.leshan.integration.tests.helper.IntegrationTestHelper;
import com.example.leshan.integration.tests.parts.LockStepLwM2mClient;

public class FailingTest {

  private static final Logger LOG = LoggerFactory.getLogger(FailingTest.class);
  private final IntegrationTestHelper helper = new IntegrationTestHelper() {

    protected org.eclipse.leshan.server.californium.LeshanServerBuilder createServerBuilder() {
      NetworkConfig coapConfig = LeshanServerBuilder.createDefaultNetworkConfig();
      
      // configure retransmission, with this configuration a request without ACK should timeout in ~200*5ms
      coapConfig.setInt(NetworkConfig.Keys.ACK_TIMEOUT, 200)
        .setFloat(NetworkConfig.Keys.ACK_RANDOM_FACTOR, 1f)
        .setFloat(NetworkConfig.Keys.ACK_TIMEOUT_SCALE, 1f)
        .setInt(NetworkConfig.Keys.MAX_RETRANSMIT, 4);
      
      LeshanServerBuilder builder = super.createServerBuilder();
      builder.setCoapConfig(coapConfig);
      
      return builder;
    };
  };

  @BeforeEach
  public void start() {
    helper.initialize();
    helper.createServer();
    helper.server.start();

  }

  @AfterEach
  public void stop() {
    helper.server.destroy();
    helper.dispose();
  }

  @Test
  public void sync_send_without_acknowleged() throws Exception {
    // Register Client
    LockStepLwM2mClient client = new LockStepLwM2mClient(helper.server.getUnsecuredAddress());
    client.sendLwM2mRequest(new RegisterRequest(helper.getCurrentEndpoint(), 60l, null, BindingMode.U, null,
        Link.parse("</1></2></3>".getBytes()), null));
    client.expectResponse().go();
    helper.waitForRegistrationAtClientSide(1);

    // Send read
    Future<ReadResponse> future = Executors.newSingleThreadExecutor().submit(new Callable<ReadResponse>() {
      @Override
      public ReadResponse call() throws Exception {
        // send a request with 3 seconds timeout
        return helper.server.send(helper.getCurrentRegistration(), new ReadRequest(3), 3000);
      }
    });

    // Request should timedout in ~ls we don't send ACK
    ReadResponse response = future.get(1500, TimeUnit.MILLISECONDS);
    assertThat(response).as("we should timeout").isNull();

  }

  @Test
  public void sync_send_with_acknowleged_request_without_response() throws Exception {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void async_send_without_acknowleged() throws Exception {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void async_send_with_acknowleged_request_without_response() throws Exception {
    Assertions.fail("Test Not Impl yet.");
  }

}
