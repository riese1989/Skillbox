import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        boolean correctPathFolder = false;
        boolean correctPathForCopy = false;
        while (!correctPathFolder) {
            File folderCopied = customScanner("Введите адрес копируемой папки");
            if (folderCopied.isDirectory()) {
                while (!correctPathForCopy) {
                    correctPathFolder = true;
                    File pathForCopy = customScanner("Введите адрес, куда надо скопировать папку " + folderCopied.getName());
                    if (pathForCopy.exists()) {
                        if (copy(folderCopied, pathForCopy)) {
                            System.out.println("Запись успешная");
                        }
                        correctPathForCopy = true;
                    } else {
                        System.out.println("Адрес некорректный, введите правильный");
                    }
                }
            } else {
                System.out.println("Это не папка");
            }
        }

    }

    public static File customScanner(String msg) {
        System.out.println(msg);
        Scanner scanner = new Scanner(System.in);
        return new File(scanner.nextLine());
    }

    public static boolean copy(File folderCopied, File pathForCopy) throws IOException {
        File[] filesCopiedFolder = folderCopied.listFiles();
        File hasCopiedFolder = new File(pathForCopy.getPath() + "/" + folderCopied.getName());
        if (hasCopiedFolder.exists()) {
            System.out.println("В папке " + pathForCopy.getName() + " уже есть папка " + folderCopied.getName());
            return false;
        }
        hasCopiedFolder.mkdir();
        for (File file : filesCopiedFolder) {
            if (file.isDirectory()) {
                File pathCopy = new File(hasCopiedFolder.getPath());
                copy(file, pathCopy);
            } else {
                if (!file.isHidden()) {
                    List<String> lines = Files.readAllLines(file.toPath());
                    FileWriter writer = new FileWriter(hasCopiedFolder.getPath() + "/" + file.getName(), false);
                    for (String line : lines)   {
                        writer.write(line);
                    }
                    writer.flush();
                    writer.close();
                }
            }
        }
        return true;
    }
}
