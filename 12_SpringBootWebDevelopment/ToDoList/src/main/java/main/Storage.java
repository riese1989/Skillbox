package main;

import main.model.Course;
import main.model.CourseRepository;

import java.util.ArrayList;
import java.util.List;

public class Storage {

    public static List<Course> getCourses(CourseRepository courseRepository)   {
        Iterable <Course> courseIterable = courseRepository.findAll();
        ArrayList<Course> courses = new ArrayList<>();
        for (Course course : courseIterable)    {
            courses.add(course);
        }
        return courses;
    }

    public static Course getCourse (int id, CourseRepository courseRepository) {
        try {
            return courseRepository.findById(id).get();
        }
        catch (Exception ex)    {
            return null;
        }
    }
}
