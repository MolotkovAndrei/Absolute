package draw;

import android.graphics.Canvas;
import android.graphics.Rect;

import function.IFunction;

public abstract class DrawerPlotInvalidPoints extends DrawerPlot {
    protected HiderInvalidPoints hiderInvalidPoints;

    public DrawerPlotInvalidPoints(IFunction function, Rect drawPanel, HiderInvalidPoints hiderInvalidPoints) {
        super(function, drawPanel);
        this.hiderInvalidPoints = hiderInvalidPoints;
    }

    @Override
    public void calculatePointsForDraw() {
        super.calculatePointsForDraw();
        if (hiderInvalidPoints != null) {
            hiderInvalidPoints.calculateHideRectangles();
        }
    }

    @Override
    public abstract void draw(Canvas canvas, int index);
}
