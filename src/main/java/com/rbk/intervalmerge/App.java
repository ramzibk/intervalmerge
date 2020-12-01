package com.rbk.intervalmerge;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        App app = new App();
        List<Interval> parsedIntervals = app.parseIntervals(args);
        List<Interval> mergedIntervals = app.mergeIntervals(parsedIntervals);
        System.out.println("Merged Intervals: "+mergedIntervals.stream().map(Interval::toString).collect(Collectors.joining()));
    }

    public List<Interval> mergeIntervals(List<Interval> intervals) {
        if (intervals==null || intervals.size()==0)
            return new ArrayList<>();
        if(intervals.size()==1)
            return intervals;

        List<Interval> sorted = sortIntervals(intervals);

        // merge the intervals
        Stack<Interval> stack = new Stack<>();
        stack.push(sorted.get(0));

        for(int i=1; i<sorted.size(); i++){
            Interval current = sorted.get(i);
            if(current.getMin()<=stack.peek().getMax()) {
                stack.peek().setMax(current.getMax());
            } else {
                stack.push(current);
            }
        }
        return new ArrayList<>(stack);
    }

    /**
     * sorts a list of intervals according to the min value
     * @param intervals the list of intervals
     * @return a sorted list in the ascending order
     */
    public List<Interval> sortIntervals(List<Interval> intervals) {
        // O(nLog(n))
        Collections.sort(intervals, (o1, o2) -> Integer.compare(o1.getMin(), o2.getMin()));
        return intervals;
    }

    /**
     * parse a string of intervals in the form [min1,max1][min2,max2]..[minn,maxn]
     * @param args a string of intervals
     * @return a list of intervals corresponding to the string intervals
     */
    public List<Interval> parseIntervals(String[] args){
        String join = String.join(" ",Arrays.asList(args));
        join = join.replace(" ","");
        Matcher matcher = Pattern.compile("\\[\\d*?,\\d*?\\]").matcher(join);
        List<Interval> intervals = new ArrayList<>();

        while (matcher.find()) {
            String strInterval = matcher.group();
            intervals.add(new Interval(strInterval));
        }

        return intervals;
    }
}
