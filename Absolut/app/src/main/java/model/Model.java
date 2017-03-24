package model;

import android.content.Context;

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
    private DisplayOptions displayOptions;

    private int currentStorage;
    private int numberStoppedTask;

    private Results results;

    public Model(Context context) {
        results = new Results();
        observers = new ArrayList<>();
        storageTasksList = new ArrayList<>();
        displayOptions = new DisplayOptions(context);
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
    public void setDisplayOptions(final DisplayOptions displayOptions) {
        this.displayOptions = displayOptions;
    }

    @Override
    public DisplayOptions getDisplayOptions() {
        return displayOptions;
    }

    @Override
    public void stopDrawing() {
        numberStoppedTask = currentStorage;
        observers.get(currentStorage).stopDrawing();
    }

    @Override
    public void continueDrawing(boolean withStop, int n) {
        displayOptions.setCurrentStopFlag(withStop);
        observers.get(n).continueDrawing(displayOptions);
    }

    @Override
    public void notifyObservers() {
        observers.get(currentStorage).update(storageTasksList.get(currentStorage), displayOptions);
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
}
