package draw;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

import function.IFunction;
import task.ITask;

public abstract class HiderInvalidPoints {
    private List<Rect> mRects = new ArrayList<>();
    private Rect drawPanel = new Rect();
    protected List<IFunction> limitedFunctions = new ArrayList<>();
    private Paint paint = new Paint();

    public HiderInvalidPoints(Rect drawPanel, List<IFunction> limitedFunctions) {
        this.drawPanel = drawPanel;
        this.limitedFunctions = limitedFunctions;
    }

    public void updateFunctions(List<IFunction> limitedFunctions) {
        this.limitedFunctions = limitedFunctions;
        //minimizedFunction = task.getMinimizedFunction();
        calculateHideRectangles();
    }

    public void calculateHideRectangles() {
        mRects.clear();
        int width = drawPanel.width();

        double leftPointRange = limitedFunctions.get(0).getLeftPointOfRange();
        double lengthIntervalX = limitedFunctions.get(0).getRightPointOfRange() - leftPointRange;

        double coordX;
        for (int pix = 0; pix < drawPanel.width(); pix += 3) {
            coordX = convertPixToCoord(width, lengthIntervalX, pix) + leftPointRange;
            if (!isValidPoint(coordX)) {
                int leftPixel = pix;
                int rightPixel = leftPixel;
                pix += 3;
                coordX = convertPixToCoord(width, lengthIntervalX, pix) + leftPointRange;
                while ((pix <= drawPanel.width()) && !isValidPoint(coordX)) {
                    rightPixel += 3;
                    pix += 3;
                    coordX = convertPixToCoord(width, lengthIntervalX, pix) + leftPointRange;
                }
                Rect rect = new Rect(leftPixel, drawPanel.top, rightPixel, drawPanel.bottom);
                mRects.add(rect);
            }
        }
    }

    public void draw(Canvas canvas) {
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.FILL);
        for (Rect rect : mRects) {
            canvas.drawRect(rect, paint);
        }
    }

    protected abstract boolean isValidPoint(double x);

    private double convertPixToCoord(final int numberPixels, final double lengthRange, final int pix) {
        return pix * lengthRange / numberPixels;
    }
}
