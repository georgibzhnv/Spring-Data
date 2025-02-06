import jakarta.persistence.EntityManager;
import model.*;
import org.hibernate.query.criteria.JpaRoot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public class Engine implements Runnable {

    private final EntityManager entityManager;
    private final BufferedReader reader;

    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.reader=new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run(){
//        changeCasing();

//        try {
//            containsEmployee();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        employeesWithSalaryUnder60000();

//        employeesFromDepartments();

//        try {
//            addingNewAddressAndUpdatingEmployee();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        addressWithEmployeesCount();
//        try {
//            getEmployeeWithProjects();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        lastTenProjects();

//        increaseSalaries();

//        try {
//            employeesByFirstName();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        departmentMaxSalary();

        try {
            removeTowns();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void removeTowns() throws IOException {
        System.out.println("Enter town name: ");
        String townName = reader.readLine();
        entityManager.getTransaction().begin();
        Town town = entityManager
                .createQuery("Select t from Town t " +
                        "where t.name = :name", Town.class)
                .setParameter("name",townName)
                .getSingleResult();

        int affectedEmployees = entityManager
                .createQuery("UPDATE Employee e SET e.address = NULL WHERE e.address.id IN " +
                        "(SELECT a.id FROM Address a WHERE a.town.id = :townId)")
                .setParameter("townId", town.getId())
                .executeUpdate();

        int deletedAdresses = entityManager
                .createQuery("DELETE FROM Address a " +
                        "where a.town.id= :townId")
                        .setParameter("townId",town.getId())
                                .executeUpdate();

        entityManager.remove(town);
        entityManager.getTransaction().commit();
        if (deletedAdresses>1){
            System.out.printf("%d addresses in %s deleted",deletedAdresses,town.getName());
        }else {
            System.out.printf("%d address in %s deleted",deletedAdresses,town.getName());
        }
    }

    private void departmentMaxSalary() {
        List<Object[]> results = entityManager
                .createQuery(
                        "SELECT e.department.name, MAX(e.salary) AS maxSalary " +
                                "FROM Employee e " +
                                "GROUP BY e.department.id, e.department.name " +
                                "HAVING MAX(e.salary) < 30000 OR MAX(e.salary) > 70000", Object[].class)
                .getResultList();

        // Process the results
        results.forEach(result -> {
            String departmentName = (String) result[0];  // Department name
            BigDecimal maxSalary = (BigDecimal) result[1]; // Maximum salary
            System.out.printf("Department: %s, Max Salary: %.2f%n", departmentName, maxSalary);
        });
    }

    private void employeesByFirstName() throws IOException {
        System.out.println("Enter a pattern: ");
        String pattern = reader.readLine();
        List<Employee> employees = entityManager
                .createQuery("SELECT e from Employee e " +
                        "WHERE e.firstName like :letters", Employee.class)
                .setParameter("letters",pattern +"%")
                .getResultList();
        employees.forEach(employee -> {
            System.out.printf("%s %s - %s - ($%.2f)%n",employee.getFirstName(),
                    employee.getLastName(),employee.getJobTitle(),employee.getSalary());
        });

    }

    private void increaseSalaries() {
        entityManager.getTransaction().begin();
        int affectedRows = entityManager
                .createQuery("update Employee e " +
                        "set e.salary = e.salary* :factor " +
                        "where e.department.id in(3,4,7,9)")
                        .setParameter("factor", BigDecimal.valueOf(1.12))
                        .executeUpdate();
        entityManager.getTransaction().commit();

        System.out.println("Affected rows: " + affectedRows);
        entityManager
                .createQuery("select e FROM Employee e " +
                        "where e.department.id in (3,4,7,9)" ,Employee.class)
                .getResultStream()
                .forEach(employee -> {
                    System.out.printf("%s %s ($%.2f)%n",
                            employee.getFirstName(),employee.getLastName(),employee.getSalary());
                });
    }

    private void lastTenProjects() {
        List<Project>projects = entityManager
                .createQuery("select p from Project p " +
                        "order by p.startDate DESC , name", Project.class)
                .setMaxResults(10)
                .getResultList();
        projects.forEach(project -> {
            System.out.printf("Project name: %s%n",project.getName());
            System.out.printf("\tProject Description: %s%n",project.getDescription());
            System.out.printf("\tProject Start Date: %s%n",project.getStartDate());
            System.out.printf("\tProject End Date: %s%n",project.getEndDate());
                }
        );
    }

    private void getEmployeeWithProjects() throws IOException {
        System.out.println("Enter valid employee id: ");
        int id = Integer.parseInt(reader.readLine());

        Employee employee = entityManager
                .find(Employee.class,id);

        System.out.printf("%s %s - %s%n",employee.getFirstName(),employee.getLastName(),employee.getJobTitle());

        employee.getProjects()
                .stream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(project -> {
                    System.out.printf("\t%s%n",project.getName());
                });
    }

    private void addressWithEmployeesCount() {
        List<Address> addresses = entityManager
                .createQuery("SELECT a FROM Address a " +
                        "LEFT JOIN a.employees e " +
                        "GROUP BY a " +
                        "ORDER BY COUNT(e) DESC", Address.class)
                .setMaxResults(10)
                .getResultList();


        addresses
                .forEach(address -> {
                    System.out.printf("%s, %s - %d %n",
                            address.getText(),address.getTown().getName(),address.getEmployees().size());
                });
        entityManager.close();
    }

    private void addingNewAddressAndUpdatingEmployee() throws IOException {
        Address address = createAddress("Vitoshka 15");

        System.out.println("Enter employee id: ");
        String firstName = reader.readLine();

        Employee employee = entityManager
                .find(Employee.class, 7);

        entityManager.getTransaction().begin();
        employee.setAddress(address);
        entityManager.getTransaction().commit();
    }

    private Address createAddress(String addressText) {
        Address address = new Address();
        address.setText(addressText);

        entityManager.getTransaction().begin();
        entityManager.persist(address);
        entityManager.getTransaction().commit();

        return address;
    }

    private void employeesFromDepartments() {
         entityManager
                .createQuery("select e from Employee e " +
                        "WHERE e.department.name ='IT' " +
                        "ORDER BY e.salary, e.id", Employee.class)
                .getResultStream()
                .forEach(employee -> {
                    System.out.printf("%s %s from IT - $%.2f%n",
                            employee.getFirstName(),
                            employee.getLastName(),
                            employee.getSalary());
                });

    }

    private void employeesWithSalaryUnder60000() {
        entityManager
                .createQuery("select e from Employee e " +
                        "where e.salary<60000", Employee.class)
                .getResultStream()
                .map(Employee::getFirstName)
                .forEach(System.out::println);

    }

    private void containsEmployee() throws IOException {
        System.out.println("Enter employee full name:");
        String fullName = reader.readLine();

        List<Employee>employees = entityManager
                .createQuery("select e from Employee e " +
                        "where concat(e.firstName,' ',e.lastName) = :name ", Employee.class)
                .setParameter("name",fullName)
                .getResultList();
        System.out.println(employees.size()==0?"NO":"YES");
    }

    private void changeCasing() {
        List<Town>towns = entityManager
                .createQuery("select t from Town t " +
                        "where length(t.name)<=7", Town.class)
                .getResultList();
        entityManager.getTransaction().begin();
        towns.forEach(entityManager::detach);
        for (Town town : towns) {
            town.setName(town.getName().toLowerCase());
        }
        towns.forEach(entityManager::merge);
        entityManager.flush();
        entityManager.getTransaction().commit();
    }

}
