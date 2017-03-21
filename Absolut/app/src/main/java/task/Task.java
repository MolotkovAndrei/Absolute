package task;

import java.util.ArrayList;
import java.util.List;

import algorithm.withoutLimitation.BAGS;
import algorithm.Settings;
import function.AbstractFunction;
import function.F7;
import function.IFunction;
import function.PenaltyFunction;
import storage.Dot;

public class Task extends AbstractTask {
    private class G1 extends AbstractFunction {
        public G1() {
            this.leftPointRange = -0.6;
            this.rightPointRange = 2.2;
            calculateMaxAndMinValueFunction();
        }

        @Override
        public double getValue(double x) {
            return Math.exp(-x / 2.0) * Math.sin(6.0 * x - 1.5);
        }
    }

    private class G2 extends AbstractFunction {
        public G2() {
            this.leftPointRange = -0.6;
            this.rightPointRange = 2.2;
            calculateMaxAndMinValueFunction();
        }

        @Override
        public double getValue(double x) {
            return Math.abs(x) * Math.sin(2.0 * Math.PI * x - 0.5);
        }
    }

    private class F extends AbstractFunction {
        public F() {
            this.leftPointRange = -0.6;
            this.rightPointRange = 2.2;
            calculateMaxAndMinValueFunction();
        }

        @Override
        public double getValue(double x) {
            return Math.cos(18.0 * x -3.0) * Math.sin(10.0 * x - 7.0) + 1.5;
        }
    }

    private class G3 extends AbstractFunction {
        public G3() {
            this.leftPointRange = -0.6;
            this.rightPointRange = 2.2;
            calculateMaxAndMinValueFunction();
        }
        @Override
        public double getValue(double x) {
            return Math.sin(4.0 * x - 2.2) + Math.cos(6.0 * x - 2.9);
        }
    }

    public Task(final Settings settings) {
        List<IFunction> functions = new ArrayList<>();
        functions.add(new G1());
        functions.add(new G3());
        functions.add(new G2());
        minimizedFunction = new PenaltyFunction(new F(), functions); //F7();
        mAlgorithm = new BAGS(settings);
        exactAlgorithm = new BAGS(mSettings);
        calculateExactValueOfFunction();
    }

    @Override
    public void run() {
        mAlgorithm.reset();
        setStartPoints();
        while (mAlgorithm.hasNext()) {
            Dot dot = mAlgorithm.next();
            dot.y = minimizedFunction.getValue(dot.x);
            mAlgorithm.addDotInStorage(dot);
        }
    }

    /*@Override
    protected void setStartPoints() {
        Dot leftPoint = new Dot();
        Dot rightPoint = new Dot();

        leftPoint.x = minimizedFunction.getLeftPointOfRange();
        leftPoint.y = minimizedFunction.getValue(leftPoint.x);
        mAlgorithm.addDotInStorage(leftPoint);

        rightPoint.x = minimizedFunction.getRightPointOfRange();
        rightPoint.y = minimizedFunction.getValue(rightPoint.x);
        mAlgorithm.addDotInStorage(rightPoint);
    }*/

    /*@Override
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
    }*/
}
