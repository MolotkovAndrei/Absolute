package algorithm.withLimitation;

import algorithm.Settings;
import storage.Dot;

public class SequentialScanWithLimitation extends AlgorithmWithLimitations {
    private Dot leftDot, rightDot;
    private double mu, z;
    private double delta;

    public SequentialScanWithLimitation(final Settings settings, final int numberFunctions) {
        super(settings, numberFunctions);
        //parameter = settings.getParameter();
    }

    @Override
    public Dot next() {
        Dot result = new Dot();
        leftDot = testDots.getDot(numberIntervalWithMaxCharacteristic - 1);
        rightDot = testDots.getDot(numberIntervalWithMaxCharacteristic);
        if (leftDot.index == rightDot.index) {
            mu = mAttributesOfSetOfPointsList.get(rightDot.index).getMu();
            result.x = (rightDot.x + leftDot.x) / 2 - (rightDot.y - leftDot.y) / (2 * parameter * mu);
        } else {
            result.x = (rightDot.x + leftDot.x) / 2;
        }
        return result;
    }

    @Override
    protected double getCharacteristicInterval(final int numberInterval) {
        leftDot = testDots.getDot(numberInterval - 1);
        rightDot = testDots.getDot(numberInterval);
        if (leftDot.index == rightDot.index) {
            return characteristicWhenIndexesAreEqual(numberInterval);
        }
        if (rightDot.index > leftDot.index) {
            return characteristicWhenRightIndexMore(numberInterval);
        }
        return characteristicWhenLeftIndexMore(numberInterval);
    }

    private double characteristicWhenIndexesAreEqual(final int numberInterval) {
        mu = mAttributesOfSetOfPointsList.get(rightDot.index).getMu();
        z = mAttributesOfSetOfPointsList.get(rightDot.index).getZ();
        delta = testDots.getLengthInterval(numberInterval);

        return delta + (rightDot.y - leftDot.y) * (rightDot.y - leftDot.y) /
                (parameter * parameter * mu * mu * delta) -
                2 * (rightDot.y + leftDot.y - 2 * z) / (parameter * mu);
    }

    private double characteristicWhenRightIndexMore(final int numberInterval) {
        delta = testDots.getLengthInterval(numberInterval);
        mu = mAttributesOfSetOfPointsList.get(rightDot.index).getMu();
        z = mAttributesOfSetOfPointsList.get(rightDot.index).getZ();

        return 2 * delta - 4 * (rightDot.y - z) / (parameter * mu);
    }

    private double characteristicWhenLeftIndexMore(final int numberInterval) {
        delta = testDots.getLengthInterval(numberInterval);
        mu = mAttributesOfSetOfPointsList.get(leftDot.index).getMu();
        z = mAttributesOfSetOfPointsList.get(leftDot.index).getZ();

        return 2 * delta - 4 * (leftDot.y - z) / (parameter * mu);
    }
}
