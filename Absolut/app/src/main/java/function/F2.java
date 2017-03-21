package function;

public class F2 extends AbstractFunction {
    public F2() {
        this.leftPointRange = 2.7;
        this.rightPointRange = 7.5;
        calculateMaxAndMinValueFunction();
    }
    @Override
    public double getValue(double x) {
        return Math.sin(x) + Math.sin(10 * x / 3);
    }
}
