package observer;

import model.DisplayOptions;
import task.StorageTasks;

public interface IObserver {
    void update(StorageTasks storageTasks, DisplayOptions displayOptions);
    void stopDrawing();
    void continueDrawing(DisplayOptions displayOptions);
}
