package com.mentoring.workshop.client;

import com.mentoring.workshop.garageshop.Workroom;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ClientTest {
    //private Car car;
    private Client client;
    private Reception reception;

    @Before
    public void setUp() throws Exception {
        //car = new Car();
        client = new Client(200);
        reception = new Reception(new Workroom(5, 1000));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createClientTest() throws Exception {
        client = new Client(0);
    }

    @Test
    public void getCarTest() throws Exception {
        Assert.assertEquals(client.getCarId(), 200);
    }

    @Test
    public void checkCarOnRpeirmentTest() throws Exception {
        reception.receiveOrder(client, true);
        Assert.assertNull(client.getCar());
    }

}
