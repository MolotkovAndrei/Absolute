package model;

import java.io.Serializable;

public class DisplayOptions implements Serializable {
    private DisplaySpeed displaySpeed = DisplaySpeed.NORMAL;
    private int numberStepsBeforeStop = 10;
    private boolean withStop = false;
    private boolean currentWithStop = false;
    private boolean isDrawing = false;
    private boolean isDrawingFinish = true;
    private float scaleFactor = 1.0f;

    public enum DisplaySpeed {
        QUICK(0L),
        NORMAL(100L),
        SLOW(200L);

        DisplaySpeed(final long timeSleepMillisecond) {
            this.timeSleepMillisecond = timeSleepMillisecond;
        }

        public long getTimeSleepMillisecond() {
            return timeSleepMillisecond;
        }

        private long timeSleepMillisecond;
    }

    public float getScaleFactor() {
        return scaleFactor;
    }

    public void setScaleFactor(float scaleFactor) {
        this.scaleFactor = scaleFactor;
    }

    public DisplaySpeed getDisplaySpeed() {
        return displaySpeed;
    }

    public int getNumberStepsBeforeStop() {
        return numberStepsBeforeStop;
    }

    public Boolean inBeginWithStop() {
        return withStop;
    }

    public void setBeginnerStopFlag(boolean isWithStop) {
        this.withStop = isWithStop;
    }

    public void setCurrentStopFlag(final boolean isWithStop) {
        currentWithStop = isWithStop;

    }

    public boolean isCurrentWithStop() {
        return currentWithStop;
    }

    public void setDisplaySpeed(final DisplaySpeed displaySpeed) {
        this.displaySpeed = displaySpeed;
    }

    public void setNumberStepsBeforeStop(final int numberStepsBeforeStop) {
        this.numberStepsBeforeStop = numberStepsBeforeStop;
    }

    public boolean isDrawing() {
        return isDrawing;
    }

    public void setDrawing(final boolean drawing) {
        isDrawing = drawing;
    }

    public boolean isDrawingFinish() {
        return isDrawingFinish;
    }

    public void setDrawingFinish(final boolean drawingFinish) {
        isDrawingFinish = drawingFinish;
    }
}
