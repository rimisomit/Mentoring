package com.mentoring.workshop.garageshop;

import com.mentoring.workshop.car.*;

import java.util.Date;

public class Garage extends CarLocation implements CarService, CarIdService {

    private boolean garageEmptiness = true;
    private Car car;
    private int carId = -1;

    public Garage(int locationId) { //TODO Why can be private?
        this.locationId = locationId;
    }

    public boolean getGarageEmptiness() {
        return garageEmptiness;
    }

    public void receiveCar(Car car, boolean repairComplete) {
        if (repairComplete) {
            throw new IllegalStateException("Placing repaired car to garage");
        } else {
            this.car = car;
            this.carId = car.getCarId();
            receiveCar(car);
        }
    }

    public void receiveCar(Car car) {
        if (car == null) {
            throw new NullPointerException("Car is null");
        }
        if (car.getCarStatus() == CarStatus.REPAIRING) {
            throw new IllegalArgumentException("Car is already repairing");
        }
        if (!garageEmptiness) {
            throw new IllegalStateException("Garage is occupied");
        }
        Date carRepairStartDate = new Date();
        this.car = car;
        this.carId = car.getCarId();
        if (car.getCarStatus() != CarStatus.REPAIRING) {
            if (garageEmptiness) {
                garageEmptiness = false;
                car.setCarStatus(CarStatus.REPAIRING);
                car.setCarRepairStartDate(carRepairStartDate);
            }
        }
    }

    public void releaseCar(Car car) {
        //car = null;
        throw new IllegalArgumentException("Cannot release from garage");
    }

    public Car getCarById(int carId) {
        if (car != null) {
            if (this.car.getCarId() == carId) {
                return car;
            }
        }
        return null;
    }

    public void removeCarById(int carId) {
        if (this.carId == carId) {
            //System.out.println("removed from garage");
            this.car = null;
            this.garageEmptiness = true;
            this.carId = -1;
        }
    }

    @Override
    public String toString() {
        return "Garage{" +
                "locationId=" + locationId +
                " garageEmptiness=" + garageEmptiness +
                ", car=" + car +
                ", carId=" + carId +
                '}';
    }

    public int getCarId() {
        return carId;
    }
}
