package function;

import java.util.Random;

import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.PI;

public class HillFunction extends AbstractFunction {
    private final int NUMBER_TERMS = 14;
    private double[] A = new double[NUMBER_TERMS];
    private double[] B = new double[NUMBER_TERMS];
    private Random random = new Random();

    public HillFunction() {
        this.leftPointRange = 0;
        this.rightPointRange = 1;


        for (int i = 0; i < NUMBER_TERMS; i++) {
            A[i] = random.nextDouble() * 2 - 1;
            B[i] = random.nextDouble() * 2 - 1;
        }
        calculateMaxAndMinValueFunction();
    }

    @Override
    public double getValue(double x) {
        double result = 0;
        for (int i = 0; i < NUMBER_TERMS; i++) {
            double argument = 2 * i * PI * x;
            result += A[i] * sin(argument) + B[i] * cos(argument);
        }
        return result;
    }
}
