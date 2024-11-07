package org.example;

import java.sql.*;

public class Delete {

    // Приветаный конструктор
    private Delete() {
    }

    static void deleteSchema() throws SQLException {

        Config config = Config.getInstance("src/main/resources/config.properties");

        try (Connection conn = DriverManager.getConnection(config.getDbUrl(), config.getDbUser(), config.getDbPassword());
             Statement stmt = conn.createStatement()) {

            System.out.println("Подключение установлено!");

            // Проверка, существует ли схема
            String checkSchemaExists = "SELECT schema_name FROM information_schema.schemata WHERE schema_name = '" + config.getDbSchema() + "'";
            ResultSet resultSet = stmt.executeQuery(checkSchemaExists);

            // Переходим к первой строке результата
            if (resultSet.next()) {
                String schemaName = resultSet.getString("schema_name");
                System.out.println("Schema name from database: " + schemaName);

                // Схема существует, удаляем ее
                String deleteSchemeCascade = "DROP SCHEMA IF EXISTS " + config.getDbSchema() + " CASCADE";
                stmt.executeUpdate(deleteSchemeCascade);
                System.out.println("Схема " + config.getDbSchema() + " успешно удалена каскадно.");
            } else {
                System.out.println("Схема " + config.getDbSchema() + " отсутствует");
            }
        }
    }
}
