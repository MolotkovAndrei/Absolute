package draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.example.absolute.R;

import java.util.Iterator;
import java.util.Map;

import results.TotalResults;

public class ViewerTotalResults extends View {
    private final int NUMBER_VALUES = 7;
    private final int INDENT_LEFT = 5;
    private final String[] indicatorsX = {"0", "100", "200", "300"};
    private final String[] indicatorsY = {"0.0", "0.2", "0.4", "0.6", "0.8", "1.0"};

    private int numberPoints;
    private Map operationalCharacteristics;

    private Rect totalResultsRect;
    private Rect operationalCharacteristicsRect;
    private Rect numberExperimentsRect;
    private Rect averageNumberIterationsRect;
    private Rect reachMinTitleRect;
    private Rect notReachMinTitleRect;
    private Rect numberFunctionsReachMinRect;
    private Rect numberFunctionsNotReachMinRect;
    private Rect averageNumberIterationsForFunctionsReachMinRect;
    private Rect averageNumberIterationsForFunctionsNotReachMinRect;
    private Rect averageDeviationRect;

    private Rect[] valuesTotalResults;

    private String numberExperiments;
    private String averageNumberIterations;
    private String numberFunctionsReachMin;
    private String numberFunctionsNotReachMin;
    private String averageNumberIterationsForFunctionsReachMin;
    private String averageNumberIterationsForFunctionsNotReachMin;
    private String averageDeviation;

    private String numberExperimentsTitle;
    private String averageNumberIterationsTitle;
    private String numberFunctionsReachMinTitle;
    private String numberFunctionsNotReachMinTitle;
    private String averageNumberIterationsForFunctionsReachMinTitle;
    private String averageNumberIterationsForFunctionsNotReachMinTitle;
    private String averageDeviationTitle;
    private String reachMinTitle;
    private String notReachMinTitle;

    private Paint paint;

    public ViewerTotalResults(Context context, AttributeSet attrs) {
        super(context, attrs);

        numberExperimentsTitle = getResources().getString(R.string.numberExperimentsTitle);
        averageNumberIterationsTitle = getResources().getString(R.string.averageNumberIterationsTitle);
        numberFunctionsReachMinTitle = getResources().getString(R.string.numberFunctionsTitle);
        numberFunctionsNotReachMinTitle = getResources().getString(R.string.numberFunctionsTitle);
        averageNumberIterationsForFunctionsReachMinTitle = getResources().getString(R.string.averageNumberIterationsTitle);
        averageNumberIterationsForFunctionsNotReachMinTitle = getResources().getString(R.string.averageNumberIterationsTitle);
        averageDeviationTitle = getResources().getString(R.string.averageDeviationTitle);
        reachMinTitle = getResources().getString(R.string.reachMinTitle);
        notReachMinTitle = getResources().getString(R.string.notReachMinTitle);

        totalResultsRect = new Rect();
        operationalCharacteristicsRect = new Rect();
        numberExperimentsRect = new Rect();
        averageNumberIterationsRect = new Rect();
        reachMinTitleRect = new Rect();
        notReachMinTitleRect = new Rect();
        numberFunctionsReachMinRect = new Rect();
        numberFunctionsNotReachMinRect = new Rect();
        averageNumberIterationsForFunctionsReachMinRect = new Rect();
        averageNumberIterationsForFunctionsNotReachMinRect = new Rect();
        averageDeviationRect = new Rect();

        valuesTotalResults = new Rect[NUMBER_VALUES];
        for (int i = 0; i < NUMBER_VALUES; i++) {
            valuesTotalResults[i] = new Rect();
        }

        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setColor(Color.WHITE);
    }

    public void setResultsForView(final TotalResults totalResults) {
        numberPoints = totalResults.getNumberExperiments();
        operationalCharacteristics = totalResults.getOperationalCharacteristics();

        numberExperiments = String.valueOf(totalResults.getNumberExperiments());
        averageNumberIterations = String.valueOf(totalResults.getAverageNumberIterations());
        numberFunctionsReachMin = String.valueOf(totalResults.getNumberFunctionsReachMin());
        numberFunctionsNotReachMin = String.valueOf(totalResults.getNumberFunctionsNotReachMin());
        averageNumberIterationsForFunctionsReachMin = String.valueOf(totalResults.getAverageNumberIterationsForFunctionsReachMin());
        averageNumberIterationsForFunctionsNotReachMin = String.valueOf(totalResults.getAverageNumberIterationsForFunctionsNotReachMin());
        averageDeviation = String.valueOf(totalResults.getAverageDeviation());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        createPanels((int)(getWidth() * 1.0), (int)(getHeight() * 1.0));
        scrollTo(0, 0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLUE);

        paint.setStrokeWidth(1);
        paint.setTextSize(12);

        canvas.drawText(numberExperimentsTitle, INDENT_LEFT + numberExperimentsRect.left,
                numberExperimentsRect.top + numberExperimentsRect.height() / 2, paint);
        canvas.drawText(averageNumberIterationsTitle, INDENT_LEFT + averageNumberIterationsRect.left,
                averageNumberIterationsRect.top + averageNumberIterationsRect.height() / 2, paint);
        canvas.drawText(reachMinTitle, INDENT_LEFT + reachMinTitleRect.left,
                reachMinTitleRect.top + reachMinTitleRect.height() / 2, paint);
        canvas.drawText(numberFunctionsReachMinTitle, INDENT_LEFT + numberFunctionsReachMinRect.left,
                numberFunctionsReachMinRect.top + numberFunctionsReachMinRect.height() / 2, paint);
        canvas.drawText(averageNumberIterationsForFunctionsReachMinTitle, INDENT_LEFT + averageNumberIterationsForFunctionsReachMinRect.left,
                averageNumberIterationsForFunctionsReachMinRect.top + averageNumberIterationsForFunctionsReachMinRect.height() / 2, paint);
        canvas.drawText(notReachMinTitle, INDENT_LEFT + notReachMinTitleRect.left, notReachMinTitleRect.top + notReachMinTitleRect.height() / 2, paint);
        canvas.drawText(numberFunctionsNotReachMinTitle, INDENT_LEFT + numberFunctionsNotReachMinRect.left,
                numberFunctionsNotReachMinRect.top + numberFunctionsNotReachMinRect.height() / 2, paint);
        canvas.drawText(averageNumberIterationsForFunctionsNotReachMinTitle, INDENT_LEFT + averageNumberIterationsForFunctionsNotReachMinRect.left,
                averageNumberIterationsForFunctionsNotReachMinRect.top + averageNumberIterationsForFunctionsNotReachMinRect.height() / 2, paint);
        canvas.drawText(averageDeviationTitle, INDENT_LEFT + averageDeviationRect.left,
                averageDeviationRect.top + averageDeviationRect.height() / 2, paint);





        canvas.drawText(numberExperiments, INDENT_LEFT + valuesTotalResults[0].left,
                valuesTotalResults[0].top + valuesTotalResults[0].height() / 2, paint);

        canvas.drawText(averageNumberIterations, INDENT_LEFT + valuesTotalResults[1].left,
                valuesTotalResults[1].top + valuesTotalResults[1].height() / 2, paint);

        canvas.drawText(numberFunctionsReachMin, INDENT_LEFT + valuesTotalResults[2].left,
                valuesTotalResults[2].top + valuesTotalResults[2].height() / 2, paint);

        canvas.drawText(averageNumberIterationsForFunctionsReachMin, INDENT_LEFT + valuesTotalResults[3].left,
                valuesTotalResults[3].top + valuesTotalResults[3].height() / 2, paint);

        canvas.drawText(numberFunctionsNotReachMin, INDENT_LEFT + valuesTotalResults[4].left,
                valuesTotalResults[4].top + valuesTotalResults[4].height() / 2, paint);

        canvas.drawText(averageNumberIterationsForFunctionsNotReachMin, INDENT_LEFT + valuesTotalResults[5].left,
                valuesTotalResults[5].top + valuesTotalResults[5].height() / 2, paint);

        canvas.drawText(averageDeviation, INDENT_LEFT + valuesTotalResults[6].left,
                valuesTotalResults[6].top + valuesTotalResults[6].height() / 2, paint);

        paint.setStrokeWidth(5);

        canvas.drawRect(totalResultsRect, paint);
        canvas.drawRect(operationalCharacteristicsRect, paint);
        canvas.drawRect(numberExperimentsRect, paint);
        canvas.drawRect(averageNumberIterationsRect, paint);
        canvas.drawRect(reachMinTitleRect, paint);
        canvas.drawRect(notReachMinTitleRect, paint);
        canvas.drawRect(numberFunctionsReachMinRect, paint);
        canvas.drawRect(averageNumberIterationsForFunctionsReachMinRect, paint);
        canvas.drawRect(numberFunctionsNotReachMinRect, paint);
        canvas.drawRect(averageNumberIterationsForFunctionsNotReachMinRect, paint);
        canvas.drawRect(averageDeviationRect, paint);

        for (int i = 0; i < NUMBER_VALUES; i++) {
            canvas.drawRect(valuesTotalResults[i], paint);
        }

        drawGrid(canvas);

    }

    private void createPanels(final int widthView, final int heightView) {
        if (widthView < heightView) {
            totalResultsRect.set(0, 0, widthView, heightView/ 2);
            operationalCharacteristicsRect.set(0, heightView / 2, widthView, heightView);
            createTotalResultsPanel();
        } else {
            totalResultsRect.set(0, 0, widthView / 2, heightView);
            operationalCharacteristicsRect.set(widthView / 2, 0, widthView, heightView);
            createTotalResultsPanel();
        }
    }

    private void createTotalResultsPanel() {
        int heightRow = totalResultsRect.height() / NUMBER_VALUES;
        int rightPointPanels = totalResultsRect.width() * 3 / 4;

        numberExperimentsRect.set(totalResultsRect.left, totalResultsRect.top,
                totalResultsRect.left + rightPointPanels,      totalResultsRect.top + heightRow);

        averageNumberIterationsRect.set(totalResultsRect.left, numberExperimentsRect.bottom,
                totalResultsRect.left + rightPointPanels,      numberExperimentsRect.bottom + heightRow);

        reachMinTitleRect.set(totalResultsRect.left, averageNumberIterationsRect.bottom,
                totalResultsRect.left + (int)(rightPointPanels * 0.35),      averageNumberIterationsRect.bottom + 2 * heightRow);

        numberFunctionsReachMinRect.set(reachMinTitleRect.right, reachMinTitleRect.top,
                totalResultsRect.left + rightPointPanels, averageNumberIterationsRect.bottom + heightRow);

        averageNumberIterationsForFunctionsReachMinRect.set(reachMinTitleRect.right, numberFunctionsReachMinRect.bottom,
                totalResultsRect.left + rightPointPanels, numberFunctionsReachMinRect.bottom + heightRow);

        notReachMinTitleRect.set(totalResultsRect.left, reachMinTitleRect.bottom,
                totalResultsRect.left + (int)(rightPointPanels * 0.35), totalResultsRect.bottom);



        numberFunctionsNotReachMinRect.set(notReachMinTitleRect.right, notReachMinTitleRect.top,
                totalResultsRect.left + rightPointPanels, averageNumberIterationsForFunctionsReachMinRect.bottom + heightRow);


        averageNumberIterationsForFunctionsNotReachMinRect.set(notReachMinTitleRect.right, numberFunctionsNotReachMinRect.bottom,
                totalResultsRect.left + rightPointPanels, numberFunctionsNotReachMinRect.bottom + heightRow);

        averageDeviationRect.set(notReachMinTitleRect.right, averageNumberIterationsForFunctionsNotReachMinRect.bottom,
                totalResultsRect.left + rightPointPanels, notReachMinTitleRect.bottom);

        for (int i = 0; i < NUMBER_VALUES - 1; i++) {
            valuesTotalResults[i].set(totalResultsRect.left + rightPointPanels, totalResultsRect.top + i * heightRow,
                    totalResultsRect.right, totalResultsRect.top + (i + 1) * heightRow);
        }
        valuesTotalResults[NUMBER_VALUES - 1].set(totalResultsRect.left + rightPointPanels, totalResultsRect.top + (NUMBER_VALUES - 1) * heightRow,
                totalResultsRect.right, totalResultsRect.bottom);
    }

    private void drawGrid(Canvas canvas) {
        final int INDENT_TOP = 10;
        final int INDENT_BOTTOM = 20;
        final int INDENT_BORDER = 30;
        final int TEXT_INDENT_BOTTOM = 5;
        paint.setStrokeWidth(1);

        float stepX = (float) (operationalCharacteristicsRect.width() - 2 * INDENT_BORDER) / 30;
        float stepY = (float) (operationalCharacteristicsRect.height() - INDENT_BOTTOM - INDENT_TOP) / 5;

        for (int i = 0; i <= 30; i++) {
            if ((i == 0) || (i == 10) || (i == 20) || (i == 30)) {
                canvas.drawText(String.valueOf(i * 10), (operationalCharacteristicsRect.left + INDENT_BORDER) + i * stepX,
                        operationalCharacteristicsRect.bottom - TEXT_INDENT_BOTTOM, paint);
                paint.setStrokeWidth(2);
                canvas.drawLine((operationalCharacteristicsRect.left + INDENT_BORDER) + i * stepX,
                        operationalCharacteristicsRect.top + INDENT_TOP,
                        (operationalCharacteristicsRect.left + INDENT_BORDER) + i * stepX,
                        operationalCharacteristicsRect.bottom - INDENT_BOTTOM, paint);
                paint.setStrokeWidth(1);
            } else {
                canvas.drawLine((operationalCharacteristicsRect.left + INDENT_BORDER) + i * stepX,
                        operationalCharacteristicsRect.top + INDENT_TOP,
                        (operationalCharacteristicsRect.left + INDENT_BORDER) + i * stepX,
                        operationalCharacteristicsRect.bottom - INDENT_BOTTOM, paint);
            }
        }

        for (int j = 0; j <= 5; j++) {
            canvas.drawLine(operationalCharacteristicsRect.left + INDENT_BORDER,
                    (operationalCharacteristicsRect.top + INDENT_TOP) + j * stepY,
                    operationalCharacteristicsRect.right - INDENT_BORDER,
                    (operationalCharacteristicsRect.top + INDENT_TOP) + j * stepY, paint);
            canvas.drawText(indicatorsY[5 - j], operationalCharacteristicsRect.left + INDENT_LEFT,
                           (operationalCharacteristicsRect.top + INDENT_TOP) + j * stepY, paint);
        }

        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(3);

        Point leftPoint = new Point(operationalCharacteristicsRect.left + INDENT_BORDER,
                                    operationalCharacteristicsRect.bottom - INDENT_BOTTOM);
        Point rightPoint = new Point();
        Integer numberIterations;
        int numberFunc = 0;

        Iterator iterator = operationalCharacteristics.keySet().iterator();
        while (iterator.hasNext()) {
            numberIterations = (Integer) iterator.next();
            rightPoint.x =  operationalCharacteristicsRect.left + INDENT_BORDER + numberIterations * (operationalCharacteristicsRect.width() - 2 * INDENT_BORDER) / 300;

            numberFunc += (Integer) operationalCharacteristics.get(numberIterations);
            rightPoint.y = operationalCharacteristicsRect.bottom - INDENT_BOTTOM - numberFunc *
                            (operationalCharacteristicsRect.height() - INDENT_BOTTOM - INDENT_TOP) / numberPoints;
            canvas.drawLine(leftPoint.x, leftPoint.y, rightPoint.x, rightPoint.y, paint);
            leftPoint.x = rightPoint.x;
            leftPoint.y = rightPoint.y;
        }
        rightPoint.x = operationalCharacteristicsRect.right - INDENT_BORDER;
        rightPoint.y = leftPoint.y;
        canvas.drawLine(leftPoint.x, leftPoint.y, rightPoint.x, rightPoint.y, paint);
    }
}
