package algorithm.withoutLimitation;

import algorithm.Settings;
import storage.Dot;

public class Koushner extends Algorithm {
    public Koushner(final Settings settings) {
        super(settings);
        name = "Koushner";
    }

    @Override
    public Dot next() {
        Dot result = new Dot();
        double valueFunctionInLeftPoint = testDots.getDot(numberIntervalWithMaxCharact - 1).y;
        double valueFunctionInRightPoint = testDots.getDot(numberIntervalWithMaxCharact).y;
        double minValueFunction = testDots.getMinValueOfTests();
        result.x = testDots.getDot(numberIntervalWithMaxCharact - 1).x +
                testDots.getLengthInterval(numberIntervalWithMaxCharact) *
                        (minValueFunction - settings.getParameter() - valueFunctionInRightPoint) /
                        (2 * (minValueFunction - settings.getParameter()) - valueFunctionInRightPoint - valueFunctionInLeftPoint);
        return result;
    }

    @Override
    protected double getCharactInterval(int numberInterval) {
        double currentMinValueFunction = testDots.getMinValueOfTests();

        return -4 * (currentMinValueFunction - settings.getParameter() - testDots.getDot(numberInterval).y) *
                    (currentMinValueFunction - settings.getParameter() - testDots.getDot(numberInterval - 1).y) /
                    testDots.getLengthInterval(numberInterval);
    }
}
