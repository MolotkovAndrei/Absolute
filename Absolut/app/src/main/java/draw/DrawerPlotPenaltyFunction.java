package draw;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

import function.IFunction;
import function.PenaltyFunction;
import task.ITask;

public class DrawerPlotPenaltyFunction extends DrawerPlotInvalidPoints {
    public DrawerPlotPenaltyFunction(IFunction function, Rect drawPanel,
                                     HiderInvalidPoints hiderInvalidPoints) {
        super(function, drawPanel, hiderInvalidPoints);
    }

    @Override
    public void draw(Canvas canvas, int index) {
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(drawPanel, paint);

        hiderInvalidPoints.draw(canvas);

        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(4);
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

        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
        canvas.drawRect(drawPanel, paint);
    }
}
