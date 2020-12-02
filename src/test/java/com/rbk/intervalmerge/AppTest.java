package com.rbk.intervalmerge;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    final App app = new App();

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
        List<Interval> intervals = new ArrayList<>(Arrays.asList(new Interval(12,6), new Interval(2,3), new Interval(4,77)));
        List<Interval> sorted = app.sortIntervals(intervals);

        List<Interval> expected = Arrays.asList( new Interval(2, 3), new Interval(4, 77), new Interval(12, 6));
        assertEquals(intervals.size(), sorted.size());
        for(Interval interval : expected) {
            int index = expected.indexOf(interval);
            assertEquals(interval, sorted.get(index));
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

        List<Interval> expected = new ArrayList<>();
        expected.add(new Interval(2,8));
        expected.add(new Interval(12,27));
        assertEquals(expected, merged);
    }

    @Test
    public void testPerformance() {
        System.out.println("testing performance..");
        final int TIMES = 5;
        Map<Integer, List<Long>> timeMap = new HashMap<>();
        int size;
        for(int i=1;i<=TIMES;i++) {
            for (int j = 1; j <= 10; j++) {
                size = j*10000;
                long time = getMergingTime(size*10);
                timeMap.computeIfAbsent(size, k -> new ArrayList<>());
                timeMap.get(size).add(time);
            }
        }

        timeMap.entrySet().parallelStream().sorted(Comparator.comparingLong(Map.Entry::getKey)).forEach(
                entry -> System.out.println("run merge "+TIMES+" times for " + entry.getKey() + " intervals with "
                        + timeMap.get(entry.getKey()).parallelStream().reduce(0L, Long::sum)/TIMES + "ms"
                        +" mean time"));
    }

    private long getMergingTime(int size) {
        List<Interval> intervals = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            int min = (int) (Math.random() * 100);
            intervals.add(new Interval(min, min + (int) (Math.random() * 100)));
        }
        long startTime = System.currentTimeMillis();
        app.mergeIntervals(intervals);
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
}
