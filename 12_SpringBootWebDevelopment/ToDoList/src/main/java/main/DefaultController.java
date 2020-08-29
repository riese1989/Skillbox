package main;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class DefaultController {
    public static int i = 0;
    @RequestMapping("/")
    public String index()   {
        if ((i++)%2 == 0)   {
            Double number = Math.random();
            return number.toString();
        }
        return (new Date()).toString();
    }
}
