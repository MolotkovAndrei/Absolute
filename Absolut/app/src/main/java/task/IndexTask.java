package task;

import algorithm.Settings;
import algorithm.withLimitation.SequentialScanWithLimitation;
import storage.Dot;

public class IndexTask extends TaskWithLimitations {

    public IndexTask(Settings settings) {
        super(settings);
        mAlgorithm = new SequentialScanWithLimitation(settings, mLimitationFunctions.size() + 1);
        exactAlgorithm = new SequentialScanWithLimitation(mSettings, mLimitationFunctions.size() + 1);
        calculateExactValueOfFunction();
    }

    @Override
    protected void setStartPoints() {
        Dot leftPoint = new Dot();
        Dot rightPoint = new Dot();
        Dot middlePoint = new Dot();

        leftPoint.x = mLimitationFunctions.get(0).getLeftPointOfRange();
        leftPoint.y = Double.MAX_VALUE;
        leftPoint.index = 0;
        mAlgorithm.addDotInStorage(leftPoint);

        rightPoint.x = mLimitationFunctions.get(0).getRightPointOfRange();
        rightPoint.y = Double.MAX_VALUE;
        rightPoint.index = 0;
        mAlgorithm.addDotInStorage(rightPoint);


        middlePoint.x = 0.5 * (leftPoint.x + rightPoint.x);
        calculateIndexAndValue(middlePoint);
        mAlgorithm.addDotInStorage(middlePoint);
    }

    @Override
    protected void calculateExactValueOfFunction() {
        exactAlgorithm.reset();
        Dot leftPoint = new Dot();
        Dot rightPoint = new Dot();
        Dot middlePoint = new Dot();

        leftPoint.x = mLimitationFunctions.get(0).getLeftPointOfRange();
        leftPoint.y = Double.MAX_VALUE;
        leftPoint.index = 0;
        exactAlgorithm.addDotInStorage(leftPoint);

        rightPoint.x = mLimitationFunctions.get(0).getRightPointOfRange();
        rightPoint.y = Double.MAX_VALUE;
        rightPoint.index = 0;
        exactAlgorithm.addDotInStorage(rightPoint);

        middlePoint.x = 0.5 * (leftPoint.x + rightPoint.x);
        calculateIndexAndValue(middlePoint);
        exactAlgorithm.addDotInStorage(middlePoint);

        int index = mLimitationFunctions.size() + 1;
        if (middlePoint.index == index) {
            bestPoint = middlePoint;
        } else {
            bestPoint = new Dot();
            bestPoint.y = Double.MAX_VALUE;
        }

        while (exactAlgorithm.hasNext()) {
            Dot dot = exactAlgorithm.next();
            calculateIndexAndValue(dot);
            if (dot.index == index && bestPoint.y > dot.y) {
                bestPoint = dot;
            }
            exactAlgorithm.addDotInStorage(dot);
        }
    }

    @Override
    protected void calculateIndexAndValue(final Dot dot) {
        double valueFunction;
        double limitationLevel;
        int i;
        for (i = 0; i < mLimitationFunctions.size(); i++) {
            valueFunction = mLimitationFunctions.get(i).getValue(dot.x);
            limitationLevel = mLimitationFunctions.get(i).getLimitationLevel();
            if (valueFunction > limitationLevel) {
                dot.y = valueFunction;
                dot.index = i + 1;
                return;
            }
        }
        dot.y = minimizedFunction.getValue(dot.x);
        dot.index = i + 1;
    }

    @Override
    protected void setNewAlgorithm(int newNumberLimitedFunctions) {
        if (mLimitationFunctions.size() != newNumberLimitedFunctions) {
            exactAlgorithm = new SequentialScanWithLimitation(mSettings, newNumberLimitedFunctions + 1);
            Settings settings = mAlgorithm.getSettings();
            mAlgorithm = new SequentialScanWithLimitation(settings, newNumberLimitedFunctions + 1);
        }
    }
}
