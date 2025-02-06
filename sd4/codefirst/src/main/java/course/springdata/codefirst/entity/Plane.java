package course.springdata.codefirst.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "planes")
public class Plane extends Vehicle{
    @Column(name="passenger_capacity")
    private int passengerCapacity;

    public Plane(){}

    public Plane(String model, BigDecimal price, String fuelType, int passengerCapacity) {
        super(model, price, fuelType);
        this.passengerCapacity = passengerCapacity;
    }

    public Plane(long id, String model, BigDecimal price, String fuelType, int passengerCapacity) {
        super(id, model, price, fuelType);
        this.passengerCapacity = passengerCapacity;
    }

    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    public void setPassengerCapacity(int passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Plane{");
        sb.append(super.toString());
        sb.append("passengerCapacity=").append(passengerCapacity);
        sb.append('}');
        return sb.toString();
    }
}
