package com.rbk.intervalmerge;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    App app = new App();

    @Test
    public void testParseIntervals(){
        String[] intervals = {"[25,30]", "[2,19]", "[14, 23]", "[4,8]"};
        List<Interval> parsed = app.parseIntervals(intervals);
        assertEquals(new Interval(25, 30), parsed.get(0));
        assertEquals(new Interval(2, 19), parsed.get(1));
        assertEquals(new Interval(14, 23), parsed.get(2));
        assertEquals(new Interval(4, 8), parsed.get(3));
    }

    @Test
    public void testSortIntervals(){
        List<Interval> intervals = new ArrayList<Interval>(Arrays.asList(new Interval(12,6), new Interval(2,3), new Interval(4,77)));
        List<Interval> sorted = app.sortIntervals(intervals);

        List<Interval> expected = Arrays.asList( new Interval(2, 3), new Interval(4, 77), new Interval(12, 6));
        assertEquals(intervals.size(), sorted.size());
        for(Interval interval : expected) {
            int index = expected.indexOf(interval);
            assertTrue(sorted.get(index).equals(interval));
        }
    }

    @Test
    public void testMerge(){
        List<Interval> intervals = new ArrayList<>();
        intervals.add(new Interval(2,8));
        intervals.add(new Interval(12,20));
        intervals.add(new Interval(18,24));
        intervals.add(new Interval(24,27));

        List<Interval> merged = app.mergeIntervals(intervals);

        List<Interval> expectd = new ArrayList<>();
        expectd.add(new Interval(2,8));
        expectd.add(new Interval(12,27));
        assertEquals(expectd, merged);
    }

    @Test
    public void testPerformance() {
        for(int size: Arrays.asList(10,100,1000,10_000,100_000,1_000_000)) {
            System.out.println("testing performace.");
            List<Interval> intervals = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                int min = (int) (Math.random() * 100);
                intervals.add(new Interval(min, min + (int) (Math.random() * 100)));
            }
            long startTime = System.currentTimeMillis();
            List<Interval> merged = app.mergeIntervals(intervals);
            long endTime = System.currentTimeMillis();
            System.out.println("test run for " + size + " intervals in " + (endTime - startTime) + "ms");
        }
    }

}
