package results;

import java.io.Serializable;

import storage.Dot;
import task.ITask;

public class RecordDiagramOfExperimentsJournal implements Serializable {
    private int numberIterations;
    private Dot exactPoint;
    private Dot bestPoint;
    private double eps;

    public RecordDiagramOfExperimentsJournal(final ITask task) {
        numberIterations = task.getStorage().size();
        exactPoint = task.getExactValue();
        bestPoint = (Dot) task.getStorage().get(0);
        Dot currentPoint;

        for (int i = 1; i < numberIterations; i++) {
            currentPoint = (Dot) task.getStorage().get(i);
            if (currentPoint.y < bestPoint.y) {
                bestPoint = currentPoint;
            }
        }

        eps = task.getSettings().getEps();
    }

    public int getNumberIterations() {
        return numberIterations;
    }

    public Dot getExactPoint() {
        return exactPoint;
    }

    public Dot getBestPoint() {
        return bestPoint;
    }

    public double getEps() {
        return eps;
    }

    public double getCoordinateError() {
        return Math.abs(bestPoint.x - exactPoint.x);
    }
}
