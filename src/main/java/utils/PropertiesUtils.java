package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtils {
    private static final Map<String, Properties> propertiesCache = new HashMap<>();

    public static String get(String key) {
        return get("config", key);
    }

    public static String get(String propertiesFile, String key) {
        Properties props = getProperties(propertiesFile);
        return props.getProperty(key);
    }

    private static Properties getProperties(String propertiesFile) {
        // Check if properties are already loaded in cache
        if (propertiesCache.containsKey(propertiesFile)) {
            return propertiesCache.get(propertiesFile);
        }

        // Load properties from file
        Properties props = new Properties();
        try (InputStream input = PropertiesUtils.class.getClassLoader().getResourceAsStream(propertiesFile + ".properties")) {
            if (input == null) {
                throw new RuntimeException(propertiesFile + ".properties not found in classpath");
            }
            props.load(input);
            // Cache the loaded properties
            propertiesCache.put(propertiesFile, props);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load " + propertiesFile + ".properties", e);
        }
        return props;
    }
}
