package com.mentoring.workshop.client;

import com.mentoring.workshop.car.Car;
import com.mentoring.workshop.car.CarStatus;
import com.mentoring.workshop.garageshop.Workroom;


public final class Reception implements ClientService {
    private final Workroom workroom;

    public Reception(Workroom workroom) {
        this.workroom = workroom;
    }

    public void receiveOrder(Client client, boolean toRepair) {
        if (client == null) {
            throw new IllegalArgumentException("Received null client");
        }
        Car car = client.getCar();
        if (toRepair) {
            System.out.println("RECEPTION to WORKROOM = " + car.toString());
            workroom.receiveCar(car);
            //workroom.receiveCar(car.getCarId());
        } else {
            //System.out.println("RECEPTION to parking = " + car.toString());
            workroom.receiveCar(car, false);
        }
        client.setCar(null);
    }

    public void releaseOrder(Client client) {
        CarStatus carStatus = getCarStatus(client);

        int carId = client.getCarId();

        if (CarStatus.PARKED.equals(carStatus) || CarStatus.REPAIR_COMPLETE.equals(carStatus)) {  //TODO What is faster equals or ==
            Car car = workroom.getCarById(carId);
            if (car != null) {
                client.setCar(car);
                workroom.releaseCar(carId);
            }
        } else {
            throw new IllegalStateException("Car is not ready");
        }

    }

    public CarStatus getCarStatus(Client client) {
        int carId = client.getCarId();
        if (carId > 0) {
            return workroom.getCarStatusById(carId);
        }
        return CarStatus.NEVER_RECEIVED;
    }
}
