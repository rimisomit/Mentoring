package com.mentoring.workshop.car;

import com.mentoring.workshop.garageshop.Garage;
import com.mentoring.workshop.garageshop.Parking;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static java.lang.Thread.sleep;

/**
 *
 */
public class CarTest {

    private Car car;
    private Car car1;
    private Garage garage;
    private Parking parking;

    @Before
    public void setUp() throws Exception {
        car = new Car(100);
        car1 = new Car(100);
        garage = new Garage(1);
        parking = new Parking();
    }

    @Test
    public void showCarDefaultStatusTest() throws Exception {
        Assert.assertEquals(CarStatus.NEVER_RECEIVED, car.getCarStatus());
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkCarIdConstructorTest() throws Exception {
        int carId = -100;
        car = new Car(carId);
    }

    @Test
    public void showCarRepairingStatusTest() throws Exception {
        car.setCarStatus(CarStatus.REPAIRING);
        Assert.assertEquals(CarStatus.REPAIRING, car.getCarStatus());
    }

    @Test
    public void showCarStatusInGarageTest() throws Exception {
        garage.receiveCar(car);
        CarStatus carStatus = car.getCarStatus(); //TODO Can I do this in one line?
        Assert.assertEquals(CarStatus.REPAIRING, carStatus);
    }

    @Test(expected = NullPointerException.class)
    public void showCarStatusInGarageNullTest() throws Exception {
        garage.receiveCar(null);
        Assert.assertTrue(garage.getGarageEmptiness());
    }

    @Test
    public void showCarStatusInParkingAfterRepairTest() throws Exception {
        parking.receiveCar(car, true);
        Assert.assertEquals(CarStatus.REPAIR_COMPLETE, car.getCarStatus());
    }

    @Test
    public void showCarStatusInParkingBeforeRepairTest() throws Exception {
        parking.receiveCar(car, false);
        Assert.assertEquals(CarStatus.WAITING_FOR_REPAIR, car.getCarStatus());
    }

    @Test
    public void showCarId() throws Exception {
        Assert.assertNotNull(car.getCarId());
    }

    @Test
    public void returnDefaultCarRepairStartDateTest() throws Exception {
        Assert.assertNull(car.getCarRepairStartDate());
    }

    @Test
    public void returnCarRepairStartDateInGarageTest() throws Exception {
        garage.receiveCar(car);
        sleep(1100);
        Date date = new Date();
        Assert.assertNotNull(car.getCarRepairStartDate());
        Assert.assertTrue(car.getCarRepairStartDate().getTime() < date.getTime());
    }

    @Test
    public void carEqualsTest() throws Exception {
        Assert.assertTrue(car.equals(car1));
        garage.receiveCar(car1);
        Assert.assertFalse(car.equals(car1));
    }

    @Test
    public void carHashCodeTest() throws Exception {
        garage.receiveCar(car);
        parking.receiveCar(car1);
        Assert.assertFalse(car.hashCode() == car1.hashCode());
    }

}
