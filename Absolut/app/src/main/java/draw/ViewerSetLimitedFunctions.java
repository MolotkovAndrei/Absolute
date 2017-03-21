package draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class ViewerSetLimitedFunctions extends View {
    private DrawerSensor drawerSensor;
    private HiderInvalidPoints hiderInvalidPoints;
    private int pos;
    private Rect drawPanel = new Rect();

    public ViewerSetLimitedFunctions(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setDrawerSensor(DrawerSensor drawerSensor) {
        this.drawerSensor = drawerSensor;

        //drawerSensor.calculatePointsForDraw();
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public void setHiderInvalidPoints(HiderInvalidPoints hiderInvalidPoints) {
        this.hiderInvalidPoints = hiderInvalidPoints;
        if (pos == 0) {
            System.out.println("pos " + pos);
        }
    }

    public HiderInvalidPoints getHiderInvalidPoints() {
        return hiderInvalidPoints;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawerSensor.draw(canvas, 0);
        if (hiderInvalidPoints != null) {
            hiderInvalidPoints.draw(canvas);
            if (pos == 0) {
                System.out.println("pos " + pos);
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //drawPanel.set(getLeft(), getTop(), getRight(), getBottom());
        drawerSensor.getDrawPanel().set(getLeft(), getTop(), getRight(), getBottom());
        drawerSensor.calculatePointsForDraw();
        if (hiderInvalidPoints != null) {
            hiderInvalidPoints.calculateHideRectangles();
            if (pos == 0) {
                System.out.println("pos " + pos);
            }
        }
    }
}
