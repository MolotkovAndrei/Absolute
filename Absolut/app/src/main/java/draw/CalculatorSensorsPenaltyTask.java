package draw;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

import function.PenaltyFunction;
import storage.Dot;
import task.ITask;

public class CalculatorSensorsPenaltyTask extends CalculatorSensors {
    private List<DrawerSensor> drawerPlotsLimitedFunctions;
    private DrawerSensor drawerPlotMinimizedFunction;
    private DrawerSensor drawerDistributionPoints;
    private DrawerSensor drawerDistributionValues;
    private DrawerSensor drawerDensityPoints;
    private DrawerSensor drawerDensityValues;
    private DrawerSensor drawerDynamicsPoints;
    private DrawerSensor drawerDynamicsValues;
    private DrawerSensor drawerInformationPanel;
    //private int[] indexesForDraw;

    private List<Dot> dots;
    private int heightPanelForPlot;
    private PenaltyFunction penaltyFunction;

    public CalculatorSensorsPenaltyTask(final ITask task) {
        super(task);

        int countLimitedFunctions = task.getLimitationFunctions().size();
        //indexesForDraw = new int[countLimitedFunctions + 2];
        dots = task.getStorage();
        drawerPlotsLimitedFunctions = new ArrayList<>();


        drawerPlotMinimizedFunction = new DrawerPlot(task.getMinimizedFunction(), new Rect());
        for (int i = 0; i < countLimitedFunctions; i++) {
            drawerPlotsLimitedFunctions.add(new DrawerPlot(task.getLimitationFunctions().get(i), new Rect()));
        }
        penaltyFunction = new PenaltyFunction(task.getMinimizedFunction(), task.getLimitationFunctions());
        drawerPlotsLimitedFunctions.add(new DrawerPlot(penaltyFunction, new Rect()));

        drawerDistributionPoints = new CalculatorDistributionPoints(task, new Rect());
        drawerDistributionValues = new CalculatorDistributionValues(penaltyFunction, dots, new Rect());
        drawerDensityPoints = new CalculatorDensityPoints(task, new Rect());
        drawerDensityValues = new CalculatorDensityValues(task, new Rect());
        drawerDynamicsPoints = new CalculatorDynamicsPoints(task, new Rect());
        drawerDynamicsValues = new CalculatorDynamicsValues(task, new Rect());
        drawerInformationPanel = new DrawerInformationPanelUnlimited(task, new Rect());
    }

    private void createPanelsForValues(Point leftTopPointPanelForPlot,
                                       Point rightBottomPointPanelForPlot,
                                       int numberPanel) {
        final int WIDTH_DISTRIBUTION_VALUES_PANEL = (int)(WIDTH_DISTRIBUTION_PANEL * DrawerSensor.scaleFactor);
        Point leftTop = new Point();
        Point rightBottom = new Point();

        leftTop.x = rightBottomPointPanelForPlot.x;
        leftTop.y = leftTopPointPanelForPlot.y + numberPanel * heightPanelForPlot;
        rightBottom.y = rightBottomPointPanelForPlot.y + numberPanel * heightPanelForPlot;
        if (isDistributionValues) {
            rightBottom.x = rightBottomPointPanelForPlot.x + WIDTH_DISTRIBUTION_VALUES_PANEL;

            drawerDistributionValues.getDrawPanel().set(leftTop.x, leftTop.y,
                    rightBottom.x, rightBottom.y);
            drawerDistributionValues.calculatePointsForDraw();

            if (isDynamicsValues && isDensityValues) {
                Rect panelForDistributionValues = drawerDistributionValues.getDrawPanel();
                leftTop.x = panelForDistributionValues.right;
                rightBottom.x = panelForDistributionValues.right +
                        (widthWorkSpace - panelForDistributionValues.right) / 2;
                drawerDynamicsValues.getDrawPanel().set(leftTop.x, leftTop.y,
                        rightBottom.x, rightBottom.y);
                drawerDynamicsValues.calculatePointsForDraw();

                Rect panelForDynamicsValues = drawerDynamicsValues.getDrawPanel();
                leftTop.x = panelForDynamicsValues.right;
                rightBottom.x = widthWorkSpace;
                drawerDensityValues.getDrawPanel().set(leftTop.x, leftTop.y,
                        rightBottom.x, rightBottom.y);
                drawerDensityValues.calculatePointsForDraw();
            } else {
                Rect panelForDistributionValues = drawerDistributionValues.getDrawPanel();
                leftTop.x = panelForDistributionValues.right;
                rightBottom.x = widthWorkSpace;
                if (isDynamicsValues) {
                    drawerDynamicsValues.getDrawPanel().set(leftTop.x, leftTop.y,
                            rightBottom.x, rightBottom.y);
                    drawerDynamicsValues.calculatePointsForDraw();
                } else {
                    drawerDensityValues.getDrawPanel().set(leftTop.x, leftTop.y,
                            rightBottom.x, rightBottom.y);
                    drawerDensityValues.calculatePointsForDraw();
                }
            }
        } else {
            if (isDynamicsValues && isDensityValues) {
                rightBottom.x = rightBottomPointPanelForPlot.x +
                        (widthWorkSpace - rightBottomPointPanelForPlot.x) / 2;
                drawerDynamicsValues.getDrawPanel().set(leftTop.x, leftTop.y,
                        rightBottom.x, rightBottom.y);
                drawerDynamicsValues.calculatePointsForDraw();

                Rect panelForDynamicsValues = drawerDynamicsValues.getDrawPanel();
                leftTop.x = panelForDynamicsValues.right;
                rightBottom.x = widthWorkSpace;
                drawerDensityValues.getDrawPanel().set(leftTop.x, leftTop.y,
                        rightBottom.x, rightBottom.y);
                drawerDensityValues.calculatePointsForDraw();
            } else {
                rightBottom.x = widthWorkSpace;
                if (isDynamicsValues) {
                    drawerDynamicsValues.getDrawPanel().set(leftTop.x, leftTop.y,
                            rightBottom.x, rightBottom.y);
                    drawerDynamicsValues.calculatePointsForDraw();
                } else {
                    drawerDensityValues.getDrawPanel().set(leftTop.x, leftTop.y,
                            rightBottom.x, rightBottom.y);
                    drawerDensityValues.calculatePointsForDraw();
                }
            }
        }
    }

    private void createPanelsForPoints(Point leftTopPointPanelForPlot,
                                       Point rightBottomPointPanelForPlot) {
        final int WIDTH_DISTRIBUTION_POINTS_PANEL = (int)(WIDTH_DISTRIBUTION_PANEL * DrawerSensor.scaleFactor);
        Point leftTop = new Point();
        Point rightBottom = new Point();

        leftTop.x = leftTopPointPanelForPlot.x;
        rightBottom.x = rightBottomPointPanelForPlot.x;
        leftTop.y = heightWorkSpace / 2;

        if (isDistributionPoints) {
            int heightPanelForDistributionPoints = WIDTH_DISTRIBUTION_POINTS_PANEL;

            rightBottom.y = leftTop.y + heightPanelForDistributionPoints;

            drawerDistributionPoints.getDrawPanel().set(leftTop.x, leftTop.y,
                        rightBottom.x, rightBottom.y);
            drawerDistributionPoints.calculatePointsForDraw();

            leftTop.y += heightPanelForDistributionPoints;
            if (isDynamicsPoints && isDensityPoints) {
                rightBottom.y = heightWorkSpace - (heightWorkSpace - leftTop.y) / 2;
                drawerDynamicsPoints.getDrawPanel().set(leftTop.x, leftTop.y, rightBottom.x, rightBottom.y);
                drawerDynamicsPoints.calculatePointsForDraw();

                leftTop.y = rightBottom.y;
                rightBottom.y = heightWorkSpace;
                drawerDensityPoints.getDrawPanel().set(leftTop.x, leftTop.y, rightBottom.x, rightBottom.y);
                drawerDensityPoints.calculatePointsForDraw();
            } else {
                rightBottom.y = heightWorkSpace;
                if (isDensityPoints) {
                    drawerDensityPoints.getDrawPanel().set(leftTop.x, leftTop.y, rightBottom.x, rightBottom.y);
                    drawerDensityPoints.calculatePointsForDraw();
                } else {
                    drawerDynamicsPoints.getDrawPanel().set(leftTop.x, leftTop.y, rightBottom.x, rightBottom.y);
                    drawerDynamicsPoints.calculatePointsForDraw();
                }
            }
        } else {
            if (isDynamicsPoints && isDensityPoints) {
                rightBottom.y = heightWorkSpace - (heightWorkSpace - leftTop.y) / 2;
                drawerDynamicsPoints.getDrawPanel().set(leftTop.x, leftTop.y, rightBottom.x, rightBottom.y);
                drawerDynamicsPoints.calculatePointsForDraw();

                leftTop.y = rightBottom.y;
                rightBottom.y = heightWorkSpace;
                drawerDensityPoints.getDrawPanel().set(leftTop.x, leftTop.y, rightBottom.x, rightBottom.y);
                drawerDensityPoints.calculatePointsForDraw();
            } else {
                rightBottom.y = heightWorkSpace;
                if (isDensityPoints) {
                    drawerDensityPoints.getDrawPanel().set(leftTop.x, leftTop.y, rightBottom.x, rightBottom.y);
                    drawerDensityPoints.calculatePointsForDraw();
                } else {
                    drawerDynamicsPoints.getDrawPanel().set(leftTop.x, leftTop.y, rightBottom.x, rightBottom.y);
                    drawerDynamicsPoints.calculatePointsForDraw();
                }
            }
        }
    }

    @Override
    public void createViewPanels(int width, int height) {
        widthWorkSpace = width;
        heightWorkSpace = height;
        int widthPanelForPlot = width / 2;
        heightPanelForPlot = height / (2 * (drawerPlotsLimitedFunctions.size() + 1));
        final int WIDTH_DISTRIBUTION_VALUES_PANEL = 30;
        Point leftTop, rightBottom;
        leftTop = new Point(0, 0);
        rightBottom = new Point(widthPanelForPlot, heightPanelForPlot);
        drawerPlotMinimizedFunction.getDrawPanel().set(leftTop.x, leftTop.y, rightBottom.x, rightBottom.y);
        drawerPlotMinimizedFunction.calculatePointsForDraw();

        for (int i = 0; i < drawerPlotsLimitedFunctions.size(); i++) {
            drawerPlotsLimitedFunctions.get(i).getDrawPanel().set(leftTop.x, leftTop.y + ((i + 1) * heightPanelForPlot),
                    rightBottom.x, rightBottom.y + ((i + 1) * heightPanelForPlot));
            drawerPlotsLimitedFunctions.get(i).calculatePointsForDraw();

           if (i + 1 == drawerPlotsLimitedFunctions.size()) {
               createPanelsForValues(leftTop, rightBottom, drawerPlotsLimitedFunctions.size());
           }
        }
        //mHiderInvalidPoints.calculateHideRectangles();

        createPanelsForPoints(leftTop, rightBottom);

        leftTop.x = drawerDistributionPoints.getDrawPanel().right;
        leftTop.y = drawerDistributionPoints.getDrawPanel().top;
        Rect informPanel = new Rect(leftTop.x, leftTop.y, width, height);
        drawerInformationPanel.setDrawPanel(informPanel);
    }

    @Override
    public void updateData(ITask task) {
        super.updateData(task);

        int countLimitedFunctions = task.getLimitationFunctions().size();
        /*for (int i = 0; i < indexesForDraw.length; i++) {
            indexesForDraw[i] = 0;
        }*/
        drawerPlotMinimizedFunction.setContent(task.getMinimizedFunction());
        for (int i = 0; i < countLimitedFunctions; i++) {
            drawerPlotsLimitedFunctions.get(i).setContent(task.getLimitationFunctions().get(i));
        }
        penaltyFunction = new PenaltyFunction(task.getMinimizedFunction(), task.getLimitationFunctions());
        drawerPlotsLimitedFunctions.get(countLimitedFunctions).setContent(penaltyFunction);
        //drawerPlotsLimitedFunctions.get(countLimitedFunctions).setContent(task.getMinimizedFunction());
        //mHiderInvalidPoints.updateFunctions(task.getLimitationFunctions());

        /*List<List<Dot>> indexDots = new ArrayList<>();
        for (int i = 0; i < countLimitedFunctions + 2; i++) {
            indexDots.add(new ArrayList<Dot>());
        }*/
        dots = task.getStorage();
        /*for (int i = 0; i < dots.size(); i++) {
            indexDots.get(dots.get(i).index).add(dots.get(i));
        }*/

        if (isDistributionPoints) {
            drawerDistributionPoints.setContent(penaltyFunction, dots);
        }
        if (isDistributionValues) {
            drawerDistributionValues.setContent(penaltyFunction, dots);
        }
        if (isDensityPoints) {
            drawerDensityPoints.setContent(penaltyFunction, dots);
        }
        if (isDensityValues) {
            drawerDensityValues.setContent(penaltyFunction, dots);
        }
        if (isDynamicsPoints) {
            drawerDynamicsPoints.setContent(penaltyFunction, dots);
        }
        if (isDynamicsValues) {
            drawerDynamicsValues.setContent(penaltyFunction, dots);
        }
        //mDrawerDistributionPoints.get(0).setContent(task.getMinimizedFunction(), indexDots.get(countLimitedFunctions + 1));
        /*drawerDistributionValues.get(0).setContent(task.getMinimizedFunction(), indexDots.get(countLimitedFunctions + 1));
        for (int i = 1; i < countLimitedFunctions + 1; i++) {
            mDrawerDistributionPoints.get(i).setContent(task.getLimitationFunctions().get(i - 1), indexDots.get(i));
            drawerDistributionValues.get(i).setContent(task.getLimitationFunctions().get(i - 1), indexDots.get(i));
        }
        mDrawerDistributionPoints.get(countLimitedFunctions + 1).setContent(task.getMinimizedFunction(), dots);*/
        //drawerDistributionValues.get(countLimitedFunctions + 1).setContent(task.getMinimizedFunction(), dots);

        //drawerDensityPoints.setContent(task);
        drawerInformationPanel.setContent(task);
    }

    @Override
    public void drawSensors(Canvas canvas, int index) {
        drawerPlotMinimizedFunction.draw(canvas, index);
        for (DrawerSensor plot : drawerPlotsLimitedFunctions) {
            plot.draw(canvas, index);
        }
        //mHiderInvalidPoints.draw(canvas);

        /*if (index < dots.size() - 1) {
            indexesForDraw[dots.get(index).index]++;
        }*/

        if (isDistributionPoints) {
            drawerDistributionPoints.draw(canvas, index);
        }
        if (isDistributionValues) {
            drawerDistributionValues.draw(canvas, index);
        }
        if (isDensityPoints) {
            drawerDensityPoints.draw(canvas, index);
        }
        if (isDensityValues) {
            drawerDensityValues.draw(canvas, index);
        }
        if (isDynamicsPoints) {
            drawerDynamicsPoints.draw(canvas, index);
        }
        if (isDynamicsValues) {
            drawerDynamicsValues.draw(canvas, index);
        }
        //mDrawerDistributionPoints.get(0).draw(canvas, index);
        //mDrawerDistributionPoints.get(0).draw(canvas, indexesForDraw[indexesForDraw.length - 1]);
        /*drawerDistributionValues.get(0).draw(canvas, indexesForDraw[indexesForDraw.length - 1]);
        int i;
        for (i = 1; i < mDrawerDistributionPoints.size() - 1; i++) {
            mDrawerDistributionPoints.get(i).draw(canvas, indexesForDraw[i]);
            drawerDistributionValues.get(i).draw(canvas, indexesForDraw[i]);
        }
        mDrawerDistributionPoints.get(i).draw(canvas, index);*/
        //drawerDistributionValues.get(i).draw(canvas, index);

        //drawerDensityPoints.draw(canvas, index);
        drawerInformationPanel.draw(canvas, index);


        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
        /*canvas.drawRect(drawerPlotMinimizedFunction.getDrawPanel(), paint);
        for (DrawerSensor drawerSensor : drawerPlotsLimitedFunctions) {
            canvas.drawRect(drawerSensor.getDrawPanel(), mPaint);
        }

        for (DrawerSensor drawerSensor : mDrawerDistributionPoints) {
            canvas.drawRect(drawerSensor.getDrawPanel(), mPaint);
        }*/
    }
}
