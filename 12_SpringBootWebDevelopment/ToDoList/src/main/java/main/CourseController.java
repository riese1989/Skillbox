package main;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import responce.Course;

import java.util.List;

@RestController
public class CourseController {
    @GetMapping("/courses/")  // сделано
    public List<Course> list()  {
        return Storage.getCourses();
    }

    @PostMapping("/courses/") // сделано
    public int createCourse(Course course)  {
        return Storage.addCourses(course);
    }

    @GetMapping("/courses/{id}") // сделано
    public ResponseEntity get (@PathVariable int id)  {
        Course course = Storage.getCourse(id);
        if (course == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity(course,HttpStatus.OK);
    }
    @PostMapping("/courses/{id}")
    public ResponseEntity postId (@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(null);
    }

    @PutMapping("/courses/") // сделано
    public List<Course>  updAllCourses(Course course)  {
        Storage.updAllCourses(course);
        return Storage.getCourses();
    }

    @PutMapping("/courses/{id}")
    public List<Course> updateCourse(@PathVariable int id, Course course)   {
        Storage.updCourse(id, course);
        return Storage.getCourses();
    }

    @DeleteMapping("/courses/") // сделано
    public ResponseEntity deleteCourses()   {
        Storage.cleatListCourses();
        if (Storage.getCourses().size() == 0)   {
            return new ResponseEntity(null,HttpStatus.OK);
        }
        return null;
    }

    @DeleteMapping("/courses/{id}") // сделано
    public ResponseEntity deleteCourseId(@PathVariable int id)   {
        Storage.deleteCourseId(id);
        if (Storage.getCourse(id) == null)  {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        return null;
    }

}
