package com.mentoring.workshop.car;

/**
 * Abstract for Garage and Parking
 */

public abstract class CarLocation implements CarService {
    protected Car car;
    protected int locationId;
}
