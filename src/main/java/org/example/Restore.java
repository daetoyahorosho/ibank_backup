package org.example;

import java.io.IOException;

public class Restore {

    public static void pgRestoreDb() {

        Config config = new Config("classes/config.properties");

        // Настраиваем ProcessBuilder для выполнения команды
        ProcessBuilder processBuilder = new ProcessBuilder(config.getPathPgRestore(),
                "-U", config.getDbUser(),
                "-d", config.getDbName(),
                "-1",
                config.getDumpFileDb());

        // Устанавливаем переменную окружения для пароля
        processBuilder.environment().put("PGPASSWORD", config.getDbPassword());

        try {
            // Запускаем процесс
            Process process = processBuilder.start();
            int exitCode = process.waitFor(); // Ожидание завершения процесса
            // Проверка результата выполнения
            if (exitCode == 0) {
                System.out.println("База данных успешно восстановлена из файла.");
            } else {
                System.out.println("Ошибка восстановления базы данных. Код выхода: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
