package function;

public class F14 extends AbstractFunction {
    public F14() {
        this.leftPointRange = 0.0;
        this.rightPointRange = 4.0;
        calculateMaxAndMinValueFunction();
    }

    @Override
    public double getValue(double x) {
        return -Math.exp(-x) * Math.sin(2 * Math.acos(-1.0) * x);
    }
}
