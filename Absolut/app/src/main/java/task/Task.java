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
    public Task(final Settings settings) {
        List<IFunction> functions = new ArrayList<>();
        minimizedFunction = new F7();
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
}
