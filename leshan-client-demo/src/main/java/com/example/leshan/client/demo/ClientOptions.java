package com.example.leshan.client.demo;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Map;

public class ClientOptions {

  private String endpoint;
  private String localAddress;
  private int localPort;
  private boolean needBootstrap;
  private Map<String, String> additionalAttributes;
  private int lifetime;
  private Integer communicationPeriod;
  private String serverURI;
  private byte[] pskIdentity;
  private byte[] pskKey;
  private PrivateKey clientPrivateKey;
  private PublicKey clientPublicKey;
  private PublicKey serverPublicKey;
  private X509Certificate clientCertificate;
  private X509Certificate serverCertificate;
  private Float latitude;
  private Float longitude;
  private float scaleFactor;
  private boolean supportOldFormat;
  private boolean supportDeprecatedCiphers;
  private boolean reconnectOnUpdate;
  private boolean forceFullhandshake;
  private String modelsFolderPath;

  private ClientOptions() {

  }

  public String getEndpoint() {
    return endpoint;
  }

  public String getLocalAddress() {
    return localAddress;
  }

  public int getLocalPort() {
    return localPort;
  }

  public boolean isNeedBootstrap() {
    return needBootstrap;
  }

  public Map<String, String> getAdditionalAttributes() {
    return additionalAttributes;
  }

  public int getLifetime() {
    return lifetime;
  }

  public Integer getCommunicationPeriod() {
    return communicationPeriod;
  }

  public String getServerURI() {
    return serverURI;
  }

  public byte[] getPskIdentity() {
    return pskIdentity;
  }

  public byte[] getPskKey() {
    return pskKey;
  }

  public PrivateKey getClientPrivateKey() {
    return clientPrivateKey;
  }

  public PublicKey getClientPublicKey() {
    return clientPublicKey;
  }

  public PublicKey getServerPublicKey() {
    return serverPublicKey;
  }

  public X509Certificate getClientCertificate() {
    return clientCertificate;
  }

  public X509Certificate getServerCertificate() {
    return serverCertificate;
  }

  public Float getLatitude() {
    return latitude;
  }

  public Float getLongitude() {
    return longitude;
  }

  public float getScaleFactor() {
    return scaleFactor;
  }

  public boolean isSupportOldFormat() {
    return supportOldFormat;
  }

  public boolean isSupportDeprecatedCiphers() {
    return supportDeprecatedCiphers;
  }

  public boolean isReconnectOnUpdate() {
    return reconnectOnUpdate;
  }

  public boolean isForceFullhandshake() {
    return forceFullhandshake;
  }

  public String getModelsFolderPath() {
    return modelsFolderPath;
  }

  public static class ClientOptionsBuilder {

    private final ClientOptions options;

    private ClientOptionsBuilder() {
      this.options = new ClientOptions();
    }

    public static ClientOptionsBuilder builder() {
      return new ClientOptionsBuilder();
    }

    public ClientOptionsBuilder endpointIs(String endpoint) {
      options.endpoint = endpoint;
      return this;
    }

    public ClientOptionsBuilder localAddressIs(String localAddress) {
      options.localAddress = localAddress;
      return this;
    }

    public ClientOptionsBuilder localPortIs(int localPort) {
      options.localPort = localPort;
      return this;
    }

    public ClientOptionsBuilder needBootstrapIs(boolean needBootstrap) {
      options.needBootstrap = needBootstrap;
      return this;
    }

    public ClientOptionsBuilder additionalAttributesIs(Map<String, String> additionalAttributes) {
      options.additionalAttributes = additionalAttributes;
      return this;
    }

    public ClientOptionsBuilder lifetimeIs(int lifetime) {
      options.lifetime = lifetime;
      return this;
    }

    public ClientOptionsBuilder communicationPeriodIs(Integer communicationPeriod) {
      options.communicationPeriod = communicationPeriod;
      return this;
    }

    public ClientOptionsBuilder serverURIIs(String serverURI) {
      options.serverURI = serverURI;
      return this;
    }

    public ClientOptionsBuilder pskIdentityIs(byte[] pskIdentity) {
      options.pskIdentity = pskIdentity;
      return this;
    }

    public ClientOptionsBuilder pskKeyIs(byte[] pskKey) {
      options.pskKey = pskKey;
      return this;
    }

    public ClientOptionsBuilder clientPrivateKeyIs(PrivateKey clientPrivateKey) {
      options.clientPrivateKey = clientPrivateKey;
      return this;
    }

    public ClientOptionsBuilder clientPublicKeyIs(PublicKey clientPublicKey) {
      options.clientPublicKey = clientPublicKey;
      return this;
    }

    public ClientOptionsBuilder serverPublicKeyIs(PublicKey serverPublicKey) {
      options.serverPublicKey = serverPublicKey;
      return this;
    }

    public ClientOptionsBuilder clientCertificateIs(X509Certificate clientCertificate) {
      options.clientCertificate = clientCertificate;
      return this;
    }

    public ClientOptionsBuilder serverCertificateIs(X509Certificate serverCertificate) {
      options.serverCertificate = serverCertificate;
      return this;
    }

    public ClientOptionsBuilder latitudeIs(Float latitude) {
      options.latitude = latitude;
      return this;
    }

    public ClientOptionsBuilder longitudeIs(Float longitude) {
      options.longitude = longitude;
      return this;
    }

    public ClientOptionsBuilder scaleFactorIs(float scaleFactor) {
      options.scaleFactor = scaleFactor;
      return this;
    }

    public ClientOptionsBuilder supportOldFormatIs(boolean supportOldFormat) {
      options.supportOldFormat = supportOldFormat;
      return this;
    }

    public ClientOptionsBuilder supportDeprecatedCiphersIs(boolean supportDeprecatedCiphers) {
      options.supportDeprecatedCiphers = supportDeprecatedCiphers;
      return this;
    }

    public ClientOptionsBuilder reconnectOnUpdateIs(boolean reconnectOnUpdate) {
      options.reconnectOnUpdate = reconnectOnUpdate;
      return this;
    }

    public ClientOptionsBuilder forceFullhandshakeIs(boolean forceFullhandshake) {
      options.forceFullhandshake = forceFullhandshake;
      return this;
    }

    public ClientOptionsBuilder modelsFolderPathIs(String modelsFolderPath) {
      options.modelsFolderPath = modelsFolderPath;
      return this;
    }

    public ClientOptions build() {
      return this.options;
    }

  }
}
