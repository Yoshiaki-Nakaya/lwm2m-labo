package com.example.leshan.integration.tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPrivateKeySpec;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.KeySpec;

import org.eclipse.leshan.core.util.Hex;
import org.eclipse.leshan.server.californium.LeshanServerBuilder;
import org.eclipse.leshan.server.security.EditableSecurityStore;
import org.eclipse.leshan.server.security.SecurityStore;

public class SecureIntegrationTestHelper extends IntegrationTestHelper {

  public static final String GOOD_PSK_ID = "Good_Client_identity";
  public static final byte[] GOOD_PSK_KEY = Hex.decodeHex("73656372657450534b".toCharArray());
  public static final String GOOD_ENDPOINT = "good_endpoint";
  public static final String BAD_PSK_ID = "Bad_Client_identity";
  public static final byte[] BAD_PSK_KEY = Hex.decodeHex("010101010101010101".toCharArray());
  public static final String BAD_ENDPOINT = "bad_endpoint";
  private SinglePSKStore singlePSKStore;
  protected SecurityStore securityStore;

  public final PublicKey clientPublicKey; // client public key used for RPK
  public final PrivateKey clientPrivateKey; // client private key used for RPK
  public final PublicKey serverPublicKey; // server public key used for RPK
  public final PrivateKey serverPrivateKey; // server private key used for RPK

  // client private key used for X509
  public final PrivateKey clientPrivateKeyFromCert;
  // server private key used for X509
  public final PrivateKey serverPrivateKeyFromCert;
  // client certificate signed by rootCA with a good CN (CN start by leshan_integration_test)
  public final X509Certificate clientX509Cert;
  // client certificate signed by rootCA but with bad CN (CN does not start by leshan_integration_test)
  public final X509Certificate clientX509CertWithBadCN;
  // client certificate self-signed with a good CN (CN start by leshan_integration_test)
  public final X509Certificate clientX509CertSelfSigned;
  // client certificate signed by another CA (not rootCA) with a good CN (CN start by leshan_integration_test)
  public final X509Certificate clientX509CertNotTrusted;
  // server certificate signed by rootCA
  public final X509Certificate serverX509Cert;
  // self-signed server certificate
  public final X509Certificate serverX509CertSelfSigned;
  // rootCA used by the server
  public final X509Certificate rootCAX509Cert;
  // certificates trustedby the server (should contain rootCA)
  public final Certificate[] trustedCertificates = new Certificate[1];

  public SecureIntegrationTestHelper() {
        // create client credentials
        try {
          // Get point values
          byte[] publicX = Hex
                  .decodeHex("89c048261979208666f2bfb188be1968fc9021c416ce12828c06f4e314c167b5".toCharArray());
          byte[] publicY = Hex
                  .decodeHex("cbf1eb7587f08e01688d9ada4be859137ca49f79394bad9179326b3090967b68".toCharArray());
          byte[] privateS = Hex
                  .decodeHex("e67b68d2aaeb6550f19d98cade3ad62b39532e02e6b422e1f7ea189dabaea5d2".toCharArray());

          // Get Elliptic Curve Parameter spec for secp256r1
          AlgorithmParameters algoParameters = AlgorithmParameters.getInstance("EC");
          algoParameters.init(new ECGenParameterSpec("secp256r1"));
          ECParameterSpec parameterSpec = algoParameters.getParameterSpec(ECParameterSpec.class);

          // Create key specs
          KeySpec publicKeySpec = new ECPublicKeySpec(new ECPoint(new BigInteger(publicX), new BigInteger(publicY)),
                  parameterSpec);
          KeySpec privateKeySpec = new ECPrivateKeySpec(new BigInteger(privateS), parameterSpec);

          // Get keys
          clientPublicKey = KeyFactory.getInstance("EC").generatePublic(publicKeySpec);
          clientPrivateKey = KeyFactory.getInstance("EC").generatePrivate(privateKeySpec);

          // Get certificates from key store
          char[] clientKeyStorePwd = "client".toCharArray();
          KeyStore clientKeyStore = KeyStore.getInstance(KeyStore.getDefaultType());
          try (FileInputStream clientKeyStoreFile = new FileInputStream("./credentials/clientKeyStore.jks")) {
              clientKeyStore.load(clientKeyStoreFile, clientKeyStorePwd);
          }

          clientPrivateKeyFromCert = (PrivateKey) clientKeyStore.getKey("client", clientKeyStorePwd);
          clientX509Cert = (X509Certificate) clientKeyStore.getCertificate("client");
          clientX509CertWithBadCN = (X509Certificate) clientKeyStore.getCertificate("client_bad_cn");
          clientX509CertSelfSigned = (X509Certificate) clientKeyStore.getCertificate("client_self_signed");
          clientX509CertNotTrusted = (X509Certificate) clientKeyStore.getCertificate("client_not_trusted");
      } catch (GeneralSecurityException | IOException e) {
          throw new RuntimeException(e);
      }

      // create server credentials
      try {
          // Get point values
          byte[] publicX = Hex
                  .decodeHex("fcc28728c123b155be410fc1c0651da374fc6ebe7f96606e90d927d188894a73".toCharArray());
          byte[] publicY = Hex
                  .decodeHex("d2ffaa73957d76984633fc1cc54d0b763ca0559a9dff9706e9f4557dacc3f52a".toCharArray());
          byte[] privateS = Hex
                  .decodeHex("1dae121ba406802ef07c193c1ee4df91115aabd79c1ed7f4c0ef7ef6a5449400".toCharArray());

          // Get Elliptic Curve Parameter spec for secp256r1
          AlgorithmParameters algoParameters = AlgorithmParameters.getInstance("EC");
          algoParameters.init(new ECGenParameterSpec("secp256r1"));
          ECParameterSpec parameterSpec = algoParameters.getParameterSpec(ECParameterSpec.class);

          // Create key specs
          KeySpec publicKeySpec = new ECPublicKeySpec(new ECPoint(new BigInteger(publicX), new BigInteger(publicY)),
                  parameterSpec);
          KeySpec privateKeySpec = new ECPrivateKeySpec(new BigInteger(privateS), parameterSpec);

          // Get keys
          serverPublicKey = KeyFactory.getInstance("EC").generatePublic(publicKeySpec);
          serverPrivateKey = KeyFactory.getInstance("EC").generatePrivate(privateKeySpec);

          // Get certificates from key store
          char[] serverKeyStorePwd = "server".toCharArray();
          KeyStore serverKeyStore = KeyStore.getInstance(KeyStore.getDefaultType());
          try (FileInputStream serverKeyStoreFile = new FileInputStream("./credentials/serverKeyStore.jks")) {
              serverKeyStore.load(serverKeyStoreFile, serverKeyStorePwd);
          }

          serverPrivateKeyFromCert = (PrivateKey) serverKeyStore.getKey("server", serverKeyStorePwd);
          rootCAX509Cert = (X509Certificate) serverKeyStore.getCertificate("rootCA");
          serverX509Cert = (X509Certificate) serverKeyStore.getCertificate("server");
          serverX509CertSelfSigned = (X509Certificate) serverKeyStore.getCertificate("server_self_signed");
          trustedCertificates[0] = rootCAX509Cert;

      } catch (GeneralSecurityException | IOException e) {
          throw new RuntimeException(e);
      }
}

  public void createPSKClient() {
  }

  public void createPSKClientUsingQueueMode() {
  }

  public void createPSKClient(boolean queueMode) {
  }

  public void setNewPsk(String identity, byte[] key) {
  }

  public void createRPKClient(boolean useServerCertificate) {
  }

  public void createRPKClient() {
  }

  public void createX509CertClient() throws CertificateEncodingException {
  }

  public void createX509CertClient(Certificate clientCertificate) throws CertificateEncodingException {
  }

  public void createX509CertClient(Certificate clientCertificate, PrivateKey privatekey) throws CertificateEncodingException {

  }

  public void createX509CertClient(Certificate clientCertificate, PrivateKey privatekey, Certificate serverCertificate) throws CertificateEncodingException {

  }

  protected LeshanServerBuilder createServerBuilder() {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

  public void createServerWithRPK() {
  }

  public void createServerWithX509Cert() {
  }

  public void createServerWithX509Cert(X509Certificate serverCertificate) {
  }

  public PublicKey getServerPublicKey() {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

  public EditableSecurityStore getSecurityStore() {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

  public void dispose() {
  }

}
