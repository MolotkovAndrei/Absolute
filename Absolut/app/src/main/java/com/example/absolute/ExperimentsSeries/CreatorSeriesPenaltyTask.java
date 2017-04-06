package com.example.absolute.ExperimentsSeries;

import function.IFunction;
import task.ITask;
import task.PenaltyTask;
import task.StorageTasks;

public class CreatorSeriesPenaltyTask extends CreatorSeries {
    public CreatorSeriesPenaltyTask(int numberFunctions) {
        super(numberFunctions);
    }

    @Override
    public void create(StorageTasks storageTasks, ICreatorFunctions creatorFunctions) {
        super.create(storageTasks, creatorFunctions);

        for (IFunction function : functions) {
            ITask task = new PenaltyTask(settings);
            task.setMinimizedFunction(function);
            storageTasks.addTask(task);
        }
    }
}
