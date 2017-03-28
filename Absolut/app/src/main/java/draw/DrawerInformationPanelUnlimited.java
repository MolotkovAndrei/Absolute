package draw;

import android.graphics.Rect;

import task.ITask;

public class DrawerInformationPanelUnlimited extends DrawerInformationPanel {

    public DrawerInformationPanelUnlimited(ITask task, Rect drawPanel) {
        super(task, drawPanel);
    }

    @Override
    protected void setFirstValuePoints() {
        if (dots.size() > 1) {
            currentPoint.x = dots.get(0).x;
            currentPoint.y = dots.get(0).y;
            currentPoint.index = dots.get(0).index;
            roundNumber(currentPoint, 1000000);
            bestPoint.x = currentPoint.x;
            bestPoint.y = currentPoint.y;
            bestPoint.index = currentPoint.index;
        } else {
            currentPoint.x = 0.0;
            currentPoint.y = 0.0;
            bestPoint.x = currentPoint.x;
            bestPoint.y = currentPoint.y;
        }
    }

    @Override
    protected boolean changeBestPoint(int index) {
        if (bestPoint.y > currentPoint.y) {
            setBestPoint(index);
        }
        return true;
    }
}
