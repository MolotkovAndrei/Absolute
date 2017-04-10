package draw;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import function.IFunction;

public class DrawerPlotIndexFunction extends DrawerPlotInvalidPoints {
    public DrawerPlotIndexFunction(IFunction function, Rect drawPanel, HiderInvalidPoints hiderInvalidPoints) {
        super(function, drawPanel, hiderInvalidPoints);
    }

    @Override
    public void draw(Canvas canvas, int index) {
       drawBackgroundPlot(canvas);

        setPaintOptionsForSensor();
        Point leftPoint = drawPoints.get(0);
        Point rightPoint;
        for (int i = 1; i < drawPoints.size(); i++) {
            rightPoint = drawPoints.get(i);
            canvas.drawLine((float)leftPoint.x,
                    (float)leftPoint.y,
                    (float)rightPoint.x,
                    (float)rightPoint.y, paint);
            leftPoint = rightPoint;
        }

        hiderInvalidPoints.draw(canvas);

        drawBorderDrawPanel(canvas);
    }
}
