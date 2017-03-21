package algorithm.withoutLimitation;

import algorithm.Settings;
import storage.Dot;

public class RandomSearch extends Algorithm {
    public RandomSearch(final Settings settings) {
        super(settings);
        name = "RandomSearch";
    }

    @Override
    public Dot next() {
        return null;
    }

    @Override
    protected double getCharactInterval(int numberInterval) {
        return 0;
    }
}
