package com.rbk.intervalmerge;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IntervalTest {

    @Test
    public void testEquals(){
        Interval interval = new Interval(45, 13);
        assertEquals(interval,new Interval(45, 13));
        assertNotEquals(interval, new Interval(20, 8));
    }


    @Test
    void testInterval_from_String(){
        Interval interval;
        interval = new Interval("[12,5]");
        assertEquals(new Interval(12,5), interval);

        assertTrue(new Interval(" [ 12 , 5]  ").equals(new Interval(12,5)));
        assertEquals(new Interval(12,5), interval);

        assertTrue(new Interval(" [ 12 , 9999]  ").equals(new Interval(12,9999)));
        assertEquals(new Interval(12,5), interval);

        assertTrue(new Interval(" [ 12 , 9999]  ").equals(new Interval(12,9999)));
        assertEquals(new Interval(12,5), interval);
    }
}
