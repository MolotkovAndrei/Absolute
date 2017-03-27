package task;

import android.content.Context;

import com.example.absolute.DisplayOptionsListener;

import java.util.ArrayList;
import java.util.List;

import algorithm.withoutLimitation.BAGS;
import algorithm.withoutLimitation.BAGS_InsideInterval;
import algorithm.withoutLimitation.BAGS_WithLocalSettings;
import algorithm.IAlgorithm;
import algorithm.withoutLimitation.Koushner;
import algorithm.withoutLimitation.LocallyAdaptive_BAGS;
import algorithm.withoutLimitation.Mix_BAGS;
import algorithm.withoutLimitation.Monotonic_BAGS;
import algorithm.withoutLimitation.PolygonalMethod;
import algorithm.withoutLimitation.RandomSearch;
import algorithm.withoutLimitation.SequentalScan;
import algorithm.Settings;
import function.IFunction;
import model.DisplayOptions;

public class StorageTasks {
    private List<ITask> taskList = new ArrayList<>();
    private String name = "";
    private ITask currentTask;
    private IAlgorithm algorithm;
    private DisplayOptions displayOptions;

    public StorageTasks(Context context, final ITask task) {
        taskList.add(task);
        algorithm = task.getAlgorithm();
        currentTask = task;
        displayOptions = new DisplayOptions(context);
    }

    public DisplayOptions getDisplayOptions() {
        return displayOptions;
    }

    public void setDisplayOptions(DisplayOptions displayOptions) {
        this.displayOptions = displayOptions;
    }

    public boolean isDrawingFinish() {
        return displayOptions.isDrawingFinish();
    }

    public void setDrawingFinish(boolean isDrawingFinish) {
        displayOptions.setDrawingFinish(isDrawingFinish);
    }

    public boolean isDrawing() {
        return displayOptions.isDrawing();
    }

    public void setDrawing(boolean isDrawing) {
        displayOptions.setDrawing(isDrawing);
    }

    public boolean isCurrentWithStop() {
        return displayOptions.isCurrentWithStop();
    }

    public boolean inBeginWithStop() {
        return displayOptions.inBeginWithStop();
    }

    public void setBeginnerStopFlag(boolean beginnerStopFlag) {
        displayOptions.setBeginnerStopFlag(beginnerStopFlag);
    }

    public int getNumberStepsBeforeStop() {
        return displayOptions.getNumberStepsBeforeStop();
    }

    public void setCurrentStopFlag(boolean withStop) {
        displayOptions.setCurrentStopFlag(withStop);
    }

    public long getValueDisplaySpeed() {
        return displayOptions.getValueDisplaySpeed();
    }

    public void setDisplayOptionsListener(DisplayOptionsListener listener) {
        displayOptions.setListener(listener);
    }

    public boolean canCloseMenuBetweenSteps() {
        return displayOptions.canCloseMenuBetweenSteps();
    }

    public void setcloseMenuBetweenSteps(boolean canCloseMenuBetweenSteps) {
        displayOptions.setCloseMenuBetweenSteps(canCloseMenuBetweenSteps);
    }

    public void setAlgorithm(final IAlgorithm algorithm) {
        this.algorithm = algorithm;
        currentTask.setAlgorithm(algorithm);
    }

    public IAlgorithm getAlgorithm() {
        return algorithm;
    }

    public Settings getSettings() {
        return algorithm.getSettings();
    }

    public List<IFunction> getCurrentLimitationFunctions() {
        if (currentTask != null) {
            return currentTask.getLimitationFunctions();
        }
        return null;
    }

    public void setLimitationFunctions(List<IFunction> functions) {
        if (currentTask != null) {
            currentTask.setLimitationFunctions(functions);
        }
    }

    public IFunction getMinimizedFunction() {
        if (currentTask != null) {
            return currentTask.getMinimizedFunction();
        }
        return null;
    }

    public void setMinimizedFunction(IFunction minimizedFunction) {
        if (currentTask != null) {
            currentTask.setMinimizedFunction(minimizedFunction);
        }
    }

    public String getName() {
        return name;
    }

    public ITask getCurrentTask() {
        return currentTask;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void addTask(final ITask task) {
        if (algorithm != null) {
            task.setAlgorithm(createAlgorithmForTask());
        } else {
            algorithm = task.getAlgorithm();
        }
        taskList.add(task);
        currentTask = task;
    }

    public void clearStorage() {
        taskList.clear();
    }

    public List<ITask> getTaskList() {
        return taskList;
    }

    public void executeTasks() {
        for (ITask task : taskList) {
            task.run();
        }
    }

    private IAlgorithm createAlgorithmForTask() {
        IAlgorithm currentAlg;
        switch (algorithm.toString()) {
            case "SequentalScan":
                currentAlg = new SequentalScan(algorithm.getSettings());
                break;
            case "Koushner":
                currentAlg = new Koushner(algorithm.getSettings());
                break;
            case "PolygonalMethod":
                currentAlg = new PolygonalMethod(algorithm.getSettings());
                break;
            case "RandomSearch":
                currentAlg = new RandomSearch(algorithm.getSettings());
                break;
            case "BAGS":
                currentAlg = new BAGS(algorithm.getSettings());
                break;
            case "BAGS_InsideInterval":
                currentAlg = new BAGS_InsideInterval(algorithm.getSettings());
                break;
            case "BAGS_WithLocalSettings":
                currentAlg = new BAGS_WithLocalSettings(algorithm.getSettings());
                break;
            case "Mix_BAGS":
                currentAlg = new Mix_BAGS(algorithm.getSettings());
                break;
            case "Monotonic_BAGS":
                currentAlg = new Monotonic_BAGS(algorithm.getSettings());
                break;
            case "LocallyAdaptive_BAGS":
                currentAlg = new LocallyAdaptive_BAGS(algorithm.getSettings());
                break;
            default:
                currentAlg = new BAGS(algorithm.getSettings());
                break;
        }
        return  currentAlg;
    }
}
