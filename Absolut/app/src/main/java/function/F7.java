package function;

public class F7 extends AbstractFunction {
    public F7() {
        this.leftPointRange = 2.7;
        this.rightPointRange = 7.5;
        calculateMaxAndMinValueFunction();
    }

    @Override
    public double getValue(double x) {
        return Math.sin(x) + Math.sin(10 * x / 3) + Math.log(x) - 0.84 * x + 3;
    }
}
