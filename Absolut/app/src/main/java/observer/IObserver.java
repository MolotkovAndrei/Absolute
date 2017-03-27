package observer;

import model.DisplayOptions;
import task.StorageTasks;

public interface IObserver {
    void update(StorageTasks storageTasks);
    void stopDrawing();
    void continueDrawing(StorageTasks storageTasks);
}
