package results;

import android.graphics.Color;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import task.ITask;

public class ExperimentsJournal implements Serializable {
    private List<RecordExperimentsJournal> experimentsList;
    private List<RecordDiagramOfExperimentsJournal> diagramsOfExperimentsList;
    private Map<Integer, Integer> operationalCharacteristics = new TreeMap<>();
    private int numberExperiments;
    private int color;
    private String nameExperiment = "";
    private Random random;

    public ExperimentsJournal(final String name, final int color) {
        experimentsList = new ArrayList<>();
        diagramsOfExperimentsList = new ArrayList<>();
        random = new Random();
        this.color = color;
        this.nameExperiment = name;
    }

    public void addNewRecord(final ITask task) {
        numberExperiments++;
        RecordExperimentsJournal recordExperimentsJournal = new RecordExperimentsJournal(task);
        experimentsList.add(recordExperimentsJournal);
        diagramsOfExperimentsList.add(new RecordDiagramOfExperimentsJournal(task));

        int numberIterations = task.getStorage().size();
        if (recordExperimentsJournal.getDeviation() < task.getSettings().getEps()) {
            Integer numberFunctions = operationalCharacteristics.get(numberIterations);
            operationalCharacteristics.put(numberIterations, numberFunctions == null ? 1 : ++numberFunctions);
        }
    }

    public List getListOfExperiments() {
        return experimentsList;
    }

    public List getListOfDiagrams() {
        return diagramsOfExperimentsList;
    }

    public Map getOperationalCharacteristics() {
        return operationalCharacteristics;
    }

    public int getNumberExperiments() {
        return numberExperiments;
    }

    public int getColor() {
        return color;
    }

    public String getNameExperiment() {
        return nameExperiment;
    }

    public void clear() {
        experimentsList.clear();
        diagramsOfExperimentsList.clear();
        operationalCharacteristics.clear();
    }
}
