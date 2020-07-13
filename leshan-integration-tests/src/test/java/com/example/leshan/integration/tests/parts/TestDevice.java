package com.example.leshan.integration.tests.parts;

import org.eclipse.leshan.client.object.Device;
import org.eclipse.leshan.client.servers.ServerIdentity;
import org.eclipse.leshan.core.response.ExecuteResponse;

public class TestDevice extends Device {

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
