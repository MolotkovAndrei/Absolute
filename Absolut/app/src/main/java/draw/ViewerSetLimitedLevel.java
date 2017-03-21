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

import com.example.absolute.ChangeLimitedLevelListener;

import function.HillFunction;
import function.IFunction;

public class ViewerSetLimitedLevel extends View {
    private IFunction function = new HillFunction();
    private DrawerSensor drawerPlot;
    private Rect drawPanel = new Rect();
    private final GestureDetector gestureDetector;
    private double limitedLevel;
    private float lineLevelCordY;
    private Paint paint;
    private ChangeLimitedLevelListener listener;

    public ViewerSetLimitedLevel(Context context, AttributeSet attrs) {
        super(context, attrs);
        try {
            listener = (ChangeLimitedLevelListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement ChangeLimitedLevelListener");
        }

        drawerPlot = new DrawerPlot(function, drawPanel);
        gestureDetector = new GestureDetector(context, new MyGestureListener());
        paint = new Paint();
        paint.setStrokeWidth(3);
    }

    public void setFunction(IFunction function) {
        this.function = function;
        drawerPlot.setContent(function);
        limitedLevel = function.getLimitationLevel();
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
        public boolean onSingleTapConfirmed(MotionEvent event){
            float x = event.getX();
            lineLevelCordY = event.getY();
            double lengthInterval = (function.getMaxValueOnRange() - function.getMinValueOnRange());
            limitedLevel = function.getMinValueOnRange() + lengthInterval * (getHeight() - lineLevelCordY) / getHeight();
            listener.updateLimitedLevelValue(limitedLevel);
            invalidate();
            return true;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        lineLevelCordY = getHeight() - (int) (getHeight() * (limitedLevel - function.getMinValueOnRange())
                / (function.getMaxValueOnRange() - function.getMinValueOnRange()));
        drawPanel.set(0, 0, w, h);
        drawerPlot.setDrawPanel(drawPanel);
        drawerPlot.calculatePointsForDraw();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawerPlot.draw(canvas, 0);
        paint.setColor(Color.RED);
        canvas.drawLine(0, lineLevelCordY, getWidth(), lineLevelCordY, paint);
    }
}
