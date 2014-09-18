package com.mentoring.workshop.garageshop;

import com.mentoring.workshop.car.*;

import java.util.ArrayList;

public class Parking extends CarLocation implements CarService, CarIdService {

    private final ArrayList<Car> parkingList;

    public Parking() {
        parkingList = new ArrayList<Car>();
        locationId = 0;
        //parkingWaitingList = new ArrayList<Car>();
    }

    public int getParkingLoad() {
        return parkingList.size();
    }

    /**
     * 1. check car not null
     * 2. check if car already in parking
     * 3. place car to parking
     *
     * @param car            client's car
     * @param repairComplete was car repaired or just came
     */
    public void receiveCar(Car car, boolean repairComplete) {
        if (car == null) {
            throw new IllegalArgumentException("Cannot receive car as null");
        }
        for (Car c : parkingList) {
            if (c.equals(car)) {
                throw new IllegalArgumentException("The car is already in parking");
            }
        }
        //System.out.println("IN PARKING: " + car.toString());
        if (parkingList.add(car)) {
            if (repairComplete) {
                car.setCarStatus(CarStatus.REPAIR_COMPLETE);
            } else {
                car.setCarStatus(CarStatus.WAITING_FOR_REPAIR);
            }
            //System.out.println("IN PARKING ARRAY" + parkingList.toString());
        }
        //System.out.println("CAR in LIST: " + parkingList);
    }

    public void receiveCar(Car car) {
        System.out.println(parkingList.toString());
        releaseWaiting();
        if (car == null) {
            throw new IllegalArgumentException("Cannot receive car as null");
        }
        //System.out.println("CAR in LIST: " + parkingList);
        for (Car c : parkingList) {
            if (c.equals(car)) {
                throw new IllegalArgumentException("The car is already in parking");
            }
        }
        if (parkingList.add(car)) {
            car.setCarStatus(CarStatus.PARKED);
        } else {
            throw new IllegalArgumentException("Cannot place car to parking");
        }
    }

    public void releaseCar(Car car) {
        if (car == null) {
            throw new NullPointerException("Cannot release car as null");
        }
        parkingList.remove(car);
    }

    public void releaseCar(int carId) {
        car = getCarById(carId);
        parkingList.remove(car);
        car.setCarStatus(CarStatus.GAVE_AWAY);
    }

    public int releaseWaiting() {
        for (Car car : parkingList) {
            if (car.getCarStatus() == CarStatus.WAITING_FOR_REPAIR) {
                //System.out.println("IN WAITING Parking" + car.toString());
                //parkingList.remove(car);
                return car.getCarId();
            }
        }
        return -1;
    }

    public Car getCarById(int carId) {
        for (Car car : parkingList) {
            //System.out.println(carId);
            if (car.getCarId() == carId) {
                return car;
            }
        }
        return null;
    }

    public void removeCarById(int carId) {
        if (carId > 0) {
            for (Car car1 : parkingList) {
                //car1 = getCarById(carId);
                if (car1.getCarId() == carId) {
                    if (parkingList.remove(car1)) {
                        System.out.println("Removed from parking " + carId);
                        return;
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Parking{" +
                "parkingList=" + parkingList +
                '}';
    }
}
