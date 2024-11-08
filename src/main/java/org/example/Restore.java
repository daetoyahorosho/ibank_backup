package org.example;

import java.io.IOException;

public class Restore {

    private Restore () {
    }

    public static void pgRestoreDb() {

        // Настраиваем ProcessBuilder для выполнения команды
        ProcessBuilder processBuilder = new ProcessBuilder(Config.getInstance().getPathPgRestore(),
                "-U", Config.getInstance().getDbUser(),
                "-d", Config.getInstance().getDbName(),
                "-1",
                Config.getInstance().getDumpFileDb());

        // Устанавливаем переменную окружения для пароля
        processBuilder.environment().put("PGPASSWORD", Config.getInstance().getDbPassword());

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
