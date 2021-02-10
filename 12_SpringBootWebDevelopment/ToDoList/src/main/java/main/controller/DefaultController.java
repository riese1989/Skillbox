package main.controller;

import main.model.Course;
import main.model.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@Controller
public class DefaultController {
    @Autowired
    CourseRepository courseRepository;
    @RequestMapping("/courses")
    public String index(Model model)   {
        Iterable<Course> courseIterable = courseRepository.findAll();
        ArrayList<Course> courses = new ArrayList<>();
        for (Course course : courseIterable)   {
            courses.add(course);
        }
        model.addAttribute("courses", courses);
        return "index";
    }
}
