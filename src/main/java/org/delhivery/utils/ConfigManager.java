package org.delhivery.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {

    private static Properties properties;
    private static String environment;
    private static boolean initialized = false;

    private static final String CONFIG_DIR = "src/test/resources";
    private static final String DEFAULT_ENV = "qa";

    private ConfigManager() {}

    public static synchronized void initialize() {
        if (initialized) return;

        properties = new Properties();
        environment = System.getProperty("environment");

        if (environment == null || environment.isEmpty()) {
            try {
                Properties baseProps = new Properties();
                String basePath = System.getProperty("user.dir") + File.separator + CONFIG_DIR + File.separator + "config.properties";
                try (FileInputStream fis = new FileInputStream(basePath)) {
                    baseProps.load(fis);
                    environment = baseProps.getProperty("environment", DEFAULT_ENV);
                }
            } catch (IOException e) {
                System.out.println("Warning: Could not load base config.properties, using default: " + DEFAULT_ENV);
                environment = DEFAULT_ENV;
            }
        }

        String envConfigPath = System.getProperty("user.dir") + File.separator + CONFIG_DIR + File.separator + "config-" + environment + ".properties";
        File envConfigFile = new File(envConfigPath);

        if (envConfigFile.exists()) {
            try (FileInputStream fis = new FileInputStream(envConfigFile)) {
                properties.load(fis);
                System.out.println("Loaded configuration for environment: " + environment);
            } catch (IOException e) {
                throw new RuntimeException("Failed to load environment configuration: config-" + environment + ".properties", e);
            }
        } else {
            System.out.println("Warning: Environment config file not found: " + envConfigPath);
        }

        properties.putAll(System.getProperties());
        initialized = true;
    }

    public static String get(String key) {
        ensureInitialized();
        return properties.getProperty(key);
    }

    public static String get(String key, String defaultValue) {
        ensureInitialized();
        return properties.getProperty(key, defaultValue);
    }

    public static int getInt(String key, int defaultValue) {
        String value = get(key);
        if (value == null) return defaultValue;
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static String getEnvironment() {
        ensureInitialized();
        return environment;
    }

    public static String getBaseUrl() {
        String baseUrl = get("base.url", "");
        if (baseUrl == null || baseUrl.isEmpty()) {
            throw new RuntimeException("Base URL is not set in config-" + environment + ".properties");
        }
        return baseUrl;
    }

    private static void ensureInitialized() {
        if (!initialized) initialize();
    }

    public static synchronized void reset() {
        properties = null;
        environment = null;
        initialized = false;
    }
}
