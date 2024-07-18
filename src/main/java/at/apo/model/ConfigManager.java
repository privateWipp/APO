package at.apo.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
    private static final String CONFIG_FILE = "config.properties";
    private Properties properties;

    public ConfigManager() {
        this.properties = new Properties();
        try (FileInputStream input = new FileInputStream(CONFIG_FILE)) {
            this.properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isLoggedIn() {
        return Boolean.parseBoolean(this.properties.getProperty("loggedin", "false"));
    }

    public void setLoggedIn(boolean loggedIn) {
        this.properties.setProperty("loggedin", Boolean.toString(loggedIn));
        try (FileOutputStream output = new FileOutputStream(CONFIG_FILE)) {
            this.properties.store(output, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
