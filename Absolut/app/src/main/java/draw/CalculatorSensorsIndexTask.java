package draw;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

import function.IFunction;
import storage.Dot;
import task.ITask;

public class CalculatorSensorsIndexTask extends CalculatorSensors {
    //private Paint mPaint = new Paint();

    private List<DrawerSensor> drawerPlotsLimitedFunctions;
    private DrawerSensor drawerPlotMinimizedFunction;
    private HiderInvalidPoints mHiderInvalidPoints;
    private List<DrawerSensor> mDrawerDistributionPoints;
    private List<DrawerSensor> drawerDistributionValues;
    private DrawerSensor drawerDensityPoints;
    private List<DrawerSensor> drawerDensityValues;
    private DrawerSensor drawerDynamicsPoints;
    private List<DrawerSensor> drawerDynamicsValues;
    private DrawerSensor drawerInformationPanel;
    private int[] indexesForDraw;
    private List<Dot> dots;
    private int heightPanelForPlot;

    private boolean[] isIndex;

    public CalculatorSensorsIndexTask(final ITask task) {
        super(task);

        int countLimitedFunctions = task.getLimitationFunctions().size();
        indexesForDraw = new int[countLimitedFunctions + 2];
        dots = task.getStorage();
        isIndex = new boolean[dots.size() + 1];
        drawerPlotsLimitedFunctions = new ArrayList<>();
        mDrawerDistributionPoints = new ArrayList<>();
        drawerDistributionValues = new ArrayList<>();
        drawerDensityValues = new ArrayList<>();
        drawerDynamicsValues = new ArrayList<>();
        drawerDynamicsPoints = new CalculatorDynamicsPoints(task, new Rect());
        drawerDensityPoints = new CalculatorDensityPoints(task, new Rect());
        drawerInformationPanel = new DrawerInformationPanelLimited(task, new Rect());

        drawerPlotMinimizedFunction = new DrawerPlot(task.getMinimizedFunction(), new Rect());
        mDrawerDistributionPoints.add(new CalculatorDistributionPoints(task, new Rect()));
        drawerDistributionValues.add(new CalculatorDistributionValues(task, new Rect()));
        drawerDynamicsValues.add(new CalculatorDynamicsValues(task, new Rect()));
        drawerDensityValues.add(new CalculatorDensityValues(task, new Rect()));
        for (int i = 0; i < countLimitedFunctions; i++) {
            IFunction function = task.getLimitationFunctions().get(i);
            drawerPlotsLimitedFunctions.add(new DrawerPlot(function, new Rect()));
            mDrawerDistributionPoints.add(new CalculatorDistributionPoints(function, dots, new Rect()));
            drawerDistributionValues.add(new CalculatorDistributionValues(function, dots, new Rect()));
            drawerDynamicsValues.add(new CalculatorDynamicsValues(function, dots, new Rect()));
            drawerDensityValues.add(new CalculatorDensityValues(function, dots, new Rect()));
        }
        Rect drawPanelHiderInvalidPoints = new Rect();
        drawerPlotsLimitedFunctions.add(new DrawerPlot(task.getMinimizedFunction(), drawPanelHiderInvalidPoints));
        mHiderInvalidPoints = new HiderInvalidPoints(drawPanelHiderInvalidPoints, task.getLimitationFunctions());
        mDrawerDistributionPoints.add(new CalculatorDistributionPoints(task, new Rect()));
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

            drawerDistributionValues.get(numberPanel).getDrawPanel().set(leftTop.x, leftTop.y,
                    rightBottom.x, rightBottom.y);
            drawerDistributionValues.get(numberPanel).calculatePointsForDraw();

            if (isDynamicsValues && isDensityValues) {
                Rect panelForDistributionValues = drawerDistributionValues.get(numberPanel).getDrawPanel();
                leftTop.x = panelForDistributionValues.right;
                rightBottom.x = panelForDistributionValues.right +
                        (widthWorkSpace - panelForDistributionValues.right) / 2;
                drawerDynamicsValues.get(numberPanel).getDrawPanel().set(leftTop.x, leftTop.y,
                        rightBottom.x, rightBottom.y);
                drawerDynamicsValues.get(numberPanel).calculatePointsForDraw();

                Rect panelForDynamicsValues = drawerDynamicsValues.get(numberPanel).getDrawPanel();
                leftTop.x = panelForDynamicsValues.right;
                rightBottom.x = widthWorkSpace;
                drawerDensityValues.get(numberPanel).getDrawPanel().set(leftTop.x, leftTop.y,
                        rightBottom.x, rightBottom.y);
                drawerDensityValues.get(numberPanel).calculatePointsForDraw();
            } else {
                Rect panelForDistributionValues = drawerDistributionValues.get(numberPanel).getDrawPanel();
                leftTop.x = panelForDistributionValues.right;
                rightBottom.x = widthWorkSpace;
                if (isDynamicsValues) {
                    drawerDynamicsValues.get(numberPanel).getDrawPanel().set(leftTop.x, leftTop.y,
                            rightBottom.x, rightBottom.y);
                    drawerDynamicsValues.get(numberPanel).calculatePointsForDraw();
                } else {
                    drawerDensityValues.get(numberPanel).getDrawPanel().set(leftTop.x, leftTop.y,
                            rightBottom.x, rightBottom.y);
                    drawerDensityValues.get(numberPanel).calculatePointsForDraw();
                }
            }
        } else {
            if (isDynamicsValues && isDensityValues) {
                rightBottom.x = rightBottomPointPanelForPlot.x +
                        (widthWorkSpace - rightBottomPointPanelForPlot.x) / 2;
                drawerDynamicsValues.get(numberPanel).getDrawPanel().set(leftTop.x, leftTop.y,
                        rightBottom.x, rightBottom.y);
                drawerDynamicsValues.get(numberPanel).calculatePointsForDraw();

                Rect panelForDynamicsValues = drawerDynamicsValues.get(numberPanel).getDrawPanel();
                leftTop.x = panelForDynamicsValues.right;
                rightBottom.x = widthWorkSpace;
                drawerDensityValues.get(numberPanel).getDrawPanel().set(leftTop.x, leftTop.y,
                        rightBottom.x, rightBottom.y);
                drawerDensityValues.get(numberPanel).calculatePointsForDraw();
            } else {
                rightBottom.x = widthWorkSpace;
                if (isDynamicsValues) {
                    drawerDynamicsValues.get(numberPanel).getDrawPanel().set(leftTop.x, leftTop.y,
                            rightBottom.x, rightBottom.y);
                    drawerDynamicsValues.get(numberPanel).calculatePointsForDraw();
                } else {
                    drawerDensityValues.get(numberPanel).getDrawPanel().set(leftTop.x, leftTop.y,
                            rightBottom.x, rightBottom.y);
                    drawerDensityValues.get(numberPanel).calculatePointsForDraw();
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
            int heightPanelForDistributionPoints = heightWorkSpace / (4 * mDrawerDistributionPoints.size());
            if (heightPanelForDistributionPoints > WIDTH_DISTRIBUTION_POINTS_PANEL) {
                heightPanelForDistributionPoints = WIDTH_DISTRIBUTION_POINTS_PANEL;
            }
            rightBottom.y = leftTop.y + heightPanelForDistributionPoints;
            for (int i = 0; i < mDrawerDistributionPoints.size(); i++) {
                mDrawerDistributionPoints.get(i).getDrawPanel().set(leftTop.x, leftTop.y + (i * heightPanelForDistributionPoints),
                        rightBottom.x, rightBottom.y + (i * heightPanelForDistributionPoints));
                mDrawerDistributionPoints.get(i).calculatePointsForDraw();
            }
            leftTop.y += heightPanelForDistributionPoints * mDrawerDistributionPoints.size();
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
        Point leftTop, rightBottom;
        leftTop = new Point(0, 0);
        rightBottom = new Point(widthPanelForPlot, heightPanelForPlot);
        drawerPlotMinimizedFunction.getDrawPanel().set(leftTop.x, leftTop.y, rightBottom.x, rightBottom.y);
        drawerPlotMinimizedFunction.calculatePointsForDraw();
        createPanelsForValues(leftTop, rightBottom, 0);
        for (int i = 0; i < drawerPlotsLimitedFunctions.size(); i++) {
            drawerPlotsLimitedFunctions.get(i).getDrawPanel().set(leftTop.x, leftTop.y + ((i + 1) * heightPanelForPlot),
                    rightBottom.x, rightBottom.y + ((i + 1) * heightPanelForPlot));
            drawerPlotsLimitedFunctions.get(i).calculatePointsForDraw();

            if (i + 1 < drawerDistributionValues.size()) {
                createPanelsForValues(leftTop, rightBottom, i + 1);
            }
        }
        mHiderInvalidPoints.calculateHideRectangles();

        createPanelsForPoints(leftTop, rightBottom);

        leftTop.x = mDrawerDistributionPoints.get(0).getDrawPanel().right;
        leftTop.y = mDrawerDistributionPoints.get(0).getDrawPanel().top - heightPanelForPlot;
        Rect informPanel = new Rect(leftTop.x, leftTop.y, width, height);
        drawerInformationPanel.setDrawPanel(informPanel);
    }

    @Override
    public void updateData(ITask task) {
        super.updateData(task);

        int countLimitedFunctions = task.getLimitationFunctions().size();
        updateDrawersPlots(task, countLimitedFunctions);

        List<List<Dot>> indexDots = new ArrayList<>();
        for (int i = 0; i < countLimitedFunctions + 2; i++) {
            indexDots.add(new ArrayList<Dot>());
        }
        dots = task.getStorage();
        if (dots.size() != isIndex.length) {
            isIndex = new boolean[dots.size()];
        }
        for (Boolean b : isIndex) {
            b = false;
        }
        for (int i = 0; i < dots.size(); i++) {
            indexDots.get(dots.get(i).index).add(dots.get(i));
        }

        IFunction function = task.getMinimizedFunction();
        List<Dot> dotsForDraw = indexDots.get(countLimitedFunctions + 1);
        System.out.println("dotsForDraw.size= "+dotsForDraw.size());
        updateDrawersSensors(function, dotsForDraw, 0);
        for (int i = 1; i < countLimitedFunctions + 1; i++) {
            function = task.getLimitationFunctions().get(i - 1);
            dotsForDraw = indexDots.get(i);
            System.out.println("dotsForDraw.size= " + "i: "+ i + " "+dotsForDraw.size());
            updateDrawersSensors(function, dotsForDraw, i);
        }
        if (isDistributionPoints) {
            mDrawerDistributionPoints.get(countLimitedFunctions + 1).setContent(task.getMinimizedFunction(), dots);
        }
        if (isDynamicsPoints) {
            drawerDynamicsPoints.setContent(task);
        }
        if (isDensityPoints) {
            drawerDensityPoints.setContent(task);
        }
        drawerInformationPanel.setContent(task);
    }

    @Override
    public void drawSensors(Canvas canvas, int index) {
        drawerPlotMinimizedFunction.draw(canvas, index);
        for (DrawerSensor plot : drawerPlotsLimitedFunctions) {
            plot.draw(canvas, index);
        }
        mHiderInvalidPoints.draw(canvas);

        if (!isIndex[index] && index < dots.size() - 1) {
            indexesForDraw[dots.get(index).index]++;
            isIndex[index] = true;
        }

        int indexForDraw = indexesForDraw[indexesForDraw.length - 1];
        /*System.out.println("index= "+index);
        System.out.println("indexesForDraw= "+indexForDraw);
        System.out.println("drawPoints= "+mDrawerDistributionPoints.get(0).getDrawPoints().size());*/
        if (isDistributionPoints) {
            mDrawerDistributionPoints.get(0).draw(canvas, indexForDraw);
        }
        if (isDistributionValues) {
            drawerDistributionValues.get(0).draw(canvas, indexForDraw);
        }
        if (isDynamicsValues) {
            drawerDynamicsValues.get(0).draw(canvas, indexForDraw-1);
        }
        if (isDensityValues) {
            drawerDensityValues.get(0).draw(canvas, Math.max(0, indexForDraw));
        }
        int i;
        for (i = 1; i < mDrawerDistributionPoints.size() - 1; i++) {
            System.out.println("index= "+index);
            System.out.println("indexesForDraw= "+indexesForDraw[i]);
            System.out.println("drawPoints= "+mDrawerDistributionPoints.get(i).getDrawPoints().size());
            if (isDistributionPoints) {
                mDrawerDistributionPoints.get(i).draw(canvas, indexesForDraw[i]);
            }
            if (isDistributionValues) {
                drawerDistributionValues.get(i).draw(canvas, indexesForDraw[i]);
            }
            if (isDynamicsValues) {
                drawerDynamicsValues.get(i).draw(canvas, indexesForDraw[i]);
            }
            if (isDensityValues) {
                drawerDensityValues.get(i).draw(canvas, Math.max(0, indexesForDraw[i]));
            }
        }
        if (isDistributionPoints) {
            mDrawerDistributionPoints.get(i).draw(canvas, index);
        }

        if (isDynamicsPoints) {
            drawerDynamicsPoints.draw(canvas, index);
        }
        if (isDensityPoints) {
            drawerDensityPoints.draw(canvas, index);
        }
        drawerInformationPanel.draw(canvas, index);


        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
        canvas.drawRect(0, 0, widthWorkSpace, heightWorkSpace, paint);
    }

    private void updateDrawersSensors(IFunction function, List<Dot> dotsForDraw, int i) {
        if (isDistributionPoints) {
            mDrawerDistributionPoints.get(i).setContent(function, dotsForDraw);
        }
        if (isDistributionValues) {
            drawerDistributionValues.get(i).setContent(function, dotsForDraw);
        }
        if (isDynamicsValues) {
            drawerDynamicsValues.get(i).setContent(function, dotsForDraw);
        }
        if (isDensityValues) {
            drawerDensityValues.get(i).setContent(function, dotsForDraw);
        }
    }

    private void updateDrawersPlots(ITask task, int countLimitedFunctions) {
        for (int i = 0; i < indexesForDraw.length; i++) {
            indexesForDraw[i] = -1;
        }
        drawerPlotMinimizedFunction.setContent(task.getMinimizedFunction());
        for (int i = 0; i < countLimitedFunctions; i++) {
            drawerPlotsLimitedFunctions.get(i).setContent(task.getLimitationFunctions().get(i));
        }
        //drawerPlotsLimitedFunctions.get(countLimitedFunctions).setContent(penaltyFunction);
        drawerPlotsLimitedFunctions.get(countLimitedFunctions).setContent(task.getMinimizedFunction());
        mHiderInvalidPoints.updateFunctions(task.getLimitationFunctions());
    }
}
