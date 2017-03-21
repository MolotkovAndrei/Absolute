package results;

import android.graphics.Color;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import task.ITask;
import task.StorageTasks;

public class Results implements Serializable {
    private TotalResults totalResults;
    private List<ExperimentsJournal> journalsList;
    private int numberCurrentTask;

    private int NUBER_COLORS = 30;
    private int colors[] = {Color.parseColor("#00ffff"), Color.parseColor("#7fff00"), Color.parseColor("#b8860b"), Color.parseColor("#006400"),
                            Color.parseColor("#8b008b"), Color.parseColor("#ff8c00"), Color.parseColor("#8b0000"), Color.parseColor("#2f4f4f"),
                            Color.parseColor("#9400d3"), Color.parseColor("#00bfff"), Color.parseColor("#ff00ff"), Color.parseColor("#cd5c5c"),
                            Color.parseColor("#add8e6"), Color.parseColor("#ffb6c1"), Color.parseColor("#20b2aa"), Color.parseColor("#00fa9a"),
                            Color.parseColor("#ffe4b5"), Color.parseColor("#808000"), Color.parseColor("#ffa500"), Color.parseColor("#ff0000"),
                            Color.parseColor("#fa8072"), Color.parseColor("#2e8b57"), Color.parseColor("#a0522d"), Color.parseColor("#708090"),
                            Color.parseColor("#00ff7f"), Color.parseColor("#9acd32"), Color.parseColor("#ff7f50"), Color.parseColor("#ffd700")};

    public Results() {
        totalResults = new TotalResults();
        journalsList = new ArrayList<>();
    }

    public void addNewExperimentsOfTask(final String name) {
        journalsList.add(new ExperimentsJournal(name, colors[journalsList.size()]));
    }

    public void setResults(final StorageTasks storageTasks, final int numberStorage) {
        ArrayList<ITask> tasks = (ArrayList<ITask>)storageTasks.getTaskList();
        for (ITask task : tasks) {
            totalResults.setResult(task);
            journalsList.get(numberStorage).addNewRecord(task);
        }
    }

    public void clearTotalResults() {
        totalResults.clear();
    }

    public TotalResults getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(final TotalResults totalResults) {
        this.totalResults = totalResults;
    }

    public void setNumberCurrentTask(final int numberCurrentTask) {
        this.numberCurrentTask = numberCurrentTask;
    }

    public ExperimentsJournal getExperimentsJournal() {
        return journalsList.get(numberCurrentTask);
    }

    public List getJournalList() {
        return journalsList;
    }

    public void setExperimentsJournal(final ExperimentsJournal experimentsJournal) {
        journalsList.set(numberCurrentTask, experimentsJournal);
    }

    public void removeJournal(final int numberJournal) {
        journalsList.remove(numberJournal);
    }
}
