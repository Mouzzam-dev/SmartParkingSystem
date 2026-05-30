package util;

import java.io.*;
import java.util.Properties;

public class ConfigLoader {
    private static final String CONFIG_FILE = "config.txt";

    // Safe fallback defaults — program never crashes if config is missing
    private static final int    DEFAULT_TOTAL_SLOTS  = 10;
    private static final double DEFAULT_RATE_PER_HOUR = 50.0;
    private static final String DEFAULT_ADMIN_PASSWORD = "admin123";

    private int    totalSlots;
    private double ratePerHour;
    private String adminPassword;

    public ConfigLoader() {
        loadConfig();
    }

    private void loadConfig() {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            props.load(fis);
            totalSlots    = Integer.parseInt(props.getProperty("total_slots",
                                             String.valueOf(DEFAULT_TOTAL_SLOTS)));
            ratePerHour   = Double.parseDouble(props.getProperty("rate_per_hour",
                                             String.valueOf(DEFAULT_RATE_PER_HOUR)));
            adminPassword = props.getProperty("admin_password", DEFAULT_ADMIN_PASSWORD);
            System.out.println("[CONFIG] Settings loaded from " + CONFIG_FILE);

        } catch (FileNotFoundException e) {
            // Graceful degradation — use hardcoded safe fallbacks
            System.out.println("[CONFIG] config.txt not found. Using default settings.");
            applyDefaults();
        } catch (IOException e) {
            System.out.println("[CONFIG] Error reading config. Using default settings.");
            applyDefaults();
        } catch (NumberFormatException e) {
            System.out.println("[CONFIG] Invalid value in config. Using default settings.");
            applyDefaults();
        }
    }

    private void applyDefaults() {
        totalSlots    = DEFAULT_TOTAL_SLOTS;
        ratePerHour   = DEFAULT_RATE_PER_HOUR;
        adminPassword = DEFAULT_ADMIN_PASSWORD;
    }

    public int    getTotalSlots()    { return totalSlots; }
    public double getRatePerHour()   { return ratePerHour; }
    public String getAdminPassword() { return adminPassword; }
}
