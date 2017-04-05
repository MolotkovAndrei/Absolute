package draw;

import android.graphics.Rect;

import java.util.List;

import function.IFunction;

import static java.lang.Math.max;

public class HiderInvalidPointsPenaltyFunction extends HiderInvalidPoints {
    public HiderInvalidPointsPenaltyFunction(Rect drawPanel, List<IFunction> limitedFunctions) {
        super(drawPanel, limitedFunctions);
    }

    @Override
    protected boolean isValidPoint(double x) {
        final double EPS = 0.0000000001;
        double penalty = 0.0;
        for (IFunction function : limitedFunctions) {
            double maxValue = max(function.getValue(x) - function.getLimitationLevel(), 0.0);
            penalty += maxValue * maxValue;
        }
        return (penalty < EPS);
    }
}
