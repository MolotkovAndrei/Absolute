package task;

import java.util.List;

import algorithm.Settings;
import algorithm.withoutLimitation.BAGS;
import function.IFunction;
import function.PenaltyFunction;
import storage.Dot;

import static java.lang.Math.*;

public class PenaltyTask extends TaskWithLimitations {
    private IFunction penaltyFunction;
    public PenaltyTask(Settings settings) {
        super(settings);
        mAlgorithm = new BAGS(settings);
        exactAlgorithm = new BAGS(mSettings);
        penaltyFunction = new PenaltyFunction(minimizedFunction, mLimitationFunctions);
        calculateExactValueOfFunction();
    }

    @Override
    protected void setStartPoints() {
        Dot leftPoint = new Dot();
        Dot rightPoint = new Dot();

        leftPoint.x = penaltyFunction.getLeftPointOfRange();
        leftPoint.y = penaltyFunction.getValue(leftPoint.x);
        mAlgorithm.addDotInStorage(leftPoint);

        rightPoint.x = penaltyFunction.getRightPointOfRange();
        rightPoint.y = penaltyFunction.getValue(rightPoint.x);
        mAlgorithm.addDotInStorage(rightPoint);
    }

    @Override
    protected void calculateIndexAndValue(Dot dot) {
        /*double penalty = 0.0;
        for (IFunction function : mLimitationFunctions) {
            double maxValue = max(function.getValue(dot.x), 0.0);
            penalty += maxValue * maxValue;
        }*/
        dot.y = penaltyFunction.getValue(dot.x);// + penalty;
    }

    @Override
    public void setLimitationFunctions(final List<IFunction> functions) {
        setNewAlgorithm(functions.size());
        mLimitationFunctions = functions;
        penaltyFunction = new PenaltyFunction(minimizedFunction, mLimitationFunctions);
        calculateExactValueOfFunction();
        mAlgorithm.reset();
    }

    @Override
    protected void calculateExactValueOfFunction() {
        exactAlgorithm.reset();
        Dot leftPoint = new Dot();
        Dot rightPoint = new Dot();

        leftPoint.x = penaltyFunction.getLeftPointOfRange();
        leftPoint.y = penaltyFunction.getValue(leftPoint.x);
        exactAlgorithm.addDotInStorage(leftPoint);
        bestPoint = leftPoint;

        rightPoint.x = penaltyFunction.getRightPointOfRange();
        rightPoint.y = penaltyFunction.getValue(rightPoint.x);
        exactAlgorithm.addDotInStorage(rightPoint);

        if (bestPoint.y > rightPoint.y) {
            bestPoint = rightPoint;
        }

        while (exactAlgorithm.hasNext()) {
            Dot dot = exactAlgorithm.next();
            dot.y = penaltyFunction.getValue(dot.x);
            if (bestPoint.y > dot.y) {
                bestPoint = dot;
            }
            exactAlgorithm.addDotInStorage(dot);
        }
    }

    @Override
    protected void setNewAlgorithm(int newNumberLimitedFunctions) {
        if (mLimitationFunctions.size() != newNumberLimitedFunctions) {
            exactAlgorithm = new BAGS(mSettings);
            Settings settings = mAlgorithm.getSettings();
            mAlgorithm = new BAGS(settings);
        }
    }
}
