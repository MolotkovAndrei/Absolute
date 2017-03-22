package task;

import java.util.ArrayList;
import java.util.List;

import algorithm.Settings;
import algorithm.withoutLimitation.BAGS;
import function.IFunction;
import function.PenaltyFunction;
import storage.Dot;

import static java.lang.Math.*;

public class PenaltyTask extends TaskWithLimitations {
    private IFunction penaltyFunction;
    private List<List<Dot>> testPointsForFunctions;

    public PenaltyTask(Settings settings) {
        super(settings);
        mAlgorithm = new BAGS(settings);
        exactAlgorithm = new BAGS(mSettings);
        penaltyFunction = new PenaltyFunction(minimizedFunction, mLimitationFunctions);
        calculateExactValueOfFunction();

        testPointsForFunctions = new ArrayList<>();
        for (int i = 0; i <= mLimitationFunctions.size(); i++) {
            testPointsForFunctions.add(new ArrayList<Dot>());
        }
    }

    @Override
    protected void setStartPoints() {
        for (List a : testPointsForFunctions) {
            a.clear();
        }

        Dot leftPoint = new Dot();
        Dot rightPoint = new Dot();

        leftPoint.x = penaltyFunction.getLeftPointOfRange();
        leftPoint.y = penaltyFunction.getValue(leftPoint.x);
        mAlgorithm.addDotInStorage(leftPoint);

        addDotInTestPointFunctions(leftPoint);

        rightPoint.x = penaltyFunction.getRightPointOfRange();
        rightPoint.y = penaltyFunction.getValue(rightPoint.x);
        mAlgorithm.addDotInStorage(rightPoint);

        addDotInTestPointFunctions(rightPoint);
    }

    private void addDotInTestPointFunctions(Dot point) {
        Dot leftDotMinimizedFunction = new Dot();
        leftDotMinimizedFunction.x = point.x;
        leftDotMinimizedFunction.y = minimizedFunction.getValue(point.x);
        testPointsForFunctions.get(0).add(leftDotMinimizedFunction);
        for (int i = 0; i < mLimitationFunctions.size(); i++) {
            Dot leftDotLimitedFunction = new Dot();
            leftDotLimitedFunction.x = point.x;
            leftDotLimitedFunction.y = mLimitationFunctions.get(i).getValue(point.x);
            testPointsForFunctions.get(i + 1).add(leftDotLimitedFunction);
        }
    }

    @Override
    protected void calculateIndexAndValue(Dot dot) {
        dot.y = minimizedFunction.getValue(dot.x);
        Dot dotMinimizedFunction = new Dot();
        dotMinimizedFunction.x = dot.x;
        dotMinimizedFunction.y = dot.y;
        testPointsForFunctions.get(0).add(dotMinimizedFunction);
        double penalty = 0.0;
        for (int i = 0; i < mLimitationFunctions.size(); i++) {
            double limitationLevel = mLimitationFunctions.get(i).getLimitationLevel();
            Dot value = new Dot();
            value.x = dot.x;
            value.y = mLimitationFunctions.get(i).getValue(value.x);
            testPointsForFunctions.get(i + 1).add(value);
            double maxValue = max(value.y, limitationLevel);
            penalty += maxValue * maxValue;
        }
        dot.y  += penalty;
    }

    @Override
    public void setLimitationFunctions(final List<IFunction> functions) {
        setNewAlgorithm(functions.size());
        mLimitationFunctions = functions;
        penaltyFunction = new PenaltyFunction(minimizedFunction, mLimitationFunctions);
        calculateExactValueOfFunction();
        mAlgorithm.reset();
    }

    public List<List<Dot>> getTestPointsForFunctions() {
        return testPointsForFunctions;
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
