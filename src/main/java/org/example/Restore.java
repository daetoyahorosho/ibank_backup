package org.example;

import java.io.IOException;

public class Restore {

    private Restore () {
    }

    /**
     * Восстановление БД пользователя с помощью встроенной в СУБД PostgreSQL утилиты pg_restore.exe
     */
    public static void pgRestoreDb() {

        ProcessBuilder processBuilder = new ProcessBuilder(Config.getInstance().getPathPgRestore(),
                "-U", Config.getInstance().getDbUser(),
                "-d", Config.getInstance().getDbName(),
                "-1",
                Config.getInstance().getDumpFileDb());

        processBuilder.environment().put("PGPASSWORD", Config.getInstance().getDbPassword());

        try {
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
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
