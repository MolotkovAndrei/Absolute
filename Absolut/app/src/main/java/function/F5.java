package function;

public class F5 extends AbstractFunction {
    public F5() {
        this.leftPointRange = 0.0;
        this.rightPointRange = 1.2;
        calculateMaxAndMinValueFunction();
    }

    @Override
    public double getValue(double x) {
        return -(-3 * x + 1.4) * Math.sin(18 * x);
    }
}
