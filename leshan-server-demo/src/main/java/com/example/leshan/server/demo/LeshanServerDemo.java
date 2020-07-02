package com.example.leshan.server.demo;

import java.io.File;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.security.cert.Certificate;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import com.example.leshan.server.demo.ServerOptions.ServerOptionsBuilder;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.leshan.core.LwM2m;
import org.eclipse.leshan.core.node.codec.DefaultLwM2mNodeDecoder;
import org.eclipse.leshan.core.node.codec.DefaultLwM2mNodeEncoder;
import org.eclipse.leshan.core.node.codec.LwM2mNodeDecoder;
import org.eclipse.leshan.core.util.SecurityUtil;
import org.eclipse.leshan.server.californium.LeshanServer;
import org.eclipse.leshan.server.californium.LeshanServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LeshanServerDemo {

  static {
    // Define a default logback.configurationFile
    String property = System.getProperty("logback.configurationFile");
    if (property == null) {
      System.setProperty("logback.configurationFile", "logback-config.xml");
    }
  }

  private static final Logger LOG = LoggerFactory.getLogger(LeshanServerDemo.class);

  private static final String USAGE = "java -jar leshan-server.demo.java [OPTION]\n\n";

  private static final String DEFAULT_KEYSTORE_TYPE = KeyStore.getDefaultType();

  private static final String DEFAULT_KEYSTORE_ALIAS = "leshan";

  public static void main(String[] args) {
    // Define options for command line tools
    Options options = defineOptions();

    HelpFormatter formatter = new HelpFormatter();
    formatter.setWidth(120);
    formatter.setOptionComparator(null);

    // Parse argument
    ServerOptionsBuilder serverOptionsBuilder = ServerOptionsBuilder.builder();
    CommandLine cl;
    try {
      cl = new DefaultParser().parse(options, args);
    } catch (ParseException e) {
      System.err.println("Parsing failed. Reason: " + e.getMessage());
      formatter.printHelp(USAGE, options);
      return;
    }

    // Print help
    if (cl.hasOption("help")) {
      formatter.printHelp(USAGE, options);
      return;
    }

    // About if unexpected options
    if (cl.getArgs().length > 0) {
      System.err.println("Unexpected option or arguments :" + cl.getArgList());
      formatter.printHelp(USAGE, options);
      return;
    }

    // Abort if all RPK config is not complete
    boolean rpkConfig = false;
    if (!cl.hasOption("pubk")) {
      if (!cl.hasOption("prik")) {
        System.err.println("pubk, prik should be used together to connect using RPK");
      } else {
        rpkConfig = true;
      }
    }

    // Abort if all X509 config is not complete
    boolean x509Config = false;
    if (cl.hasOption("cert")) {
      if (!cl.hasOption("prik")) {
        System.err.println("cert, prik should be used together to connect using X509");
        formatter.printHelp(USAGE, options);
        return;
      } else {
        x509Config = true;
      }
    }

    // Abort if prik is used without complete RPK or X509 config
    if (cl.hasOption("prik")) {
      if (!rpkConfig && !x509Config) {
        System.err.println("print should be used with cert for X509 config OR pubk for RPK config");
        formatter.printHelp(USAGE, options);
        return ;
      }
    }

    // get local address
    String localPortOption = cl.getOptionValue("lp");
    Integer localPort = null;
    if (localPortOption != null) {
        localPort = Integer.parseInt(localPortOption);
    }
    serverOptionsBuilder.localPortIs(localPort);
    serverOptionsBuilder.localAddressIs(cl.getOptionValue("lh"));

    // get secure local address
    String secureLocalPortOption = cl.getOptionValue("slp");
    Integer secureLocalPort = null;
    if (secureLocalPortOption != null) {
        secureLocalPort = Integer.parseInt(secureLocalPortOption);
    }
    serverOptionsBuilder.secureLocalPortIs(secureLocalPort);
    serverOptionsBuilder.secureLocalAddressIs(cl.getOptionValue("slh"));


    // get http address
    serverOptionsBuilder.webAddressIs(cl.getOptionValue("wh"));
    String webPortOption = cl.getOptionValue("wp");
    int webPort = 8080;
    if (webPortOption != null) {
      webPort = Integer.parseInt(webPortOption);
    }
    serverOptionsBuilder.webPortIs(webPort);

    // Get models folder
    serverOptionsBuilder.modelsFolderPathIs(cl.getOptionValue("m"));

    // get the Redis hostname:port
    serverOptionsBuilder.redisUrlIs(cl.getOptionValue("r"));

    // get RPK info
    PublicKey publicKey = null;
    PrivateKey privateKey = null;
    if (rpkConfig) {
      try {
        serverOptionsBuilder.privateKeyIs(SecurityUtil.privateKey.readFromFile(cl.getOptionValue("prik")));
        serverOptionsBuilder.publicKeyIs(SecurityUtil.publicKey.readFromFile(cl.getOptionValue("pubk")));
      } catch (Exception e) {
        System.err.println("Unable to load RPK files : " + e.getMessage());
        e.printStackTrace();
        formatter.printHelp(USAGE, options);
        return ;
      }
    }


    // get X509 info
    X509Certificate certificate = null;
    if (cl.hasOption("cert")) {
      try {
        serverOptionsBuilder.privateKeyIs(SecurityUtil.privateKey.readFromFile(cl.getOptionValue("prik")));
        serverOptionsBuilder.publicKeyIs(SecurityUtil.publicKey.readFromFile(cl.getOptionValue("cert")));
      } catch (Exception e) {
        System.err.println("Unable to load X509 files : " + e.getMessage());
        e.printStackTrace();
        formatter.printHelp(USAGE, options);
        return ;
      }
    }


    // get X509 info
    List<Certificate> trustStore = null;
    if (cl.hasOption("truststore") {
      trustStore = new ArrayList<>();
      File input = new File(cl.getOptionValue("truststore"));

      // check input exists
      File[] files;
      if (!input.exists()) {
        files = input.listFiles();
      } else {
        files = new File[] { input };
      }
      for (File file : files) {
        try {
          trustStore.add(SecurityUtil.certificate.readFromFile(file.getAbsolutePath()));
        } catch (Exception e) {
          LOG.warn("Unable to load X509 files {}:{}", file.getAbsolutePath(), e.getMessage());
        }
      }
    }

    // Get keystore parameters
    serverOptionsBuilder.keyStorePathIs(cl.getOptionValue("ks"));
    serverOptionsBuilder.keyStoreTypeIs(cl.getOptionValue("kst", KeyStore.getDefaultType()));
    serverOptionsBuilder.keyStorePassIs(cl.getOptionValue("ksp"));
    serverOptionsBuilder.keyStoreAliasIs(cl.getOptionValue("ksa"));
    serverOptionsBuilder.keyStoreAliasPassIs(cl.getOptionValue("ksap"));

    // Get mDNS publish switch
    serverOptionsBuilder.publishDNSSdServicesIs(cl.hasOption("mdns"));

    serverOptionsBuilder.supportDeprecatedCiphersIs(cl.hasOption("oc"));


    try {
      LeshanServerController.createWith(serverOptionsBuilder.build()).start();
    } catch (BindException e) {
      System.err
          .println(String.format("Web port %s is already used, you colud change it using 'webport' option", webPort));
    } catch (Exception e) {
      LOG.error("Jetty stopped with unexpected error ...", e);
    }
  }

  private static final Options defineOptions() {
    Options options = new Options();

    final StringBuilder RPKChapter = new StringBuilder();
    RPKChapter.append("\n .");
    RPKChapter.append("\n .");
    RPKChapter.append("\n================================[ RPK ]=================================");
    RPKChapter.append("\n| By default Leshan demo uses an embedded self-signed certificate and  |");
    RPKChapter.append("\n| trusts any client certificates allowing to use RPK or X509           |");
    RPKChapter.append("\n| at client side.                                                      |");
    RPKChapter.append("\n| To use RPK only with your own keys :                                 |");
    RPKChapter.append("\n|            -pubk -prik options should be used together.              |");
    RPKChapter.append("\n| To get helps about files format and how to generate it, see :        |");
    RPKChapter.append("\n| See https://github.com/eclipse/leshan/wiki/Credential-files-format   |");
    RPKChapter.append("\n------------------------------------------------------------------------");

    final StringBuilder X509Chapter = new StringBuilder();
    X509Chapter.append("\n .");
    X509Chapter.append("\n .");
    X509Chapter.append("\n===============================[ X509 ]=================================");
    X509Chapter.append("\n| By default Leshan demo uses an embedded self-signed certificate and  |");
    X509Chapter.append("\n| trusts any client certificates allowing to use RPK or X509           |");
    X509Chapter.append("\n| at client side.                                                      |");
    X509Chapter.append("\n| To use X509 with your own server key, certificate and truststore :   |");
    X509Chapter.append("\n|               [-cert, -prik], [-truststore] should be used together  |");
    X509Chapter.append("\n| To get helps about files format and how to generate it, see :        |");
    X509Chapter.append("\n| See https://github.com/eclipse/leshan/wiki/Credential-files-format   |");
    X509Chapter.append("\n------------------------------------------------------------------------");

    final StringBuilder X509ChapterDeprecated = new StringBuilder();
    X509ChapterDeprecated.append("\n .");
    X509ChapterDeprecated.append("\n .");
    X509ChapterDeprecated.append("\n=======================[ X509 deprecated way]===========================");
    X509ChapterDeprecated.append("\n| By default Leshan demo uses an embedded self-signed certificate and  |");
    X509ChapterDeprecated.append("\n| trusts any client certificates allowing to use RPK or X509           |");
    X509ChapterDeprecated.append("\n| at client side.                                                      |");
    X509ChapterDeprecated.append("\n| If you want to use your own server keys, certificates and truststore,|");
    X509ChapterDeprecated.append("\n| you can provide a keystore using :                                   |");
    X509ChapterDeprecated.append("\n|         -ks, -ksp, [-kst], [-ksa], -ksap should be used together     |");
    X509ChapterDeprecated.append("\n| To get helps about files format and how to generate it, see :        |");
    X509ChapterDeprecated.append("\n| See https://github.com/eclipse/leshan/wiki/Credential-files-format   |");
    X509ChapterDeprecated.append("\n------------------------------------------------------------------------");

    options.addOption("h", "help", false, "Display help information");
    options.addOption("h", "help", false, "Display help information.");
    options.addOption("lh", "coaphost", true, "Set the local CoAP address.\n  Default: any local address.");
    options.addOption("lp", "coapport", true,
        String.format("Set the local CoAP port.\n  Default: %d.", LwM2m.DEFAULT_COAP_PORT));
    options.addOption("slh", "coapshost", true, "Set the secure local CoAP address.\nDefault: any local address.");
    options.addOption("slp", "coapsport", true,
        String.format("Set the secure local CoAP port.\nDefault: %d.", LwM2m.DEFAULT_COAP_SECURE_PORT));
    options.addOption("wh", "webhost", true, "Set the HTTP address for web server.\nDefault: any local address.");
    options.addOption("wp", "webport", true, "Set the HTTP port for web server.\nDefault: 8080.");
    options.addOption("m", "modelsfolder", true, "A folder which contains object models in OMA DDF(.xml) format.");
    options.addOption("oc", "activate support of old/deprecated cipher suites.");
    options.addOption("r", "redis", true,
        "Use redis to store registration and securityInfo. \nThe URL of the redis server should be given using this format : 'redis://:password@hostname:port/db_number'\nExample without DB and password: 'redis://localhost:6379'\nDefault: redis is not used.");
    options.addOption("mdns", "publishDNSSdServices", false,
        "Publish leshan's services to DNS Service discovery" + RPKChapter);
    options.addOption("pubk", true,
        "The path to your server public key file.\n The public Key should be in SubjectPublicKeyInfo format (DER encoding).");
    options.addOption("prik", true,
        "The path to your server private key file.\nThe private key should be in PKCS#8 format (DER encoding)."
            + X509Chapter);
    options.addOption("cert", true,
        "The path to your server certificate file.\n"
            + "The certificate Common Name (CN) should generally be equal to the server hostname.\n"
            + "The certificate should be in X509v3 format (DER encoding).");
    options.addOption("truststore", true,
        "The path to a root certificate file to trust or a folder containing all the trusted certificates in X509v3 format (DER encoding).\n Default: All certificates are trusted which is only OK for a demo."
            + X509ChapterDeprecated);
    options.addOption("ks", "keystore", true,
        "Set the key store file.\nIf set, X.509 mode is enabled, otherwise built-in RPK credentials are used.");
    options.addOption("ksp", "storepass", true, "Set the key store password.");
    options.addOption("kst", "storetype", true,
        String.format("Set the key store type.\nDefault: %s.", DEFAULT_KEYSTORE_TYPE));
    options.addOption("ksa", "alias", true, String.format(
        "Set the key store alias to use for server credentials.\nDefault: %s.\n All other alias referencing a certificate will be trusted.",
        DEFAULT_KEYSTORE_ALIAS));
    options.addOption("ksap", "keypass", true, "Set the key store alias password to use.");
    return options;
  }

}
