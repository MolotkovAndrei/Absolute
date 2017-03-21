package task;

import java.util.ArrayList;
import java.util.List;

import algorithm.IAlgorithm;
import algorithm.Settings;
import function.IFunction;
import storage.Dot;

public abstract class AbstractTask implements ITask {
    protected List<IFunction> mLimitationFunctions = new ArrayList<>();
    protected IFunction minimizedFunction;
    protected IAlgorithm mAlgorithm;
    private String name = "";
    protected Dot bestPoint;
    protected final double ACCURACY = 1.0e-6;
    protected final int NUMBER_ITERATIONS = 300;
    protected final double PARAMETER = 3.0;
    protected IAlgorithm exactAlgorithm;
    protected Settings mSettings = new Settings(ACCURACY, NUMBER_ITERATIONS, PARAMETER);

    @Override
    public void setLimitationFunctions(final List<IFunction> functions) { }

    @Override
    public void setMinimizedFunction(IFunction function) {
        minimizedFunction = function;
        calculateExactValueOfFunction();
        mAlgorithm.reset();
    }

    @Override
    public IFunction getMinimizedFunction() {
        return minimizedFunction;
    }

    @Override
    public List<IFunction> getLimitationFunctions() {
        return mLimitationFunctions;
    }

    @Override
    public void setAlgorithm(final IAlgorithm algorithm) {
        boolean[] sensors = mAlgorithm.getSettings().getCheckedSensors();
        mAlgorithm = algorithm;
        mAlgorithm.getSettings().setCheckedSensors(sensors);
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public IAlgorithm getAlgorithm() {
        return mAlgorithm;
    }

    @Override
    public Settings getSettings() {
        return mAlgorithm.getSettings();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Dot> getStorage() {
        return mAlgorithm.getTestDots();
    }

    @Override
    public Dot getExactValue() {
        return bestPoint;
    }

    protected void setStartPoints() {
        Dot leftPoint = new Dot();
        Dot rightPoint = new Dot();

        leftPoint.x = minimizedFunction.getLeftPointOfRange();
        leftPoint.y = minimizedFunction.getValue(leftPoint.x);
        mAlgorithm.addDotInStorage(leftPoint);

        rightPoint.x = minimizedFunction.getRightPointOfRange();
        rightPoint.y = minimizedFunction.getValue(rightPoint.x);
        mAlgorithm.addDotInStorage(rightPoint);
    }

    protected void calculateExactValueOfFunction() {
        exactAlgorithm.reset();
        Dot leftPoint = new Dot();
        Dot rightPoint = new Dot();

        leftPoint.x = minimizedFunction.getLeftPointOfRange();
        leftPoint.y = minimizedFunction.getValue(leftPoint.x);
        exactAlgorithm.addDotInStorage(leftPoint);
        bestPoint = leftPoint;

        rightPoint.x = minimizedFunction.getRightPointOfRange();
        rightPoint.y = minimizedFunction.getValue(rightPoint.x);
        exactAlgorithm.addDotInStorage(rightPoint);

        if (bestPoint.y > rightPoint.y) {
            bestPoint = rightPoint;
        }

        while (exactAlgorithm.hasNext()) {
            Dot dot = exactAlgorithm.next();
            dot.y = minimizedFunction.getValue(dot.x);
            if (bestPoint.y > dot.y) {
                bestPoint = dot;
            }
            exactAlgorithm.addDotInStorage(dot);
        }
    }
}
