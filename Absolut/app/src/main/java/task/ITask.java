package task;

import java.io.Serializable;
import java.util.List;

import algorithm.IAlgorithm;
import algorithm.Settings;
import function.IFunction;
import storage.Dot;

public interface ITask extends Serializable {
    void setLimitationFunctions(final List<IFunction> functions);
    void setMinimizedFunction(IFunction function);
    void setAlgorithm(final IAlgorithm algorithm);
    void setSettings(Settings settings);
    void setName(final String name);

    List<IFunction> getLimitationFunctions();
    IFunction getMinimizedFunction();
    IAlgorithm getAlgorithm();
    Settings getSettings();
    String getName();

    void run();
    List<Dot> getStorage();
    Dot getExactValue();
}
