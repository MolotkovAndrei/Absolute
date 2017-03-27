package model;

import android.content.Context;

import com.example.absolute.DisplayOptionsListener;

import java.util.ArrayList;
import java.util.List;

import algorithm.Settings;
import function.IFunction;
import observer.IObserver;
import results.Results;
import storage.Dot;
import task.ITask;
import task.StorageTasks;
import task.Task;

public class Model implements IModel {
    private List<IObserver> observers;
    private List<StorageTasks> storageTasksList;
    //private DisplayOptions displayOptions;

    private int currentStorage;
    private int numberStoppedTask;

    private Results results;

    public Model() {
        results = new Results();
        observers = new ArrayList<>();
        storageTasksList = new ArrayList<>();
        currentStorage = 0;
    }

    @Override
    public void registerObserver(final IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(final IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void setDisplayOptions(final DisplayOptions displayOptions, int numberTask) {
        storageTasksList.get(numberTask).setDisplayOptions(displayOptions);
    }

    @Override
    public DisplayOptions getDisplayOptions(int numberTask) {
        return storageTasksList.get(numberTask).getDisplayOptions();
    }

    @Override
    public void stopDrawing() {
        numberStoppedTask = currentStorage;
        observers.get(currentStorage).stopDrawing();
    }

    @Override
    public void continueDrawing(boolean withStop, int numberTask) {
        storageTasksList.get(numberTask).setCurrentStopFlag(withStop);
        observers.get(numberTask).continueDrawing(storageTasksList.get(numberTask));
    }

    @Override
    public void notifyObservers() {
        observers.get(currentStorage).update(storageTasksList.get(currentStorage));
    }

    @Override
    public void setStorageTask(final int numberStorage, final StorageTasks storageTasks) {
        if (storageTasksList.isEmpty()) {
            return;
        }
        storageTasksList.set(numberStorage, storageTasks);
        currentStorage = numberStorage;
        notifyObservers();
    }

    @Override
    public StorageTasks getStorageTask(final int numberStorage) {
        if (!storageTasksList.isEmpty()) {
            return storageTasksList.get(numberStorage);
        }
        return null;
    }

    @Override
    public StorageTasks getCurrentStorage() {
        return storageTasksList.get(currentStorage);
    }

    @Override
    public Results getResults() {
        return results;
    }

    @Override
    public void setResults(Results results) {
        this.results = results;
    }

    @Override
    public void addTask(final StorageTasks storageTasks) {
        storageTasksList.add(storageTasks);
        results.addNewExperimentsOfTask(storageTasks.getName());
        currentStorage = storageTasksList.size() - 1;
    }

    @Override
    public void removeTask(int numberStorage) {
        storageTasksList.remove(numberStorage);
        observers.remove(numberStorage);
        results.removeJournal(numberStorage);
    }

    @Override
    public void calculateTask(final int numberStorage) {
        if (storageTasksList.isEmpty()) {
            return;
        }
        storageTasksList.get(numberStorage).executeTasks();
        currentStorage = numberStorage;

        results.setResults(storageTasksList.get(currentStorage), currentStorage);
        notifyObservers();
    }

    @Override
    public boolean isDrawingFinish(int numberTask) {
        return storageTasksList.get(numberTask).isDrawingFinish();
    }

    @Override
    public void setDrawingFinish(int numberTask, boolean isDrawingFinish) {
        storageTasksList.get(numberTask).setDrawingFinish(isDrawingFinish);
    }

    @Override
    public boolean isDrawing(int numberTask) {
        return storageTasksList.get(numberTask).isDrawing();
    }

    @Override
    public void setDrawing(int numberTask, boolean isDrawing) {
        storageTasksList.get(numberTask).setDrawing(isDrawing);
    }

    @Override
    public boolean isCurrentWithStop(int numberTask) {
        return storageTasksList.get(numberTask).isCurrentWithStop();
    }

    @Override
    public boolean inBeginWithStop(int numberTask) {
        return storageTasksList.get(numberTask).inBeginWithStop();
    }

    @Override
    public void setBeginnerStopFlag(int numberTask, boolean beginerStopFlag) {
        storageTasksList.get(numberTask).setBeginnerStopFlag(beginerStopFlag);
    }

    @Override
    public void setCurrentStopFlag(int numberTask, boolean withStop) {
        storageTasksList.get(numberTask).setCurrentStopFlag(withStop);
    }

    @Override
    public void setDisplayOptionsListener(int numberTask, DisplayOptionsListener listener) {
        storageTasksList.get(numberTask).setDisplayOptionsListener(listener);
    }

    @Override
    public boolean canCloseMenuBetweenSteps(int numberTask) {
        return storageTasksList.get(numberTask).canCloseMenuBetweenSteps();
    }

    @Override
    public void setCloseMenuBetweenSteps(int numberTask, boolean canClose) {
        storageTasksList.get(numberTask).setcloseMenuBetweenSteps(canClose);
    }
}
