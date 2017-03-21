package function;

import java.util.List;

import static java.lang.Math.*;

public class PenaltyFunction extends AbstractFunction {
    private IFunction minimizedFunction;
    private List<IFunction> limitedFunctions;

    public PenaltyFunction(IFunction minimizedFunction, List<IFunction> limitedFunctions) {
        this.minimizedFunction = minimizedFunction;
        this.limitedFunctions = limitedFunctions;
        this.leftPointRange = minimizedFunction.getLeftPointOfRange();
        this.rightPointRange = minimizedFunction.getRightPointOfRange();
        calculateMaxAndMinValueFunction();
    }

    @Override
    public double getValue(double x) {
        double penalty = 0.0;
        for (IFunction function : limitedFunctions) {
            double maxValue = max(function.getValue(x), function.getLimitationLevel());
            penalty += maxValue * maxValue;
        }
        return minimizedFunction.getValue(x) + penalty;
    }
}
