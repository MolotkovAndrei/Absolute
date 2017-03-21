package function;

public class F3 extends AbstractFunction {
    public F3() {
        this.leftPointRange = -10;
        this.rightPointRange = 10;
        calculateMaxAndMinValueFunction();
    }
    @Override
    public double getValue(double x) {
        double res = 0;
        for(int i = 1; i <= 5; i++) {
            res += i * Math.sin((i + 1) * x + i);
        }
        return -res;
    }
}
