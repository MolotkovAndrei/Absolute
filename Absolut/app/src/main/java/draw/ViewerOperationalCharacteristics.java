package draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import results.ExperimentsJournal;

public class ViewerOperationalCharacteristics extends View {
    private Paint paint;
    private List<Integer> numberPoints;
    private List<Map> operationalCharacteristics;
    private List<Integer> colors;
    private Random random;
    private float norm;

    final int INDENT_TOP = 10;
    final int INDENT_BOTTOM = 20;
    final int INDENT_BORDER = 30;
    final int NUMBER_STEPS_Y = 10;
    final int NUMBER_STEPS_X = 11;

    private final String[] indicatorsX = {"10", "20", "30", "40", "50", "60", "70", "80", "90", "100", "200", "300"};
    private final String[] indicatorsY = {"0.0", "0.1", "0.2", "0.3", "0.4", "0.5", "0.6", "0.7", "0.8", "0.9", "1.0"};

    public ViewerOperationalCharacteristics(Context context, AttributeSet attrs) {
        super(context, attrs);

        numberPoints = new ArrayList<>();
        operationalCharacteristics = new ArrayList<>();
        colors = new ArrayList<>();
        random = new Random();
        norm = (float) Math.log10(300) - 1.0f;

        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setColor(Color.WHITE);
    }

    public void setResultsForView(final List journalList) {
        numberPoints.clear();
        operationalCharacteristics.clear();
        ExperimentsJournal experimentsJournal;

        for (int i = 0; i < journalList.size(); i++) {
            experimentsJournal = (ExperimentsJournal) journalList.get(i);
            numberPoints.add(experimentsJournal.getNumberExperiments());
            operationalCharacteristics.add(experimentsJournal.getOperationalCharacteristics());
            colors.add(experimentsJournal.getColor());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLUE);
        drawGrid(canvas);
        for (int i = 0; i < operationalCharacteristics.size(); i++) {
            drawPlotOperationCharacteristics(canvas, i);
        }
    }

    private void drawGrid(Canvas canvas) {
        final int TEXT_INDENT_LEFT = 5;
        final int TEXT_INDENT_BOTTOM = 5;
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(1);
        paint.setTextSize(10.0f);

        double coefficientsOfRanges[] = new double[NUMBER_STEPS_X];
        float stepsX[] = new float[NUMBER_STEPS_X];

        for (int i = 10, j = 0; i < 100; i += 10, j++) {
            coefficientsOfRanges[j] = (Math.log10(i + 10) - Math.log10(i)) / norm;
        }
        coefficientsOfRanges[9] = (Math.log10(200) - Math.log10(100)) / norm;
        coefficientsOfRanges[10] = (Math.log10(300) - Math.log10(200)) / norm;

        for (int i = 0; i < NUMBER_STEPS_X; i++) {
            stepsX[i] =(float) (getWidth() - 2 * INDENT_BORDER) * (float) coefficientsOfRanges[i];
        }
        float stepY = (float) (getHeight() - INDENT_BOTTOM - INDENT_TOP) / NUMBER_STEPS_Y;


        float sumSteps = 0.0f;
        for (int i = 0; i < NUMBER_STEPS_X; i++) {
            sumSteps += stepsX[i];
            canvas.drawLine(INDENT_BORDER + sumSteps, INDENT_TOP, INDENT_BORDER + sumSteps,
                    getHeight() - INDENT_BOTTOM, paint);
            canvas.drawText(indicatorsX[i + 1], INDENT_BORDER + sumSteps, getHeight() - TEXT_INDENT_BOTTOM, paint);
        }
        canvas.drawLine(INDENT_BORDER, INDENT_TOP, INDENT_BORDER,
                getHeight() - INDENT_BOTTOM, paint);
        canvas.drawText(indicatorsX[0], INDENT_BORDER, getHeight() - TEXT_INDENT_BOTTOM, paint);

        for (int j = 0; j <= NUMBER_STEPS_Y; j++) {
            canvas.drawLine(INDENT_BORDER, INDENT_TOP + j * stepY, getWidth() - INDENT_BORDER, INDENT_TOP + j * stepY, paint);
            canvas.drawText(indicatorsY[NUMBER_STEPS_Y - j], TEXT_INDENT_LEFT, INDENT_TOP + j * stepY, paint);
        }


    }

    private void drawPlotOperationCharacteristics(Canvas canvas, int numberTask) {
        paint.setColor(colors.get(numberTask));
        paint.setStrokeWidth(3);

        Point leftPoint = new Point(INDENT_BORDER, getHeight() - INDENT_BOTTOM);
        Point rightPoint = new Point();
        Integer numberIterations;
        int numberFunc = 0;

        Iterator iterator = operationalCharacteristics.get(numberTask).keySet().iterator();
        while (iterator.hasNext()) {
            numberIterations = (Integer) iterator.next();
            if (numberIterations < 10) {
                numberIterations = 10;
            }
            rightPoint.x = INDENT_BORDER + (int) ((Math.log10(numberIterations) - 1.0) * (getWidth() - 2 * INDENT_BORDER) / norm);

            numberFunc += (Integer) operationalCharacteristics.get(numberTask).get(numberIterations);
            rightPoint.y = getHeight() - INDENT_BOTTOM - numberFunc * (getHeight() - INDENT_BOTTOM - INDENT_TOP) / numberPoints.get(numberTask);
            canvas.drawLine(leftPoint.x, leftPoint.y, rightPoint.x, rightPoint.y, paint);
            leftPoint.x = rightPoint.x;
            leftPoint.y = rightPoint.y;
        }
        rightPoint.x = getWidth() - INDENT_BORDER;
        rightPoint.y = leftPoint.y;
        canvas.drawLine(leftPoint.x, leftPoint.y, rightPoint.x, rightPoint.y, paint);
    }
}
