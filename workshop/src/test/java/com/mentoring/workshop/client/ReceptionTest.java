package com.mentoring.workshop.client;

import com.mentoring.workshop.car.Car;
import com.mentoring.workshop.car.CarStatus;
import com.mentoring.workshop.garageshop.Workroom;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static java.lang.Thread.sleep;

public class ReceptionTest {
    private final static int INTERVAL = 1100;
    private Client[] client;
    private Reception reception0;
    private Reception reception1;

    @Before
    public void setUp() throws Exception {
        client = new Client[8];
        for (int i = 0, j = 100; i <= 7; i++, j = j + 100) {
            client[i] = new Client(j);
        }
        reception0 = new Reception(new Workroom(5, 1000));
        reception1 = new Reception(new Workroom(5, 1000));
    }

    @Test
    public void clientGetCarTest() {
        reception0.receiveOrder(client[0], true);
        reception1.receiveOrder(client[1], false);
        Assert.assertNotEquals(reception0.getCarStatus(client[0]), CarStatus.PARKED);
        Assert.assertEquals(reception1.getCarStatus(client[1]), CarStatus.PARKED);
    }

    @Test
    public void repairmentGetCarTest() {
        reception0.receiveOrder(client[0], true);
        reception1.receiveOrder(client[1], false);
        Assert.assertNotEquals(reception0.getCarStatus(client[0]), CarStatus.PARKED);
        Assert.assertEquals(reception1.getCarStatus(client[1]), CarStatus.PARKED);
    }

    @Test(expected = IllegalArgumentException.class)
    public void receiveOderNullTest() {
        reception0.receiveOrder(null, true);
        reception0.receiveOrder(null, false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void placeTwoEqualsCarsToParkingTest() {
        reception0.receiveOrder(client[0], false);
        reception0.receiveOrder(client[1], false);
        reception0.receiveOrder(client[2], false);
        reception0.receiveOrder(client[2], false);
    }

    @Test
    public void placeSixCarsToGaragesTest() {
        reception0.receiveOrder(client[0], true);
        //System.out.println("-----------------");
        reception0.receiveOrder(client[1], true);
        //System.out.println("-----------------");
        reception0.receiveOrder(client[2], true);
        //System.out.println("-----------------");
        reception0.receiveOrder(client[3], true);
        reception0.receiveOrder(client[4], true);
        reception0.receiveOrder(client[5], true);
        Assert.assertEquals(reception0.getCarStatus(client[0]), CarStatus.REPAIRING);
        Assert.assertEquals(reception0.getCarStatus(client[1]), CarStatus.REPAIRING);
        Assert.assertEquals(reception0.getCarStatus(client[2]), CarStatus.REPAIRING);
        Assert.assertEquals(reception0.getCarStatus(client[3]), CarStatus.REPAIRING);
        Assert.assertEquals(reception0.getCarStatus(client[4]), CarStatus.REPAIRING);
        Assert.assertEquals(reception0.getCarStatus(client[5]), CarStatus.WAITING_FOR_REPAIR);
    }

    @Test
    public void repairCarsFromParkingTest() throws InterruptedException {
        reception0.receiveOrder(client[0], true);
        reception0.receiveOrder(client[1], true);
        reception0.receiveOrder(client[2], true);
        reception0.receiveOrder(client[3], true);
        reception0.receiveOrder(client[4], true);
        //out of capacity
        reception0.receiveOrder(client[5], true); //will go to parking
        sleep(INTERVAL);
        reception0.receiveOrder(client[6], true);
        //Client1..4 REPAIR_COMPLETE
        /*
        System.out.println(client0.getCar().toString());
        System.out.println(client1.getCar().toString());
        System.out.println(client2.getCar().toString());
        System.out.println(client3.getCar().toString());
        System.out.println(client4.getCar().toString());
        System.out.println(client5.getCar().toString());
        System.out.println(client6.getCar().toString());
        */
        Assert.assertEquals(reception0.getCarStatus(client[4]), CarStatus.REPAIR_COMPLETE);
        Assert.assertEquals(reception0.getCarStatus(client[5]), CarStatus.REPAIRING);
        Assert.assertEquals(reception0.getCarStatus(client[6]), CarStatus.REPAIRING);
        sleep(INTERVAL);
        //Assert.assertEquals(client5.getCar().getCarStatus(), CarStatus.REPAIR_COMPLETE);
    }

    @Test
    public void repairOneCarTest() throws InterruptedException {
        reception0.receiveOrder(client[0], true);
        sleep(INTERVAL);
        reception0.receiveOrder(client[1], true);
        Assert.assertEquals(reception0.getCarStatus(client[0]), CarStatus.REPAIR_COMPLETE);
    }

    @Test
    public void checkCarStatusTest() {
        reception0.receiveOrder(client[0], true);
        reception0.receiveOrder(client[1], true);
        reception0.getCarStatus(client[0]);
    }

    @Test(expected = IllegalStateException.class)
    public void releaseUnrepairedCarTest() {
        reception0.receiveOrder(client[0], true);
        reception0.releaseOrder(client[0]);
        Assert.assertNull(client[0].getCar());
    }

    @Test
    public void releaseCarStatusTest() throws Exception {
        Car car = client[0].getCar();
        reception0.receiveOrder(client[0], true);
        sleep(2000);
        reception0.receiveOrder(client[1], true);
        reception0.releaseOrder(client[0]);
        Assert.assertEquals(car.getCarStatus(), CarStatus.GAVE_AWAY);

    }
}
