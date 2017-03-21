package draw;

import android.graphics.Canvas;
import android.graphics.Paint;

import task.ITask;

public abstract class CalculatorSensors {
    protected boolean isDistributionPoints = true;
    protected boolean isDistributionValues = true;
    protected boolean isDynamicsPoints = true;
    protected boolean isDynamicsValues = false;
    protected boolean isDensityPoints = false;
    protected boolean isDensityValues = true;

    protected int widthWorkSpace;
    protected int heightWorkSpace;

    protected Paint paint = new Paint();
    protected final int WIDTH_DISTRIBUTION_PANEL = 30;

    public CalculatorSensors(ITask task) {
        boolean[] isCheckedSensors = task.getSettings().getCheckedSensors();

        isDistributionPoints = isCheckedSensors[0];
        isDistributionValues = isCheckedSensors[1];
        isDynamicsPoints     = isCheckedSensors[2];
        isDynamicsValues     = isCheckedSensors[3];
        isDensityPoints      = isCheckedSensors[4];
        isDensityValues      = isCheckedSensors[5];
    }
    public abstract void createViewPanels(int width, int height);
    public void updateData(ITask task) {
        boolean[] isCheckedSensors = task.getSettings().getCheckedSensors();
        boolean isCreateNewViewPanels = false;

        if (isDistributionPoints != isCheckedSensors[0]) {
            isDistributionPoints = isCheckedSensors[0];
            isCreateNewViewPanels = true;
        }
        if (isDistributionValues != isCheckedSensors[1]) {
            isDistributionValues = isCheckedSensors[1];
            isCreateNewViewPanels = true;
        }
        if (isDynamicsPoints != isCheckedSensors[2]) {
            isDynamicsPoints = isCheckedSensors[2];
            isCreateNewViewPanels = true;
        }
        if (isDynamicsValues != isCheckedSensors[3]) {
            isDynamicsValues = isCheckedSensors[3];
            isCreateNewViewPanels = true;
        }
        if (isDensityPoints != isCheckedSensors[4]) {
            isDensityPoints = isCheckedSensors[4];
            isCreateNewViewPanels = true;
        }
        if (isDensityValues != isCheckedSensors[5]) {
            isDensityValues = isCheckedSensors[5];
            isCreateNewViewPanels = true;
        }
        if (isCreateNewViewPanels) {
            createViewPanels(widthWorkSpace, heightWorkSpace);
        }
    }
    public abstract void drawSensors(Canvas canvas, int index);
    public void setScaleFactor(float scaleFactor) {
        DrawerSensor.scaleFactor = scaleFactor;
    }
}
