package function;

import java.util.Random;

public class ShekelFunction extends AbstractFunction {
    private final int NUMBER_TERMS = 10;
    private double[] K = new double[NUMBER_TERMS];
    private double[] A = new double[NUMBER_TERMS];
    private double[] C = new double[NUMBER_TERMS];
    private Random random = new Random();

    public ShekelFunction() {
        this.leftPointRange = 0.0;
        this.rightPointRange = 10.0;


        for (int i = 0; i < NUMBER_TERMS; i++) {
            K[i] = random.nextDouble() * 2 + 1;
            A[i] = random.nextDouble() * 10;
            C[i] = random.nextDouble() * 10;
        }
        calculateMaxAndMinValueFunction();
    }

    @Override
    public double getValue(double x) {
        double result = 0;
        for (int i = 0; i < NUMBER_TERMS; i++) {
            result += (1.0 / (K[i] * (x - A[i]) * (x - A[i]) + C[i]));
        }
        return -result;
    }
}
