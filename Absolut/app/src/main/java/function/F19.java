package function;

public class F19 extends AbstractFunction {
    public F19() {
        this.leftPointRange = 0.0;
        this.rightPointRange = 6.5;
        calculateMaxAndMinValueFunction();
    }

    @Override
    public double getValue(double x) {
        return -x + Math.sin(3 * x) - 1;
    }
}
