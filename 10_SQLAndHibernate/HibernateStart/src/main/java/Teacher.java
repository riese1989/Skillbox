import javax.persistence.*;
import java.util.List;

@Entity
@Table (name = "Teachers")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int salary;
    private int age;
    @OneToMany(mappedBy = "teacher")
    private List<Course> cource;

    public List<Course> getCource() {
        return cource;
    }

    public void setCource(List<Course> cource) {
        this.cource = cource;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
