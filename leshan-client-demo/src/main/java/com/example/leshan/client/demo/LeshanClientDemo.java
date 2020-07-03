package com.example.leshan.client.demo;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.leshan.client.demo.ClientOptions.ClientOptionsBuilder;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.Option.Builder;
import org.eclipse.leshan.core.LwM2m;
import org.eclipse.leshan.core.util.Hex;
import org.eclipse.leshan.core.util.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LeshanClientDemo {

  private static final Logger LOG = LoggerFactory.getLogger(LeshanClientDemo.class);

  // /!\ This field is a COPY of
  // org.eclipse.leshan.server.demo.LeshanServerDemo.modelPaths /!\
  // TODO create a leshan-demo project ?
  public static final String[] modelPaths = new String[] {
      // TODO include file list
  };

  private static final int OBJECT_ID_TEMPERATURE_SENSOR = 3303;
  private static final String DEFAULT_ENDPOINT = "LeshanCLientDemo";
  private static final long DEFAULT_LIFETIME = TimeUnit.MINUTES.toSeconds(5);
  private static final String USAGE = "java -jarleshan-client-demo-0.0.1-SNAPSHOT-jar-with-dependencies.jar [OPTION]\n\n";

  public static void main(final String[] args) {
    Options options = defineCliOptions();

    HelpFormatter formatter = new HelpFormatter();
    formatter.setWidth(90);
    formatter.setOptionComparator(null);

    // Parse Argument
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

    // Abort if unexpected options
    if (cl.getArgs().length > 0) {
      System.err.println("Unexpected option or arguments : " + cl.getArgList());
      formatter.printHelp(USAGE, options);
      return;
    }

    // Abort if PSK config is not complete
    if ((cl.hasOption("i") && !cl.hasOption("p")) || !cl.hasOption("i") && cl.hasOption("p")) {
      System.err.println("You should precise identity (-i) and Pre-Shared-Key (-p) if you want to connect in PSK");
      formatter.printHelp(USAGE, options);
      return;
    }

    // Abort if all RPK config is not complete
    boolean rpkConfig = false;
    if (cl.hasOption("cpubk") || cl.hasOption("spubk")) {
      if (!cl.hasOption("cpubk") || !cl.hasOption("cprik") || !cl.hasOption("spubk")) {
        System.err.println("cpubk, cprik and spubk should be used together to connect using RPK");
        formatter.printHelp(USAGE, options);
        return;
      } else {
        rpkConfig = true;
      }
    }

    // Abort if all X509 config is not complete
    boolean x509config = false;
    if (cl.hasOption("ccert") || cl.hasOption("scert")) {
      if (!cl.hasOption("ccert") || !cl.hasOption("cprik") || !cl.hasOption("scert")) {
        System.err.println("ccert, cprik and scert should be used together to connect using X509");
        formatter.printHelp(USAGE, options);
        return;
      } else {
        x509config = true;
      }
    }

    // Abort if cprik is used without complete RPK or X509 config
    if (cl.hasOption("cprik")) {
      if (!x509config && !rpkConfig) {
        System.err
            .println("cprik should be used with ccert and scert for X509 config OR cpubk and spubk for RPK config");
        formatter.printHelp(USAGE, options);
        return;
      }
    }

    ClientOptionsBuilder optionsBuilder = ClientOptionsBuilder.builder();

    // Get endpoint name
    String endpoint;
    if (cl.hasOption("n")) {
      endpoint = cl.getOptionValue("n");
    } else {
      try {
        endpoint = InetAddress.getLocalHost().getHostName();
      } catch (UnknownHostException e) {
        endpoint = DEFAULT_ENDPOINT;
      }
    }

    // Get lifetime
    long lifetime;
    if (cl.hasOption("l")) {
      lifetime = Long.parseLong(cl.getOptionValue("l"));
    } else {
      lifetime = DEFAULT_LIFETIME;
    }

    // Get lifetime
    Integer communicationPeriod = null;
    if (cl.hasOption("cp")) {
      communicationPeriod = Integer.valueOf(cl.getOptionValue("cp")) * 1000;
    }

    // Get additional attributes
    Map<String, String> additionalAttributes = null;
    if (cl.hasOption("aa")) {
      additionalAttributes = new HashMap<>();
      Pattern p1 = Pattern.compile("(.*)=\"(.*)\"");
      Pattern p2 = Pattern.compile("(.*)=(.*)");
      String[] values = cl.getOptionValues("aa");
      for (String v : values) {
        Matcher m = p1.matcher(v);
        if (m.matches()) {
          String attrName = m.group(1);
          String attrValue = m.group(2);
          additionalAttributes.put(attrName, attrValue);
        } else {
          m = p2.matcher(v);
          if (m.matches()) {
            String attrName = m.group(1);
            String attrValue = m.group(2);
            additionalAttributes.put(attrName, attrValue);
          } else {
            System.err.println(String.format("Invalid syntax for additional attributes : %s", v));
            return;
          }
        }
      }
    }

    // Get server URI
    String serverURI;
    if (cl.hasOption("u")) {
      if (cl.hasOption("i") || cl.hasOption("cpubk"))
        serverURI = "coaps://" + cl.getOptionValue("u");
      else
        serverURI = "coap://" + cl.getOptionValue("u");
    } else {
      if (cl.hasOption("i") || cl.hasOption("cpubk") || cl.hasOption("ccert"))
        serverURI = "coaps://localhost:" + LwM2m.DEFAULT_COAP_SECURE_PORT;
      else
        serverURI = "coap://localhost:" + LwM2m.DEFAULT_COAP_PORT;
    }

    // get PSK info
    byte[] pskIdentity = null;
    byte[] pskKey = null;
    if (cl.hasOption("i")) {
      pskIdentity = cl.getOptionValue("i").getBytes();
      pskKey = Hex.decodeHex(cl.getOptionValue("p").toCharArray());
    }

    // get RPK info
    PublicKey clientPublicKey = null;
    PrivateKey clientPrivateKey = null;
    PublicKey serverPublicKey = null;
    if (cl.hasOption("cpubk")) {
      try {
        clientPrivateKey = SecurityUtil.privateKey.readFromFile(cl.getOptionValue("cprik"));
        clientPublicKey = SecurityUtil.publicKey.readFromFile(cl.getOptionValue("cpubk"));
        serverPublicKey = SecurityUtil.publicKey.readFromFile(cl.getOptionValue("spubk"));
      } catch (Exception e) {
        System.err.println("Unable to load RPK files : " + e.getMessage());
        e.printStackTrace();
        formatter.printHelp(USAGE, options);
        return;
      }
    }

    // get X509 info
    X509Certificate clientCertificate = null;
    X509Certificate serverCertificate = null;
    if (cl.hasOption("ccert")) {
      try {
        clientPrivateKey = SecurityUtil.privateKey.readFromFile(cl.getOptionValue("cprik"));
        clientCertificate = SecurityUtil.certificate.readFromFile(cl.getOptionValue("ccert"));
        serverCertificate = SecurityUtil.certificate.readFromFile(cl.getOptionValue("scert"));
      } catch (Exception e) {
        System.err.println("Unable to load X509 files : " + e.getMessage());
        e.printStackTrace();
        formatter.printHelp(USAGE, options);
        return;
      }
    }

    // get local address
    String localAddress = null;
    int localPort = 0;
    if (cl.hasOption("lh")) {
      localAddress = cl.getOptionValue("lh");
    }
    if (cl.hasOption("lp")) {
      localPort = Integer.parseInt(cl.getOptionValue("lp"));
    }

    Float latitude = null;
    Float longitude = null;
    Float scaleFactor = 1.0f;
    // get initial Location
    if (cl.hasOption("pos")) {
      try {
        String pos = cl.getOptionValue("pos");
        int colon = pos.indexOf(':');
        if (colon == -1 || colon == 0 || colon == pos.length() - 1) {
          System.err.println("Position must be a set of two floats separated by a colon, e.g. 48.131:11.459");
          formatter.printHelp(USAGE, options);
          return;
        }
        latitude = Float.valueOf(pos.substring(0, colon));
        longitude = Float.valueOf(pos.substring(colon + 1));
      } catch (NumberFormatException e) {
        System.err.println("Position must be a set of two floats separated by a colon, e.g. 48.131:11.459");
        formatter.printHelp(USAGE, options);
        return;
      }
    }
    if (cl.hasOption("sf")) {
      try {
        scaleFactor = Float.valueOf(cl.getOptionValue("sf"));
      } catch (NumberFormatException e) {
        System.err.println("Scale factor must be a float, e.g. 1.0 or 0.01");
        formatter.printHelp(USAGE, options);
        return;
      }
    }

    // Get models folder
    String modelsFolderPath = cl.getOptionValue("m");

    try {
      new LeshanClientController().createAndStartClient(optionsBuilder.build());
    } catch (Exception e) {
      System.err.println("Unable to create and start client...");
      e.printStackTrace();
      return;
    }

  }

  private static Options defineCliOptions() {
    Options options = new Options();

    final StringBuilder PSKChapter = new StringBuilder();
    PSKChapter.append("\n .");
    PSKChapter.append("\n .");
    PSKChapter.append("\n ================================[ PSK ]=================================");
    PSKChapter.append("\n | By default Leshan demo use non secure connection.                    |");
    PSKChapter.append("\n | To use PSK, -i and -p options should be used together.               |");
    PSKChapter.append("\n ------------------------------------------------------------------------");

    final StringBuilder RPKChapter = new StringBuilder();
    RPKChapter.append("\n .");
    RPKChapter.append("\n .");
    RPKChapter.append("\n ================================[ RPK ]=================================");
    RPKChapter.append("\n | By default Leshan demo use non secure connection.                    |");
    RPKChapter.append("\n | To use RPK, -cpubk -cprik -spubk options should be used together.    |");
    RPKChapter.append("\n | To get helps about files format and how to generate it, see :        |");
    RPKChapter.append("\n | See https://github.com/eclipse/leshan/wiki/Credential-files-format   |");
    RPKChapter.append("\n ------------------------------------------------------------------------");

    final StringBuilder X509Chapter = new StringBuilder();
    X509Chapter.append("\n .");
    X509Chapter.append("\n .");
    X509Chapter.append("\n ================================[X509]==================================");
    X509Chapter.append("\n | By default Leshan demo use non secure connection.                    |");
    X509Chapter.append("\n | To use X509, -ccert -cprik -scert options should be used together.   |");
    X509Chapter.append("\n | To get helps about files format and how to generate it, see :        |");
    X509Chapter.append("\n | See https://github.com/eclipse/leshan/wiki/Credential-files-format   |");
    X509Chapter.append("\n ------------------------------------------------------------------------");

    options.addOption("h", "help", false, "Display help information.");
    options.addOption("n", true, String
        .format("Set the endpoint name of the Client.\nDefault: the local hostname or '%s' if any.", DEFAULT_ENDPOINT));
    options.addOption("b", false, "If present use bootstrap.");
    options.addOption("l", true, String
        .format("The lifetime in seconds used to register, ignored if -b is used.\n Default : %ds", DEFAULT_LIFETIME));
    options.addOption("cp", true,
        "The communication period in seconds which should be smaller than the lifetime, will be used even if -b is used.");
    options.addOption("lh", true, "Set the local CoAP address of the Client.\n  Default: any local address.");
    options.addOption("lp", true,
        "Set the local CoAP port of the Client.\n  Default: A valid port value is between 0 and 65535.");
    options.addOption("u", true,
        String.format("Set the LWM2M or Bootstrap server URL.\nDefault: localhost:%d.", LwM2m.DEFAULT_COAP_PORT));
    options.addOption("r", false, "Force reconnect/rehandshake on update.");
    options.addOption("f", false, "Do not try to resume session always, do a full handshake.");
    options.addOption("ocf",
        "activate support of old/unofficial content format .\n See https://github.com/eclipse/leshan/pull/720");
    options.addOption("oc", "activate support of old/deprecated cipher suites.");
    Builder aa = Option.builder("aa");
    aa.desc(
        "Use additional attributes at registration time, syntax is \n -aa attrName1=attrValue1 attrName2=\\\"attrValue2\\\" ...");
    aa.hasArgs();
    options.addOption(aa.build());
    options.addOption("m", true, "A folder which contains object models in OMA DDF(.xml)format.");
    options.addOption("pos", true,
        "Set the initial location (latitude, longitude) of the device to be reported by the Location object.\n Format: lat_float:long_float");
    options.addOption("sf", true, "Scale factor to apply when shifting position.\n Default is 1.0." + PSKChapter);
    options.addOption("i", true, "Set the LWM2M or Bootstrap server PSK identity in ascii.");
    options.addOption("p", true, "Set the LWM2M or Bootstrap server Pre-Shared-Key in hexa." + RPKChapter);
    options.addOption("cpubk", true,
        "The path to your client public key file.\n The public Key should be in SubjectPublicKeyInfo format (DER encoding).");
    options.addOption("cprik", true,
        "The path to your client private key file.\nThe private key should be in PKCS#8 format (DER encoding).");
    options.addOption("spubk", true,
        "The path to your server public key file.\n The public Key should be in SubjectPublicKeyInfo format (DER encoding)."
            + X509Chapter);
    options.addOption("ccert", true,
        "The path to your client certificate file.\n The certificate Common Name (CN) should generaly be equal to the client endpoint name (see -n option).\nThe certificate should be in X509v3 format (DER encoding).");
    options.addOption("scert", true,
        "The path to your server certificate file.\n The certificate should be in X509v3 format (DER encoding).");

    return options;
  }
}
