package function;

public class F17 extends AbstractFunction {
    public F17() {
        this.leftPointRange = -4.0;
        this.rightPointRange = 4.0;
        calculateMaxAndMinValueFunction();
    }

    @Override
    public double getValue(double x) {
        return Math.pow(x, 6) - 15 * Math.pow(x, 4) + 27 * x * x + 250;
    }
}
