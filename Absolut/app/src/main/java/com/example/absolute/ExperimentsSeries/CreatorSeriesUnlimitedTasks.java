package com.example.absolute.ExperimentsSeries;

import algorithm.CreatorAlgorithm;
import algorithm.IAlgorithm;
import function.IFunction;
import task.ITask;
import task.StorageTasks;
import task.Task;

public class CreatorSeriesUnlimitedTasks extends CreatorSeries {
    public CreatorSeriesUnlimitedTasks(int numberFunctions) {
        super(numberFunctions);
    }

    @Override
    public void create(StorageTasks storageTasks, ICreatorFunctions creatorFunctions) {
        super.create(storageTasks, creatorFunctions);
        String nameAlgorithmForSerial = storageTasks.getNameAlgorithm();

        for (IFunction function : functions) {
            ITask task = new Task(settings);
            task.setAlgorithm(CreatorAlgorithm.create(nameAlgorithmForSerial, settings));
            task.setMinimizedFunction(function);
            storageTasks.addTask(task);
        }
    }
}
