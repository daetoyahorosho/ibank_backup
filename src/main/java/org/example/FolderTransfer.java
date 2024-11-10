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

    /**
     * Метод для вывода информационных сообщений при старте работы программы
     */
    public static void systemMessage() {

        System.out.println(MESSAGE_WARNING);
        System.out.println(SKIP_LINE);
        System.out.println(FIRST_QUESTIONS + Config.getInstance().getIbankFolder());
        System.out.println(SKIP_LINE);
        System.out.println(SECOND_QUESTIONS + Config.getInstance().getBackupIbankFolder());
        System.out.println(SKIP_LINE);
        System.out.println(MESSAGE_TO_CONTINUE);

    }

    private FolderTransfer () {
    }

    /**
     * Метод для удаления папки с вложенными в нее файлами
     * @param folderIbank
     */
    public static void deleteFolder(Path folderIbank) {

        try {
            DirectoryStream<Path> directoryStream = Files.newDirectoryStream(folderIbank);
            for (Path path : directoryStream) {
                if (Files.isDirectory(path)) {
                    deleteFolder(path);
                }
                Files.deleteIfExists(path);
            }
            Files.deleteIfExists(folderIbank);
            System.out.println("Папка " + folderIbank + " и её содержимое успешно удалены.");
        } catch (IOException e) {
            System.err.println("Ошибка при удалении папки: " + e.getMessage());
        }
    }

    /**
     * Метод для копирования папки c дальнейшим перемещением
     * @param source
     * @param target
     */
    public static void copyFolder(Path source, Path target) {

        try {
            if (!Files.exists(target)) {
                Files.createDirectories(target);
            }

            Files.walk(source)
                    .forEach(sourcePath -> {
                        Path targetPath = target.resolve(source.relativize(sourcePath));
                        try {
                            if (Files.isDirectory(sourcePath)) {
                                Files.createDirectories(targetPath);
                            } else {
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


