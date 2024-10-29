import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class Delete {

    private static String url = "jdbc:postgresql://127.0.0.1:5432/ibank";
    private static String userPostgres = "postgres";
    private static String passwordPostgres = "123123";
    private static String schemePostgres = "ibanktest";

    public static void main(String[] args) {

        // https://docs.oracle.com/javase/8/docs/api/java/sql/package-summary.html#package.description
        // auto java.sql.Driver discovery -- no longer need to load a java.sql.Driver class via Class.forName

        // register JDBC driver, optional, since java 1.6
        /*try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/


        // auto close connection
        try (Connection conn = DriverManager.getConnection(url, userPostgres, passwordPostgres); Statement stmt = conn.createStatement()) {

            if (conn != null) {
                System.out.println("Подключение установлено!");
            } else {
                System.out.println("Во время подключения произошла ошибка!");
            }

            String deleteSchemeCascade = "DROP SCHEMA IF EXISTS " + schemePostgres + " CASCADE";
            stmt.executeUpdate(deleteSchemeCascade);

            System.out.println("Схема " + schemePostgres + " успешно удалена каскадно.");

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}


