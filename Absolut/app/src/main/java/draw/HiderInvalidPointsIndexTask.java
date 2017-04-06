package draw;

import android.graphics.Rect;

import java.util.List;

import function.IFunction;

public class HiderInvalidPointsIndexTask extends HiderInvalidPoints {
    public HiderInvalidPointsIndexTask(Rect drawPanel, List<IFunction> limitedFunctions) {
        super(drawPanel, limitedFunctions);
    }

    @Override
    protected boolean isValidPoint(double x) {
        double valueFunction;
        double limitedLevel;
        int i;
        for (i = 0; i < limitedFunctions.size(); i++) {
            valueFunction = limitedFunctions.get(i).getValue(x);
            limitedLevel = limitedFunctions.get(i).getLimitationLevel();
            if (valueFunction > limitedLevel) {
                return false;
            }
        }
        return true;
    }
}
