package com.example.leshan.integration.tests;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;

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
  public final PrivateKey clientPrivateKeyFromCert;
  public final PrivateKey serverPrivateKeyFromCert;
  public final X509Certificate clientX509Cert;
  public final X509Certificate clientX509CertWithBadCN;
  public final X509Certificate clientX509CertSelfSigned;
  public final X509Certificate clientX509CertNotTrusted;
  public final X509Certificate serverX509Cert;
  public final X509Certificate serverX509CertSelfSigned;
  public final X509Certificate rootCAX509Cert;
  public final Certificate[] trustedCertificates = new Certificate[1];

  public SecureIntegrationTestHelper() {
    throw new UnsupportedOperationException("Not Impl yet.");

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
