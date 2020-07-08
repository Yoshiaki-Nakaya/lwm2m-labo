package com.example.leshan.server.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

import com.example.leshan.server.demo.servlet.ClientServlet;
import com.example.leshan.server.demo.servlet.EventServlet;
import com.example.leshan.server.demo.servlet.ObjectSpecServlet;
import com.example.leshan.server.demo.servlet.SecurityServlet;
import com.example.leshan.server.demo.utils.MagicLwM2mValueConverter;

import org.eclipse.californium.core.network.config.NetworkConfig;
import org.eclipse.californium.core.network.config.NetworkConfig.Keys;
import org.eclipse.californium.scandium.config.DtlsConnectorConfig;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.leshan.core.LwM2m;
import org.eclipse.leshan.core.model.ObjectLoader;
import org.eclipse.leshan.core.model.ObjectModel;
import org.eclipse.leshan.core.node.codec.DefaultLwM2mNodeDecoder;
import org.eclipse.leshan.core.node.codec.DefaultLwM2mNodeEncoder;
import org.eclipse.leshan.core.node.codec.LwM2mNodeDecoder;
import org.eclipse.leshan.core.util.SecurityUtil;
import org.eclipse.leshan.server.californium.LeshanServer;
import org.eclipse.leshan.server.californium.LeshanServerBuilder;
import org.eclipse.leshan.server.model.LwM2mModelProvider;
import org.eclipse.leshan.server.model.VersionedModelProvider;
import org.eclipse.leshan.server.redis.RedisRegistrationStore;
import org.eclipse.leshan.server.redis.RedisSecurityStore;
import org.eclipse.leshan.server.security.EditableSecurityStore;
import org.eclipse.leshan.server.security.FileSecurityStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.commands.JedisCommands;
import redis.clients.jedis.util.Pool;

public class LeshanServerController {

  private static final Logger LOG = LoggerFactory.getLogger(LeshanServerController.class);

  private Server server;
  private LeshanServer lwServer;

  private LeshanServerController(final ServerOptions serverOptions)
      throws URISyntaxException, UnrecoverableKeyException, NoSuchAlgorithmException, CertificateException,
      UnknownHostException, IOException {
    createServer(serverOptions);
  }

  private void createServer(final ServerOptions serverOptions) throws URISyntaxException, UnrecoverableKeyException,
      NoSuchAlgorithmException, CertificateException, UnknownHostException, IOException {
    // Prepare LWM2M server
    final LeshanServerBuilder builder = new LeshanServerBuilder();
    builder.setEncoder(new DefaultLwM2mNodeEncoder());
    final LwM2mNodeDecoder decoder = new DefaultLwM2mNodeDecoder();
    builder.setDecoder(decoder);

    // Create CoAP Config
    NetworkConfig coapConfig;
    final File configFile = new File(NetworkConfig.DEFAULT_FILE_NAME);
    if (configFile.isFile()) {
      coapConfig = new NetworkConfig();
      coapConfig.load(configFile);
    } else {
      coapConfig = LeshanServerBuilder.createDefaultNetworkConfig();
      coapConfig.store(configFile);
    }
    builder.setCoapConfig(coapConfig);

    // ports from CoAP Config if needed
    builder.setLocalAddress(serverOptions.getLocalAddress(),
        serverOptions.getLocalPort() == null ? coapConfig.getInt(Keys.COAP_PORT, LwM2m.DEFAULT_COAP_PORT)
            : serverOptions.getLocalPort());
    builder.setLocalSecureAddress(serverOptions.getSecureLocalAddress(),
        serverOptions.getSecureLocalPort() == null
            ? coapConfig.getInt(Keys.COAP_SECURE_PORT, LwM2m.DEFAULT_COAP_SECURE_PORT)
            : serverOptions.getSecureLocalPort());

    // Connect to redis if needed
    Pool<Jedis> jedis = null;
    if (serverOptions.getRedisUrl() != null) {
      jedis = new JedisPool(new URI(serverOptions.getRedisUrl()));
    }

    // Create DTLS Config
    final DtlsConnectorConfig.Builder dtlsConfig = new DtlsConnectorConfig.Builder();
    dtlsConfig.setRecommendedCipherSuitesOnly(!serverOptions.isSupportDeprecatedCiphers());

    X509Certificate serverCertificate = null;
    if (serverOptions.getCertificate() != null) {
      // use X509 mode ( + RPK)
      serverCertificate = serverOptions.getCertificate();
      builder.setPrivateKey(serverOptions.getPrivateKey());
      builder.setCertificateChain(new X509Certificate[] { serverCertificate });
    } else if (serverOptions.getPublicKey() != null) {
      // use RPK only
      builder.setPublicKey(serverOptions.getPublicKey());
      builder.setPrivateKey(serverOptions.getPrivateKey());
    } else if (serverOptions.getKeyStorePath() != null) {
      LOG.warn(
          "Keystore way [-ks, -ksp, -kst, -ksa, -ksap] is DEPRECATED for leshan demo and will probably be removed soon, please use [-cert, -prik, -truststore] options");

      // Deprecated way : Set up X.509 mode (+ RPK)
      try {
        final KeyStore keyStore = KeyStore.getInstance(serverOptions.getKeyStoreType());
        try (FileInputStream fis = new FileInputStream(serverOptions.getKeyStorePath())) {
          keyStore.load(fis,
              serverOptions.getKeyStorePass() == null ? null : serverOptions.getKeyStorePass().toCharArray());
          final List<Certificate> trustedCertificates = new ArrayList<>();
          for (final Enumeration<String> aliases = keyStore.aliases(); aliases.hasMoreElements();) {
            final String alias = aliases.nextElement();
            if (keyStore.isCertificateEntry(alias)) {
              trustedCertificates.add(keyStore.getCertificate(alias));
            } else if (keyStore.isKeyEntry(alias) && alias.equals(serverOptions.getKeyStoreAlias())) {
              final List<X509Certificate> x509CertificateChain = new ArrayList<>();
              final Certificate[] certificateChain = keyStore.getCertificateChain(alias);
              if (certificateChain == null || certificateChain.length == 0) {
                LOG.error("Keystore alias must have a non-empty chain of X509Certificates.");
                System.exit(-1);
              }

              for (final Certificate cert : certificateChain) {
                if (!(cert instanceof X509Certificate)) {
                  LOG.error("Non-X.509 certificate in alias chain is not supported: {}", cert);
                  System.exit(-1);
                }
                x509CertificateChain.add((X509Certificate) cert);
              }

              final Key key = keyStore.getKey(alias, serverOptions.getKeyStoreAliasPass() == null ? new char[0]
                  : serverOptions.getKeyStoreAliasPass().toCharArray());
              if (!(key instanceof PrivateKey)) {
                LOG.error("Keystore alias must have a PrivateKey entry, was {}",
                    key == null ? null : key.getClass().getName());
                System.exit(-1);
              }
              builder.setPrivateKey((PrivateKey) key);
              serverCertificate = (X509Certificate) keyStore.getCertificate(alias);
              builder
                  .setCertificateChain(x509CertificateChain.toArray(new X509Certificate[x509CertificateChain.size()]));
            }
          }
          builder.setTrustedCertificates(trustedCertificates.toArray(new Certificate[trustedCertificates.size()]));
        }
      } catch (KeyStoreException | IOException e) {
        LOG.error("Unable to initialize X.509.", e);
        System.exit(-1);
      }

    }

    if (serverOptions.getPublicKey() == null && serverCertificate == null) {
      // public key or server certificated is not defined
      // use default embedded credentials (X.509 + RPK mode)
      try {
          PrivateKey embeddedPrivateKey = SecurityUtil.privateKey
                  .readFromResource("credentials/server_privkey.der");
          serverCertificate = SecurityUtil.certificate.readFromResource("credentials/server_cert.der");
          builder.setPrivateKey(embeddedPrivateKey);
          builder.setCertificateChain(new X509Certificate[] { serverCertificate });
      } catch (Exception e) {
          LOG.error("Unable to load embedded X.509 certificate.", e);
          System.exit(-1);
      }
  }


    // Define trust store
    if (serverCertificate != null && serverOptions.getKeyStorePath() == null) {
      if (serverOptions.getTrustStore() != null && !serverOptions.getTrustStore().isEmpty()) {
        builder.setTrustedCertificates(serverOptions.getTrustStore().toArray(new Certificate[serverOptions.getTrustStore().size()]));
      } else {
        // by default trust all
        builder.setTrustedCertificates(new X509Certificate[0]);
      }
    }

    // Set DTLS Config
    builder.setDtlsConfig(dtlsConfig);

    // Define model provider
    List<ObjectModel> models = ObjectLoader.loadDefault();
    models.addAll(ObjectLoader.loadDdfResources("/models/", LwM2mDemoConstant.modelPaths));
    if (serverOptions.getModelsFolderPath() != null) {
        models.addAll(ObjectLoader.loadObjectsFromDir(new File(serverOptions.getModelsFolderPath())));
    }
    LwM2mModelProvider modelProvider = new VersionedModelProvider(models);
    builder.setObjectModelProvider(modelProvider);


    // Set securityStore & registrationStore
    EditableSecurityStore securityStore;
    if (jedis == null) {
        // use file persistence
        securityStore = new FileSecurityStore();
    } else {
        // use Redis Store
        securityStore = new RedisSecurityStore(jedis);
        builder.setRegistrationStore(new RedisRegistrationStore(jedis));
    }
    builder.setSecurityStore(securityStore);

    // use a magic converter to support bad type send by the UI
    builder.setEncoder(new DefaultLwM2mNodeEncoder(new MagicLwM2mValueConverter()));

    // Create and start LWM2M server
    final LeshanServer lwServer = builder.build();

    // Now prepare Jetty
    InetSocketAddress jettyAddr;
    if (serverOptions.getWebAddress() == null) {
      jettyAddr = new InetSocketAddress(serverOptions.getWebPort());
    } else {
      jettyAddr = new InetSocketAddress(serverOptions.getWebAddress(), serverOptions.getWebPort());
    }
    final Server server = new Server(jettyAddr);
    final WebAppContext root = new WebAppContext();
    root.setContextPath("/");
    root.setResourceBase(LeshanServerDemo.class.getClassLoader().getResource("webapp").toExternalForm());
    root.setParentLoaderPriority(true);
    server.setHandler(root);

    // Create Servlet
    EventServlet eventServlet = new EventServlet(lwServer, lwServer.getSecuredAddress().getPort());
    ServletHolder eventServletHolder = new ServletHolder(eventServlet);
    root.addServlet(eventServletHolder, "/event/*");

    ServletHolder clientServletHolder = new ServletHolder(new ClientServlet(lwServer));
    root.addServlet(clientServletHolder, "/api/clients/*");

    ServletHolder securityServletHolder;
    if (serverOptions.getPublicKey() != null) {
        securityServletHolder = new ServletHolder(new SecurityServlet(securityStore, serverOptions.getPublicKey()));
    } else {
        securityServletHolder = new ServletHolder(new SecurityServlet(securityStore, serverCertificate));
    }
    root.addServlet(securityServletHolder, "/api/security/*");

    ServletHolder objectSpecServletHolder = new ServletHolder(
            new ObjectSpecServlet(lwServer.getModelProvider(), lwServer.getRegistrationService()));
    root.addServlet(objectSpecServletHolder, "/api/objectspecs/*");

    // Register a service to DNS-SD
    if (serverOptions.getPublishDNSSdServices()) {
      // Create a JmDNS instance
      JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());

      // Publish Leshan HTTP Service
      ServiceInfo httpServiceInfo = ServiceInfo.create("_http._tcp.local", "leshan", serverOptions.getWebPort(), "");
      jmdns.registerService(httpServiceInfo);

      // Publish LeshanCoAP Service
      ServiceInfo coapServiceInfo = ServiceInfo.create("_coap._udp.local.", "leshan", serverOptions.getLocalPort(), "");
      jmdns.registerService(coapServiceInfo);

      // Publish Leshan Secure CoAP Service
      ServiceInfo coapSecureServiceInfo = ServiceInfo.create("_coap._udp.local", "leshan", serverOptions.getSecureLocalPort(), "");
      jmdns.registerService(coapSecureServiceInfo);

    }
  }

  public void start() throws Exception {
    // Start Jetty & Leshan
    lwServer.start();
    server.start();
    LOG.info("Web Server started at {}.", server.getURI());
  }

  public static final LeshanServerController createWith(final ServerOptions options)
      throws UnrecoverableKeyException, NoSuchAlgorithmException, URISyntaxException, CertificateException,
      UnknownHostException, IOException {
    return new LeshanServerController(options);
  }
}
