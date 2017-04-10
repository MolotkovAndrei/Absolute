package draw;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

import function.IFunction;
import storage.Dot;
import task.ITask;

public class CalculatorDynamicsPoints extends DrawerSensor {
    public CalculatorDynamicsPoints(ITask task, Rect drawPanel) {
        super(task, drawPanel);
        colorSensor = Color.argb(255, 255, 0, 255);
    }

    @Override
    public void calculatePointsForDraw() {
        super.calculatePointsForDraw();
        int height = drawPanel.height();
        int lengthIntervalY = drawPoints.size();
        int valuePixel;
        for (int i = 0; i < drawPoints.size(); i++) {
            valuePixel = i * height / lengthIntervalY;
            drawPoints.get(i).y = drawPanel.bottom - valuePixel;
        }
    }

    @Override
    public void draw(Canvas canvas, int index) {
        setPaintOptionsForSensor();
        for (int i = 1; i <= index; i++) {
            canvas.drawLine(drawPoints.get(i - 1).x, drawPoints.get(i -1).y,
                    drawPoints.get(i).x, drawPoints.get(i).y, paint);
        }
        drawBorderDrawPanel(canvas);
    }

    @Override
    protected void setPaintOptionsForSensor() {
        paint.setColor(colorSensor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
    }
}
