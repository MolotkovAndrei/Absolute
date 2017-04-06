package com.example.absolute.ExperimentsSeries;

import function.IFunction;
import task.ITask;
import task.IndexTask;
import task.StorageTasks;

public class CreatorSeriesIndexTask extends CreatorSeries {
    public CreatorSeriesIndexTask(int numberFunctions) {
        super(numberFunctions);
    }

    @Override
    public void create(StorageTasks storageTasks, ICreatorFunctions creatorFunctions) {
        super.create(storageTasks, creatorFunctions);

        for (IFunction function : functions) {
            ITask task = new IndexTask(settings);
            task.setMinimizedFunction(function);
            storageTasks.addTask(task);
        }
    }
}
