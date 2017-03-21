package function;

public class F11 extends AbstractFunction {
    public F11() {
        this.leftPointRange = -1.57;
        this.rightPointRange = 6.28;
        calculateMaxAndMinValueFunction();
    }

    @Override
    public double getValue(double x) {
        return 2 * Math.cos(x) + Math.cos(2 * x);
    }
}
