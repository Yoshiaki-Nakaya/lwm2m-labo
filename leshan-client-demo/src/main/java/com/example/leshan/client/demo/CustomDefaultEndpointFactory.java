package com.example.leshan.client.demo;

import org.eclipse.californium.elements.Connector;
import org.eclipse.californium.scandium.DTLSConnector;
import org.eclipse.californium.scandium.config.DtlsConnectorConfig;
import org.eclipse.californium.scandium.dtls.ClientHandshaker;
import org.eclipse.californium.scandium.dtls.DTLSSession;
import org.eclipse.californium.scandium.dtls.HandshakeException;
import org.eclipse.californium.scandium.dtls.Handshaker;
import org.eclipse.californium.scandium.dtls.ResumingClientHandshaker;
import org.eclipse.californium.scandium.dtls.ResumingServerHandshaker;
import org.eclipse.californium.scandium.dtls.ServerHandshaker;
import org.eclipse.californium.scandium.dtls.SessionAdapter;
import org.eclipse.leshan.core.californium.DefaultEndpointFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomDefaultEndpointFactory extends DefaultEndpointFactory {

  public CustomDefaultEndpointFactory(String loggingTag) {
    super(loggingTag);
  }

  @Override
  protected Connector createSecuredConnector(DtlsConnectorConfig dtlsConfig) {
    return new CustomDTLSConnector(dtlsConfig);
  }

  public static class CustomDTLSConnector extends DTLSConnector {

    public CustomDTLSConnector(DtlsConnectorConfig configuration) {
      super(configuration);
    }

    @Override
    protected void onInitializeHandshaker(Handshaker handshaker) {
      handshaker.addSessionListener(new CustomSessionAdapter());
    }
  }

  public static class CustomSessionAdapter extends SessionAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(CustomSessionAdapter.class);

    @Override
    public void handshakeStarted(Handshaker handshaker) throws HandshakeException {
      if (handshaker instanceof ServerHandshaker) {
        LOG.info("DTLS Full Handshake initiated by server : STARTED ...");
      } else if (handshaker instanceof ResumingServerHandshaker) {
        LOG.info("DTLS abbreviated Handshake initiated by server : STARTED ...");
      } else if (handshaker instanceof ClientHandshaker) {
        LOG.info("DTLS Full Handshake initiated by client : STARTED ...");
      } else if (handshaker instanceof ResumingClientHandshaker) {
        LOG.info("DTLS abbreviated Handshake initiated by client : STARTED ...");
      }
    }

    @Override
    public void sessionEstablished(Handshaker handshaker, DTLSSession establishedSession) throws HandshakeException {
      if (handshaker instanceof ServerHandshaker) {
        LOG.info("DTLS Full Handshake initiated by server : SUCCEED");
      } else if (handshaker instanceof ResumingServerHandshaker) {
        LOG.info("DTLS abbreviated Handshake initiated by server : SUCCEED");
      } else if (handshaker instanceof ClientHandshaker) {
        LOG.info("DTLS Full Handshake initiated by client : SUCCEED");
      } else if (handshaker instanceof ResumingClientHandshaker) {
        LOG.info("DTLS abbreviated Handshake initiated by client : SUCCEED");
      }
    }

    @Override
    public void handshakeFailed(Handshaker handshaker, Throwable error) {
      // get cause
      String cause;
      if (error != null) {
        if (error.getMessage() != null) {
          cause = error.getMessage();
        } else {
          cause = error.getClass().getName();
        }
      } else {
        cause = "unknown cause";
      }

      if (handshaker instanceof ServerHandshaker) {
        LOG.info("DTLS Full Handshake initiated by server : FAILED ({})", cause);
      } else if (handshaker instanceof ResumingServerHandshaker) {
        LOG.info("DTLS abbreviated Handshake initiated by server : FAILED ({})", cause);
      } else if (handshaker instanceof ClientHandshaker) {
        LOG.info("DTLS Full Handshake initiated by client : FAILED ({})", cause);
      } else if (handshaker instanceof ResumingClientHandshaker) {
        LOG.info("DTLS abbreviated Handshake initiated by client : FAILED ({})", cause);
      }
    }
  }
}
