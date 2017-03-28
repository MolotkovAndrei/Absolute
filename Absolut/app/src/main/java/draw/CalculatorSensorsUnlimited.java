package draw;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import task.ITask;

public class CalculatorSensorsUnlimited extends CalculatorSensors {
    private Rect panelForPlot = new Rect();
    private Rect panelForDistributionPoints= new Rect();
    private Rect panelForDynamicsPoints= new Rect();
    private Rect panelForDistributionValue= new Rect();
    private Rect panelForDynamicsValue= new Rect();
    private Rect panelForDensityPoints = new Rect();
    private Rect panelForDensityValues = new Rect();
    private Rect panelForInformation = new Rect();

    private DrawerSensor drawerPlot;
    private DrawerSensor calculatorDistributionPoints;
    private DrawerSensor calculatorDistributionValues;
    private DrawerSensor calculatorDynamicsPoints;
    private DrawerSensor calculatorDynamicsValues;
    private DrawerSensor calculatorDensityPoints;
    private DrawerSensor calculatorDensityValues;
    private DrawerSensor drawerInformation;

    public CalculatorSensorsUnlimited(ITask task) {
        super(task);

        drawerPlot = new DrawerPlot(task, panelForPlot);
        calculatorDistributionPoints = new CalculatorDistributionPoints(task, panelForDistributionPoints);
        calculatorDistributionValues = new CalculatorDistributionValues(task, panelForDistributionValue);
        calculatorDynamicsPoints = new CalculatorDynamicsPoints(task, panelForDynamicsPoints);
        calculatorDynamicsValues = new CalculatorDynamicsValues(task, panelForDynamicsValue);
        calculatorDensityPoints = new CalculatorDensityPoints(task, panelForDensityPoints);
        calculatorDensityValues = new CalculatorDensityValues(task, panelForDensityValues);
        drawerInformation = new DrawerInformationPanelUnlimited(task, panelForInformation);
    }

    @Override
    public void createViewPanels(int width, int height) {
        this.widthWorkSpace = width;
        this.heightWorkSpace = height;

        int widthPanelForPlot = width / 2;
        int heightPanelForPlot = height / 2;
        panelForPlot.set(0, 0, widthPanelForPlot, heightPanelForPlot);
        drawerPlot.setDrawPanel(panelForPlot);

        widthDistributionPanel = (int) (heightPanelForPlot * WIDTH_COEFFICIENT);
        createPanelsForPoints(widthPanelForPlot, heightPanelForPlot);
        createPanelsForValues(widthPanelForPlot, heightPanelForPlot);

        panelForInformation.set(widthPanelForPlot, heightPanelForPlot, width, height);
        drawerInformation.setDrawPanel(panelForInformation);
    }

    @Override
    public void updateData(ITask task) {
        super.updateData(task);

        drawerPlot.setContent(task);
        drawerInformation.setContent(task);
        if (isDistributionPoints) {
            calculatorDistributionPoints.setContent(task);
        }
        if (isDistributionValues) {
            calculatorDistributionValues.setContent(task);
        }
        if (isDynamicsPoints) {
            calculatorDynamicsPoints.setContent(task);
        }
        if (isDynamicsValues) {
            calculatorDynamicsValues.setContent(task);
        }
        if (isDensityPoints) {
            calculatorDensityPoints.setContent(task);
        }
        if (isDensityValues) {
            calculatorDensityValues.setContent(task);
        }
    }

    @Override
    public void drawSensors(Canvas canvas, int index) {
        System.out.println("index: " + index);

        drawerPlot.draw(canvas, index);
        if (isDensityPoints) {
            calculatorDensityPoints.draw(canvas, index);
        }
        if (isDensityValues) {
            calculatorDensityValues.draw(canvas, index);
        }
        if (isDistributionPoints) {
            calculatorDistributionPoints.draw(canvas, index);
        }
        if (isDistributionValues) {
            calculatorDistributionValues.draw(canvas, index);
        }
        if (isDynamicsPoints) {
            calculatorDynamicsPoints.draw(canvas, index);
        }
        if (isDynamicsValues) {
            calculatorDynamicsValues.draw(canvas, index);
        }
        drawerInformation.draw(canvas, index);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
        canvas.drawRect(0, 0, widthWorkSpace, heightWorkSpace, paint);
    }

    private void createPanelsForPoints(int widthPanelForPlot, int heightPanelForPlot) {
        if (isDistributionPoints) {
            int bottomPointPanelDistribution = heightPanelForPlot + widthDistributionPanel;
            panelForDistributionPoints.set(0, heightPanelForPlot, widthPanelForPlot, bottomPointPanelDistribution);
            calculatorDistributionPoints.setDrawPanel(panelForDistributionPoints);

            if (isDynamicsPoints && isDensityPoints) {
                int bottomPointPanelDynamics = bottomPointPanelDistribution + (heightWorkSpace - bottomPointPanelDistribution) / 2;
                panelForDynamicsPoints.set(0, bottomPointPanelDistribution, widthPanelForPlot, bottomPointPanelDynamics);
                calculatorDynamicsPoints.setDrawPanel(panelForDynamicsPoints);

                panelForDensityPoints.set(0, panelForDynamicsPoints.bottom, widthPanelForPlot, heightWorkSpace);
                calculatorDensityPoints.setDrawPanel(panelForDensityPoints);
            } else if (isDynamicsPoints) {
                panelForDynamicsPoints.set(0, bottomPointPanelDistribution, widthPanelForPlot, heightWorkSpace);
                calculatorDynamicsPoints.setDrawPanel(panelForDynamicsPoints);
            } else {
                panelForDensityPoints.set(0, bottomPointPanelDistribution, widthPanelForPlot, heightWorkSpace);
                calculatorDensityPoints.setDrawPanel(panelForDensityPoints);
            }
        } else {
            panelForDistributionPoints.set(0, 0, 0, 0);
            if (isDynamicsPoints && isDensityPoints) {
                int bottomPointPanelDynamics = heightPanelForPlot + (heightWorkSpace - heightPanelForPlot) / 2;
                panelForDynamicsPoints.set(0, heightPanelForPlot, widthPanelForPlot, bottomPointPanelDynamics);
                calculatorDynamicsPoints.setDrawPanel(panelForDynamicsPoints);

                panelForDensityPoints.set(0, panelForDynamicsPoints.bottom, widthPanelForPlot, heightWorkSpace);
                calculatorDensityPoints.setDrawPanel(panelForDensityPoints);
            } else if (isDynamicsPoints){
                panelForDynamicsPoints.set(0, heightPanelForPlot, widthPanelForPlot, heightWorkSpace);
                calculatorDynamicsPoints.setDrawPanel(panelForDynamicsPoints);
            } else {
                panelForDensityPoints.set(0, heightPanelForPlot, widthPanelForPlot, heightWorkSpace);
                calculatorDensityPoints.setDrawPanel(panelForDensityPoints);
            }
        }
    }

    private void createPanelsForValues(int widthPanelForPlot, int heightPanelForPlot) {
        if (isDistributionValues) {
            int rightPointPanelForDistribution = widthPanelForPlot + widthDistributionPanel;
            panelForDistributionValue.set(widthPanelForPlot, 0, rightPointPanelForDistribution, heightPanelForPlot);
            calculatorDistributionValues.setDrawPanel(panelForDistributionValue);

            if (isDynamicsValues && isDensityValues) {
                int rightPointPanelForDynamics = rightPointPanelForDistribution + (widthWorkSpace - rightPointPanelForDistribution) / 2;
                panelForDynamicsValue.set(rightPointPanelForDistribution, 0, rightPointPanelForDynamics, heightPanelForPlot);
                calculatorDynamicsValues.setDrawPanel(panelForDynamicsValue);

                panelForDensityValues.set(panelForDynamicsValue.right, 0, widthWorkSpace, heightPanelForPlot);
                calculatorDensityValues.setDrawPanel(panelForDensityValues);
            } else if (isDynamicsValues) {
                panelForDynamicsValue.set(rightPointPanelForDistribution, 0, widthWorkSpace, heightPanelForPlot);
                calculatorDynamicsValues.setDrawPanel(panelForDynamicsValue);
                panelForDensityValues.set(0, 0, 0, 0);
            } else {
                panelForDensityValues.set(rightPointPanelForDistribution, 0, widthWorkSpace, heightPanelForPlot);
                calculatorDensityValues.setDrawPanel(panelForDensityValues);
                panelForDynamicsValue.set(0, 0, 0, 0);
            }
        } else {
            panelForDistributionValue.set(0, 0, 0, 0);
            if (isDynamicsValues && isDensityValues) {
                int rightPointPanelForDynamics = widthPanelForPlot + (widthWorkSpace - widthPanelForPlot) / 2;
                panelForDynamicsValue.set(widthPanelForPlot, 0, rightPointPanelForDynamics, heightPanelForPlot);
                calculatorDynamicsValues.setDrawPanel(panelForDynamicsValue);

                panelForDensityValues.set(panelForDynamicsValue.right, 0, widthWorkSpace, heightPanelForPlot);
                calculatorDensityValues.setDrawPanel(panelForDensityValues);
            } else if (isDynamicsValues) {
                panelForDynamicsValue.set(widthPanelForPlot, 0, widthWorkSpace, heightPanelForPlot);
                calculatorDynamicsValues.setDrawPanel(panelForDynamicsValue);
                panelForDensityValues.set(0, 0, 0, 0);
            } else {
                panelForDensityValues.set(widthPanelForPlot, 0, widthWorkSpace, heightPanelForPlot);
                calculatorDensityValues.setDrawPanel(panelForDensityValues);
                panelForDynamicsValue.set(0, 0, 0, 0);
            }
        }
    }
}
