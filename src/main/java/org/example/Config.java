package org.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

class Config {

    private static Config instance;
    private Properties properties = new Properties();

    private Config(String filePath) {
        try (FileInputStream inputStream = new FileInputStream(filePath)) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Config getInstance(String filePath) {
        if (instance == null) {
            instance = new Config(filePath);
        }
        return instance;
    }


    public String getDbUrl() {
        return properties.getProperty("db.url");
    }

    public String getDbUser() {
        return properties.getProperty("db.user");
    }

    public String getDbPassword() {
        return properties.getProperty("db.password");
    }

    public String getDbSchema() {
        return properties.getProperty("db.schema");
    }

    public String getPathPgRestore() {
        return properties.getProperty("pathPgRestore");
    }

    public String getDumpFileDb() {
        return properties.getProperty("dumpFileDb");
    }

    public String getIbankFolder() {
        return properties.getProperty("ibankFolder");
    }

    public String getBackupIbankFolder() {
        return properties.getProperty("backupIbankFolder");
    }

    public String getDbName() {
        return properties.getProperty("db.name");
    }
}
