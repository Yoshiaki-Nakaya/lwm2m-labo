package com.example.leshan.integration.tests;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.leshan.client.californium.LeshanClient;
import org.eclipse.leshan.client.californium.LeshanClientBuilder;
import org.eclipse.leshan.client.object.Device;
import org.eclipse.leshan.client.object.Security;
import org.eclipse.leshan.client.resource.DummyInstanceEnabler;
import org.eclipse.leshan.client.resource.LwM2mObjectEnabler;
import org.eclipse.leshan.client.resource.ObjectsInitializer;
import org.eclipse.leshan.client.resource.SimpleInstanceEnabler;
import org.eclipse.leshan.client.servers.ServerIdentity;
import org.eclipse.leshan.core.LwM2mId;
import org.eclipse.leshan.core.model.ObjectLoader;
import org.eclipse.leshan.core.model.ObjectModel;
import org.eclipse.leshan.core.model.ResourceModel;
import org.eclipse.leshan.core.model.ResourceModel.Operations;
import org.eclipse.leshan.core.model.ResourceModel.Type;
import org.eclipse.leshan.core.model.StaticModel;
import org.eclipse.leshan.core.node.codec.DefaultLwM2mNodeDecoder;
import org.eclipse.leshan.core.node.codec.DefaultLwM2mNodeEncoder;
import org.eclipse.leshan.core.request.BindingMode;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.server.californium.LeshanServer;
import org.eclipse.leshan.server.californium.LeshanServerBuilder;
import org.eclipse.leshan.server.model.StaticModelProvider;
import org.eclipse.leshan.server.registration.Registration;
import org.eclipse.leshan.server.registration.RegistrationServiceImpl;
import org.eclipse.leshan.server.security.InMemorySecurityStore;

public class IntegrationTestHelper {
  public static final Random r = new Random();

  static final String MODEL_NUMBER = "IT-TEST-123";
  public static final long LIFETIME = 2;

  public static final int TEST_OBJECT_ID = 2000;
  public static final int STRING_RESOURCE_ID = 0;
  public static final int BOOLEAN_RESOURCE_ID = 1;
  public static final int INTEGER_RESOURCE_ID = 2;
  public static final int FLOAT_RESOURCE_ID = 3;
  public static final int TIME_RESOURCE_ID = 4;
  public static final int OPAQUE_RESOURCE_ID = 5;
  public static final int OBJLNK_MULTI_INSTANCE_RESOURCE_ID = 6;
  public static final int OBJLNK_SINGLE_INSTANCE_RESOURCE_ID = 7;
  public static final int INTEGER_MANDATORY_RESOURCE_ID = 8;
  public static final int STRING_MANDATORY_RESOURCE_ID = 9;

  LeshanServer server;
  LeshanClient client;
  AtomicReference<String> currentEndpointIdentifier = new AtomicReference<String>();

  private SynchronousClientObserver clientObserver = new SynchronousClientObserver();
  private SynchronousRegistrationListener registrationListener = new SynchronousRegistrationListener() {

    @Override
    public boolean accept(Registration registration) {
      return (registration != null && registration.getEndpoint().equals(currentEndpointIdentifier.get()));
    };

  };

  protected List<ObjectModel> createObjectModels() {
    // load default object from the spec
    List<ObjectModel> objectModel = ObjectLoader.loadDefault();
    // define custom model for testing purpose
    ResourceModel stringField = new ResourceModel(STRING_RESOURCE_ID, "stringres", Operations.RW, false, false, Type.STRING, null, null, null);
    ResourceModel booleanField = new ResourceModel(BOOLEAN_RESOURCE_ID, "booleanres", Operations.RW, false, false, Type.BOOLEAN, null, null, null);
    ResourceModel integerField= new ResourceModel(INTEGER_RESOURCE_ID, "integerres", Operations.RW, false, false, Type.INTEGER, null, null, null);
    ResourceModel floatField = new ResourceModel(FLOAT_RESOURCE_ID, "floatres", Operations.RW, false, false, Type.FLOAT, null, null, null);
    ResourceModel timeField = new ResourceModel(TIME_RESOURCE_ID, "timeres", Operations.RW, false, false, Type.TIME, null, null, null);
    ResourceModel opaqueField = new ResourceModel(OPAQUE_RESOURCE_ID, "opaque", Operations.RW, false, false, Type.OPAQUE, null, null, null);
    ResourceModel objlinkField = new ResourceModel(OBJLNK_MULTI_INSTANCE_RESOURCE_ID, "objlink", Operations.RW, false, false, Type.OBJLNK, null, null, null);
    ResourceModel objlinkStringField = new ResourceModel(OBJLNK_SINGLE_INSTANCE_RESOURCE_ID, "objlink", Operations.RW, true, false, Type.OBJLNK, null, null, null);
    ResourceModel integerMandatoryField = new ResourceModel(INTEGER_MANDATORY_RESOURCE_ID, "intermandatory", Operations.RW, false, true, Type.INTEGER, null, null, null);
    ResourceModel stringMandatoryField = new ResourceModel(STRING_MANDATORY_RESOURCE_ID, "stringmandatory", Operations.RW, false, true, Type.STRING, null, null, null);
    
    objectModel.add(new ObjectModel(TEST_OBJECT_ID, "testobject", null, ObjectModel.DEFAULT_VERSION, true, false,
        stringField, booleanField, integerField, floatField, timeField, opaqueField, objlinkField, objlinkStringField, integerMandatoryField, stringMandatoryField));
    
    return objectModel;

  }

  public void initialize() {
    currentEndpointIdentifier.set("leshan_integration_test_" + r.nextInt());
  }

  public String getCurrentEndpoint() {
    return currentEndpointIdentifier.get();
  }

  public void createClient() {
    createClient(null);
  }

  public static class TestDevice extends Device {

    public TestDevice() {
      super();
    }

    public TestDevice(String manufacturer, String modelNumber, String serialNumber, String supportedBinding) {
      super(manufacturer, modelNumber, serialNumber, supportedBinding);
    }

    @Override
    public ExecuteResponse execute(ServerIdentity identity, int resourceid, String params) {
      if (resourceid == 4) {
        return ExecuteResponse.success();
      } else {
        return super.execute(identity, resourceid, params);
      }
    }
  }

  public void createClient(Map<String, String> additionalAttributes) {
    // Create Object Enabler
    ObjectsInitializer initializer = new ObjectsInitializer(new StaticModel(createObjectModels()));
    initializer.setInstancesForObject(LwM2mId.SECURITY,
        Security.noSec(
            "coap://" + server.getUnsecuredAddress().getHostString() + ":" + server.getUnsecuredAddress().getPort(),
            12345));
    initializer.setInstancesForObject(LwM2mId.SERVER,
        new org.eclipse.leshan.client.object.Server(12345, LIFETIME, BindingMode.U, false));
    initializer.setInstancesForObject(LwM2mId.DEVICE, new TestDevice("Eclipse Leshan", MODEL_NUMBER, "12345", "U"));
    initializer.setClassForObject(LwM2mId.ACCESS_CONTROL, DummyInstanceEnabler.class);
    initializer.setInstancesForObject(TEST_OBJECT_ID, new DummyInstanceEnabler(0),
        new SimpleInstanceEnabler(1, OPAQUE_RESOURCE_ID, new byte[0]));
    List<LwM2mObjectEnabler> objects = initializer.createAll();

    // Build Client
    LeshanClientBuilder builder = new LeshanClientBuilder(currentEndpointIdentifier.get());
    builder.setDecoder(new DefaultLwM2mNodeDecoder(true));
    builder.setAdditionalAttributes(additionalAttributes);
    builder.setObjects(objects);
    client = builder.build();
    setupClientMonitoring();

  }

  public void createServer() {
    server = createServerBuilder().build();
    // monitor client registration
    setupServerMonitoring();
  }

  protected LeshanServerBuilder createServerBuilder() {
    LeshanServerBuilder builder = new LeshanServerBuilder();
    builder.setEncoder(new DefaultLwM2mNodeEncoder(true));
    builder.setObjectModelProvider(new StaticModelProvider(createObjectModels()));
    builder.setLocalAddress(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0));
    builder.setLocalSecureAddress(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0));
    builder.setSecurityStore(new InMemorySecurityStore());

    return builder;
  }

  protected void setupServerMonitoring() {
    server.getRegistrationService().addListener(registrationListener);
  }

  protected void setupClientMonitoring() {
    client.addObserver(clientObserver);
  }

  public void waitForRegistrationAtClientSide(long timeInSeconds) {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

  public void waitForRegistrationAtServerSide(long timeInSeconds) {
    try {
      registrationListener.waitForRegister(timeInSeconds, TimeUnit.SECONDS);
    } catch (InterruptedException | TimeoutException e) {
      throw new RuntimeException(e);
    }

  }

  public void ensureNoRegistration(long timeInSeconds) {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

  public void waitForUpdateAtClientSide(long timeInSeconds) {
    try {
      assertThat(clientObserver.waitForUpdate(timeInSeconds, TimeUnit.SECONDS)).isTrue();
    } catch (InterruptedException | TimeoutException e) {
      throw new RuntimeException(e);
    }

  }

  public void waitForBootstrapFinishedAtClientSide(long timeInSeconds) {
    throw new UnsupportedOperationException("Not Impl yet.");
    
  }

  public void ensureNoUpdate(long timeInSeconds) {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

  public void waitForDeregistrationAtServerSide(long timeInSeconds) {
    try {
      registrationListener.waitForDeregister(timeInSeconds, TimeUnit.SECONDS);
    } catch (InterruptedException | TimeoutException e) {
      throw new RuntimeException(e);
    }
  }

  public void waitForDeregistrationAtClientSide(long timeInSeconds) {
  }

  public void ensureNoDeregistration(long timeInSeconds) {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

  public Registration getCurrentRegistration() {
    return server.getRegistrationService().getByEndpoint(currentEndpointIdentifier.get());
  }

  public ServerIdentity getCurrentRegisteredServer() {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

  public void deregisterClient() {
    Registration r = getCurrentRegistration();
    if (r != null) {
      ((RegistrationServiceImpl) server.getRegistrationService()).getStore().removeRegistration(r.getId());
    }
  }

  public void dispose() {
    deregisterClient();
    currentEndpointIdentifier.set(null);
  }

  public void assertClientRegisterered() {
    assertThat(getCurrentRegistration()).isNotNull();
  }

  public void assertClientNotRegisterered() {
    assertThat(getCurrentRegistration()).isNull();
  }

  public Registration getLastRegistration() {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

}
