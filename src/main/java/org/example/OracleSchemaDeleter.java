package org.example;

import java.sql.*;
import java.text.MessageFormat;

public class OracleSchemaDeleter implements SchemaDeleter{

    private static String CHECK_SCHEMA_QUERY = "SELECT username FROM all_users WHERE username = ''{0}''";
    private static String DROP_CHEMA = "DROP USER {0} CASCADE";

    /**
     * Метод для удаления схемы и пользователя БД
     * @throws SQLException
     */
    @Override
    public void deleteSchema() throws SQLException {

        try (Connection conn = DriverManager.getConnection(Config.getInstance().getDbUrl(), Config.getInstance().getDbUser(), Config.getInstance().getDbPassword());
             Statement stmt = conn.createStatement()) {

            System.out.println("Подключение к Oracle установлено!");

            String checkSchemaExists = MessageFormat.format(CHECK_SCHEMA_QUERY, Config.getInstance().getDbSchema());
            ResultSet resultSet = stmt.executeQuery(checkSchemaExists);

            if (resultSet.next()) {
                String deleteSchemeCascade = MessageFormat.format(DROP_CHEMA, Config.getInstance().getDbSchema());
                stmt.executeUpdate(deleteSchemeCascade);
                System.out.println("Схема и пользователь " + Config.getInstance().getDbSchema() + " успешно удалена каскадно.");
            } else {
                System.out.println("Схема или пользователь " + Config.getInstance().getDbSchema() + " отсутствует");
            }
        }
    }
}
