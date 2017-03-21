package task;

import java.util.ArrayList;
import java.util.List;

import algorithm.Settings;
import algorithm.withLimitation.SequentialScanWithLimitation;
import function.AbstractFunction;
import function.HillFunction;
import function.IFunction;
import storage.Dot;

public abstract class TaskWithLimitations extends AbstractTask {
    /*private class G1 extends AbstractFunction {
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
    }*/

    public TaskWithLimitations(final Settings settings) {
        //mLimitationFunctions.add(new HillFunction());
        //mLimitationFunctions.add(new HillFunction());
        mLimitationFunctions.add(new HillFunction());
        minimizedFunction = new HillFunction();
    }

    @Override
    public void setLimitationFunctions(final List<IFunction> functions) {
        setNewAlgorithm(functions.size());
        mLimitationFunctions = functions;
        calculateExactValueOfFunction();
        mAlgorithm.reset();
    }

    @Override
    public void run() {
        mAlgorithm.reset();
        setStartPoints();
        while (mAlgorithm.hasNext()) {
            Dot dot = mAlgorithm.next();
            calculateIndexAndValue(dot);
            mAlgorithm.addDotInStorage(dot);
        }
    }

    protected abstract void calculateIndexAndValue(final Dot dot);

    protected abstract void setNewAlgorithm(int newNubberLimitedFunctions);
}
