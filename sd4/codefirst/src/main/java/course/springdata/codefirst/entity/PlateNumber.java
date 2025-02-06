package course.springdata.codefirst.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity(name = "plate")
@Table(name = "platenumber")
public class PlateNumber {
    @Id
    private Long id;
    @Column(name = "plate_number")
    private String plateNumber;
    @OneToOne
    @JoinColumn(name = "id")
    @MapsId
    private Car car;

    public PlateNumber() {
    }

    public PlateNumber(Car car, String plateNumber) {
        this.car = car;
        this.plateNumber = plateNumber;
    }

    public PlateNumber(Long id, Car car, String plateNumber) {
        this.id = id;
        this.car = car;
        this.plateNumber = plateNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PlateNumber that = (PlateNumber) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PlateNumber{");
        sb.append("plateNumber='").append(plateNumber).append('\'');
        sb.append(", id=").append(id);
        sb.append('}');
        return sb.toString();
    }
}
