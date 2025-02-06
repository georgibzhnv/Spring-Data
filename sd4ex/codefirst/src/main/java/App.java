import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class App {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hospital");

        EntityManager em = emf.createEntityManager();
        Engine engine = new Engine(em);

        engine.run();
    }
}
