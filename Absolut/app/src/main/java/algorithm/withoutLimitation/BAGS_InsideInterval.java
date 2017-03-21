package algorithm.withoutLimitation;

import algorithm.Settings;
import storage.Dot;

public class BAGS_InsideInterval extends BAGS {
    public BAGS_InsideInterval(Settings settings) {
        super(settings);
        canCalculateIntervalBoundary = false;
        name = "BAGS_InsideInterval";
    }

    @Override
    public Dot next() {
        Dot result = new Dot();
        Dot leftDot = testDots.getDot(numberIntervalWithMaxCharact - 1);
        Dot rightDot = testDots.getDot(numberIntervalWithMaxCharact);
        double m = constLipschitz.get(numberIntervalWithMaxCharact - 1);
        if (numberIntervalWithMaxCharact == 1 || numberIntervalWithMaxCharact == testDots.getIntervalCount()) {
            result.x = 0.5 * (rightDot.x + leftDot.x);
        } else {
            result.x = 0.5 * (rightDot.x + leftDot.x) - (rightDot.y - leftDot.y) / (2 * m);
        }
        return result;
    }

    @Override
    protected double getCharactInterval(int numberInterval) {
        double m = constLipschitz.get(numberInterval - 1);

        double lengthInterval = testDots.getLengthInterval(numberInterval);

        if (numberInterval == 1) {
            double rightValueOfInterval = testDots.getDot(numberInterval).y;
            return m * lengthInterval - 4 * testDots.getDot(numberInterval).y;
        } else if (numberInterval == testDots.getIntervalCount()) {
            return m * lengthInterval - 4 * testDots.getDot(numberInterval - 1).y;
        } else {
            double leftValueOfInterval = testDots.getDot(numberInterval - 1).y;
            double rightValueOfInterval = testDots.getDot(numberInterval).y;

            return m * lengthInterval +
                    settings.getParameter() * (rightValueOfInterval - leftValueOfInterval) *
                                      (rightValueOfInterval - leftValueOfInterval) /
                                      (calculateM(numberInterval) * lengthInterval) -
                    2 * (rightValueOfInterval + leftValueOfInterval);
        }
    }
}
