import org.hibernate.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ConfigSession configSession = new ConfigSession();
        Session session = configSession.getSession();
        String hql = "From "+PurchaseList.class.getSimpleName();
        List<PurchaseList> purchases = session.createQuery(hql).getResultList();
        for (PurchaseList purchase : purchases) {
            hql = "From "+Student.class.getSimpleName()+ " Where name = '"+purchase.getStudentName() + "'";
            List<Student> listStudents = session.createQuery(hql).getResultList();
            hql = "From "+Course.class.getSimpleName()+ " Where name = '"+purchase.getCourseName() + "'";
            List<Course> listCourses = session.createQuery(hql).getResultList();
            Course course = listCourses.get(0);
            Student student = listStudents.get(0);
            hql = "From "+LinkedPurchaseList.class.getSimpleName()+ " Where course_id = "+course.getId() + "AND student_id = " + student.getId();
            List<LinkedPurchaseList> linkedPurchaseLists = session.createQuery(hql).getResultList();
            if (linkedPurchaseLists.size() > 0) {
                continue;
            }
            Transaction transaction = session.beginTransaction();
            Key key = new Key(student.getId(),course.getId());
            LinkedPurchaseList linkedPurchaseList = new LinkedPurchaseList();
            linkedPurchaseList.setId(key);
            transaction.commit();
            session.save(linkedPurchaseList);
        }
        configSession.getSessionFactory().close();
    }
}
