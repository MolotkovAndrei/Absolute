package algorithm.withoutLimitation;

import java.util.ArrayList;
import java.util.List;

import algorithm.Settings;
import storage.Dot;

public class BAGS extends Algorithm {
    private final double ZERO = 0.000000001;
    protected List<Double> constLipschitz;

    public BAGS(final Settings settings) {
        super(settings);
        constLipschitz = new ArrayList<>();
        name = "BAGS";
    }

    protected double calculateM(final int numberInterval) {
        double leftValueOfInterval = testDots.getDot(0).y;
        double rightValueOfInterval = testDots.getDot(1).y;

        double maxValue = Math.abs((rightValueOfInterval - leftValueOfInterval) /
                                    testDots.getLengthInterval(1));
        double currentValue;
        for (int i = 2; i <=  testDots.getIntervalCount(); i++) {
            leftValueOfInterval = rightValueOfInterval;
            rightValueOfInterval = testDots.getDot(i).y;
            currentValue = Math.abs((rightValueOfInterval - leftValueOfInterval) /
                                    testDots.getLengthInterval(i));
            if (maxValue < currentValue) {
                maxValue = currentValue;
            }
        }
        return maxValue;
    }

    protected double calculate_m(final int numberInterval) {
        double M = calculateM(numberInterval);
        if (M < ZERO) {
            return 1.0;
        } else {
            return settings.getParameter() * M;
        }
    }

    @Override
    public void addDotInStorage(final Dot dot) {
        super.addDotInStorage(dot);

        try {
            constLipschitz.clear();
            double m = calculate_m(1);
            for (int i = 0; i < testDots.getIntervalCount(); i++) {
                constLipschitz.add(m);
            }
        } catch (IndexOutOfBoundsException e) { }
    }

    @Override
    public Dot next() {
        Dot result = new Dot();
        Dot leftDot = testDots.getDot(numberIntervalWithMaxCharact - 1);
        Dot rightDot = testDots.getDot(numberIntervalWithMaxCharact);
        double m = constLipschitz.get(numberIntervalWithMaxCharact - 1);
        result.x = 0.5 * (rightDot.x + leftDot.x) - (rightDot.y - leftDot.y) / (2 * m);
        return result;
    }

    @Override
    protected double getCharactInterval(int numberInterval) {
        double m = constLipschitz.get(numberInterval - 1);
        double leftValueOfInterval = testDots.getDot(numberInterval - 1).y;
        double rightValueOfInterval = testDots.getDot(numberInterval).y;
        double lengthInterval = testDots.getLengthInterval(numberInterval);

        return m * lengthInterval +
                        (rightValueOfInterval - leftValueOfInterval) *
                        (rightValueOfInterval - leftValueOfInterval) /
                        (m * lengthInterval) -
                        2 * (rightValueOfInterval + leftValueOfInterval);
    }
}
