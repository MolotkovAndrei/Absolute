package function;

public class F4 extends AbstractFunction {
    public F4() {
        this.leftPointRange = 1.9;
        this.rightPointRange = 3.9;
        calculateMaxAndMinValueFunction();
    }

    @Override
    public double getValue(double x) {
        return (-16 * x * x + 24 * x - 5) * Math.exp(-x);
    }
}
