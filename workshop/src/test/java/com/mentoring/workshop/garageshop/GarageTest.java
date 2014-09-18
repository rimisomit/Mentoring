package com.mentoring.workshop.garageshop;

import com.mentoring.workshop.car.Car;
import com.mentoring.workshop.car.CarLocation;
import com.mentoring.workshop.car.CarStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GarageTest {

    private Garage[] garage;
    private Car[] car;

    @Before
    public void setUp() {
        int size = 5;
        car = new Car[size];
        garage = new Garage[size];
        for (int i = 0, j = 100; i < size; i++, j = j + 100) {
            car[i] = new Car(j);
            garage[i] = new Garage(i + 1);
        }

    }

    @Test
    public void checkInstanceOfTest() {
        Assert.assertTrue(garage[0] instanceof CarLocation);
    }

    @Test
    public void returnEmptyGarageTest() {
        for (Garage g : garage) {
            Assert.assertTrue(g.getGarageEmptiness());
        }
    }

    @Test
    public void returnNotEmptyGarageTest() {
        garage[0].receiveCar(car[0]);
        Assert.assertFalse(garage[0].getGarageEmptiness());
    }

    @Test(expected = IllegalStateException.class)
    public void carAlreadyInGarageTest() {
        garage[0].receiveCar(car[0]);
        garage[0].receiveCar(car[1]);
    }

    @Test
    public void returnCarTest() {
        garage[0].receiveCar(car[0]);
        Assert.assertNotNull(garage[0].getCarById(garage[0].getCarId()));
        Assert.assertNull(garage[1].getCarById(garage[1].getCarId()));
    }

    @Test(expected = IllegalStateException.class)
    public void receiveRepairedCarTest() {
        garage[0].receiveCar(car[0], true);
    }

    @Test
    public void receiveCarTest() {
        garage[0].receiveCar(car[0], false);
        Assert.assertEquals(car[0].getCarStatus(), CarStatus.REPAIRING);
    }

    @Test(expected = IllegalArgumentException.class)
    public void releaseCarTest() {
        garage[0].releaseCar(car[0]);
    }
}
