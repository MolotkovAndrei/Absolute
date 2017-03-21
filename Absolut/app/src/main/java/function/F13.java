package function;

public class F13 extends AbstractFunction {
    public F13() {
        this.leftPointRange = 0.001;
        this.rightPointRange = 0.99;
        calculateMaxAndMinValueFunction();
    }

    @Override
    public double getValue(double x) {
        double sgn = (x * x - 1 < 0) ? -1.0 : 1.0;
        return -Math.pow(x * x, 1.0 / 3.0) + sgn * Math.pow(sgn * (x * x - 1.0), 1.0 / 3.0);
    }
}
