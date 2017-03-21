package storage;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;

public class TestDots implements IStorage {
    private List<Dot> sortPoints;
    private List<Dot> serialPoints;

    private double minValueOfFunction = Double.MAX_VALUE;
    private double maxValueOfFunction = Double.MIN_VALUE;

    public TestDots() {
        sortPoints = new ArrayList<>();
        serialPoints = new ArrayList<>();
    }

    @Override
    public void clearAllDots() {
        serialPoints.clear();
        sortPoints.clear();
        minValueOfFunction = Double.MAX_VALUE;
        maxValueOfFunction = Double.MIN_VALUE;
    }

    @Override
    public void addDot(Dot dot) {
        serialPoints.add(dot);
        sortPoints.add(dot);

        if (minValueOfFunction > dot.y) {
            minValueOfFunction = dot.y;
        }

        if (maxValueOfFunction < dot.y) {
            maxValueOfFunction = dot.y;
        }

        for (int i = sortPoints.size() - 1; i > 0; i--) {
            if (sortPoints.get(i).x < sortPoints.get(i - 1).x) {
                Dot temp;
                temp = sortPoints.get(i);
                sortPoints.set(i, sortPoints.get(i - 1));
                sortPoints.set(i - 1, temp);
            } else {
                break;
            }
        }
    }

    @Override
    public List<Dot> getAllDots() {
        return serialPoints;
    }

    /*@Override
    public List<Dot> getSortDots() {
        return sortPoints;
    }*/

    @Override
    public int getIntervalCount() {
        return serialPoints.size() - 1;
    }

    @Override
    public double getLengthInterval(final int numberInterval) {
        return sortPoints.get(numberInterval).x -
                sortPoints.get(numberInterval - 1).x;
    }

    @Override
    public Dot getDot(final int numberDot) {
        return sortPoints.get(numberDot);
    }

    @Override
    public double getLengthDomain() {
        return serialPoints.get(1).x - serialPoints.get(0).x;
    }

    @Override
    public double getMaxLengthInterval() {
        double maxLength = getLengthInterval(1);
        for (int i = 2; i <= getIntervalCount(); i++) {
            if (maxLength < getLengthInterval(i)) {
                maxLength = getLengthInterval(i);
            }
        }
        return maxLength;
    }

    @Override
    public double getMinValueOfTests() {
        return minValueOfFunction;
    }

    @Override
    public double getMaxValueOfTests() {
        return maxValueOfFunction;
    }
}
