import org.hibernate.*;

import java.util.Iterator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ConfigSession configSession = new ConfigSession();
        Session session = configSession.getSession();
        //List<Object> courses = session.createQuery("Select name, studentsCount From Course").list();
        //Iterator itr = courses.iterator();
        Teacher teacher = session.get(Teacher.class, 10);
        List<Course> courses = teacher.getCource();
        for (Course course : courses)   {
            System.out.println(course.getName());
        }
//        while (itr.hasNext())   {
//            try {
//            Object[] course = (Object[]) itr.next();
//            String name = String.valueOf(course[0]);
//            Integer countStudents = Integer.parseInt(String.valueOf(course[1]));
//            System.out.println(name + " " + countStudents);
//        }
//            catch (Exception ex)    {
//
//            }
//        }
        configSession.getSessionFactory().close();
    }
}
