package com.example.leshan.client.demo;

import java.io.File;
import java.security.PublicKey;
import java.security.cert.CertificateEncodingException;
import java.security.interfaces.ECPublicKey;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.eclipse.californium.core.network.config.NetworkConfig;
import org.eclipse.californium.scandium.config.DtlsConnectorConfig;
import org.eclipse.leshan.client.californium.LeshanClient;
import org.eclipse.leshan.client.californium.LeshanClientBuilder;
import org.eclipse.leshan.client.engine.DefaultRegistrationEngineFactory;
import org.eclipse.leshan.client.object.Security;
import org.eclipse.leshan.client.object.Server;
import org.eclipse.leshan.client.resource.LwM2mObjectEnabler;
import org.eclipse.leshan.client.resource.ObjectsInitializer;
import org.eclipse.leshan.client.resource.listener.ObjectsListenerAdapter;
import org.eclipse.leshan.core.LwM2mId;
import org.eclipse.leshan.core.californium.DefaultEndpointFactory;
import org.eclipse.leshan.core.model.LwM2mModel;
import org.eclipse.leshan.core.model.ObjectLoader;
import org.eclipse.leshan.core.model.ObjectModel;
import org.eclipse.leshan.core.model.StaticModel;
import org.eclipse.leshan.core.node.codec.DefaultLwM2mNodeDecoder;
import org.eclipse.leshan.core.node.codec.DefaultLwM2mNodeEncoder;
import org.eclipse.leshan.core.request.BindingMode;
import org.eclipse.leshan.core.util.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LeshanClientController {

  private static final Logger LOG = LoggerFactory.getLogger(LeshanClientController.class);

  // /!\ This field is a COPY of
  // org.eclipse.leshan.server.demo.LeshanServerDemo.modelPaths /!\
  // TODO create a leshan-demo project ?
  public final static String[] modelPaths = new String[] { "10241.xml", "10242.xml", "10243.xml", "10244.xml",
      "10245.xml", "10246.xml", "10247.xml", "10248.xml", "10249.xml", "10250.xml", "10251.xml", "10252.xml",
      "10253.xml", "10254.xml", "10255.xml", "10256.xml", "10257.xml", "10258.xml", "10259.xml", "10260-2_0.xml",
      "10262.xml", "10263.xml", "10264.xml", "10265.xml", "10266.xml", "10267.xml", "10268.xml", "10269.xml",
      "10270.xml", "10271.xml", "10272.xml", "10273.xml", "10274.xml", "10275.xml", "10276.xml", "10277.xml",
      "10278.xml", "10279.xml", "10280.xml", "10281.xml", "10282.xml", "10283.xml", "10284.xml", "10286.xml",
      "10290.xml", "10291.xml", "10292.xml", "10299.xml", "10300.xml", "10308-2_0.xml", "10309.xml", "10311.xml",
      "10313.xml", "10314.xml", "10315.xml", "10316.xml", "10318.xml", "10319.xml", "10320.xml", "10322.xml",
      "10323.xml", "10324.xml", "10326.xml", "10327.xml", "10328.xml", "10329.xml", "10330.xml", "10331.xml",
      "10332.xml", "10333.xml", "10334.xml", "10335.xml", "10336.xml", "10337.xml", "10338.xml", "10339.xml",
      "10340.xml", "10341.xml", "10342.xml", "10343.xml", "10344.xml", "10345.xml", "10346.xml", "10347.xml",
      "10348.xml", "10349.xml", "10350.xml", "10351.xml", "10352.xml", "10353.xml", "10354.xml", "10355.xml",
      "10356.xml", "10357.xml", "10358.xml", "10359.xml", "10360.xml", "10361.xml", "10362.xml", "10363.xml",
      "10364.xml", "10365.xml", "10366.xml", "10368.xml", "10369.xml",

      "2048.xml", "2049.xml", "2050.xml", "2051.xml", "2052.xml", "2053.xml", "2054.xml", "2055.xml", "2056.xml",
      "2057.xml",

      "3200.xml", "3201.xml", "3202.xml", "3203.xml", "3300.xml", "3301.xml", "3302.xml", "3303.xml", "3304.xml",
      "3305.xml", "3306.xml", "3308.xml", "3310.xml", "3311.xml", "3312.xml", "3313.xml", "3314.xml", "3315.xml",
      "3316.xml", "3317.xml", "3318.xml", "3319.xml", "3320.xml", "3321.xml", "3322.xml", "3323.xml", "3324.xml",
      "3325.xml", "3326.xml", "3327.xml", "3328.xml", "3329.xml", "3330.xml", "3331.xml", "3332.xml", "3333.xml",
      "3334.xml", "3335.xml", "3336.xml", "3337.xml", "3338.xml", "3339.xml", "3340.xml", "3341.xml", "3342.xml",
      "3343.xml", "3344.xml", "3345.xml", "3346.xml", "3347.xml", "3348.xml", "3349.xml", "3350.xml", "3351.xml",
      "3352.xml", "3353.xml", "3354.xml", "3355.xml", "3356.xml", "3357.xml", "3358.xml", "3359.xml", "3360.xml",
      "3361.xml", "3362.xml", "3363.xml", "3364.xml", "3365.xml", "3366.xml", "3367.xml", "3368.xml", "3369.xml",
      "3370.xml", "3371.xml", "3372.xml", "3373.xml", "3374.xml", "3375.xml", "3376.xml", "3377.xml", "3378.xml",
      "3379.xml", "3380-2_0.xml", "3381.xml", "3382.xml", "3383.xml", "3384.xml", "3385.xml", "3386.xml",

      "LWM2M_APN_Connection_Profile-v1_0_1.xml", "LWM2M_Bearer_Selection-v1_0_1.xml",
      "LWM2M_Cellular_Connectivity-v1_0_1.xml", "LWM2M_DevCapMgmt-v1_0.xml", "LWM2M_LOCKWIPE-v1_0_1.xml",
      "LWM2M_Portfolio-v1_0.xml", "LWM2M_Software_Component-v1_0.xml", "LWM2M_Software_Management-v1_0.xml",
      "LWM2M_WLAN_connectivity4-v1_0.xml", "LwM2M_BinaryAppDataContainer-v1_0_1.xml", "LwM2M_EventLog-V1_0.xml" };

  private static final int OBJECT_ID_TEMPERATURE_SENSOR = 3303;

  private static MyLocation locationInstance;
  private final LeshanClient client;
  private final LwM2mModel model;


  public LeshanClientController(ClientOptions clientOptions) throws CertificateEncodingException {

    locationInstance = new MyLocation(clientOptions.getLatitude(), clientOptions.getLongitude(),
        clientOptions.getScaleFactor());

    // Initialize model
    List<ObjectModel> models = ObjectLoader.loadDefault();
    models.addAll(ObjectLoader.loadDdfResources("/models", modelPaths));
    if (clientOptions.getModelsFolderPath() != null) {
      models.addAll(ObjectLoader.loadObjectsFromDir(new File(clientOptions.getModelsFolderPath())));
    }

    // Initialize object list
    model = new StaticModel(models);
    final ObjectsInitializer initializer = new ObjectsInitializer(model);
    if (clientOptions.isNeedBootstrap()) {
      if (clientOptions.getPskIdentity() != null) {
        initializer.setInstancesForObject(LwM2mId.SECURITY, Security.pskBootstrap(clientOptions.getServerURI(),
            clientOptions.getPskIdentity(), clientOptions.getPskKey()));
        initializer.setClassForObject(LwM2mId.SERVER, Server.class);
      } else if (clientOptions.getClientPublicKey() != null) {
        initializer.setInstancesForObject(LwM2mId.SECURITY,
            Security.rpkBootstrap(clientOptions.getServerURI(), clientOptions.getClientPublicKey().getEncoded(),
                clientOptions.getClientPrivateKey().getEncoded(), clientOptions.getServerPublicKey().getEncoded()));
        initializer.setClassForObject(LwM2mId.SERVER, Server.class);
      } else if (clientOptions.getClientCertificate() != null) {
        initializer.setInstancesForObject(LwM2mId.SECURITY,
            Security.x509Bootstrap(clientOptions.getServerURI(), clientOptions.getClientCertificate().getEncoded(),
                clientOptions.getClientPrivateKey().getEncoded(), clientOptions.getServerCertificate().getEncoded()));
        initializer.setClassForObject(LwM2mId.SERVER, Server.class);
      } else {
        initializer.setInstancesForObject(LwM2mId.SECURITY, Security.noSecBootstap(clientOptions.getServerURI()));
        initializer.setClassForObject(LwM2mId.SERVER, Server.class);
      }
    } else {
      if (clientOptions.getPskIdentity() != null) {
        initializer.setInstancesForObject(LwM2mId.SECURITY,
            Security.psk(clientOptions.getServerURI(), 123, clientOptions.getPskIdentity(), clientOptions.getPskKey()));
        initializer.setInstancesForObject(LwM2mId.SERVER,
            new Server(123, clientOptions.getLifetime(), BindingMode.U, false));
      } else if (clientOptions.getClientPublicKey() != null) {
        initializer.setInstancesForObject(LwM2mId.SECURITY,
            Security.rpk(clientOptions.getServerURI(), 123, clientOptions.getClientPublicKey().getEncoded(),
                clientOptions.getClientPrivateKey().getEncoded(), clientOptions.getServerPublicKey().getEncoded()));
        initializer.setInstancesForObject(LwM2mId.SERVER,
            new Server(123, clientOptions.getLifetime(), BindingMode.U, false));
      } else if (clientOptions.getClientCertificate() != null) {
        initializer.setInstancesForObject(LwM2mId.SECURITY,
            Security.x509(clientOptions.getServerURI(), 123, clientOptions.getClientCertificate().getEncoded(),
                clientOptions.getClientPrivateKey().getEncoded(), clientOptions.getServerCertificate().getEncoded()));
        initializer.setInstancesForObject(LwM2mId.SERVER,
            new Server(123, clientOptions.getLifetime(), BindingMode.U, false));
      } else {
        initializer.setInstancesForObject(LwM2mId.SECURITY, Security.noSec(clientOptions.getServerURI(), 123));
        initializer.setInstancesForObject(LwM2mId.SERVER,
            new Server(123, clientOptions.getLifetime(), BindingMode.U, false));
      }
    }
    initializer.setInstancesForObject(LwM2mId.DEVICE, new MyDevice());
    initializer.setInstancesForObject(LwM2mId.LOCATION, locationInstance);
    initializer.setInstancesForObject(OBJECT_ID_TEMPERATURE_SENSOR, new RandomTemperatureSensor());
    List<LwM2mObjectEnabler> enablers = initializer.createAll();

    // Create CoAP Config
    NetworkConfig coapConfig;
    File configFile = new File(NetworkConfig.DEFAULT_FILE_NAME);
    if (configFile.isFile()) {
      coapConfig = new NetworkConfig();
      coapConfig.load(configFile);
    } else {
      coapConfig = LeshanClientBuilder.createDefaultNetworkConfig();
      coapConfig.store(configFile);
    }

    // Create DTLS Config
    DtlsConnectorConfig.Builder dtlsConfig = new DtlsConnectorConfig.Builder();
    dtlsConfig.setRecommendedCipherSuitesOnly(!clientOptions.isSupportDeprecatedCiphers());

    // Configure Registration Engine
    DefaultRegistrationEngineFactory engineFactory = new DefaultRegistrationEngineFactory();
    engineFactory.setCommunicationPeriod(clientOptions.getCommunicationPeriod());
    engineFactory.setReconnectOnUpdate(clientOptions.isReconnectOnUpdate());
    engineFactory.setResumeOnConnect(!clientOptions.isForceFullhandshake());

    // configure EndpointFactory
    DefaultEndpointFactory endpointFactory = new CustomDefaultEndpointFactory("LWM2M CLIENT");

    // Create client
    LeshanClientBuilder builder = new LeshanClientBuilder(clientOptions.getEndpoint());
    builder.setLocalAddress(clientOptions.getLocalAddress(), clientOptions.getLocalPort());
    builder.setObjects(enablers);
    builder.setCoapConfig(coapConfig);
    builder.setDtlsConfig(dtlsConfig);
    builder.setRegistrationEngineFactory(engineFactory);
    builder.setEndpointFactory(endpointFactory);
    if (clientOptions.isSupportOldFormat()) {
      builder.setDecoder(new DefaultLwM2mNodeDecoder(true));
      builder.setEncoder(new DefaultLwM2mNodeEncoder(true));
    }
    builder.setAdditionalAttributes(clientOptions.getAdditionalAttributes());
    client = builder.build();

    client.getObjectTree().addListener(new ObjectsListenerAdapter() {
      @Override
      public void objectRemoved(LwM2mObjectEnabler object) {
        LOG.info("Object {} disabled.", object.getId());
      }

      @Override
      public void objectAdded(LwM2mObjectEnabler object) {
        LOG.info("Object {} enabled.", object.getId());
      }
    });

    // Display client public key to easily add it in demo servers.
    if (clientOptions.getClientPublicKey() != null) {
      PublicKey rawPublicKey = clientOptions.getClientPublicKey();
      if (rawPublicKey instanceof ECPublicKey) {
        ECPublicKey ecPublicKey = (ECPublicKey) rawPublicKey;
        // Get x coordinate
        byte[] x = ecPublicKey.getW().getAffineX().toByteArray();
        if (x[0] == 0)
          x = Arrays.copyOfRange(x, 1, x.length);

        // Get Y coordinate
        byte[] y = ecPublicKey.getW().getAffineY().toByteArray();
        if (y[0] == 0)
          y = Arrays.copyOfRange(y, 1, y.length);

        // Get Curves params
        String params = ecPublicKey.getParams().toString();

        LOG.info(
            "Client uses RPK : \n Elliptic Curve parameters  : {} \n Public x coord : {} \n Public y coord : {} \n Public Key (Hex): {} \n Private Key (Hex): {}",
            params, Hex.encodeHexString(x), Hex.encodeHexString(y), Hex.encodeHexString(rawPublicKey.getEncoded()),
            Hex.encodeHexString(clientOptions.getClientPrivateKey().getEncoded()));

      } else {
        throw new IllegalStateException("Unsupported Public Key Format (only ECPublicKey supported).");
      }
    }
    // Display X509 credentials to easily at it in demo servers.
    if (clientOptions.getClientCertificate() != null) {
      LOG.info("Client uses X509 : \n X509 Certificate (Hex): {} \n Private Key (Hex): {}",
          Hex.encodeHexString(clientOptions.getClientCertificate().getEncoded()),
          Hex.encodeHexString(clientOptions.getClientPrivateKey().getEncoded()));
    }

    // Print commands help
    StringBuilder commandsHelp = new StringBuilder("Commands available :");
    commandsHelp.append(System.lineSeparator());
    commandsHelp.append(System.lineSeparator());
    commandsHelp.append("  - create <objectId> : to enable a new object.");
    commandsHelp.append(System.lineSeparator());
    commandsHelp.append("  - delete <objectId> : to disable a new object.");
    commandsHelp.append(System.lineSeparator());
    commandsHelp.append("  - update : to trigger a registration update.");
    commandsHelp.append(System.lineSeparator());
    commandsHelp.append("  - w : to move to North.");
    commandsHelp.append(System.lineSeparator());
    commandsHelp.append("  - a : to move to East.");
    commandsHelp.append(System.lineSeparator());
    commandsHelp.append("  - s : to move to South.");
    commandsHelp.append(System.lineSeparator());
    commandsHelp.append("  - d : to move to West.");
    commandsHelp.append(System.lineSeparator());
    LOG.info(commandsHelp.toString());


  }

  public void start() {
    // Start the client
    client.start();

    // De-register on shutdown and stop client.
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        client.destroy(true); // send de-registration request before destroy
      }
    });

    // Change the location through the Console
    try (Scanner scanner = new Scanner(System.in)) {
      List<Character> wasdCommands = Arrays.asList('w', 'a', 's', 'd');
      while (scanner.hasNext()) {
        String command = scanner.next();
        if (command.startsWith("create")) {
          try {
            int objectId = scanner.nextInt();
            if (client.getObjectTree().getObjectEnabler(objectId) != null) {
              LOG.info("Object {} already enabled.", objectId);
            }
            if (model.getObjectModel(objectId) == null) {
              LOG.info("Unable to enable Object {} : there no model for this.", objectId);
            } else {
              ObjectsInitializer objectsInitializer = new ObjectsInitializer(model);
              objectsInitializer.setDummyInstancesForObject(objectId);
              LwM2mObjectEnabler object = objectsInitializer.create(objectId);
              client.getObjectTree().addObjectEnabler(object);
            }
          } catch (Exception e) {
            // skip last token
            scanner.next();
            LOG.info("Invalid syntax, <objectid> must be an integer : create <objectId>");
          }
        } else if (command.startsWith("delete")) {
          try {
            int objectId = scanner.nextInt();
            if (objectId == 0 || objectId == 0 || objectId == 3) {
              LOG.info("Object {} can not be disabled.", objectId);
            } else if (client.getObjectTree().getObjectEnabler(objectId) == null) {
              LOG.info("Object {} is not enabled.", objectId);
            } else {
              client.getObjectTree().removeObjectEnabler(objectId);
            }
          } catch (Exception e) {
            // skip last token
            scanner.next();
            LOG.info("\"Invalid syntax, <objectid> must be an integer : delete <objectId>");
          }
        } else if (command.startsWith("update")) {
          client.triggerRegistrationUpdate();
        } else if (command.length() == 1 && wasdCommands.contains(command.charAt(0))) {
          locationInstance.moveLocation(command);
        } else {
          LOG.info("Unknown command '{}'", command);
        }
      }
    }

  }

}
