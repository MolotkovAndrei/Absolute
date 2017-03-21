package results;

import java.io.Serializable;

import storage.Dot;
import task.ITask;

public class RecordExperimentsJournal implements Serializable {
    private Dot bestPoint;
    private int numberIterations;
    private double deviation;

    public RecordExperimentsJournal(final ITask task) {
        numberIterations = task.getStorage().size();
        Dot exactValue = task.getExactValue();
        bestPoint = (Dot) task.getStorage().get(0);
        Dot currentPoint;

        for (int i = 1; i < numberIterations; i++) {
            currentPoint = (Dot) task.getStorage().get(i);
            if (currentPoint.y < bestPoint.y) {
                bestPoint = currentPoint;
            }
        }

        deviation = Math.abs(exactValue.x - bestPoint.x);
    }

    public Dot getBestPoint() {
        return bestPoint;
    }

    public int getNumberIterations() {
        return numberIterations;
    }

    public double getDeviation() {
        return deviation;
    }
}
