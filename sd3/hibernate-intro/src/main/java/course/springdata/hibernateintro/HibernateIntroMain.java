package course.springdata.hibernateintro;

import org.hibernate.FlushMode;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

public class HibernateIntroMain {
    public static void main(String[] args) {
        Configuration cfg = new Configuration();
        cfg.configure();

        SessionFactory sf = cfg.buildSessionFactory();

        Session session = sf.openSession();

        Student student = new Student("Dimitar Pavlov");
        session.beginTransaction();
        session.save(student);
        session.getTransaction().commit();

        //read
        session.beginTransaction();
        session.setHibernateFlushMode(FlushMode.MANUAL);
        long queryId = 10L;
        Optional<Student> result = session.byId(Student.class).loadOptional(queryId);
//        Student result = session.byId(Student.class).load(queryId);
        session.getTransaction().commit();
        if (result.isPresent()) {
            System.out.printf("Student: %s", result.get());
        }else {
            System.out.printf("Student with ID:%d does not exist.%n",queryId);
        }

        //List with HQL
        session.beginTransaction();
        session.createQuery("FROM Student ",Student.class)
                .setFirstResult(5)
                .setMaxResults(10)
                .stream().forEach(System.out::println);
        session.getTransaction().commit();

        System.out.println("\n-----------------------------------");
        session.createQuery("FROM Student WHERE name = ?1",Student.class)
                .setParameter(1,"Hristo Georgiev")
                .stream().forEach(System.out::println);

        //Type-safe criteria queries
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Student> query = cb.createQuery(Student.class);
        Root<Student> Student_ = query.from(Student.class);
        query.select(Student_).where(cb.like(Student_.get("name"),"D%"));
        session.createQuery(query).getResultStream()
                        .forEach(System.out::println);

        session.close();
    }
}
