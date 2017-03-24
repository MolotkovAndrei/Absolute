package model;

import java.io.Serializable;

import observer.IObserver;
import results.Results;
import storage.IStorage;
import task.ITask;
import task.StorageTasks;

public interface IModel extends Serializable {
    void registerObserver(final IObserver observer);
    void removeObserver(final IObserver observer);
    void setDisplayOptions(final DisplayOptions displayOptions);
    DisplayOptions getDisplayOptions();
    void stopDrawing();
    void continueDrawing(boolean withStop, int n);
    void notifyObservers();
    void setStorageTask(final int numberStorage, final StorageTasks storageTasks);
    StorageTasks getStorageTask(final int numberStorage);
    StorageTasks getCurrentStorage();
    Results getResults();
    void setResults(final Results results);
    void addTask(final StorageTasks storageTasks);
    void removeTask(int numberTask);
    void calculateTask(final int numberTask);
}
