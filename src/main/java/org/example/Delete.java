package org.example;

import java.sql.*;
import java.text.MessageFormat;
import java.util.Objects;

import static org.example.Config.*;

public class Delete {

    private static String CHECK_SCHEMA_QUERY = "SELECT schema_name FROM information_schema.schemata WHERE schema_name = ''{0}''";
    private static String DROP_CHEMA = "DROP SCHEMA IF EXISTS {0} CASCADE";

    // Приватный конструктор
    private Delete() {
    }

    static void deleteSchema() throws SQLException {

        try (Connection conn = DriverManager.getConnection(Config.getInstance().getDbUrl(), Config.getInstance().getDbUser(), Config.getInstance().getDbPassword());
             Statement stmt = conn.createStatement()) {

            System.out.println("Подключение установлено!");

            // Проверка, существует ли схема
            String checkSchemaExists = MessageFormat.format(CHECK_SCHEMA_QUERY, Config.getInstance().getDbSchema());
            ResultSet resultSet = stmt.executeQuery(checkSchemaExists);

            // Переходим к первой строке результата
            if (resultSet.next()) {
                // Схема существует, удаляем ее
                String deleteSchemeCascade = MessageFormat.format(DROP_CHEMA, Config.getInstance().getDbSchema());
                stmt.executeUpdate(deleteSchemeCascade);
                System.out.println("Схема " + Config.getInstance().getDbSchema() + " успешно удалена каскадно.");
            } else {
                System.out.println("Схема " + Config.getInstance().getDbSchema() + " отсутствует");
            }
        }
    }

    public static void main(String[] args) throws SQLException {
        deleteSchema();
    }

}
