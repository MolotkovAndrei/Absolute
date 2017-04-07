package algorithm;

import java.io.Serializable;
import java.util.List;

import storage.Dot;

public interface IAlgorithm extends Serializable {
    void reset();
    Dot next();
    boolean hasNext();
    void addDotInStorage(final Dot dot);
    void setSettings(final Settings settings);
    Settings getSettings();
    List<Dot> getTestDots();
    String getName();
}
