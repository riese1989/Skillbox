import org.hibernate.*;

import java.util.Iterator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ConfigSession configSession = new ConfigSession();
        Session session = configSession.getSession();
        List<Object> courses = session.createQuery("Select name, studentsCount From Course").list();
        Iterator itr = courses.iterator();
        while (itr.hasNext())   {
            Object[] course = (Object[]) itr.next();
            String name = String.valueOf(course[0]);
            Integer countStudents = Integer.parseInt(String.valueOf(course[1]));
            System.out.println(name + " " + countStudents);
        }
        configSession.getSessionFactory().close();
    }
}
