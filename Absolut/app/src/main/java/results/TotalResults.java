package results;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import storage.Dot;
import task.ITask;

public class TotalResults implements Serializable {
    private Map<Integer, Integer> operationalCharacteristics = new TreeMap<>();

    private int numberExperiments;
    private int averageNumberIterations;

    private int numberFunctionsReachMin;
    private int averageNumberIterationsForFunctionsReachMin;

    private int numberFunctionsNotReachMin;
    private int averageNumberIterationsForFunctionsNotReachMin;
    private double averageDeviation;

    private int totalNumberIterations;
    private int totalNumberIterationsForFunctionsReachMin;
    private int totalNumberIterationsForFunctionsNotReachMin;
    private double totalDeviation;



    public void setResult(final ITask task) {
        int numberIterations = task.getStorage().size();
        numberExperiments++;
        totalNumberIterations += numberIterations;
        averageNumberIterations = totalNumberIterations / numberExperiments;

        double eps = task.getSettings().getEps();
        Dot exactValue = task.getExactValue();
        Dot bestPoint = task.getStorage().get(0);
        Dot currentPoint;

        for (int i = 1; i < numberIterations; i++) {
            currentPoint = task.getStorage().get(i);
            if (currentPoint.y < bestPoint.y) {
                bestPoint = currentPoint;
            }
        }

        double currentDeviation = Math.abs(exactValue.x - bestPoint.x);
        if (currentDeviation < eps) {
            numberFunctionsReachMin++;
            totalNumberIterationsForFunctionsReachMin += numberIterations;
            averageNumberIterationsForFunctionsReachMin = totalNumberIterationsForFunctionsReachMin / numberFunctionsReachMin;

            Integer numberFunctions = operationalCharacteristics.get(numberIterations);
            operationalCharacteristics.put(numberIterations, numberFunctions == null ? 1 : ++numberFunctions);
        } else {
            numberFunctionsNotReachMin++;
            totalNumberIterationsForFunctionsNotReachMin += numberIterations;
            averageNumberIterationsForFunctionsNotReachMin = totalNumberIterationsForFunctionsNotReachMin / numberFunctionsNotReachMin;
            totalDeviation += Math.abs(exactValue.y - bestPoint.y);
            averageDeviation = totalDeviation / numberFunctionsNotReachMin;
        }
    }

    public int getNumberExperiments() {
        return numberExperiments;
    }

    public int getAverageNumberIterations() {
        return averageNumberIterations;
    }

    public int getNumberFunctionsReachMin() {
        return numberFunctionsReachMin;
    }

    public int getAverageNumberIterationsForFunctionsReachMin() {
        return averageNumberIterationsForFunctionsReachMin;
    }

    public int getNumberFunctionsNotReachMin() {
        return numberFunctionsNotReachMin;
    }

    public int getAverageNumberIterationsForFunctionsNotReachMin() {
        return averageNumberIterationsForFunctionsNotReachMin;
    }

    public double getAverageDeviation() {
        return roundNumber(averageDeviation, 1000000000);
    }

    public Map getOperationalCharacteristics() {
        return operationalCharacteristics;
    }

    public void clear() {
        operationalCharacteristics.clear();

        numberExperiments = 0;
        averageNumberIterations = 0;

        numberFunctionsReachMin = 0;
        averageNumberIterationsForFunctionsReachMin = 0;

        numberFunctionsNotReachMin = 0;
        averageNumberIterationsForFunctionsNotReachMin = 0;
        averageDeviation = 0.0;

        totalNumberIterations = 0;
        totalNumberIterationsForFunctionsReachMin = 0;
        totalNumberIterationsForFunctionsNotReachMin = 0;
        totalDeviation = 0.0;
    }

    private double roundNumber(double number, int roundMark) {
        number *= roundMark;
        number = Math.round(number);
        number /= roundMark;
        return number;
    }
}
