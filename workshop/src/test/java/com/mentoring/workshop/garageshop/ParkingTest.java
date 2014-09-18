package com.mentoring.workshop.garageshop;

import com.mentoring.workshop.car.Car;
import com.mentoring.workshop.car.CarStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Done
 */
public class ParkingTest {

    private Parking parking;
    private Car car;

    @Before
    public void setUp() {
        parking = new Parking();
        car = new Car(300);
    }

    @Test
    public void checkInstanceOfTest() {
        Assert.assertTrue(parking != null); //TODO WTF?
    }

    @Test
    public void returnParkingLoadTest() {
        Assert.assertTrue(parking.getParkingLoad() >= 0);
    }

    @Test
    public void placeCarToParkingAfterRepairTest() {
        parking.receiveCar(car, true);
        Assert.assertEquals(car.getCarStatus(), CarStatus.REPAIR_COMPLETE);
    }

    @Test
    public void placeCarToParkingBeforeRepairTest() {
        parking.receiveCar(car, false);
        Assert.assertNotEquals(car.getCarStatus(), CarStatus.REPAIRING);
    }

    @Test(expected = IllegalArgumentException.class)
    public void placeCarToParkingBeforeRepairNullTest() {
        parking.receiveCar(null, false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void placeCarToParkingAfterRepairAgainTest() {
        parking.receiveCar(car, true);
        parking.receiveCar(car, true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void placeCarToParkingBeforeRepairAgainTest() {
        parking.receiveCar(car, false);
        parking.receiveCar(car, false);
    }

    @Test
    public void releaseCarTest() {
        car.setCarStatus(CarStatus.WAITING_FOR_REPAIR);
        parking.releaseCar(car);
        Assert.assertEquals(parking.getParkingLoad(), 0);
    }

    @Test(expected = NullPointerException.class)
    public void releaseCarNullTest() {
        parking.releaseCar(null);
    }

    @Test
    public void releaseWaitingCarsTest() {
        Car car = new Car(100);
        Car car1 = new Car(200);
        Car car2 = new Car(300);
        parking.receiveCar(car, false);
        Assert.assertEquals(parking.releaseWaiting(), 100);
    }
}
