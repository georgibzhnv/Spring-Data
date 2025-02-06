import entities.gringotts.WizardDeposit;
import entities.hospital.Patient;
import jakarta.persistence.EntityManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.List;

public class Engine implements Runnable{

    private  BufferedReader bufferedReader;
    private final EntityManager entityManager;



    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {
        try {
            userInterface();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void userInterface() throws IOException {
        System.out.printf("Welcome to the hospital Database.%n" +
                " For showing the patients list enter S.%n" +
                "For adding new patient enter A.%n" +
                "For deleting a patient enter D.%n");
        String option = bufferedReader.readLine().toUpperCase();
        switch (option){
            case "S" -> showPatients();
            case "A" -> addPatient();
            case "D" -> deletePatient();
            default -> System.out.println("Invalid option. Please enter S, A, or D.");
        }
    }

    private void deletePatient() throws IOException {
        System.out.println("Enter patient ID for deleting: ");
        String patientId= bufferedReader.readLine().toUpperCase();

        try{
        entityManager.getTransaction().begin();
        int rowsDeleted =  entityManager
                .createQuery("DELETE FROM Patient p " +
                        "where p.id= :patientId")
                .setParameter("patientId",Long.parseLong(patientId))
                .executeUpdate();
        entityManager.getTransaction().commit();
        if (rowsDeleted > 0) {
            System.out.println("Patient deleted successfully.");
        } else {
            System.out.println("No patient found with the given ID.");
        }
    } catch (Exception e) {
        entityManager.getTransaction().rollback();
        System.out.println("Error deleting patient: " + e.getMessage());
    }
    }

    private void addPatient() throws IOException {
        System.out.println("Enter first name: ");
        String firstName = bufferedReader.readLine();

        System.out.println("Enter last name: ");
        String lastName = bufferedReader.readLine();

        System.out.println("Enter address: ");
        String address = bufferedReader.readLine();

        System.out.println("Enter email: ");
        String email = bufferedReader.readLine();

        System.out.println("Enter date of birth (yyyy-MM-dd): ");
        String dobInput = bufferedReader.readLine();
        LocalDate dateOfBirth = LocalDate.parse(dobInput);

        System.out.println("Does the patient have insurance? (true/false): ");
        boolean hasInsurance = Boolean.parseBoolean(bufferedReader.readLine());

        // Create new patient
        Patient patient = new Patient();
        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setAddress(address);
        patient.setEmail(email);
        patient.setDateOfBirth(dateOfBirth);
        patient.setHasInsurance(hasInsurance);

        // Persist patient to the database
        entityManager.getTransaction().begin();
        entityManager.persist(patient);
        entityManager.getTransaction().commit();

        System.out.println("Patient added successfully!");
    }

    private void showPatients() {
        List<Patient> patients = entityManager
                .createQuery("SELECT p from Patient p ", Patient.class)
                .getResultList();

        if (patients.isEmpty()) {
            System.out.println("No patients found in the database.");
        } else {
            patients.forEach(patient -> {
                System.out.printf("Name: %s %s, Address: %s, Email: %s, DOB: %s, Insurance: %b%n",
                        patient.getFirstName(), patient.getLastName(), patient.getAddress(),
                        patient.getEmail(), patient.getDateOfBirth(), patient.isHasInsurance());
            });
        }
    }
}
