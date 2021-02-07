package services;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import objects.Student;
import settings.FileSettings;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileService {
    private FileSettings fileSettings = new FileSettings();

    public ArrayList<Student> readFile()    {
        ArrayList<Student> studentList = new ArrayList<Student>();
        try (CSVReader reader = new CSVReader(new FileReader(fileSettings.getPath()))) {
            String[] lineInArray;
            while ((lineInArray = reader.readNext()) != null) {
                String name = lineInArray[0];
                int age = Integer.parseInt(lineInArray[1]);
                String courses = lineInArray[2];
                Student student = new Student(name, age, courses);
                studentList.add(student);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return studentList;
    }
}
