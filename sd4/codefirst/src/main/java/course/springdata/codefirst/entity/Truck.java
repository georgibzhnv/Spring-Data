package course.springdata.codefirst.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "trucks")
public class Truck extends Vehicle{
    @Column(name = "load_capacity")
    private double loadCapacity;

    public Truck(){}

    public Truck(String model, BigDecimal price, String fuelType, double loadCapacity) {
        super(model, price, fuelType);
        this.loadCapacity = loadCapacity;
    }

    public Truck(long id, String model, BigDecimal price, String fuelType, int loadCapacity) {
        super(id, model, price, fuelType);
        this.loadCapacity = loadCapacity;
    }

    public double getLoadCapacity() {
        return loadCapacity;
    }

    public void setLoadCapacity(double loadCapacity) {
        this.loadCapacity = loadCapacity;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Truck{");
        sb.append(super.toString());
        sb.append("loadCapacity=").append(loadCapacity);
        sb.append('}');
        return sb.toString();
    }
}
