package entities.hospital;

import entities.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name="medicaments")
public class Medicament extends BaseEntity {
    private String name;
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "patient_id",referencedColumnName = "id")
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Medicament() {
    }

    @Column(name = "name",length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
