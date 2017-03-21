package algorithm.withoutLimitation;

import algorithm.Settings;
import storage.Dot;

public class BAGS_WithLocalSettings extends BAGS {
    private final double MU_0 = 0.000001;
    private double M;

    public BAGS_WithLocalSettings(Settings settings) {
        super(settings);
        name = "BAGS_WithLocalSettings";
    }

    @Override
    protected double calculateM(final int numberInterval) {
        double MU_1 = calculateMU_1(numberInterval);
        double MU_2 = calculateMU_2(numberInterval);

        return Math.max(Math.max(MU_1, MU_2), MU_0);
    }

    @Override
    public void addDotInStorage(final Dot dot) {
        super.addDotInStorage(dot);

        try {
            constLipschitz.clear();
            M = super.calculateM(1);
            for (int i = 0; i < testDots.getIntervalCount(); i++) {
                constLipschitz.add(calculate_m(i + 1));
            }
        } catch (IndexOutOfBoundsException e) { }
    }

    private double calculateMU_1(final int numberInterval) {
        if (testDots.getIntervalCount() == 1) {
            return Math.abs(testDots.getDot(1).y - testDots.getDot(0).y) / testDots.getLengthInterval(1);
        }
        if (numberInterval == 1) {
            double value1 = Math.abs(testDots.getDot(numberInterval).y - testDots.getDot(numberInterval - 1).y) /
                    testDots.getLengthInterval(numberInterval);
            double value2 = Math.abs(testDots.getDot(numberInterval + 1).y - testDots.getDot(numberInterval).y) /
                    testDots.getLengthInterval(numberInterval + 1);
            return Math.max(value1, value2);
        }
        if (numberInterval == testDots.getIntervalCount()) {
            double value1 = Math.abs(testDots.getDot(numberInterval - 1).y - testDots.getDot(numberInterval - 2).y) /
                    testDots.getLengthInterval(numberInterval - 1);
            double value2 = Math.abs(testDots.getDot(numberInterval).y - testDots.getDot(numberInterval - 1).y) /
                    testDots.getLengthInterval(numberInterval);
            return Math.max(value1, value2);
        } else {
            double value1 = Math.abs(testDots.getDot(numberInterval - 1).y - testDots.getDot(numberInterval - 2).y) /
                    testDots.getLengthInterval(numberInterval - 1);
            double value2 = Math.abs(testDots.getDot(numberInterval).y - testDots.getDot(numberInterval - 1).y) /
                    testDots.getLengthInterval(numberInterval);
            double value3 = Math.abs(testDots.getDot(numberInterval + 1).y - testDots.getDot(numberInterval).y) /
                    testDots.getLengthInterval(numberInterval + 1);
            return Math.max(Math.max(value1, value2), value3);
        }
    }

    private double calculateMU_2(final int numberInterval) {
        return M * testDots.getLengthInterval(numberInterval) / testDots.getMaxLengthInterval();
    }
}
