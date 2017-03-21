package algorithm.withoutLimitation;

import algorithm.Settings;
import storage.Dot;

public class Monotonic_BAGS extends BAGS {
    double maxValue = Double.MIN_VALUE;
    double minValue = Double.MAX_VALUE;

    public Monotonic_BAGS(Settings settings) {
        super(settings);
        name = "Monotonic_BAGS";
    }

    @Override
    public void addDotInStorage(final Dot dot) {
        if (minValue > dot.y) {
            minValue = dot.y;
        }

        if (maxValue < dot.y) {
            maxValue = dot.y;
        }

        currentIteration++;
        if (currentIteration > 2) {
            double tmp = ((maxValue - dot.y) / (maxValue - minValue) * (maxValue - dot.y) / (maxValue - minValue));
            dot.y = Math.sqrt(1 - tmp);
        }
        testDots.addDot(dot);
    }
}
