package com.example.leshan.server.demo;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.List;


public class ServerOptions {

  private String webAddress;
  private int webPort;
  private String localAddress;
  private Integer localPort;
  private String secureLocalAddress;
  private Integer secureLocalPort;
  private String modelsFolderPath;
  private String redisUrl;
  private PublicKey publicKey;
  private PrivateKey privateKey;
  private X509Certificate certificate;
  private List<Certificate> trustStore;
  private String keyStorePath;
  private String keyStoreType;
  private String keyStorePass;
  private String keyStoreAlias;
  private String keyStoreAliasPass;
  private Boolean publishDNSSdServices;
  private boolean supportDeprecatedCiphers;

  private ServerOptions() {

  }

  public String getWebAddress() {
    return webAddress;
  }

  public int getWebPort() {
    return webPort;
  }

  public String getLocalAddress() {
    return localAddress;
  }

  public Integer getLocalPort() {
    return localPort;
  }

  public String getSecureLocalAddress() {
    return secureLocalAddress;
  }

  public Integer getSecureLocalPort() {
    return secureLocalPort;
  }

  public String getModelsFolderPath() {
    return modelsFolderPath;
  }

  public String getRedisUrl() {
    return redisUrl;
  }

  public PublicKey getPublicKey() {
    return publicKey;
  }

  public PrivateKey getPrivateKey() {
    return privateKey;
  }

  public X509Certificate getCertificate() {
    return certificate;
  }

  public List<Certificate> getTrustStore() {
    return trustStore;
  }

  public String getKeyStorePath() {
    return keyStorePath;
  }

  public String getKeyStoreType() {
    return keyStoreType;
  }

  public String getKeyStorePass() {
    return keyStorePass;
  }

  public String getKeyStoreAlias() {
    return keyStoreAlias;
  }

  public String getKeyStoreAliasPass() {
    return keyStoreAliasPass;
  }

  public Boolean getPublishDNSSdServices() {
    return publishDNSSdServices;
  }

  public boolean isSupportDeprecatedCiphers() {
    return supportDeprecatedCiphers;
  }

  public static class ServerOptionsBuilder {

    // 中身がビルダーっぽくないかも

    private ServerOptions serverOptions;

    private ServerOptionsBuilder() {
      this.serverOptions = new ServerOptions();
    }

    public static ServerOptionsBuilder builder() {
      return new ServerOptionsBuilder();
    }

    public ServerOptionsBuilder webAddressIs(String webAddress) {
      serverOptions.webAddress = webAddress;
      return this;
    }

    public ServerOptionsBuilder webPortIs(int webPort) {
      serverOptions.webPort = webPort;
      return this;
    }

    public ServerOptionsBuilder localAddressIs(String localAddress) {
      serverOptions.localAddress = localAddress;
      return this;
    }

    public ServerOptionsBuilder localPortIs(Integer localPort) {
      serverOptions.localPort = localPort;
      return this;
    }

    public ServerOptionsBuilder secureLocalAddressIs(String secureLocalAddress) {
      serverOptions.secureLocalAddress = secureLocalAddress;
      return this;
    }

    public ServerOptionsBuilder secureLocalPortIs(Integer secureLocalPort) {
      serverOptions.secureLocalPort = secureLocalPort;
      return this;
    }

    public ServerOptionsBuilder modelsFolderPathIs(String modelsFolderPath) {
      serverOptions.modelsFolderPath = modelsFolderPath;
      return this;
    }

    public ServerOptionsBuilder redisUrlIs(String redisUrl) {
      serverOptions.redisUrl = redisUrl;
      return this;
    }

    public ServerOptionsBuilder publicKeyIs(PublicKey publicKey) {
      // TODO 呼び出す側が↓みたいな感じなので、少し改良が必要
      // serverOptionsBuilder.publicKeyIs(SecurityUtil.publicKey.readFromFile(cl.getOptionValue("cert")));
      serverOptions.publicKey = publicKey;
      return this;
    }

    public ServerOptionsBuilder privateKeyIs(PrivateKey privateKey) {
      serverOptions.privateKey = privateKey;
      return this;
    }

    public ServerOptionsBuilder certificateIs(X509Certificate certificate) {
      serverOptions.certificate = certificate;
      return this;
    }

    public ServerOptionsBuilder trustStoreIs(List<Certificate> trustStore) {
      serverOptions.trustStore = trustStore;
      return this;
    }

    public ServerOptionsBuilder keyStorePathIs(String keyStorePath) {
      serverOptions.keyStorePath = keyStorePath;
      return this;
    }

    public ServerOptionsBuilder keyStoreTypeIs(String keyStoreType) {
      serverOptions.keyStoreType = keyStoreType;
      return this;
    }

    public ServerOptionsBuilder keyStorePassIs(String keyStorePass) {
      serverOptions.keyStorePass = keyStorePass;
      return this;
    }

    public ServerOptionsBuilder keyStoreAliasIs(String keyStoreAlias) {
      serverOptions.keyStoreAlias = keyStoreAlias;
      return this;
    }

    public ServerOptionsBuilder keyStoreAliasPassIs(String keyStoreAliasPass) {
      serverOptions.keyStoreAliasPass = keyStoreAliasPass;
      return this;
    }

    public ServerOptionsBuilder publishDNSSdServicesIs(Boolean publishDNSSdServices) {
      serverOptions.publishDNSSdServices = publishDNSSdServices;
      return this;
    }

    public ServerOptionsBuilder supportDeprecatedCiphersIs(boolean supportDeprecatedCiphers) {
      serverOptions.supportDeprecatedCiphers = supportDeprecatedCiphers;
      return this;
    }

    public ServerOptions build() {
      return this.serverOptions;
    }
  }
}
