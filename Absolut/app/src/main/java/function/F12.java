package function;

public class F12 extends AbstractFunction {
    public F12() {
        this.leftPointRange = 0.0;
        this.rightPointRange = 6.28;
        calculateMaxAndMinValueFunction();
    }

    @Override
    public double getValue(double x) {
        return Math.pow(Math.sin(x), 3) + Math.pow(Math.cos(x), 3);
    }
}
