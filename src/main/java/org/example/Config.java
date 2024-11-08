package org.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

class Config {

    private static Config instance;
    private static String filePath = "src/main/resources/config.properties";
    private Properties properties = new Properties();

    // Статический блок инициализации - выполняется при загрузке класса
    static {
        instance = new Config(filePath);
    }

    // Приватный конструктор, инициализирующий конфигурацию
    private Config(String filePath) {
        try (FileInputStream inputStream = new FileInputStream(filePath)) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для получения инициализированного класса
    public static Config getInstance() {
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

    public String getDbName() {
        return properties.getProperty("db.name");
    }

    public String getDbSchema() {
        return properties.getProperty("db.schema");
    }

    public String getPathPgRestore() {
        return properties.getProperty("path.restore");
    }

    public String getDumpFileDb() {
        return properties.getProperty("path.filedb");
    }

    public String getIbankFolder() {
        return properties.getProperty("path.backup");
    }

    public String getBackupIbankFolder() {
        return properties.getProperty("path.ibank");
    }

}
