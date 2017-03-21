package algorithm.withoutLimitation;

import algorithm.Settings;
import storage.Dot;

public class PolygonalMethod extends Algorithm {
    public PolygonalMethod(final Settings settings) {
        super(settings);
        name = "PolygonalMethod";
    }

    @Override
    public Dot next() {
        Dot result = new Dot();
        Dot leftDot = testDots.getDot(numberIntervalWithMaxCharact - 1);
        Dot rightDot = testDots.getDot(numberIntervalWithMaxCharact);
        result.x = 0.5 * (rightDot.x + leftDot.x) - (rightDot.y - leftDot.y) / (2 * settings.getParameter());
        return result;
    }

    @Override
    protected double getCharactInterval(int numberInterval) {
        return 0.5 * settings.getParameter() * testDots.getLengthInterval(numberInterval) -
                (testDots.getDot(numberInterval).y + testDots.getDot(numberInterval - 1).y) / 2;
    }
}
