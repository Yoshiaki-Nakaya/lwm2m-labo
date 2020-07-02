package com.example.leshan.client.demo;

import org.apache.commons.cli.Options;
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
  private static final String USAGE = "java -jarleshan-client-demo-0.0.1-SNAPSHOT-jar-with-dependencies.jar [OPTION]\n\n";

  public static void main(String[] args) {

    System.out.println("Hello");
  }

}
