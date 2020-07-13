package com.example.leshan.integration.tests.parts;

import java.net.InetSocketAddress;

import javax.crypto.SecretKey;

import org.eclipse.californium.scandium.dtls.PskPublicInformation;
import org.eclipse.californium.scandium.dtls.pskstore.PskStore;
import org.eclipse.californium.scandium.util.ServerNames;

public class SinglePSKStore implements PskStore {

  private PskPublicInformation identity;
  private SecretKey key;

  public SinglePSKStore(PskPublicInformation identity, byte[] key) {
  }

  public SinglePSKStore(PskPublicInformation identity, SecretKey key) {
  }

  @Override
  public SecretKey getKey(PskPublicInformation identity) {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

  @Override
  public SecretKey getKey(ServerNames serverName, PskPublicInformation identity) {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

  @Override
  public PskPublicInformation getIdentity(InetSocketAddress inetAddress) {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

  @Override
  public PskPublicInformation getIdentity(InetSocketAddress peerAddress, ServerNames virtualHost) {
    throw new UnsupportedOperationException("Not Impl yet.");

  }

  public void setKey(byte[] key) {
  }

  public void setIdentity(String identity) {
  }

}
