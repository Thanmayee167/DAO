package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
  private PropertiesLoader() {}

  public static Properties loadProperties(String fileName) {
    Properties properties = new Properties();
    try (InputStream input =
        PropertiesLoader.class.getClassLoader().getResourceAsStream(fileName)) {
      if (input == null) {
        System.out.println("Sorry, unable to find " + fileName);
        return null;
      }
      // Load a properties file from class path
      properties.load(input);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return properties;
  }
}
