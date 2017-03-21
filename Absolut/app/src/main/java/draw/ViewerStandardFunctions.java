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

import algorithm.Settings;
import function.F11;
import function.F14;
import function.F15;
import function.F16;
import function.F19;
import function.F2;
import function.F3;
import function.F5;
import function.F6;
import function.F7;
import function.HillFunction;
import function.IFunction;
import task.ITask;
import task.Task;

public class ViewerStandardFunctions extends View {
    private final int NUMBER_FUNCTIONS = 10;
    private final int INDENT = 30;
    private int countPlotsInRow = 2;
    private int sizeWindowForPlot;
    private int heightCanvas;
    private IFunction[] functions = new IFunction[NUMBER_FUNCTIONS];
    private DrawerSensor[] drawerSensors = new DrawerSensor[NUMBER_FUNCTIONS];
    private Rect[] panels = new Rect[NUMBER_FUNCTIONS];
    private int numberSelectPlot = 0;
    private Rect frame = new Rect();
    private Paint paint = new Paint();
    private final GestureDetector gestureDetector;

    public ViewerStandardFunctions(Context context, AttributeSet attrs) {
        super(context, attrs);
        for (int i = 0; i < NUMBER_FUNCTIONS; i++) {
            panels[i] = new Rect();
        }
        createPanelsForPlots(getWidth(), getHeight());
        functions[0] = new F2();
        functions[1] = new F3();
        functions[2] = new F5();
        functions[3] = new F6();
        functions[4] = new F7();
        functions[5] = new F11();
        functions[6] = new F14();
        functions[7] = new F15();
        functions[8] = new F19();
        functions[9] = new F16();
        for (int i = 0; i < NUMBER_FUNCTIONS; i++) {
            drawerSensors[i] = new DrawerPlot(functions[i], panels[i]);
        }

        gestureDetector = new GestureDetector(context, new MyGestureListener());
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setColor(Color.RED);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        createPanelsForPlots(getWidth(), getHeight());
        for (int i = 0; i < NUMBER_FUNCTIONS; i++) {
            drawerSensors[i].setDrawPanel(panels[i]);
        }
        frame.set(panels[numberSelectPlot]);
        heightCanvas = INDENT + NUMBER_FUNCTIONS / countPlotsInRow * (INDENT + sizeWindowForPlot);
        scrollTo(0, 0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (DrawerSensor o : drawerSensors) {
            o.draw(canvas, 0);
        }
        canvas.drawRect(frame, paint);
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
                scrollBy(0, (int)distanceY);
            }
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent event){
            float x = event.getX();
            float y = event.getY();
            int scrollX = getScrollX();
            int scrollY = getScrollY();
            numberSelectPlot = (int)((x + scrollX) / (INDENT + sizeWindowForPlot)) + countPlotsInRow * (int)((y+scrollY) / (INDENT + sizeWindowForPlot));
            if (numberSelectPlot >= 0 && numberSelectPlot < NUMBER_FUNCTIONS) {
                frame.set(panels[numberSelectPlot]);
            }
            invalidate();
            return true;
        }
    }

    private void createPanelsForPlots(int widthCanvas, int heightCanvas) {
        if (widthCanvas < heightCanvas) {
            countPlotsInRow = 2;
        } else {
            countPlotsInRow = 5;
        }
        sizeWindowForPlot = (widthCanvas - (countPlotsInRow + 1) * INDENT) / countPlotsInRow;
        for (int i = 0; i < NUMBER_FUNCTIONS / countPlotsInRow; i++) {
            for (int j = 0; j < countPlotsInRow; j++) {
                panels[i * countPlotsInRow + j].set(INDENT + j * (INDENT + sizeWindowForPlot),
                                                      INDENT + i * (INDENT + sizeWindowForPlot),
                                                      widthCanvas - INDENT - (countPlotsInRow - 1 - j) * (INDENT + sizeWindowForPlot),
                                                      INDENT + sizeWindowForPlot + i * (INDENT + sizeWindowForPlot));
            }
        }
    }

    public IFunction getSelectFunction() {
        return functions[numberSelectPlot];
    }
}
