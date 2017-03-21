package draw.testdraw;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.GestureDetector;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.List;

import algorithm.Settings;
import draw.CalculatorSensors;
import function.F1;
import function.IFunction;
import storage.Dot;

class DrawThread extends Thread {

    private boolean running = false;
    private SurfaceHolder surfaceHolder;


    CalculatorSensors calculatorSensors;
    private List<Dot> points;
    private int index = 0;


    public void setcalc(CalculatorSensors calculatorSensors, List<Dot> points, int index) {
        this.calculatorSensors = calculatorSensors;
        this.points = points;
        this.index = index;
    }


    public DrawThread(SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        Canvas canvas;
        float x = 0.0f, y = 0.0f;
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);


        while (running ) {
            canvas = null;
            try {
                canvas = surfaceHolder.lockCanvas(null);
                if (canvas == null)
                    continue;
                canvas.drawARGB(80, 102, 204, 255);
                calculatorSensors.drawSensors(canvas, index);
                sleep(200);
                if (index < points.size() - 1) {
                    index++;
                }
            } catch (InterruptedException e) {

            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }


}

