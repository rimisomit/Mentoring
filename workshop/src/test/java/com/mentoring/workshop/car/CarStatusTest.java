package com.mentoring.workshop.car;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by user on 9/18/14.
 */
public class CarStatusTest {

    CarStatus carStatus1;
    CarStatus carStatus2;
    CarStatus carStatus3;

    @Before
    public void setUp() throws Exception {
        carStatus1 = CarStatus.NEVER_RECEIVED;
        carStatus2 = CarStatus.GAVE_AWAY;
        carStatus3 = CarStatus.REPAIR_COMPLETE;
    }

    @Test
    public void getLocationTest() throws Exception {
        String location = "Parking";
        Assert.assertTrue(location.equals(carStatus3.getLocation()));
    }

}
