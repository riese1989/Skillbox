package objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter

public class Student {
    private String name;
    private int age;
    private String courses;
}
