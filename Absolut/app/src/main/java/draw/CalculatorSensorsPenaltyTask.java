package draw;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

import function.IFunction;
import function.PenaltyFunction;
import storage.Dot;
import task.ITask;
import task.PenaltyTask;

public class CalculatorSensorsPenaltyTask extends CalculatorSensors {
    private List<DrawerSensor> drawerPlotsLimitedFunctions;
    private DrawerSensor drawerPlotMinimizedFunction;
    private DrawerSensor drawerDistributionPoints;
    private List<DrawerSensor> drawersDistributionValues;
    private DrawerSensor drawerDensityPoints;
    private List<DrawerSensor> drawersDensityValues;
    private DrawerSensor drawerDynamicsPoints;
    private List<DrawerSensor> drawersDynamicsValues;
    private DrawerSensor drawerInformationPanel;

    private List<Dot> dots;
    private int heightPanelForPlot;
    private PenaltyFunction penaltyFunction;

    public CalculatorSensorsPenaltyTask(final ITask task) {
        super(task);
        int countLimitedFunctions = task.getLimitationFunctions().size();
        dots = task.getStorage();
        drawerPlotsLimitedFunctions = new ArrayList<>();
        drawersDistributionValues = new ArrayList<>();
        drawersDynamicsValues = new ArrayList<>();
        drawersDensityValues = new ArrayList<>();


        drawerPlotMinimizedFunction = new DrawerPlot(task.getMinimizedFunction(), new Rect());
        drawersDistributionValues.add(new CalculatorDistributionValues(task, new Rect()));
        drawersDynamicsValues.add(new CalculatorDynamicsValues(task, new Rect()));
        drawersDensityValues.add(new CalculatorDensityValues(task, new Rect()));
        for (int i = 0; i < countLimitedFunctions; i++) {
            IFunction function = task.getLimitationFunctions().get(i);
            drawerPlotsLimitedFunctions.add(new DrawerPlot(function, new Rect()));
            drawersDistributionValues.add(new CalculatorDistributionValues(function, dots, new Rect()));
            drawersDynamicsValues.add(new CalculatorDynamicsValues(function, dots, new Rect()));
            drawersDensityValues.add(new CalculatorDensityValues(function, dots, new Rect()));
        }
        penaltyFunction = new PenaltyFunction(task.getMinimizedFunction(), task.getLimitationFunctions());
        drawerPlotsLimitedFunctions.add(new DrawerPlot(penaltyFunction, new Rect()));
        drawerDistributionPoints = new CalculatorDistributionPoints(task, new Rect());
        drawerDensityPoints = new CalculatorDensityPoints(task, new Rect());
        drawerDynamicsPoints = new CalculatorDynamicsPoints(task, new Rect());
        drawersDistributionValues.add(new CalculatorDistributionValues(penaltyFunction, dots, new Rect()));
        drawersDynamicsValues.add(new CalculatorDynamicsValues(penaltyFunction, dots, new Rect()));
        drawersDensityValues.add(new CalculatorDensityValues(penaltyFunction, dots, new Rect()));
        drawerInformationPanel = new DrawerInformationPanelUnlimited(task, new Rect());
    }

    @Override
    public void createViewPanels(int width, int height) {
        widthWorkSpace = width;
        heightWorkSpace = height;
        int widthPanelForPlot = width / 2;
        heightPanelForPlot = height / (2 * (drawerPlotsLimitedFunctions.size() + 1));
        widthDistributionPanel = (int)(height * WIDTH_COEFFICIENT) / 2;
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

           if (i + 1 <= drawerPlotsLimitedFunctions.size()) {
               createPanelsForValues(leftTop, rightBottom, i + 1);
           }
        }
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
        drawerPlotMinimizedFunction.setContent(task.getMinimizedFunction());
        for (int i = 0; i < countLimitedFunctions; i++) {
            drawerPlotsLimitedFunctions.get(i).setContent(task.getLimitationFunctions().get(i));
        }
        penaltyFunction = new PenaltyFunction(task.getMinimizedFunction(), task.getLimitationFunctions());
        drawerPlotsLimitedFunctions.get(countLimitedFunctions).setContent(penaltyFunction);

        dots = task.getStorage();
        System.out.println("size dots: " + dots.size());
        ArrayList<Dot> testDotsFunctions = new ArrayList<>();
        if (task instanceof PenaltyTask) {
            IFunction function;
            for (int i = 0; i <= countLimitedFunctions; i++) {
                if (i == 0) {
                    function = task.getMinimizedFunction();
                } else {
                    function = task.getLimitationFunctions().get(i - 1);
                }
                testDotsFunctions = (ArrayList) ((PenaltyTask) task).getTestPointsForFunctions().get(i);
                System.out.println("size testDotsFunctions: " + testDotsFunctions.size());
                if (isDistributionValues) {
                    drawersDistributionValues.get(i).setContent(function, testDotsFunctions);
                }
                if (isDensityValues) {
                    drawersDensityValues.get(i).setContent(function, testDotsFunctions);
                }
                if (isDynamicsValues) {
                    drawersDynamicsValues.get(i).setContent(function, testDotsFunctions);
                }
            }
        }

        if (isDistributionPoints) {
            drawerDistributionPoints.setContent(penaltyFunction, dots);
        }
        if (isDistributionValues) {
            drawersDistributionValues.get(countLimitedFunctions + 1).setContent(penaltyFunction, dots);
        }
        if (isDensityPoints) {
            drawerDensityPoints.setContent(penaltyFunction, dots);
        }
        if (isDensityValues) {
            drawersDensityValues.get(countLimitedFunctions + 1).setContent(penaltyFunction, dots);
        }
        if (isDynamicsPoints) {
            drawerDynamicsPoints.setContent(penaltyFunction, dots);
        }
        if (isDynamicsValues) {
            drawersDynamicsValues.get(countLimitedFunctions + 1).setContent(penaltyFunction, dots);
        }
        drawerInformationPanel.setContent(task);
    }

    @Override
    public void drawSensors(Canvas canvas, int index) {
        drawerPlotMinimizedFunction.draw(canvas, index);
        for (DrawerSensor plot : drawerPlotsLimitedFunctions) {
            plot.draw(canvas, index);
        }

        if (isDistributionPoints) {
            drawerDistributionPoints.draw(canvas, index);
        }
        if (isDistributionValues) {
            for (DrawerSensor drawer : drawersDistributionValues) {
                drawer.draw(canvas, index);
            }
        }
        if (isDensityPoints) {
            drawerDensityPoints.draw(canvas, index);
        }
        if (isDensityValues) {
            for (DrawerSensor drawer : drawersDensityValues) {
                drawer.draw(canvas, index);
            }
        }
        if (isDynamicsPoints) {
            drawerDynamicsPoints.draw(canvas, index);
        }
        if (isDynamicsValues) {
            for (DrawerSensor drawer : drawersDynamicsValues) {
                drawer.draw(canvas, index);
            }
        }
        drawerInformationPanel.draw(canvas, index);


        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
    }

    private void createPanelsForValues(Point leftTopPointPanelForPlot,
                                       Point rightBottomPointPanelForPlot,
                                       int numberPanel) {
        Point leftTop = new Point();
        Point rightBottom = new Point();

        leftTop.x = rightBottomPointPanelForPlot.x;
        leftTop.y = leftTopPointPanelForPlot.y + numberPanel * heightPanelForPlot;
        rightBottom.y = rightBottomPointPanelForPlot.y + numberPanel * heightPanelForPlot;
        if (isDistributionValues) {
            rightBottom.x = rightBottomPointPanelForPlot.x + widthDistributionPanel;

            drawersDistributionValues.get(numberPanel).getDrawPanel().set(leftTop.x, leftTop.y,
                    rightBottom.x, rightBottom.y);
            drawersDistributionValues.get(numberPanel).calculatePointsForDraw();

            if (isDynamicsValues && isDensityValues) {
                Rect panelForDistributionValues = drawersDistributionValues.get(numberPanel).getDrawPanel();
                leftTop.x = panelForDistributionValues.right;
                rightBottom.x = panelForDistributionValues.right +
                        (widthWorkSpace - panelForDistributionValues.right) / 2;
                drawersDynamicsValues.get(numberPanel).getDrawPanel().set(leftTop.x, leftTop.y,
                        rightBottom.x, rightBottom.y);
                drawersDynamicsValues.get(numberPanel).calculatePointsForDraw();

                Rect panelForDynamicsValues = drawersDynamicsValues.get(numberPanel).getDrawPanel();
                leftTop.x = panelForDynamicsValues.right;
                rightBottom.x = widthWorkSpace;
                drawersDensityValues.get(numberPanel).getDrawPanel().set(leftTop.x, leftTop.y,
                        rightBottom.x, rightBottom.y);
                drawersDensityValues.get(numberPanel).calculatePointsForDraw();
            } else {
                Rect panelForDistributionValues = drawersDistributionValues.get(numberPanel).getDrawPanel();
                leftTop.x = panelForDistributionValues.right;
                rightBottom.x = widthWorkSpace;
                if (isDynamicsValues) {
                    drawersDynamicsValues.get(numberPanel).getDrawPanel().set(leftTop.x, leftTop.y,
                            rightBottom.x, rightBottom.y);
                    drawersDynamicsValues.get(numberPanel).calculatePointsForDraw();
                } else {
                    drawersDensityValues.get(numberPanel).getDrawPanel().set(leftTop.x, leftTop.y,
                            rightBottom.x, rightBottom.y);
                    drawersDensityValues.get(numberPanel).calculatePointsForDraw();
                }
            }
        } else {
            if (isDynamicsValues && isDensityValues) {
                rightBottom.x = rightBottomPointPanelForPlot.x +
                        (widthWorkSpace - rightBottomPointPanelForPlot.x) / 2;
                drawersDynamicsValues.get(numberPanel).getDrawPanel().set(leftTop.x, leftTop.y,
                        rightBottom.x, rightBottom.y);
                drawersDynamicsValues.get(numberPanel).calculatePointsForDraw();

                Rect panelForDynamicsValues = drawersDynamicsValues.get(numberPanel).getDrawPanel();
                leftTop.x = panelForDynamicsValues.right;
                rightBottom.x = widthWorkSpace;
                drawersDensityValues.get(numberPanel).getDrawPanel().set(leftTop.x, leftTop.y,
                        rightBottom.x, rightBottom.y);
                drawersDensityValues.get(numberPanel).calculatePointsForDraw();
            } else {
                rightBottom.x = widthWorkSpace;
                if (isDynamicsValues) {
                    drawersDynamicsValues.get(numberPanel).getDrawPanel().set(leftTop.x, leftTop.y,
                            rightBottom.x, rightBottom.y);
                    drawersDynamicsValues.get(numberPanel).calculatePointsForDraw();
                } else {
                    drawersDensityValues.get(numberPanel).getDrawPanel().set(leftTop.x, leftTop.y,
                            rightBottom.x, rightBottom.y);
                    drawersDensityValues.get(numberPanel).calculatePointsForDraw();
                }
            }
        }
    }

    private void createPanelsForPoints(Point leftTopPointPanelForPlot,
                                       Point rightBottomPointPanelForPlot) {
        Point leftTop = new Point();
        Point rightBottom = new Point();

        leftTop.x = leftTopPointPanelForPlot.x;
        rightBottom.x = rightBottomPointPanelForPlot.x;
        leftTop.y = heightWorkSpace / 2;

        if (isDistributionPoints) {
            rightBottom.y = leftTop.y + widthDistributionPanel;

            drawerDistributionPoints.getDrawPanel().set(leftTop.x, leftTop.y,
                    rightBottom.x, rightBottom.y);
            drawerDistributionPoints.calculatePointsForDraw();

            leftTop.y += widthDistributionPanel;
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
}
