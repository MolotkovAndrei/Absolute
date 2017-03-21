package function;

import java.io.Serializable;

public interface IFunction extends Serializable {
    double getValue(double x);
    double getLeftPointOfRange();
    double getRightPointOfRange();
    double getMinValueOnRange();
    double getMaxValueOnRange();
    void setLeftPointOfRange(final double leftPoint);
    void setRightPointOfRange(final double rightPoint);
    double getLimitationLevel();
    void setLimitationLevel(double level);
}
