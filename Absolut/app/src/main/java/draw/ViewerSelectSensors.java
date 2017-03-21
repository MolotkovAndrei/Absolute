package draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import algorithm.Settings;
import function.HillFunction;
import function.IFunction;
import task.ITask;
import task.Task;

public class ViewerSelectSensors extends View {
    private final int COUNT_SENSORS = 6;
    private final int INDENT = 30;
    private int countSensorsInRow = 2;
    int sizeWindowForSensor;
    int heightCanvas;

    ITask task;
    private DrawerSensor[] drawerFunctions = new DrawerSensor[COUNT_SENSORS];
    private DrawerSensor[] verticalDrawerSensors = new DrawerSensor[COUNT_SENSORS];
    private DrawerSensor[] horizontalDrawerSensors = new DrawerSensor[COUNT_SENSORS];
    private Rect[] panels = new Rect[9];
    private Rect[] panelsForPlot = new Rect[9];
    private Rect[] panelsForSensors = new Rect[9];
    private boolean isVertical = true;

    Paint paint = new Paint();
    private boolean[][] isCheckSensorVertical = new boolean[3][2];
    private boolean[][] isCheckSensorHorizontal = new boolean[2][3];

    private final GestureDetector gestureDetector;

    public ViewerSelectSensors(Context context, AttributeSet attrs) {
        super(context, attrs);
        for (int i = 0; i < 9; i++) {
            panels[i] = new Rect();
            panelsForPlot[i] = new Rect();
            panelsForSensors[i] = new Rect();
        }
        task = new Task(new Settings(0.0001, 200, 2.0));
        IFunction f = task.getMinimizedFunction();
        drawerFunctions[0] = new DrawerPlot(task, panelsForPlot[0]);
        verticalDrawerSensors[0] = new CalculatorDistributionPoints(task, panelsForSensors[0]);
        horizontalDrawerSensors[0] = new CalculatorDistributionPoints(task, panelsForSensors[0]);

        drawerFunctions[1] = new DrawerPlot(task, panelsForPlot[1]);
        verticalDrawerSensors[1] = new CalculatorDistributionValues(task, panelsForSensors[1]);
        horizontalDrawerSensors[1] = new CalculatorDynamicsPoints(task, panelsForSensors[1]);

        drawerFunctions[2] = new DrawerPlot(task, panelsForPlot[2]);
        verticalDrawerSensors[2] = new CalculatorDynamicsPoints(task, panelsForSensors[2]);
        horizontalDrawerSensors[2] = new CalculatorDensityPoints(task, panelsForSensors[2]);

        drawerFunctions[3] = new DrawerPlot(task, panelsForPlot[3]);
        verticalDrawerSensors[3] = new CalculatorDynamicsValues(task, panelsForSensors[3]);
        horizontalDrawerSensors[3] = new CalculatorDistributionValues(task, panelsForSensors[3]);

        drawerFunctions[4] = new DrawerPlot(task, panelsForPlot[4]);
        verticalDrawerSensors[4] = new CalculatorDensityPoints(task, panelsForSensors[4]);
        horizontalDrawerSensors[4] = new CalculatorDynamicsValues(task, panelsForSensors[4]);

        drawerFunctions[5] = new DrawerPlot(task, panelsForPlot[5]);
        verticalDrawerSensors[5] = new CalculatorDensityValues(task, panelsForSensors[5]);
        horizontalDrawerSensors[5] = new CalculatorDensityValues(task, panelsForSensors[5]);
        task.run();
        verticalDrawerSensors[4].setContent(task);
        horizontalDrawerSensors[2].setContent(task);

        verticalDrawerSensors[5].setContent(task);
        horizontalDrawerSensors[5].setContent(task);

        gestureDetector = new GestureDetector(context, new MyGestureListener());

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setColor(Color.RED);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w < h) {
            isVertical = true;
            countSensorsInRow = 2;
        } else {
            isVertical = false;
            countSensorsInRow = 3;
        }
        createPanelsForSensors(w, h);
        for (int i = 0; i < COUNT_SENSORS / countSensorsInRow; i++) {
            for (int j = 0; j < countSensorsInRow; j++) {
                drawerFunctions[i * countSensorsInRow + j].setDrawPanel(panelsForPlot[i * countSensorsInRow + j]);
                if (isVertical) {
                    verticalDrawerSensors[i * countSensorsInRow + j].setDrawPanel(panelsForSensors[i * countSensorsInRow + j]);

                } else {
                    horizontalDrawerSensors[i * countSensorsInRow + j].setDrawPanel(panelsForSensors[i * countSensorsInRow + j]);
                }
            }
        }
        heightCanvas = INDENT + COUNT_SENSORS / countSensorsInRow * (INDENT + sizeWindowForSensor);
        scrollTo(0, 0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isVertical) {
            for (int i = 0; i < COUNT_SENSORS; i++) {
                verticalDrawerSensors[i].draw(canvas, task.getStorage().size() - 2);
            }
        } else {
            for (int i = 0; i < COUNT_SENSORS; i++) {
                horizontalDrawerSensors[i].draw(canvas, task.getStorage().size() - 2);
            }
        }

        for (int i = 0; i < COUNT_SENSORS; i++) {
            drawerFunctions[i].draw(canvas, 0);
        }
        for (int i = 0; i < COUNT_SENSORS / countSensorsInRow; i++) {
            for (int j = 0; j < countSensorsInRow; j++) {
                if (isVertical) {
                    if (isCheckSensorVertical[i][j]) {
                        canvas.drawRect(panels[i * countSensorsInRow + j], paint);
                    }
                } else {
                    if (isCheckSensorHorizontal[i][j]) {
                        canvas.drawRect(panels[i * countSensorsInRow + j], paint);
                    }
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (gestureDetector.onTouchEvent(event)) return true;
        return true;
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener
    {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
        {
            if (getScrollY() + distanceY < heightCanvas - getHeight() && getScrollY() + distanceY > 0) {
                scrollBy(0, (int) distanceY);
            }
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent event){
            float x = event.getX();
            float y = event.getY();
            int scrollX = getScrollX();
            int scrollY = getScrollY();
            int j = (int)((x + scrollX) / (INDENT + sizeWindowForSensor));
            int i = (int)((y+scrollY) / (INDENT + sizeWindowForSensor));
            if (isVertical) {
                isCheckSensorHorizontal[j][i] = !isCheckSensorVertical[i][j];
                isCheckSensorVertical[i][j]   = !isCheckSensorVertical[i][j];
            } else {
                isCheckSensorVertical[j][i]   = !isCheckSensorHorizontal[i][j];
                isCheckSensorHorizontal[i][j] = !isCheckSensorHorizontal[i][j];
            }
            invalidate();
            return true;
        }
    }

    public boolean[] getCheckedSensors() {
        boolean[] result = new boolean[6];
        if (isVertical) {
            for (int i = 0; i < COUNT_SENSORS / countSensorsInRow; i++) {
                for (int j = 0; j < countSensorsInRow; j++) {
                    result[i * countSensorsInRow + j] = isCheckSensorVertical[i][j];
                }
            }
        } else {
            for (int j = 0; j < countSensorsInRow; j++) {
                for (int i = 0; i < COUNT_SENSORS / countSensorsInRow; i++) {
                    result[j * (COUNT_SENSORS / countSensorsInRow) + i] = isCheckSensorHorizontal[i][j];
                }
            }
        }
        return result;
    }

    public void setCheckedSensors(boolean[] isCheckedSensors) {
        for (int i = 0; i < COUNT_SENSORS / countSensorsInRow; i++) {
            for (int j = 0; j < countSensorsInRow; j++) {
                if (isVertical) {
                    isCheckSensorVertical[i][j] = isCheckedSensors[i * countSensorsInRow + j];
                    isCheckSensorHorizontal[j][i] = isCheckSensorVertical[i][j];
                } else {
                    isCheckSensorHorizontal[i][j] = isCheckedSensors[i * countSensorsInRow + j];
                    isCheckSensorVertical[j][i] = isCheckSensorHorizontal[i][j];
                }
            }
        }
    }

    private void createPanelsForSensors(int widthCanvas, int heightCanvas) {
        sizeWindowForSensor = (widthCanvas - (countSensorsInRow + 1) * INDENT) / countSensorsInRow;
        for (int i = 0; i < COUNT_SENSORS / countSensorsInRow; i++) {
            for (int j = 0; j < countSensorsInRow; j++) {
                if (isVertical) {
                    panels[i * countSensorsInRow + j].set(INDENT + j * (INDENT + sizeWindowForSensor),
                            INDENT + i * (INDENT + sizeWindowForSensor),
                            widthCanvas - INDENT - (countSensorsInRow - 1 - j) * (INDENT + sizeWindowForSensor),
                            INDENT + sizeWindowForSensor + i * (INDENT + sizeWindowForSensor));
                    if (j % 2 == 0) {
                        panelsForPlot[i * countSensorsInRow + j].set(panels[i * countSensorsInRow + j].left,
                                panels[i * countSensorsInRow + j].top,
                                panels[i * countSensorsInRow + j].right,
                                panels[i * countSensorsInRow + j].bottom - (int) (0.25 * panels[i * countSensorsInRow + j].height()));
                        panelsForSensors[i * countSensorsInRow + j].set(panels[i * countSensorsInRow + j].left,
                                panelsForPlot[i * countSensorsInRow + j].bottom,
                                panels[i * countSensorsInRow + j].right,
                                panels[i * countSensorsInRow + j].bottom);
                    } else {
                        panelsForPlot[i * countSensorsInRow + j].set(panels[i * countSensorsInRow + j].left,
                                panels[i * countSensorsInRow + j].top,
                                panels[i * countSensorsInRow + j].right - (int) (0.25 * panels[i * countSensorsInRow + j].width()),
                                panels[i * countSensorsInRow + j].bottom);
                        panelsForSensors[i * countSensorsInRow + j].set(panelsForPlot[i * countSensorsInRow + j].right,
                                panels[i * countSensorsInRow + j].top,
                                panels[i * countSensorsInRow + j].right,
                                panels[i * countSensorsInRow + j].bottom);
                    }
                } else {
                    panels[i * countSensorsInRow + j].set(INDENT + j * (INDENT + sizeWindowForSensor),
                            INDENT + i * (INDENT + sizeWindowForSensor),
                            widthCanvas - INDENT - (countSensorsInRow - 1 - j) * (INDENT + sizeWindowForSensor),
                            INDENT + sizeWindowForSensor + i * (INDENT + sizeWindowForSensor));
                    if (i % 2 == 0) {
                        panelsForPlot[i * countSensorsInRow + j].set(panels[i * countSensorsInRow + j].left,
                                panels[i * countSensorsInRow + j].top,
                                panels[i * countSensorsInRow + j].right,
                                panels[i * countSensorsInRow + j].bottom - (int) (0.25 * panels[i * countSensorsInRow + j].height()));
                        panelsForSensors[i * countSensorsInRow + j].set(panels[i * countSensorsInRow + j].left,
                                panelsForPlot[i * countSensorsInRow + j].bottom,
                                panels[i * countSensorsInRow + j].right,
                                panels[i * countSensorsInRow + j].bottom);
                    } else {
                        panelsForPlot[i * countSensorsInRow + j].set(panels[i * countSensorsInRow + j].left,
                                panels[i * countSensorsInRow + j].top,
                                panels[i * countSensorsInRow + j].right - (int) (0.25 * panels[i * countSensorsInRow + j].width()),
                                panels[i * countSensorsInRow + j].bottom);
                        panelsForSensors[i * countSensorsInRow + j].set(panelsForPlot[i * countSensorsInRow + j].right,
                                panels[i * countSensorsInRow + j].top,
                                panels[i * countSensorsInRow + j].right,
                                panels[i * countSensorsInRow + j].bottom);
                    }
                }
            }
        }
    }
}
