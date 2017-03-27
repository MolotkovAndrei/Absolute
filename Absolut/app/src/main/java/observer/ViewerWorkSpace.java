package observer;

import android.content.Context;
import android.graphics.Canvas;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import algorithm.Settings;
import draw.CalculatorSensors;
import draw.CalculatorSensorsIndexTask;
import draw.CalculatorSensorsPenaltyTask;
import draw.CalculatorSensorsUnlimited;
import function.IFunction;
import model.DisplayOptions;
import model.IModel;
import storage.Dot;
import task.ITask;
import task.IndexTask;
import task.PenaltyTask;
import task.StorageTasks;
import task.Task;
import task.TaskWithLimitations;

public class ViewerWorkSpace extends View implements IObserver {
    private IModel model;
    private List<Dot> points = new ArrayList<>();
    private StorageTasks storageTasks;
    //private DisplayOptions displayOptions;
    private boolean isRun = true;
    private CalculatorSensors calculatorSensors;

    //private CalculatorSensorsWithLimitation mCalculatorSensorsWithLimitation;

    private final GestureDetector gestureDetector;
    private final ScaleGestureDetector scaleGestureDetector;
    private float scaleFactor = 1f;
    private int index = 0;
    private int indexSteps = 0;
    private int numberTasks = 0;
    private int indexTask = 0;
    private int width, height;
    private int numberLimitedFunctions;

    public ViewerWorkSpace(Context context, IModel model) {
        super(context);
        //displayOptions = new DisplayOptions(context);
        this.model = model;
        //displayOptions = model.getDisplayOptions();
        model.registerObserver(this);
        storageTasks = model.getCurrentStorage();
        ITask task = storageTasks.getCurrentTask();
        numberLimitedFunctions = task.getLimitationFunctions().size();
         //new TaskWithLimitations(new Settings(0.001, 200, 2.0));//new Task(new Settings(0.001, 200, 2.0));
        //ITask task = new Task(new Settings(0.001, 200, 2.0));
        //calculatorSensors = new CalculatorSensors(task);
        if (task.getLimitationFunctions().isEmpty()) {
            calculatorSensors = new CalculatorSensorsUnlimited(task);
        } else {
            if (task instanceof PenaltyTask) {
                calculatorSensors = new CalculatorSensorsPenaltyTask(task); //CalculatorSensorsWithLimitation(task);
            } else if (task instanceof IndexTask) {
                calculatorSensors = new CalculatorSensorsIndexTask(task);
            }
        }
        gestureDetector = new GestureDetector(context, new MyGestureListener());
        scaleGestureDetector = new ScaleGestureDetector(context, new MyScaleGestureListener());
        width = getWidth();
        height = getHeight();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;

        int x = getScrollX();
        int y = getScrollY();
        if (x > getScaledWidth() - w) {
            x = getScaledWidth() - w;
        }
        if (y > getScaledHeight() - h) {
            y = getScaledHeight() - h;
        }
       scrollTo(x, y);
       calculatorSensors.createViewPanels(getScaledWidth(), getScaledHeight());
        //mCalculatorSensorsWithLimitation.createViewPanels(getScaledWidth(), getScaledHeight());
    }

    private int getScaledWidth()
    {
        return (int)(width * scaleFactor);
    }

    private int getScaledHeight()
    {
        return (int)(height * scaleFactor);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        scaleGestureDetector.onTouchEvent(event);
        gestureDetector.onTouchEvent(event);
        return true;
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener
    {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
        {
            if (getScrollX() + distanceX < getScaledWidth() - width && getScrollX() + distanceX > 0) {
                scrollBy((int) distanceX, 0);
            }
            if (getScrollY() + distanceY < getScaledHeight() - height && getScrollY() + distanceY > 0) {
                scrollBy(0, (int)distanceY);
            }
            return true;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent event){
            scaleFactor = 1;
            calculatorSensors.setScaleFactor(scaleFactor);
            calculatorSensors.createViewPanels(getScaledWidth(), getScaledHeight());
            //mCalculatorSensorsWithLimitation.createViewPanels(getScaledWidth(), getScaledHeight());
            scrollTo(0, 0);
            invalidate();
            return true;
        }
    }

    private class MyScaleGestureListener implements ScaleGestureDetector.OnScaleGestureListener
    {
        public boolean onScale(ScaleGestureDetector detector)
        {
            scaleFactor *= detector.getScaleFactor();
            if (scaleFactor > 2.5f) {
                scaleFactor = 2.5f;
            } else if (scaleFactor < 1.0f) {
                scaleFactor = 1.0f;
            }
            calculatorSensors.setScaleFactor(scaleFactor);
            calculatorSensors.createViewPanels(getScaledWidth(), getScaledHeight());
            //mCalculatorSensorsWithLimitation.createViewPanels(getScaledWidth(), getScaledHeight());

            int newScrollX = (int)((getScrollX() + detector.getFocusX()) * detector.getScaleFactor() - detector.getFocusX());
            newScrollX = Math.min(Math.max(newScrollX, 0), getScaledWidth() - width);
            int newScrollY = (int)((getScrollY() + detector.getFocusY()) * detector.getScaleFactor() - detector.getFocusY());
            newScrollY = Math.min(Math.max(newScrollY, 0), getScaledHeight() - height);
            scrollTo(newScrollX, newScrollY);

            invalidate();

            return true;
        }

        public boolean onScaleBegin(ScaleGestureDetector detector)
        {
            return true;
        }

        public void onScaleEnd(ScaleGestureDetector detector) { }
    }

    @Override
    public void update(final StorageTasks storageTasks) {
        //this.displayOptions = displayOptions;
        this.storageTasks = storageTasks;
        ITask task = storageTasks.getTaskList().get(0);
        this.points = task.getStorage();
        int currentNumberLimitedFunctions = task.getLimitationFunctions().size();


        if (task instanceof PenaltyTask && !(calculatorSensors instanceof CalculatorSensorsPenaltyTask)) {
            calculatorSensors = new CalculatorSensorsPenaltyTask(task);
        } else if (task instanceof IndexTask && !(calculatorSensors instanceof CalculatorSensorsIndexTask)) {
            calculatorSensors = new CalculatorSensorsIndexTask(task);
        } else if (task instanceof Task && !(calculatorSensors instanceof CalculatorSensorsUnlimited)) {
            calculatorSensors = new CalculatorSensorsUnlimited(task);
        } else {
            if (currentNumberLimitedFunctions != numberLimitedFunctions) {
                if (task instanceof IndexTask) {
                    calculatorSensors = new CalculatorSensorsIndexTask(task);
                } else if (task instanceof PenaltyTask) {
                    calculatorSensors = new CalculatorSensorsPenaltyTask(task);
                }
                numberLimitedFunctions = currentNumberLimitedFunctions;
            } else {
                calculatorSensors.updateData(task);
            }
        }

        calculatorSensors.createViewPanels(getScaledWidth(), getScaledHeight());
        //mCalculatorSensorsWithLimitation.updateData(task);
        index = 0;
        indexSteps = 0;
        isRun = true;
        numberTasks = storageTasks.getTaskList().size();
        indexTask = 0;
        invalidate();
    }

    @Override
    public void stopDrawing() {
        isRun = false;
        storageTasks.setDrawing(false);
        //invalidate();
    }

    @Override
    public void continueDrawing(StorageTasks storageTasks) {
        this.storageTasks = storageTasks;
        index++;
        try {
            indexSteps = index % storageTasks.getNumberStepsBeforeStop();
        } catch (ArithmeticException e) {
            indexSteps = 0;
        }

        isRun = true;
        storageTasks.setDrawing(true);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawARGB(80, 102, 204, 255);
        calculatorSensors.drawSensors(canvas, index);
        //mCalculatorSensorsWithLimitation.drawSensors(canvas, index);
        if (storageTasks.isCurrentWithStop()) {
            if (isRun && (index < points.size() - 1) && (indexSteps < storageTasks.getNumberStepsBeforeStop() - 1)) {
                index++;
                indexSteps++;
                try {
                    Thread.sleep(storageTasks.getValueDisplaySpeed());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                invalidate();
            } else if (!isRun || indexSteps >= storageTasks.getNumberStepsBeforeStop() - 1) {
                storageTasks.setDrawing(false);
                if (index >= points.size() - 1) {
                    storageTasks.setDrawingFinish(true);
                }
                return;
            } else if (/*(index >= points.size() - 1) && */(indexTask < numberTasks - 1)) {
                indexTask++;
                index = 0;
                indexSteps = 0;
                ITask task = storageTasks.getTaskList().get(indexTask);
                this.points = task.getStorage();
                calculatorSensors.updateData(task);
                //mCalculatorSensorsWithLimitation.updateData(task);
                invalidate();
            } else {
                storageTasks.setDrawing(false);
                storageTasks.setDrawingFinish(true);
            }
        } else {
            if (isRun && index < points.size() - 1) {
                index++;
                try {
                    Thread.sleep(storageTasks.getValueDisplaySpeed());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                invalidate();
            } else if (!isRun) {
                storageTasks.setDrawing(false);
                return;
            } else if (/*(index >= points.size() - 1) && */(indexTask < numberTasks - 1)) {
                indexTask++;
                index = 0;
                ITask task = storageTasks.getTaskList().get(indexTask);
                this.points = task.getStorage();
                calculatorSensors.updateData(task);
                //mCalculatorSensorsWithLimitation.updateData(task);
                invalidate();
            } else {
                storageTasks.setDrawing(false);
                storageTasks.setDrawingFinish(true);
            }
        }

    }
}
