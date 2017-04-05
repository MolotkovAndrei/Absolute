package draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class ViewerSetLimitedFunctions extends View {
    private DrawerSensor drawerSensor;

    public ViewerSetLimitedFunctions(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setDrawerSensor(DrawerSensor drawerSensor) {
        this.drawerSensor = drawerSensor;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawerSensor.draw(canvas, 0);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        drawerSensor.getDrawPanel().set(getLeft(), getTop(), getRight(), getBottom());
        drawerSensor.calculatePointsForDraw();
    }
}
