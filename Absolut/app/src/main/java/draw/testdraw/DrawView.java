/*package draw.testdraw;

import android.content.Context;
import android.view.GestureDetector;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

import algorithm.Settings;
import draw.CalculatorSensors;
import function.F1;
import function.IFunction;
import model.IModel;
import observer.IObserver;
import observer.ViewerWorkSpace;
import storage.Dot;
import task.ITask;
import task.Task;

public class DrawView extends SurfaceView implements SurfaceHolder.Callback, IObserver {

    private DrawThread drawThread;




    private IModel model;
    private List<Dot> points = new ArrayList<>();
    private IFunction function;
    private Dot exactValue;

    private Settings settings;
    //private final GestureDetector gestureDetector;
    //private final ScaleGestureDetector scaleGestureDetector;
    private float scaleFactor = 1f;
    private int index = 0;
    private int width, height;

    CalculatorSensors calculatorSensors;

    public DrawView(Context context, IModel model) {
        super(context);
        getHolder().addCallback(this);
        this.model = model;
        model.registerObserver(this);
        ITask task = new Task(new Settings(0.001, 200, 2.0));
        function = task.getFunction();
        settings = task.getSettings();
        exactValue = task.getExactValue();
        calculatorSensors = new CalculatorSensors(points, function, settings, exactValue);
       // gestureDetector = new GestureDetector(context, new ViewerWorkSpace.MyGestureListener());
        //scaleGestureDetector = new ScaleGestureDetector(context, new ViewerWorkSpace.MyScaleGestureListener());
        width = getWidth();
        height = getHeight();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread = new DrawThread(getHolder());
        drawThread.setcalc(calculatorSensors, points, index);
        drawThread.setRunning(true);
        drawThread.start();

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        drawThread.setRunning(false);
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    @Override
    public void update(ITask task) {
        this.points = task.getStorage();
        boolean retry = true;
        drawThread.setRunning(false);
        ArrayList<Dot> points2 = new ArrayList<>();
        for (int i = 0; i < this.points.size(); i++) {
            points2.add(points.get(i));
        }
        this.function = task.getFunction();
        this.settings = task.getSettings();
        this.exactValue = task.getExactValue();

        calculatorSensors.updateData(points2, function, settings, exactValue);

        index = 0;
        drawThread.setcalc(calculatorSensors, points2, index);
        drawThread.setRunning(true);
        //drawThread.start();
    }

    @Override
    public void changeSensors(List<Boolean> isCheckedSensors) {
        calculatorSensors.setSensors(isCheckedSensors);
        index = 0;
        drawThread.setcalc(calculatorSensors, points, index);
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
    }

    private int getScaledWidth()
    {
        return (int)(width * scaleFactor);
    }

    private int getScaledHeight()
    {
        return (int)(height * scaleFactor);
    }

    public DrawThread getDrawThread() {
        return drawThread;
    }
}*/
