package org.example;

import java.sql.*;
import java.text.MessageFormat;

public class PostgresSchemaDeleter implements SchemaDeleter{

    private static String CHECK_SCHEMA_QUERY = "SELECT schema_name FROM information_schema.schemata WHERE schema_name = ''{0}''";
    private static String DROP_CHEMA = "DROP SCHEMA IF EXISTS {0} CASCADE";

    /**
     * Метод для удаления схемы БД
     * @throws SQLException
     */
    @Override
    public void deleteSchema() throws SQLException {

        try (Connection conn = DriverManager.getConnection(Config.getInstance().getDbUrl(), Config.getInstance().getDbUser(), Config.getInstance().getDbPassword());
             Statement stmt = conn.createStatement()) {

            System.out.println("Подключение к PostgreSQL установлено!");

            String checkSchemaExists = MessageFormat.format(CHECK_SCHEMA_QUERY, Config.getInstance().getDbSchema());
            ResultSet resultSet = stmt.executeQuery(checkSchemaExists);

            if (resultSet.next()) {
                String deleteSchemeCascade = MessageFormat.format(DROP_CHEMA, Config.getInstance().getDbSchema());
                stmt.executeUpdate(deleteSchemeCascade);
                System.out.println("Схема " + Config.getInstance().getDbSchema() + " успешно удалена каскадно.");
            } else {
                System.out.println("Схема " + Config.getInstance().getDbSchema() + " отсутствует");
            }
        }
    }
}
