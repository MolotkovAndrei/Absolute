package algorithm;

import algorithm.withLimitation.SequentialScanWithLimitation;
import algorithm.withoutLimitation.BAGS;
import algorithm.withoutLimitation.BAGS_InsideInterval;
import algorithm.withoutLimitation.BAGS_WithLocalSettings;
import algorithm.withoutLimitation.Koushner;
import algorithm.withoutLimitation.LocallyAdaptive_BAGS;
import algorithm.withoutLimitation.Mix_BAGS;
import algorithm.withoutLimitation.Monotonic_BAGS;
import algorithm.withoutLimitation.PolygonalMethod;
import algorithm.withoutLimitation.RandomSearch;
import algorithm.withoutLimitation.SequentalScan;

public class CreatorAlgorithm {

    public static IAlgorithm create(String name, Settings settings) {
        IAlgorithm algorithm;
        switch (name) {
            case "SequentalScan":
                algorithm =  new SequentalScan(settings);
                break;
            case "Koushner":
                algorithm = new Koushner(settings);
                break;
            case "PolygonalMethod":
                algorithm = new PolygonalMethod(settings);
                break;
            case "RandomSearch":
                algorithm = new RandomSearch(settings);
                break;
            case "BAGS":
                algorithm = new BAGS(settings);
                break;
            case "BAGS_InsideInterval":
                algorithm = new BAGS_InsideInterval(settings);
                break;
            case "BAGS_WithLocalSettings":
                algorithm = new BAGS_WithLocalSettings(settings);
                break;
            case "Mix_BAGS":
                algorithm = new Mix_BAGS(settings);
                break;
            case "Monotonic_BAGS":
                algorithm = new Monotonic_BAGS(settings);
                break;
            case "LocallyAdaptive_BAGS":
                algorithm = new LocallyAdaptive_BAGS(settings);
                break;
            default:
                algorithm = new BAGS(settings);
                break;
        }
        return algorithm;
    }
}
