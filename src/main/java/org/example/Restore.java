package org.example;

import java.io.IOException;

public class Restore {

    public static void pgRestoreDb() {

        //Знаю, что хардкод пути к файлу - исправлю!
        Config config = new Config("C:\\Users\\danil\\IdeaProjects\\ibank_backup\\src\\main\\java\\org\\example\\config.properties");
        // Формируем команду для восстановления
        String commandPgRestore = "\"" + config.getPathPgRestore() + "\" -U " + config.getDbUser() + " -d ibank2 -1 \"" + config.getDumpFieDb() + "\"";

        // Настраиваем ProcessBuilder для выполнения команды
        ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", commandPgRestore);

        // Устанавливаем переменную окружения для пароля
        processBuilder.environment().put("PGPASSWORD", config.getDbPassword()); // Убедитесь, что это ваш пароль

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
