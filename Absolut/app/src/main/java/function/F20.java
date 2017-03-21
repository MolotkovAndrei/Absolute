package function;

public class F20 extends AbstractFunction {
    public F20() {
        this.leftPointRange = -10.0;
        this.rightPointRange = 10.0;
        calculateMaxAndMinValueFunction();
    }

    @Override
    public double getValue(double x) {
        return -(x - Math.sin(x)) * Math.exp(-x * x);
    }
}
