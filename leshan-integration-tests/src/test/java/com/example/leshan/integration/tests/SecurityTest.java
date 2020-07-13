package com.example.leshan.integration.tests;

import org.eclipse.leshan.server.security.NonUniqueSecurityInfoException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.leshan.integration.tests.helper.SecureIntegrationTestHelper;

public class SecurityTest {

  protected SecureIntegrationTestHelper helper = new SecureIntegrationTestHelper();

  @BeforeEach
  public void start() {
    Assertions.fail("Test Not Impl yet.");
  }

  @AfterEach
  public void stop() {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void registered_device_with_psk_to_server_with_psk() throws NonUniqueSecurityInfoException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void dont_sent_request_if_identity_change() {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void register_update_deregister_reregister_device_with_psk_to_server_with_psk() {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void register_update_reregister_device_with_psk_to_server_with_psk() throws NonUniqueSecurityInfoException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void server_initiates_dtls_handshake() throws NonUniqueSecurityInfoException, InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void server_initiates_dtls_handshake_timeout() throws NonUniqueSecurityInfoException, InterruptedException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void server_does_not_initiate_dtls_handshake_with_queue_mode()
      throws NonUniqueSecurityInfoException, InterruptedException {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void registered_device_with_bad_psk_identity_to_server_with_psk() throws NonUniqueSecurityInfoException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void registered_device_with_bad_psk_key_to_server_with_psk() throws NonUniqueSecurityInfoException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void registered_device_with_psk_and_bad_endpoint_to_server_with_psk() throws NonUniqueSecurityInfoException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void registered_device_with_psk_identity_to_server_with_psk_then_remove_security_info() {
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void nonunique_psk_identity() throws NonUniqueSecurityInfoException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void change_psk_identity_cleanup() throws NonUniqueSecurityInfoException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void registered_device_with_rpk_to_server_with_rpk() throws NonUniqueSecurityInfoException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void registered_device_with_bad_rpk_to_server_with_rpk_() throws NonUniqueSecurityInfoException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void registered_device_with_rpk_to_server_with_rpk_then_remove_security_info() throws Exception {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void registered_device_with_rpk_and_bad_endpoint_to_server_with_rpk() throws NonUniqueSecurityInfoException {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void registered_device_with_x509cert_to_server_with_x509cert_then_remove_security_info() throws Exception {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void registered_device_with_x509cert_to_server_with_x509cert() throws Exception {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void registered_device_with_x509cert_to_server_with_self_signed_x509cert() throws Exception {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void registered_device_with_x509cert_and_bad_endpoint_to_server_with_x509cert() throws Exception {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void registered_device_with_x509cert_and_bad_cn_certificate_to_server_with_x509cert() throws Exception {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void registered_device_with_x509cert_and_bad_private_key_to_server_with_x509cert() throws Exception {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void registered_device_with_untrusted_x509cert_to_server_with_x509cert() throws Exception {
    Assertions.fail("Test Not Impl yet.");
  }

  @Test
  public void registered_device_with_selfsigned_x509cert_to_server_with_x509cert() throws Exception {
    Assertions.fail("Test Not Impl yet.");
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void registered_device_with_x509cert_to_server_with_rpk() throws Exception {
    Assertions.fail("Test Not Impl yet.");
    Assertions.fail("Test Not Impl yet.");

  }

  @Test
  public void registered_device_with_rpk_to_server_with_x509cert() throws NonUniqueSecurityInfoException {
    Assertions.fail("Test Not Impl yet.");
    Assertions.fail("Test Not Impl yet.");

  }

}
