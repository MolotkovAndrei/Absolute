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
    protected final int COLOR_BACKGROUND_PLOT = Color.BLUE;

    public DrawerPlot(ITask task, Rect drawPanel) {
        super(task, drawPanel);
        colorSensor = Color.WHITE;
    }

    public DrawerPlot(IFunction function, Rect drawPanel) {
        super(function, drawPanel);
        colorSensor = Color.WHITE;
    }

    @Override
    public void calculatePointsForDraw() {
        super.calculatePointsForDraw();
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
        drawBorderDrawPanel(canvas);
    }

    @Override
    protected void setPaintOptionsForSensor() {
        paint.setColor(colorSensor);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(4);
    }

    protected void drawBackgroundPlot(Canvas canvas) {
        paint.setColor(COLOR_BACKGROUND_PLOT);
        paint.setStyle(Paint.Style.FILL);
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
