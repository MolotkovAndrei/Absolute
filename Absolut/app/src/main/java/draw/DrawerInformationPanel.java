package draw;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import storage.Dot;
import task.ITask;

import static java.lang.Math.*;

public abstract class DrawerInformationPanel extends DrawerSensor {
    private Rect panelParametersTask = new Rect();
    private Rect panelResourcesTask = new Rect();
    private Rect panelBestPointTask = new Rect();
    private Rect panelCurrentPointTask = new Rect();
    private Rect panelAccuracySensor = new Rect();
    private Rect accuracyRect = new Rect();
    private Rect accuracyRectInto = new Rect();
    private int stepPanelParameters;
    private int stepPanelResources;
    private int stepPanelBestPoint;
    private int stepPanelCurrentPoint;
    private float[] accuracySegmentsX = new float[4];
    private float[] accuracySegmentsY = new float[4];
    private final double[] ACCURACY_SEGMENT = {1.0e-2, 1.0e-3, 1.0e-4, 1.0e-5};
    protected Dot currentPoint = new Dot();
    protected Dot bestPoint = new Dot();
    private int numberBestPoint = 1;
    private String bestPointX = "";
    private String bestPointY = "";
    private String currentPointX = "";
    private String currentPointY = "";
    private String numberBestPointStr = "";
    private String numberCurrentPointStr = "";
    private final int INDENT_TEXT_LEFT = 5;
    private final int INDENT_TEXT_TOP = 15;
    //private int indexPoints = 0;

    private int heightPanelParameter;
    private int heightPanelBestPoint;
    private final float COEFFICIENT_HEIGHT_PANEL_PARAMETER = 0.15f;
    private final float COEFFICIENT_INDENT_ACCURACY_SENSOR = 0.04f;
    private final float COEFFICIENT_TEXT_SIZE = 0.02f;
    private int indentAccuracySensor;

    public DrawerInformationPanel(ITask task, Rect drawPanel) {
        super(task, drawPanel);
        setContent(task);
    }

    @Override
    public void setDrawPanel(Rect drawPanel) {
        this.drawPanel = drawPanel;
        createOtherPanels();
    }

    @Override
    public void setContent(ITask task) {
        this.function = task.getMinimizedFunction();
        this.dots = task.getStorage();
        this.exactValue = task.getExactValue();
        this.settings = task.getSettings();
        setStartValues();
        setFirstValuePoints();
    }

    protected abstract void setFirstValuePoints();

    @Override
    public void draw(Canvas canvas, int index) {
        if (dots.size() != 0) {
            calculateBestPoint(index);
        } else {
            setStartValues();
        }
        calculateAccuracySensor(canvas);

        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(scaleFactor);
        paint.setTextSize(drawPanel.height() * COEFFICIENT_TEXT_SIZE);


        printInformationBestPoint(canvas);
        printInformationCurrentPoint(canvas);
        printInformationParametersTask(canvas);
        printInformationResources(canvas, index);
        drawAccuracySensor(canvas);
        paint.setStyle(Paint.Style.STROKE);
        drawFrame(canvas);
    }

    private void createOtherPanels() {
        if (drawPanel.width() < drawPanel.height()) {
            heightPanelParameter = (int)(drawPanel.height() * COEFFICIENT_HEIGHT_PANEL_PARAMETER);
            heightPanelBestPoint = heightPanelParameter;
            createVerticalOrientationPanels();
        } else {
            heightPanelParameter = (int)(drawPanel.height() * COEFFICIENT_HEIGHT_PANEL_PARAMETER);
            createHorizontalOrientationPanels();
        }
        createAccuracySensor();
        createAccuracySegments();
    }

    private void createHorizontalOrientationPanels() {
        int rightPointPanelForCurrentPoint = drawPanel.left + drawPanel.width() / 3;
        int rightPointPanelForParameters = drawPanel.left + drawPanel.width() / 2;

        panelParametersTask.set(drawPanel.left, drawPanel.top,
                rightPointPanelForParameters, drawPanel.top + heightPanelParameter);
        stepPanelParameters = panelParametersTask.height() / 2;

        panelResourcesTask.set(panelParametersTask.right, drawPanel.top,
                drawPanel.right, drawPanel.top + heightPanelParameter);
        stepPanelResources = stepPanelParameters;

        int heightPanelsCurrentPoint = (drawPanel.bottom - panelParametersTask.bottom) / 2;
        int bottomPointPanelForCurrentPoint = panelParametersTask.bottom + heightPanelsCurrentPoint;
        panelCurrentPointTask.set(drawPanel.left, panelParametersTask.bottom,
                rightPointPanelForCurrentPoint, bottomPointPanelForCurrentPoint);
        stepPanelCurrentPoint = panelCurrentPointTask.height() / 3;

        panelBestPointTask.set(drawPanel.left, panelCurrentPointTask.bottom,
                rightPointPanelForCurrentPoint, drawPanel.bottom);
        stepPanelBestPoint = stepPanelCurrentPoint;

        panelAccuracySensor.set(panelCurrentPointTask.right, panelCurrentPointTask.top,
                drawPanel.right, drawPanel.bottom);
    }

    private void createVerticalOrientationPanels() {
        int rightPointPanelForParameters = drawPanel.left + drawPanel.width() / 2;
        panelParametersTask.set(drawPanel.left, drawPanel.top,
                rightPointPanelForParameters, drawPanel.top + heightPanelParameter);
        stepPanelParameters = panelParametersTask.height() / 2;

        panelResourcesTask.set(panelParametersTask.right, drawPanel.top,
                drawPanel.right, drawPanel.top + heightPanelParameter);
        stepPanelResources = stepPanelParameters;

        panelBestPointTask.set(drawPanel.left, panelParametersTask.bottom,
                rightPointPanelForParameters, panelParametersTask.bottom + heightPanelBestPoint);
        stepPanelBestPoint = panelBestPointTask.height() / 3;

        panelCurrentPointTask.set(panelBestPointTask.right, panelParametersTask.bottom,
                drawPanel.right, panelBestPointTask.bottom);
        stepPanelCurrentPoint = stepPanelBestPoint;

        panelAccuracySensor.set(drawPanel.left, panelBestPointTask.bottom, drawPanel.right, drawPanel.bottom);
    }

    private void createAccuracySensor() {
        indentAccuracySensor = (int) (drawPanel.height() * COEFFICIENT_INDENT_ACCURACY_SENSOR);
        accuracyRect.set(panelAccuracySensor.left + indentAccuracySensor,
                panelAccuracySensor.top + indentAccuracySensor,
                panelAccuracySensor.right - indentAccuracySensor,
                panelAccuracySensor.bottom - indentAccuracySensor);
        accuracyRectInto.set(accuracyRect);
    }

    private void createAccuracySegments() {
        for (int i = 0; i < 4; i++) {
            accuracySegmentsX[i] = accuracySegmentsY[i] = (float) log10(ACCURACY_SEGMENT[i]) + 6;
            accuracySegmentsX[i] = (int) (accuracySegmentsX[i] * accuracyRect.width() / 5) + accuracyRect.left;
            accuracySegmentsY[i] = accuracyRect.bottom - (int) (accuracySegmentsY[i] * accuracyRect.height() / 5);
        }
    }

    private void printInformationResources(Canvas canvas, int index) {
        canvas.drawText("Ресурс: " + settings.getNumberIterations(), panelResourcesTask.left + INDENT_TEXT_LEFT,
                panelResourcesTask.top + INDENT_TEXT_TOP * scaleFactor, paint);
        if (dots.size() != 0) {
            canvas.drawText("Остаток: " + (settings.getNumberIterations() - (index + 1)), panelResourcesTask.left + INDENT_TEXT_LEFT,
                    panelResourcesTask.top + (INDENT_TEXT_TOP * scaleFactor + stepPanelResources), paint);
        } else {
            canvas.drawText("Остаток: " + settings.getNumberIterations(), panelResourcesTask.left + INDENT_TEXT_LEFT,
                    panelResourcesTask.top + (INDENT_TEXT_TOP * scaleFactor + stepPanelResources), paint);
        }
    }

    private void printInformationParametersTask(Canvas canvas) {
        canvas.drawText("Точность: " + settings.getEps(), panelParametersTask.left + INDENT_TEXT_LEFT,
                panelParametersTask.top + INDENT_TEXT_TOP * scaleFactor, paint);
        canvas.drawText("Надежность: " + settings.getParameter(), panelParametersTask.left + INDENT_TEXT_LEFT,
                panelParametersTask.top + (INDENT_TEXT_TOP * scaleFactor + stepPanelParameters), paint);
    }

    private void printInformationCurrentPoint(Canvas canvas) {
        canvas.drawText("Текущая " + numberCurrentPointStr,
                panelCurrentPointTask.left + INDENT_TEXT_LEFT,
                panelCurrentPointTask.top + INDENT_TEXT_TOP * scaleFactor, paint);
        canvas.drawText("Z = " + currentPointY,
                panelCurrentPointTask.left + INDENT_TEXT_LEFT,
                panelCurrentPointTask.top + (INDENT_TEXT_TOP * scaleFactor + stepPanelCurrentPoint), paint);
        canvas.drawText("X = " + currentPointX,
                panelCurrentPointTask.left + INDENT_TEXT_LEFT,
                panelCurrentPointTask.top + INDENT_TEXT_TOP * scaleFactor + 2 * stepPanelCurrentPoint, paint);
    }

    private void printInformationBestPoint(Canvas canvas) {
        canvas.drawText("Лучшая " + numberBestPointStr,
                panelBestPointTask.left + INDENT_TEXT_LEFT,
                panelBestPointTask.top + INDENT_TEXT_TOP * scaleFactor, paint);
        canvas.drawText("Z = " + bestPointY,
                panelBestPointTask.left + INDENT_TEXT_LEFT,
                panelBestPointTask.top + (stepPanelBestPoint + INDENT_TEXT_TOP * scaleFactor), paint);
        canvas.drawText("X = " + bestPointX,
                panelBestPointTask.left + INDENT_TEXT_LEFT,
                panelBestPointTask.top + 2 * stepPanelBestPoint + INDENT_TEXT_TOP * scaleFactor, paint);
    }

    private void drawFrame(Canvas canvas) {
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
        canvas.drawRect(panelBestPointTask, paint);
        canvas.drawRect(panelCurrentPointTask, paint);
        canvas.drawRect(panelAccuracySensor, paint);
        canvas.drawRect(panelResourcesTask, paint);
        canvas.drawRect(panelParametersTask, paint);
        canvas.drawRect(accuracyRect, paint);
    }

    private void calculateBestPoint(int index) {
        currentPoint.x = dots.get(index).x;
        currentPoint.y = dots.get(index).y;
        currentPoint.index = dots.get(index).index;
        roundNumber(currentPoint, 1000000);
        if (changeBestPoint(index)) {
            bestPointX = String.valueOf(bestPoint.x);
            bestPointY = String.valueOf(bestPoint.y);
            numberBestPointStr = String.valueOf(numberBestPoint);
        }
        currentPointX = String.valueOf(currentPoint.x);
        currentPointY = String.valueOf(currentPoint.y);
        numberCurrentPointStr = String.valueOf(index + 1);
    }

    protected boolean changeBestPoint(int index) {
        if (bestPoint.y > currentPoint.y) {
            setBestPoint(index);
            return true;
        }
        return false;
    }

    protected void setBestPoint(int index) {
        bestPoint.x = currentPoint.x;
        bestPoint.y = currentPoint.y;
        bestPoint.index = currentPoint.index;
        numberBestPoint = index + 1;
    }

    private void setStartValues() {
        currentPointX = "";
        currentPointY = "";
        bestPointX = "";
        bestPointY = "";
        numberBestPointStr = "";
        numberCurrentPointStr = "";
    }

    private void calculateAccuracySensor(Canvas canvas) {
        Point point = new Point();
        System.out.println("bestPoint: " + bestPoint.x);
        System.out.println("exactValue: " + exactValue.x);
        double x = Math.log10(Math.abs(bestPoint.x - exactValue.x)) + 6;
        System.out.println("X: " + x);
        double y = Math.log10(Math.abs(bestPoint.y - exactValue.y)) + 6;

        point.x = (int)(x * accuracyRect.width() / 5) + accuracyRect.left;
        if (point.x > accuracyRect.right){
            point.x = accuracyRect.right;
        } else if (point.x < accuracyRect.left) {
            point.x = accuracyRect.left;
        }
        point.y = accuracyRect.bottom - (int)(y * accuracyRect.height() / 5);
        if (point.y < accuracyRect.top) {
            point.y = accuracyRect.top;
        } else if (point.y > accuracyRect.bottom) {
            point.y = accuracyRect.bottom;
        }
        accuracyRectInto.set(accuracyRect.left, point.y, point.x, accuracyRect.bottom);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.GREEN);
        canvas.drawRect(accuracyRectInto, paint);

        float epsLineCoordinate = (float) Math.log10(settings.getEps()) + 6;
        epsLineCoordinate = (int) (epsLineCoordinate * accuracyRect.width() / 5) + accuracyRect.left;
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(4);
        canvas.drawLine(epsLineCoordinate, accuracyRect.top, epsLineCoordinate, accuracyRect.bottom, paint);
    }

    private void drawAccuracySensor(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(panelAccuracySensor, paint);
        canvas.drawRect(accuracyRect, paint);
        canvas.drawRect(accuracyRectInto, paint);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        final String LOG_DELTA_Z = "log \u0394Z";
        final String LOG_DELTA_X = "log \u0394X";
        final String MIN = "min";
        final int INDENT = 10;

        int textBottomBorder = indentAccuracySensor / 2;


        canvas.drawText(LOG_DELTA_Z, accuracyRect.left,
                panelAccuracySensor.top + textBottomBorder, paint);
        canvas.drawText(LOG_DELTA_X, accuracyRect.right - 30 * scaleFactor,
                accuracyRect.bottom + textBottomBorder, paint);
        canvas.drawText(MIN, accuracyRect.left,
                accuracyRect.bottom + textBottomBorder, paint);
        for (int i = 0; i < 4; i++) {
            canvas.drawLine(accuracySegmentsX[i], accuracyRect.bottom - INDENT * scaleFactor, accuracySegmentsX[i], accuracyRect.bottom, paint);
            canvas.drawLine(accuracyRect.left, accuracySegmentsY[i], accuracyRect.left + INDENT * scaleFactor, accuracySegmentsY[i], paint);
        }
    }

    protected void roundNumber(Dot dot, int roundMark) {
        dot.x *= roundMark;
        dot.x = Math.round(dot.x);
        dot.x /= roundMark;
        dot.y *= roundMark;
        dot.y = Math.round(dot.y);
        dot.y /= roundMark;
    }
}
