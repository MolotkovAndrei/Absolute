package draw;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

import function.IFunction;
import storage.Dot;
import task.ITask;

public class CalculatorDensityValues extends DrawerSensor {
    private final int NUMBER_OF_COLUMNS = 10;
    private int[][] densityPoints = new int[302][NUMBER_OF_COLUMNS];
    private int stepOfColumn;
    double coefficientHeight = 1;
    int maxCountPoints = 1;
    private Rect column = new Rect();
    private Rect realDrawPanel = new Rect();

    public CalculatorDensityValues(ITask task, Rect drawPanel) {
        super(task, drawPanel);
        realDrawPanel.set(drawPanel);
        this.drawPanel.set(0, 0, 300, 300);
    }

    public CalculatorDensityValues(IFunction function, List<Dot> dots, Rect drawPanel) {
        super(function, dots, drawPanel);
        realDrawPanel.set(drawPanel);
        this.drawPanel.set(0, 0, 300, 300);
    }

    @Override
    public void setDrawPanel(Rect drawPanel) {
        realDrawPanel.set(drawPanel);
        stepOfColumn = realDrawPanel.height() / NUMBER_OF_COLUMNS;
        coefficientHeight = (double)realDrawPanel.width() / maxCountPoints;
    }

    @Override
    public void calculatePointsForDraw() {
        if (densityPoints == null) {
            return;
        }
        super.calculatePointsForDraw();
        int stepOfColumn = drawPanel.height() / NUMBER_OF_COLUMNS;
        for (int i = 0; i < drawPoints.size(); i++) {
            for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
                densityPoints[i][j] = 0;
            }
        }
        int normalCoordinate;
        for (int i = 0; i < drawPoints.size(); i++) {
            for (int j = 0; j < i; j++) {
                normalCoordinate = drawPoints.get(j).y - drawPanel.top;
                if (normalCoordinate / stepOfColumn >= NUMBER_OF_COLUMNS) {
                    densityPoints[i][NUMBER_OF_COLUMNS - 1]++;
                } else  if (normalCoordinate / stepOfColumn < 0){
                    densityPoints[i][0]++;
                } else {
                    densityPoints[i][normalCoordinate / stepOfColumn]++;
                }
            }
        }
        if (!drawPoints.isEmpty()) {
            maxCountPoints = densityPoints[drawPoints.size() - 1][0];
            for (int i = 1; i < NUMBER_OF_COLUMNS; i++) {
                if (densityPoints[drawPoints.size() - 1][i] > maxCountPoints) {
                    maxCountPoints = densityPoints[drawPoints.size() - 1][i];
                }
            }
        }
        realDrawPanel.set(drawPanel);
        this.stepOfColumn = realDrawPanel.height() / NUMBER_OF_COLUMNS;
        coefficientHeight = (double)realDrawPanel.width() / maxCountPoints;
    }

    @Override
    public void draw(Canvas canvas, int index) {
        if (index > 0 && index == dots.size()) {
            index--;
        }
        int height;
        paint.setColor(Color.YELLOW);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        for (int i = 0; i < NUMBER_OF_COLUMNS; i++) {
            height = (int)(densityPoints[index][i] * coefficientHeight);
            column.set(drawPanel.right - height, drawPanel.top + i * stepOfColumn,
                    drawPanel.right, drawPanel.top + (i + 1) * stepOfColumn);

            canvas.drawRect(column, paint);
        }

        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        for (int i = 0; i < NUMBER_OF_COLUMNS; i++) {
            height = (int)(densityPoints[index][i] * coefficientHeight);
            column.set(drawPanel.right - height, drawPanel.top + i * stepOfColumn,
                    drawPanel.right, drawPanel.top + (i + 1) * stepOfColumn);

            canvas.drawRect(column, paint);
        }
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);

        canvas.drawRect(drawPanel, paint);
    }
}
