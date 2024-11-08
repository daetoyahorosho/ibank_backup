package org.example;

import java.nio.file.Path;
import java.sql.SQLException;
import java.util.Scanner;

public class Start {

    public static void main(String[] args) {

        //Начало работы программы
        try (Scanner scanner = new Scanner(System.in)) {

            // Создание экземпляра класса FolderTransfer и вывод информации пользователю
            FolderTransfer.systemMessage();

            String userInput = scanner.nextLine();

            if (userInput.equalsIgnoreCase("Yes")) {
                // Удаление схемы БД PostgreSQL
                try {
                    Delete.deleteSchema();
                } catch (SQLException e) {
                    System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Восстанавливаем БД из файла с помощью pg_restore
                try {
                    Restore.pgRestoreDb();
                } catch (RuntimeException e) {
                    System.err.println("Ошибка восстановления базы данных: " + e.getMessage());
                    e.printStackTrace();
                }

                // Удаляем папку с сервером ibank
                try {
                    FolderTransfer.deleteFolder(Path.of(Config.getInstance().getIbankFolder()));
                } catch (RuntimeException e) {
                    System.err.println("Ошибка при удалении папки ibank: " + e.getMessage());
                    e.printStackTrace();
                }

                // Копируем папку из бэкапа на место удаленной папки
                try {
                    FolderTransfer.copyFolder(Path.of(Config.getInstance().getBackupIbankFolder()), Path.of(Config.getInstance().getIbankFolder()));
                } catch (RuntimeException e) {
                    System.err.println("Ошибка при копировании папки: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                System.out.println("До новых встреч!");
            }
        }
    }

}
