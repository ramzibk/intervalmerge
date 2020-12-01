package com.rbk.intervalmerge;

import java.util.Objects;

public class Interval {
    Integer min;
    Integer max;

    public Interval() {
        this(0, 0);
    }

    public Interval(Integer min, Integer max){
        this.min = min;
        this.max = max;
    }

    public Interval(String string){
        String[] split = extractValues(string);
        this.setMin(Integer.parseInt(split[0]));
        this.setMax(Integer.parseInt(split[1]));
    }

    private static String[] extractValues(String string) {
        String s = string.trim().replaceAll("[\\[\\] ]", "");
        return s.split(",");
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Interval)) return false;
        Interval interval = (Interval) o;
        return getMin().equals(interval.getMin()) &&
                getMax().equals(interval.getMax());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMin(), getMax());
    }

    @Override
    public String toString() {
        return "["+min+","+max+"]";
    }
}
