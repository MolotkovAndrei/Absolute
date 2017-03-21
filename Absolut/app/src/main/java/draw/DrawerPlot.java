package draw;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.List;

import function.IFunction;
import storage.Dot;
import task.ITask;

public class DrawerPlot extends DrawerSensor {

    public DrawerPlot(ITask task, Rect drawPanel) {
        super(task, drawPanel);
    }

    public DrawerPlot(IFunction function, Rect drawPanel) {
        super(function, drawPanel);
    }

    @Override
    public void calculatePointsForDraw() {
        super.calculatePointsForDraw();
    }

    @Override
    public void draw(Canvas canvas, int index) {
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(drawPanel, paint);

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

    @Override
    protected void convertPointsToPixelsPanel() {
        drawPoints.clear();
        int width = drawPanel.width();
        int height = drawPanel.height();

        double leftPointRange = function.getLeftPointOfRange();
        double lengthIntervalX = function.getRightPointOfRange() - leftPointRange;

        double minPointRange = function.getMinValueOnRange();
        double lengthIntervalY = function.getMaxValueOnRange() - minPointRange;

        int valPix;
        double coordX;
        double currentFunctionValue;
        for (int pix = 0; pix <= width; pix += 3) {
            Point point = new Point();
            coordX = convertPixToCoord(width, lengthIntervalX, pix) + leftPointRange;
            currentFunctionValue = function.getValue(coordX);
            valPix = convertCoordToPix(height, lengthIntervalY, currentFunctionValue - minPointRange);
            point.x = pix + drawPanel.left;
            point.y = drawPanel.bottom - valPix;
            drawPoints.add(point);
        }
    }
}
