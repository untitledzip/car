package com.example.car.Entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Owner")
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long ownerId;

    private String firstName;

    private String lastName;

    public Owner(String firstName, String lastName){
        super();
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<Car> cars;

    private List<Car> getCars(){
        return cars;
    }

    private void setCars(List<Car> cars){
        this.cars = cars;
    }
}
