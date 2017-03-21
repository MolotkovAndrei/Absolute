package algorithm.withoutLimitation;

import java.util.ArrayList;
import java.util.List;

import algorithm.Settings;
import storage.Dot;

enum Pattern {
    GLOBAL(1),
    LOCAL(9);

    private Pattern(final int q) {
        this.degreeLocal = Math.pow(10, -q);
    }

    private double degreeLocal;

    public void setDegreeLocal(final int q) {
        this.degreeLocal = Math.pow(10, -q);
    }

    public double getDegreeLocal() {
        return  degreeLocal;
    }
}

public class LocallyAdaptive_BAGS extends BAGS {
    protected Pattern pattern = Pattern.GLOBAL;
    private List<Double> normDiff = new ArrayList<>();

    public LocallyAdaptive_BAGS(Settings settings) {
        super(settings);
        Pattern.LOCAL.setDegreeLocal(settings.getDegreeLocally());
        name = "LocallyAdaptive_BAGS";
    }

    @Override
    public void reset() {
        super.reset();
        pattern = Pattern.GLOBAL;
        Pattern.LOCAL.setDegreeLocal(settings.getDegreeLocally());
    }

    @Override
    protected double getCharactInterval(int numberInterval) {
        double Ri = super.getCharactInterval(numberInterval);

        return Ri / (Math.sqrt(normDiff.get(numberInterval - 1) * normDiff.get(numberInterval)) +
        pattern.getDegreeLocal() * testDots.getLengthDomain());
    }

    @Override
    public void addDotInStorage(final Dot dot) {
        super.addDotInStorage(dot);

        if (testDots.getIntervalCount() > 0) {
            calculateNormalizeDifference();
        }

        if (currentIteration >= settings.getStepTurnOnMix()) {
            setPattern();
        }
    }

    protected void setPattern() {
        if (pattern == Pattern.GLOBAL) {
            pattern = Pattern.LOCAL;
        }
    }

    private void calculateNormalizeDifference() {
        normDiff.clear();
        double mu = calculateMu();
        for (int i = 0; i < testDots.getIntervalCount() + 1; i++) {
            normDiff.add((testDots.getDot(i).y - testDots.getMinValueOfTests()) / mu);
        }
    }

    private double calculateMu() {
        double leftValue = testDots.getDot(0).y;
        double rightValue = testDots.getDot(1).y;
        double result = Math.abs(rightValue - leftValue) / testDots.getLengthInterval(1);
        double tmp;
        for (int i = 2; i <= testDots.getIntervalCount(); i++) {
            leftValue = rightValue;
            rightValue = testDots.getDot(i).y;
            tmp = Math.abs(rightValue - leftValue) / testDots.getLengthInterval(i);

            if (result < tmp) {
                result = tmp;
            }
        }
        return result;
    }
}
