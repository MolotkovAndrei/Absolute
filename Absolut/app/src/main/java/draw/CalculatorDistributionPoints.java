package draw;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.List;

import function.IFunction;
import storage.Dot;
import task.ITask;

public class CalculatorDistributionPoints extends DrawerSensor {
    public CalculatorDistributionPoints(ITask task, Rect drawPanel) {
        super(task, drawPanel);
        colorSensor = Color.argb(255, 255, 0, 255);
    }

    public CalculatorDistributionPoints(IFunction function, List<Dot> dots, Rect drawPanel) {
        super(function, dots, drawPanel);
        colorSensor = Color.argb(255, 255, 0, 255);
    }

    @Override
    public void calculatePointsForDraw() {
        super.calculatePointsForDraw();
    }

    @Override
    public void draw(Canvas canvas, int index) {
        if (drawPoints.size() == 0) {
            drawBorderDrawPanel(canvas);
            return;
        }
        setPaintOptionsForSensor();
        for (int i = 0; i <= index; i++) {
            canvas.drawLine(drawPoints.get(i).x, drawPanel.top,
                    drawPoints.get(i).x, drawPanel.bottom, paint);
        }
        drawBorderDrawPanel(canvas);
    }

    @Override
    protected void setPaintOptionsForSensor() {
        paint.setColor(colorSensor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
    }
}
