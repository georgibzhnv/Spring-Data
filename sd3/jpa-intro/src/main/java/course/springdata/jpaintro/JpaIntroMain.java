package course.springdata.jpaintro;

import course.springdata.jpaintro.entity.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaIntroMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("school_jpa");
        EntityManager em = emf.createEntityManager();
        Student student = new Student("Georgi Pavlov");
        em.getTransaction().begin();
        em.persist(student);
        em.getTransaction().commit();

        em.getTransaction().begin();
        Student found =em.find(Student.class,2L);
        System.out.printf("Found student: %s%n",found);
        em.getTransaction().commit();

        em.getTransaction().begin();
        em.createQuery("SELECT s FROM Student s WHERE s.name LIKE ?1")
                .setParameter(1,"%")
                .getResultList().forEach(System.out::println);
        em.getTransaction().commit();

        em.getTransaction().begin();
//        em.remove(found);
        em.detach(found);
        found.setName("Atanas Petrov");
        Student managedEntity= em.merge(found);
        System.out.printf("Same reference: %b%n",managedEntity==found);
//        Student removed =em.find(Student.class,1L);
//        System.out.printf("Removed entity: %s%n",removed);
        em.getTransaction().commit();

        em.close();
    }
}
