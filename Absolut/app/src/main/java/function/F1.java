package function;

public class F1 extends AbstractFunction {
    public F1() {
        this.leftPointRange = -1.5;
        this.rightPointRange = 11;
        calculateMaxAndMinValueFunction();
    }

    @Override
    public double getValue(double x) {
        return Math.pow(x, 6) / 6.0 - 52.0 / 25.0 * Math.pow(x, 5) + 39.0 / 80.0 * Math.pow(x, 4) +
                71.0 / 10.0 * Math.pow(x, 3) - 79.0 / 20.0 * Math.pow(x, 2) - x + 0.1;
    }


}
