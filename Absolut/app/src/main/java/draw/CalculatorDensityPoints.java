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

public class CalculatorDensityPoints extends DrawerSensor {
    private final int NUMBER_OF_COLUMNS = 10;
    private int[][] densityPoints = new int[302][NUMBER_OF_COLUMNS];
    private int stepOfColumn;
    double coefficientHeight = 1;
    int maxCountPoints = 1;
    private Rect column = new Rect();

    private Rect realDrawPanel = new Rect();

    public CalculatorDensityPoints(ITask task, Rect drawPanel) {
        super(task, drawPanel);
        colorSensor = Color.YELLOW;
        realDrawPanel.set(drawPanel);
        this.drawPanel.set(0, 0, 300, 300);
    }

    @Override
    public void setDrawPanel(Rect drawPanel) {
        realDrawPanel.set(drawPanel);
        stepOfColumn = realDrawPanel.width() / NUMBER_OF_COLUMNS;
        coefficientHeight = (double)realDrawPanel.height() / maxCountPoints;
    }

    @Override
    public void calculatePointsForDraw() {
        if (densityPoints == null) {
            return;
        }
        super.calculatePointsForDraw();
        setDefaultValues();
        calculateValuesOfDensity();
        calculateCoefficientHeight();
    }

    @Override
    public void draw(Canvas canvas, int index) {
        setPaintOptionsForSensor();
        drawColumns(canvas, index);
        setPaintOptionsForBorderColumns();
        drawBorderColumns(canvas, index);
        drawBorderDrawPanel(canvas);
    }

    @Override
    protected void setPaintOptionsForSensor() {
        paint.setColor(colorSensor);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    private void calculateCoefficientHeight() {
        if (!drawPoints.isEmpty()) {
            maxCountPoints = densityPoints[drawPoints.size() - 1][0];
            for (int i = 1; i < NUMBER_OF_COLUMNS; i++) {
                if (densityPoints[drawPoints.size() - 1][i] > maxCountPoints) {
                    maxCountPoints = densityPoints[drawPoints.size() - 1][i];
                }
            }
        }
        realDrawPanel.set(drawPanel);
        this.stepOfColumn = realDrawPanel.width() / NUMBER_OF_COLUMNS;
        coefficientHeight = (double)realDrawPanel.height() / maxCountPoints;
    }

    private void calculateValuesOfDensity() {
        int stepOfColumn = drawPanel.width() / NUMBER_OF_COLUMNS;

        for (int i = 0; i < drawPoints.size(); i++) {
            for (int j = 0; j <= i; j++) {
                if (drawPoints.get(j).x / stepOfColumn >= NUMBER_OF_COLUMNS) {
                    densityPoints[i][NUMBER_OF_COLUMNS - 1]++;
                } else {
                    densityPoints[i][drawPoints.get(j).x / stepOfColumn]++;
                }
            }
        }
    }

    private void setDefaultValues() {
        for (int i = 0; i < densityPoints.length; i++) {
            for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
                densityPoints[i][j] = 0;
            }
        }
    }

    private void drawColumns(Canvas canvas, int index) {
        for (int i = 0; i < NUMBER_OF_COLUMNS; i++) {
            int height = (int)(densityPoints[index][i] * coefficientHeight);
            column.set(i * stepOfColumn + realDrawPanel.left, realDrawPanel.bottom - height,
                    (i + 1) * stepOfColumn + realDrawPanel.left, realDrawPanel.bottom);
            canvas.drawRect(column, paint);
        }
    }

    private void drawBorderColumns(Canvas canvas, int index) {
        for (int i = 0; i < NUMBER_OF_COLUMNS; i++) {
            int height = (int)(densityPoints[index][i] * coefficientHeight);
            column.set(i * stepOfColumn + realDrawPanel.left, realDrawPanel.bottom - height,
                    (i + 1) * stepOfColumn + realDrawPanel.left, realDrawPanel.bottom);

            canvas.drawRect(column, paint);
        }
    }

    private void setPaintOptionsForBorderColumns() {
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
    }
}
