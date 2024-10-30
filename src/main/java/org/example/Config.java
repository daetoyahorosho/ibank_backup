package org.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

class Config {

    private Properties properties = new Properties();

    public Config(String filePath) {
        try (FileInputStream inputStream = new FileInputStream(filePath)) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //Геттеры
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

    public String getPathPgRestore () {

        return properties.getProperty("pathPgRestore");

    }

    public String getDumpFileDb () {

        return properties.getProperty("dumpFileDb");

    }

}
