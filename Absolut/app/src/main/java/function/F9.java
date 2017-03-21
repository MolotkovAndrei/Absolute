package function;

public class F9 extends AbstractFunction {
    public F9() {
        this.leftPointRange = 3.1;
        this.rightPointRange = 20.4;
        calculateMaxAndMinValueFunction();
    }

    @Override
    public double getValue(double x) {
        return Math.sin(x) + Math.sin(2.0 / 3.0 * x);
    }
}
