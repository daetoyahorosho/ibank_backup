package org.example;

import java.sql.*;
import java.text.MessageFormat;
import java.util.Objects;

public class Delete {

    private static String CHECK_SCHEMA_QUERY = "SELECT schema_name FROM information_schema.schemata WHERE schema_name = ''{0}''";
    private static String DROP_CHEMA = "DROP SCHEMA IF EXISTS {0} CASCADE";

    // Приватный конструктор
    private Delete() {
    }

    static void deleteSchema() throws SQLException {

        Config config = Config.getInstance("src/main/resources/config.properties");

        try (Connection conn = DriverManager.getConnection(config.getDbUrl(), config.getDbUser(), config.getDbPassword());
             Statement stmt = conn.createStatement()) {

            System.out.println("Подключение установлено!");

            // Проверка, существует ли схема
            String checkSchemaExists = MessageFormat.format(CHECK_SCHEMA_QUERY, config.getDbSchema());
            ResultSet resultSet = stmt.executeQuery(checkSchemaExists);

            // Переходим к первой строке результата
            if (resultSet.next()) {
                // Схема существует, удаляем ее
                String deleteSchemeCascade = MessageFormat.format(DROP_CHEMA, config.getDbSchema());
                stmt.executeUpdate(deleteSchemeCascade);
                System.out.println("Схема " + config.getDbSchema() + " успешно удалена каскадно.");
            } else {
                System.out.println("Схема " + config.getDbSchema() + " отсутствует");
            }
        }
    }

    public static void main(String[] args) throws SQLException {
        deleteSchema();
    }
}
