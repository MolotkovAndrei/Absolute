package algorithm.withoutLimitation;

import algorithm.Settings;
import storage.Dot;

public class SequentalScan extends Algorithm {
    public SequentalScan(final Settings settings) {
        super(settings);
        name = "SequentalScan";
    }

    @Override
    public Dot next() {
        Dot result = new Dot();
        //int numberInterval = getNumberIntervalWithMaxCharact();
        result.x = 0.5 * (testDots.getDot(numberIntervalWithMaxCharact - 1).x +
                          testDots.getDot(numberIntervalWithMaxCharact).x);
        return result;
    }

    @Override
    protected double getCharactInterval(final int numberInterval) {
        return testDots.getLengthInterval(numberInterval);
    }
}
