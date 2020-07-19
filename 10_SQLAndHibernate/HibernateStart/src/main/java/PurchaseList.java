import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PurchaseList")
public class PurchaseList {

    @Column(name = "student_name")
    private String studentName;
    @Column(name = "course_name")
    private String courseName;

    @EmbeddedId
    private KeyPurchaseList keyPurchaseList;


    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
