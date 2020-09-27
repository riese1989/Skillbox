package main.controller;

import main.Storage;
import main.model.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import main.model.Course;

import java.util.List;
import java.util.Optional;

@RestController
public class CourseController {
    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/courses/")  // сделано бд
    public List<Course> list()  {
        return Storage.getCourses(courseRepository);
    }

    @PostMapping("/courses/") // сделано бд
    public int createCourse(Course course)  {
        Course newCourse = courseRepository.save(course);
        return newCourse.getId();
    }

    @GetMapping("/courses/{id}") // сделано бд
    public ResponseEntity get (@PathVariable int id)  {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if(!optionalCourse.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity(optionalCourse.get(), HttpStatus.OK);
    }
    @PostMapping("/courses/{id}")
    public ResponseEntity postId (@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(null);
    }

    @PutMapping("/courses/") // сделано бд
    public List<Course>  updAllCourses(Course course)  {
        //Storage.updAllCourses(course);
        Iterable <Course> allCourses = courseRepository.findAll();
        allCourses.forEach(c -> {c.setName(course.getName()); c.setYear(course.getYear());});
        courseRepository.saveAll(allCourses);
        return Storage.getCourses(courseRepository);
    }

    @PutMapping("/courses/{id}")
    public List<Course> updateCourse(@PathVariable int id, Course course)   {
        Course optionalCourse = Storage.getCourse(id,courseRepository);
        optionalCourse.setName(course.getName());
        optionalCourse.setYear(course.getYear());
        courseRepository.save(optionalCourse);
        return Storage.getCourses(courseRepository);
    }

    @DeleteMapping("/courses/") // сделано
    public ResponseEntity deleteCourses()   {
        courseRepository.deleteAll();
        if (Storage.getCourses(courseRepository).size() == 0)   {
            return new ResponseEntity(null,HttpStatus.OK);
        }
        return null;
    }

    @DeleteMapping("/courses/{id}") // сделано
    public ResponseEntity deleteCourseId(@PathVariable int id)   {
        Course course = Storage.getCourse(id,courseRepository);
        courseRepository.delete(course);
        if (Storage.getCourse(id, courseRepository) == null)  {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        return null;
    }

}
