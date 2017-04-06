package com.example.absolute.ExperimentsSeries;

import java.util.List;

import algorithm.Settings;
import function.IFunction;
import task.StorageTasks;

public abstract class CreatorSeries {
    protected int numberFunctions;
    protected List<IFunction> functions;
    protected Settings settings;

    public CreatorSeries(int numberFunctions) {
        this.numberFunctions = numberFunctions;
    }

    public void create(StorageTasks storageTasks, ICreatorFunctions creatorFunctions) {
        settings = storageTasks.getSettings();
        storageTasks.clearStorage();

        functions = creatorFunctions.getListFunctions(numberFunctions);
    }
}
