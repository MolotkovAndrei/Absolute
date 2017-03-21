package function;

public class F8 extends AbstractFunction {
    public F8() {
        this.leftPointRange = -10.0;
        this.rightPointRange = 10.0;
        calculateMaxAndMinValueFunction();
    }

    @Override
    public double getValue(double x) {
        double res = 0;
        for(int i = 1; i <= 5; i++) {
            res += i * Math.cos((i + 1) * x + i);
        }
        return -res;
    }
}
