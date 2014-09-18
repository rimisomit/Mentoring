package com.mentoring.workshop.garageshop;

import com.mentoring.workshop.car.Car;
import com.mentoring.workshop.car.CarStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WorkroomTest {

    private Workroom workroom;
    private Car car;
    private int garagesCount = 5;

    @Before
    public void setUp() throws Exception {
        car = new Car(100);
        workroom = new Workroom(garagesCount, 1000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setWorkroomTest() throws Exception {
        workroom = new Workroom(-50, 1000);
        workroom = new Workroom(0, 1000);
        workroom = new Workroom(-50, 0);
        workroom = new Workroom(0, -10);
    }

    @Test
    public void receiveNewCarStatusTest() {
        workroom.receiveCar(car);
        Assert.assertEquals(CarStatus.REPAIRING, car.getCarStatus());
    }

    @Test(expected = NullPointerException.class)
    public void receiveNewCarStatusNullTest() {
        workroom.receiveCar(null);
    }

    @Test
    public void receiveNewCarStatusToClientTest() {
        workroom.receiveCar(car);
        Assert.assertEquals(CarStatus.REPAIRING, workroom.getCarStatusById(car.getCarId()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getCarWithUnproperIDTest() throws Exception {
        //workroom.getCarStatus(null);
        workroom.getCarStatusById(0);
        workroom.getCarStatusById(-1);
    }

    /*
        @Test(expected = NullPointerException.class)
        public void takeOutCarNullTest() {
            workroom.releaseCar(null);
        }
    */
    @Test(expected = UnsupportedOperationException.class)
    public void releaseCarByIdTest() {
        workroom.removeCarById(car.getCarId());
    }
}
