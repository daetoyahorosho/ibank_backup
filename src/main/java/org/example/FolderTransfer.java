package org.example;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.SQLOutput;

public class FolderTransfer {

    private static final String FIRST_QUESTIONS = "Директория ibank находится по данному пути: ";
    private static final String SECOND_QUESTIONS = "Backup сервера ibank находится по данному пути: ";
    private static final String MESSAGE_WARNING = "ВАЖНО! Сервер Pegasus должен находиться в корне папки ibank, так как приложение переносит только папку ibank. \n" +
            "Некорректно настроенный config.properties может привести к необратимым последствиям.";
    private static final String MESSAGE_TO_CONTINUE = "Вы хотите продолжить? (yes/no): ";
    private static final String SKIP_LINE = "*********************************************************************************************";

    public static void systemMessage() {

        Config config = new Config("classes/config.properties");

        // Вывод информации о работе, а также о расположении сервера и бэкапа
        System.out.println(MESSAGE_WARNING);
        System.out.println(SKIP_LINE);
        System.out.println(FIRST_QUESTIONS + config.getIbankFolder());
        System.out.println(SKIP_LINE);
        System.out.println(SECOND_QUESTIONS + config.getBackupIbankFolder());
        System.out.println(SKIP_LINE);
        System.out.println(MESSAGE_TO_CONTINUE);

    }

    public static void deleteFolder(Path folderIbank) {

        try {
            // Получаем список всех файлов и подкаталогов в указанной директории
            DirectoryStream<Path> directoryStream = Files.newDirectoryStream(folderIbank);
            for (Path path : directoryStream) {
                if (Files.isDirectory(path)) {
                    // Если это директория, вызываем deleteFolder рекурсивно
                    deleteFolder(path);
                }
                // Удаляем файл или пустую директорию
                Files.deleteIfExists(path);
            }
            // Удаляем саму директорию после удаления её содержимого
            Files.deleteIfExists(folderIbank);
            System.out.println("Папка " + folderIbank + " и её содержимое успешно удалены.");
        } catch (IOException e) {
            System.err.println("Ошибка при удалении папки: " + e.getMessage());
        }
    }

    public static void copyFolder(Path source, Path target) {

        try {
            // Создаем папку назначения, если она не существует
            if (!Files.exists(target)) {
                Files.createDirectories(target);
            }

            // Копируем содержимое директории
            Files.walk(source)
                    .forEach(sourcePath -> {
                        Path targetPath = target.resolve(source.relativize(sourcePath));
                        try {
                            if (Files.isDirectory(sourcePath)) {
                                // Создаем директорию
                                Files.createDirectories(targetPath);
                            } else {
                                // Копируем файл
                                Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                            }
                        } catch (IOException e) {
                            System.err.println("Ошибка при копировании файла " + sourcePath + ": " + e.getMessage());
                        }
                    });
            System.out.println("Папка " + source + " успешно скопирована в " + target + ".");
        } catch (IOException e) {
            System.err.println("Ошибка при копировании папки: " + e.getMessage());
        }

    }
}


