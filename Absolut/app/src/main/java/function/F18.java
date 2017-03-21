package function;

public class F18 extends AbstractFunction {
    public F18() {
        this.leftPointRange = 0.0;
        this.rightPointRange = 6.0;
        calculateMaxAndMinValueFunction();
    }

    @Override
    public double getValue(double x) {
        return (x <= 3) ? (x - 2) * (x - 2) : 2 * Math.log(x - 2) + 1;
    }
}
