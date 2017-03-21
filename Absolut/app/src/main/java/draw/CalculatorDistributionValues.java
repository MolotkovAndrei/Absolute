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

public class CalculatorDistributionValues extends DrawerSensor {
    public CalculatorDistributionValues(ITask task, Rect drawPanel) {
        super(task, drawPanel);
    }

    public CalculatorDistributionValues(IFunction function, List<Dot> dots, Rect drawPanel) {
        super(function, dots, drawPanel);
    }

    @Override
    public void calculatePointsForDraw() {
        super.calculatePointsForDraw();
    }

    @Override
    public void draw(Canvas canvas, int index) {
        paint.setColor(Color.argb(255, 255, 0, 255));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);

        for (int i = 0; i < index; i++) {
            canvas.drawLine(drawPanel.left, drawPoints.get(i).y,
                    drawPanel.right, drawPoints.get(i).y, paint);
        }

        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);

        canvas.drawRect(drawPanel, paint);
    }
}
