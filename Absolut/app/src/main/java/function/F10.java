package function;

public class F10 extends AbstractFunction {
    public F10() {
        this.leftPointRange = 0.0;
        this.rightPointRange = 10.0;
        calculateMaxAndMinValueFunction();
    }

    @Override
    public double getValue(double x) {
        return -x * Math.sin(x);
    }
}
