package start;

import objects.Student;
import services.DbService;
import services.FileService;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        FileService fileService = new FileService();
        DbService dbService = new DbService();
        ArrayList<Student> students = fileService.readFile();
        dbService.write(students);
        dbService.printDb();
    }
}
