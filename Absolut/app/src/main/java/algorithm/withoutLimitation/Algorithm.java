package algorithm.withoutLimitation;

import java.util.List;

import algorithm.IAlgorithm;
import algorithm.Settings;
import storage.Dot;
import storage.IStorage;
import storage.TestDots;

public abstract class Algorithm implements IAlgorithm {
    protected IStorage testDots;
    protected Settings settings;
    protected boolean canCalculateIntervalBoundary = true;
    protected String name;

    protected int currentIteration = 0;
    protected int numberIntervalWithMaxCharact;

    public Algorithm(final Settings settings) {
        testDots = new TestDots();
        this.settings = settings;
    }

    @Override
    public void reset() {
        testDots.clearAllDots();
        currentIteration = 0;
    }

    @Override
    public abstract Dot next();

    @Override
    public boolean hasNext() {
        double eps = settings.getEps() * testDots.getLengthDomain();
        numberIntervalWithMaxCharact = getNumberIntervalWithMaxCharact();
        return currentIteration < settings.getNumberIterations() &&
                testDots.getLengthInterval(numberIntervalWithMaxCharact) > eps;

    }

    @Override
    public void addDotInStorage(final Dot dot) {
        testDots.addDot(dot);
        currentIteration++;
    }

    @Override
    public void setSettings(final Settings settings) {
        this.settings = settings;
        reset();
    }

    @Override
    public Settings getSettings() {
        return settings;
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean canCalculateFunction() {
        return canCalculateIntervalBoundary;
    }

    protected abstract double getCharactInterval(final int numberInterval);

    private int getNumberIntervalWithMaxCharact() {
        double charactInterval = getCharactInterval(1);
        double maxCharactInterval = charactInterval;
        int result = 1;
        for (int interval = 2; interval <= testDots.getIntervalCount(); interval++) {
            charactInterval = getCharactInterval(interval);
            if (maxCharactInterval < charactInterval) {
                maxCharactInterval = charactInterval;
                result = interval;
            }
        }
        return result;
    }

    @Override
    public List<Dot> getTestDots() {
        return testDots.getAllDots();
    }
}
