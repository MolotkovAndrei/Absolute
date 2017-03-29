package draw;

import android.graphics.Rect;

import task.ITask;

public class DrawerInformationPanelLimited extends DrawerInformationPanel {
    private int indexPoints = 0;

    public DrawerInformationPanelLimited(ITask task, Rect drawPanel) {
        super(task, drawPanel);
    }

    @Override
    public void setContent(ITask task) {
        indexPoints = task.getLimitationFunctions().size() + 1;
        super.setContent(task);
    }

    @Override
    protected void setFirstValuePoints() {
        if (dots.size() > 1) {
            currentPoint.x = dots.get(0).x;
            currentPoint.y = dots.get(0).y;
            currentPoint.index = dots.get(0).index;
            roundNumber(currentPoint, 1000000);
            bestPoint.x = Double.MAX_VALUE; //currentPoint.x;
            bestPoint.y = Double.MAX_VALUE; //currentPoint.y;
            bestPoint.index = currentPoint.index;
        } else {
            currentPoint.x = Double.MAX_VALUE;//= 0.0;
            currentPoint.y = Double.MAX_VALUE;//= 0.0;
            bestPoint.x = currentPoint.x;
            bestPoint.y = currentPoint.y;
        }
    }

    @Override
    protected boolean changeBestPoint(int index) {
        if (currentPoint.index == indexPoints && bestPoint.y > currentPoint.y) {
            setBestPoint(index);
            return true;
        }
        return false;
    }
}
