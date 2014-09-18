package com.mentoring.workshop.garageshop;

import com.mentoring.workshop.car.Car;
import com.mentoring.workshop.car.CarIdService;
import com.mentoring.workshop.car.CarStatus;

import java.util.ArrayList;

public final class Workroom implements CarIdService {

    private final ArrayList<Garage> garage;
    private final Parking parking;
    private int repairTime;
    private Car car;
    private ArrayList<CarIdService> carIdService;

    public Workroom(int garageCapacity, int repairTime) {
        if (garageCapacity < 0) {
            throw new IllegalArgumentException("Wrong garages count");
        }
        if (repairTime <= 0) {
            throw new IllegalArgumentException("Repair time too low");
        }
        this.repairTime = repairTime;
        carIdService = new ArrayList<CarIdService>();
        garage = new ArrayList<Garage>();
        parking = new Parking();
        carIdService.add(parking);

        for (int i = 0; i < garageCapacity; i++) {
            Garage g = new Garage(i + 1);
            carIdService.add(g);
            garage.add(g);
        }
    }

    public void receiveCar(Car car, boolean toRepair) {
        this.car = car;
        if (toRepair) {
            receiveCar(car);
        } else {
            parking.receiveCar(car);
        }
    }

    /*
        public void receiveCar(int carId) {
            receiveCar(getCarById(carId));
        }

        public void receiveCar(int carId, boolean toRepair) {
            if (toRepair) {
                receiveCar(getCarById(carId));
            } else {
                parking.receiveCar(getCarById(carId));
            }
        }
    */
    public void receiveCar(Car car) {
        if (car == null) {
            throw new NullPointerException("Car is null");
        }
        System.out.println("IN WORKROOM CAR = " + car.toString());
        System.out.println("----PRE receive location status----");
        System.out.println(parking.toString());
        System.out.println(garage.toString());

        removeRepaired();
        releaseWaiting();

        this.car = car;
        Garage firstEmptyGarage = findFirstEmptyGarage();
        if (firstEmptyGarage != null) {
            firstEmptyGarage.receiveCar(car);
        } else {
            parking.receiveCar(car, false);
            //System.out.println("PARKING CAR = " + car.toString());
        }

        System.out.println("----POST receive location status----");
        System.out.println(parking.toString());
        System.out.println(garage.toString());
    }

    public Garage findFirstEmptyGarage() { //TODO issue here
        System.out.println("Garage emptiness method start");
        for (Garage g : garage) {
            if (g.getGarageEmptiness()) {
                //System.out.println(g.toString());
                return g;
            }
        }
        System.out.println("Find NO empty garages");
        return null;
    }

    /*
        public void releaseCar(Car car) {
            if (car == null) {
                throw new NullPointerException();
            }
            this.car = car;
            parking.releaseCar(car);
        }
    */
    public void releaseCar(int carId) {
        if (carId <= 0) {
            throw new IllegalArgumentException("Not existing car ID");
        }
        parking.releaseCar(carId);
    }


    void removeRepaired() {
        //Car car;
        long timeNow = System.currentTimeMillis();
        for (Garage g : garage) {
            if (!g.getGarageEmptiness()) {
                int carId = g.getCarId();
                car = g.getCarById(carId);
                if (car != null) {
                    if ((timeNow - car.getCarRepairStartDate().getTime()) > repairTime) {
                        car.setCarStatus(CarStatus.REPAIR_COMPLETE);
                        parking.receiveCar(car, true);
                        g.removeCarById(carId); //TODO where
                        //System.out.println("Repaired " + car.toString());
                    } else {
                        System.out.println("Found no repaired cars");
                    }

                }
            }
        }
    }

    public void releaseWaiting() {
        int carId = parking.releaseWaiting();
        //System.out.println(parking.toString());
        if (carId > 0) {
            //System.out.println("Returned" + carId);
            car = getCarById(carId);
            //System.out.println(car.toString());
            if (car != null) {
                //System.out.println("-----Returned " + carId);
                //parking.releaseCar(car);
                //TODO issue here
                //receiveCar(car);
                Garage g = findFirstEmptyGarage();
                if (g != null) {
                    parking.removeCarById(carId);
                    g.receiveCar(car);
                }
            }
        }
    }

    public Car getCarById(int carId) {
        /*
        if (carId == -1) {
            return null;
        }
        */
        if (carId <= 0) {
            throw new IllegalArgumentException("Not existing car ID");
        }
        for (CarIdService cs : carIdService) {
            Car car = cs.getCarById(carId);
            //System.out.println(cs.toString());
            if (car != null) {
                //cs.removeCarById(carId); //TODO commented removal
                return car;
            }
        }
        return null;
    }

    @Override
    public void removeCarById(int carId) {
        throw new UnsupportedOperationException(); //TODO ok?
    }

    public CarStatus getCarStatusById(int carId) {
        if (carId <= 0) {
            throw new IllegalArgumentException("Not existing car ID");
        }
        car = getCarById(carId);
        return (car == null) ? CarStatus.NEVER_RECEIVED : car.getCarStatus();
    }
}
