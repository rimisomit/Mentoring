package com.mentoring.workshop.client;

import com.mentoring.workshop.car.Car;

/**
 * Class can interact with Workshop
 */
public class Client {

    private final int carId;
    private Car car;

    public Client(int carId) {
        if (carId < 0) {
            throw new IllegalArgumentException("Unproper carId");
        }
        car = new Car(carId);
        this.carId = carId;
    }

    public int getCarId() {
        return carId;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

}
