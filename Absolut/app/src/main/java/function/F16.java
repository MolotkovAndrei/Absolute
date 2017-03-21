package function;

public class F16 extends AbstractFunction {
    public F16() {
        this.leftPointRange = -3.0;
        this.rightPointRange = 3.0;
        calculateMaxAndMinValueFunction();
    }

    @Override
    public double getValue(double x) {
        return 2 * (x - 3) * (x - 3) + Math.exp(x * x / 2);
    }
}
