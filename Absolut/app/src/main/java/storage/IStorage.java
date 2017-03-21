package storage;

import java.io.Serializable;
import java.util.List;

public interface IStorage extends Serializable {
    void clearAllDots();
    void addDot(final Dot dot);
    List<Dot> getAllDots();
    //List<Dot> getSortDots();
    int getIntervalCount();
    double getLengthInterval(final int numberInterval);
    Dot getDot(final int numberDot);
    double getLengthDomain();
    double getMaxLengthInterval();
    double getMinValueOfTests();
    double getMaxValueOfTests();
}
