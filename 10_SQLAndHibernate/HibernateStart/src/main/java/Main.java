import org.hibernate.*;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Iterator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
        Session session = sessionFactory.openSession();
        List<Object> courses = session.createQuery("Select name, studentsCount From Course").list();
        Iterator itr = courses.iterator();
        while (itr.hasNext())   {
            Object[] course = (Object[]) itr.next();
            String name = String.valueOf(course[0]);
            Integer countStudents = Integer.parseInt(String.valueOf(course[1]));
            System.out.println(name + " " + countStudents);
        }
        sessionFactory.close();
    }
}
