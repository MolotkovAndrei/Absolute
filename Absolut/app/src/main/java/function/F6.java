package function;

public class F6 extends AbstractFunction {
    public F6() {
        this.leftPointRange = -10.0;
        this.rightPointRange = 10.0;
        calculateMaxAndMinValueFunction();
    }

    @Override
    public double getValue(double x) {
        return -(x + Math.sin(x)) * Math.exp(-x * x);
    }
}
