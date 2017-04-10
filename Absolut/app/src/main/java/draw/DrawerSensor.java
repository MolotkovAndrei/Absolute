package draw;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

import algorithm.Settings;
import function.IFunction;
import storage.Dot;
import task.ITask;

public abstract class DrawerSensor {
    protected IFunction function;
    protected List<Dot> dots;
    protected Dot exactValue;
    protected Settings settings;
    protected static float scaleFactor = 1.0f;

    protected List<Point> drawPoints;
    protected Rect drawPanel;
    protected Paint paint;
    protected int colorSensor = Color.WHITE;

    public DrawerSensor(ITask task, Rect drawPanel) {
        paint = new Paint();
        this.drawPanel = drawPanel;
        drawPoints = new ArrayList<>();
        this.function = task.getMinimizedFunction();
        this.dots = task.getStorage();
        this.settings = task.getSettings();
        this.exactValue = task.getExactValue();
        calculatePointsForDraw();
    }

    public DrawerSensor(IFunction function, Rect drawPanel) {
        this.function = function;
        paint = new Paint();
        this.drawPanel = drawPanel;
        drawPoints = new ArrayList<>();
        calculatePointsForDraw();
    }

    public DrawerSensor(IFunction function, List<Dot> dots, Rect drawPanel) {
        this.function = function;
        paint = new Paint();
        this.dots = dots;
        this.drawPanel = drawPanel;
        drawPoints = new ArrayList<>();
        calculatePointsForDraw();
    }

    public void setContent(ITask task) {
        this.function = task.getMinimizedFunction();
        this.dots = task.getStorage();
        calculatePointsForDraw();
    }

    public void setContent(IFunction function) {
        this.function = function;
        calculatePointsForDraw();
    }

    public void setContent(IFunction function, List<Dot> dots) {
        this.function = function;
        this.dots = dots;
        calculatePointsForDraw();
    }

    public void setDrawPanel(Rect drawPanel) {
        this.drawPanel = drawPanel;
        calculatePointsForDraw();
    }

    protected void calculatePointsForDraw() {
        convertPointsToPixelsPanel();
    }

    public List getDrawPoints() {
        return drawPoints;
    }

    public Rect getDrawPanel() {
        return drawPanel;
    }

    public abstract void draw(Canvas canvas, int index);

    protected abstract void setPaintOptionsForSensor();

    protected void convertPointsToPixelsPanel() {
        drawPoints.clear();
        int width = drawPanel.width();
        int height = drawPanel.height();

        double leftPointRange = function.getLeftPointOfRange();
        double lengthIntervalX = function.getRightPointOfRange() - leftPointRange;

        double minPointRange = function.getMinValueOnRange();
        double lengthIntervalY = function.getMaxValueOnRange() - minPointRange;

        int valPix;
        for (int i = 0; i < dots.size(); i++) {
            Point point = new Point();
            valPix = convertCoordToPix(width, lengthIntervalX, dots.get(i).x - leftPointRange);
            point.x = valPix + drawPanel.left;

            double coord = dots.get(i).y - minPointRange;
            valPix = convertCoordToPix(height, lengthIntervalY, coord);
            point.y = drawPanel.bottom - valPix;
            drawPoints.add(point);
        }
    }

    protected double convertPixToCoord(final int numberPixels, final double lengthRange, final int pix) {
        return pix * lengthRange / numberPixels;
    }

    protected int convertCoordToPix(final int numberPixels, final double lengthRange, final double coord) {
        return (int)(coord * numberPixels / lengthRange);
    }

    protected void drawBorderDrawPanel(Canvas canvas) {
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
        canvas.drawRect(drawPanel, paint);
    }
}
