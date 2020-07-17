import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class ConfigSession {
    private SessionFactory sessionFactory;
    private Session session;

    private void init() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        this.sessionFactory = metadata.getSessionFactoryBuilder().build();
    }
    public Session getSession() {
        init();
        session = sessionFactory.openSession();
        return session;
    }
    public SessionFactory  getSessionFactory()  {
        return sessionFactory;
    }
}
