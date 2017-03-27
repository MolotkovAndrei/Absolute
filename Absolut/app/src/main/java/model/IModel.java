package model;

import com.example.absolute.DisplayOptionsListener;

import java.io.Serializable;

import observer.IObserver;
import results.Results;
import storage.IStorage;
import task.ITask;
import task.StorageTasks;

public interface IModel extends Serializable {
    void registerObserver(final IObserver observer);
    void removeObserver(final IObserver observer);
    void setDisplayOptions(DisplayOptions displayOptions, int numberTask);
    DisplayOptions getDisplayOptions(int numberTask);
    void stopDrawing();
    void continueDrawing(boolean withStop, int numberTask);
    void notifyObservers();
    void setStorageTask(final int numberStorage, final StorageTasks storageTasks);
    StorageTasks getStorageTask(final int numberStorage);
    StorageTasks getCurrentStorage();
    Results getResults();
    void setResults(final Results results);
    void addTask(final StorageTasks storageTasks);
    void removeTask(int numberTask);
    void calculateTask(final int numberTask);

    boolean isDrawingFinish(int numberTask);
    void setDrawingFinish(int numberTask, boolean isDrawingFinish);
    boolean isDrawing(int numberTask);
    void setDrawing(int numberTask, boolean isDrawing);
    boolean isCurrentWithStop(int numberTask);
    boolean inBeginWithStop(int numberTask);
    void setBeginnerStopFlag(int numberTask, boolean beginerStopFlag);
    void setCurrentStopFlag(int numberTask, boolean withStop);
    void setDisplayOptionsListener(int numberTask, DisplayOptionsListener listener);
    boolean canCloseMenuBetweenSteps(int numberTask);
    void setCloseMenuBetweenSteps(int numberTask, boolean canClose);
}
