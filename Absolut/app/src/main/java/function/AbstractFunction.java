package function;

public abstract class AbstractFunction implements IFunction{
    protected String name;
    protected double leftPointRange;
    protected double rightPointRange;
    private double minValueOnRange;
    private double maxValueOnRange;
    private double limitationLevel = 0.0;
    private final int NUMBER_POINTS_FOR_MIN_VALUE = 300;

    public AbstractFunction() {}

    public AbstractFunction(final double leftPointRange, final double rightPointRange) {
        this.leftPointRange = leftPointRange;
        this.rightPointRange = rightPointRange;
        calculateMaxAndMinValueFunction();
        if (minValueOnRange > limitationLevel || maxValueOnRange < limitationLevel) {
            limitationLevel = (minValueOnRange + maxValueOnRange) / 2.0;
        }
    }

    @Override
    public double getLeftPointOfRange() {
        return leftPointRange;
    }

    @Override
    public double getRightPointOfRange() {
        return rightPointRange;
    }

    @Override
    public double getMinValueOnRange() {
        return minValueOnRange;
    }

    @Override
    public double getMaxValueOnRange() {
        return maxValueOnRange;
    }

    @Override
    public void setLeftPointOfRange(final double leftPoint) {
        this.leftPointRange = leftPoint;
    }

    @Override
    public void setRightPointOfRange(final double rightPoint) {
        this.rightPointRange = rightPoint;
    }

    @Override
    public double getLimitationLevel() {
        return limitationLevel;
    }

    @Override
    public void setLimitationLevel(double level) {
        if (level > maxValueOnRange) {
            limitationLevel = maxValueOnRange;
        } else if (level < minValueOnRange) {
            limitationLevel = minValueOnRange;
        } else {
            limitationLevel = level;
        }
    }

    protected void calculateMaxAndMinValueFunction() {
        double lengthRange = rightPointRange - leftPointRange;
        double step = lengthRange / NUMBER_POINTS_FOR_MIN_VALUE;

        minValueOnRange = getValue(leftPointRange);
        maxValueOnRange = minValueOnRange;
        double currentValue;
        for (int i = 1; i <= NUMBER_POINTS_FOR_MIN_VALUE; i++) {
            currentValue = getValue(leftPointRange + i * step);
            if (minValueOnRange > currentValue) {
                minValueOnRange = currentValue;
            } else if (maxValueOnRange < currentValue) {
                maxValueOnRange = currentValue;
            }
        }

    }
}
