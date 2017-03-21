package function;

public class F15 extends AbstractFunction {
    public F15() {
        this.leftPointRange = -5.0;
        this.rightPointRange = 5.0;
        calculateMaxAndMinValueFunction();
    }

    @Override
    public double getValue(double x) {
        return (x * x - 5 * x + 6) / (x * x + 1);
    }
}
