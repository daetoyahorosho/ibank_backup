package org.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Delete {

    static void deleteSchema() throws SQLException {

        //Знаю, что хардкод пути к файлу - исправлю!
        Config config = new Config("C:\\Users\\danil\\IdeaProjects\\ibank_backup\\src\\main\\java\\org\\example\\config.properties");

        try (Connection conn = DriverManager.getConnection(config.getDbUrl(), config.getDbUser(), config.getDbPassword()); Statement stmt = conn.createStatement()) {


            System.out.println("Подключение установлено!");

            String deleteSchemeCascade = "DROP SCHEMA IF EXISTS " + config.getDbSchema() + " CASCADE";
            stmt.executeUpdate(deleteSchemeCascade);

            System.out.println("Схема " + config.getDbSchema() + " успешно удалена каскадно.");
        }
    }
}
