package main;

import responce.Course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Storage {

    private static HashMap<Integer, Course> courses = new HashMap<>();
    private static int currentId = 1;

    public static List<Course> getCourses()   {
        List<Course> courseList = new ArrayList<>();
        courseList.addAll(courses.values());
        return courseList;
    }

    public static int addCourses (Course course)   {
        int id = currentId++;
        course.setId(id);
        courses.put(id, course);
        return id;
    }

    public static Course getCourse (int courseId) {
        if(courses.containsKey(courseId))   {
            return courses.get(courseId);
        }
        return null;
    }

    public static int updCourse (int id, Course course)   {
        Course courseList = getCourse(id);
        courseList.setName(course.getName());
        courseList.setYear(course.getYear());
        return courseList.getId();
    }

    public static void cleatListCourses()   {
        courses.clear();
    }

    public static void deleteCourseId(int id) {
        courses.remove(id);
    }

    public static int updAllCourses (Course course)   {
        int changes = 0;
        for(Integer key : courses.keySet())    {
            Course currCourse = courses.get(key);
            currCourse.setName(course.getName());
            currCourse.setYear(course.getYear());
            changes++;
        }
        return changes;
    }
}
