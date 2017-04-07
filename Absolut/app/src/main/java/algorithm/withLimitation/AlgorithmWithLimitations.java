package algorithm.withLimitation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import algorithm.IAlgorithm;
import algorithm.Settings;
import storage.Dot;
import storage.IStorage;
import storage.TestDots;

public abstract class AlgorithmWithLimitations implements IAlgorithm {
    protected IStorage testDots;
    protected Settings settings;
    protected double parameter;
    protected String name = "IndexingAlgorithm";
    private int currentIteration = 0;
    protected int numberIntervalWithMaxCharacteristic;
    private int M = 0;
    protected List<AttributesOfSetOfPoints> mAttributesOfSetOfPointsList;

    //protected boolean canCalculateIntervalBoundary = true;

    public AlgorithmWithLimitations(final Settings settings, final int numberFunctions) {
        testDots = new TestDots();
        this.settings = settings;
        parameter = this.settings.getParameter();
        mAttributesOfSetOfPointsList = new ArrayList<>();
        for (int i = 0; i <= numberFunctions; i++) {
            mAttributesOfSetOfPointsList.add(new AttributesOfSetOfPoints());
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void reset() {
        testDots.clearAllDots();
        for (AttributesOfSetOfPoints o : mAttributesOfSetOfPointsList) {
            o.setInitialValue();
        }
        currentIteration = 0;
    }

    @Override
    public abstract Dot next();

    @Override
    public boolean hasNext() {
        double eps = settings.getEps() * testDots.getLengthDomain();
        numberIntervalWithMaxCharacteristic = getNumberIntervalWithMaxCharacteristic();
        return currentIteration < settings.getNumberIterations() &&
                testDots.getLengthInterval(numberIntervalWithMaxCharacteristic) > eps;
    }

    @Override
    public void addDotInStorage(final Dot dot) {
        testDots.addDot(dot);
        if (dot.index > M) {
            M = dot.index;
        }

        for (AttributesOfSetOfPoints o : mAttributesOfSetOfPointsList) {
            o.setInitialValue();
        }

        int index;
        for (int number = 0; number < testDots.getAllDots().size(); number++) {
            index = testDots.getDot(number).index;
            mAttributesOfSetOfPointsList.get(index).addNumberInClassification(number);
        }

        for (int i = 1; i < mAttributesOfSetOfPointsList.size(); i++) {
            mAttributesOfSetOfPointsList.get(i).calculateMu();
            mAttributesOfSetOfPointsList.get(i).calculateZ();
        }

        currentIteration++;
    }

    @Override
    public void setSettings(final Settings settings) {
        this.settings = settings;
        parameter = this.settings.getParameter();
        reset();
    }

    @Override
    public Settings getSettings() {
        return settings;
    }

    @Override
    public List<Dot> getTestDots() {
        return testDots.getAllDots();
    }

    protected abstract double getCharacteristicInterval(final int numberInterval);

    private int getNumberIntervalWithMaxCharacteristic() {
        double characteristicInterval = getCharacteristicInterval(1);
        double maxCharacteristicInterval = characteristicInterval;
        int result = 1;
        for (int interval = 2; interval <= testDots.getIntervalCount(); interval++) {
            characteristicInterval = getCharacteristicInterval(interval);
            if (maxCharacteristicInterval < characteristicInterval) {
                maxCharacteristicInterval = characteristicInterval;
                result = interval;
            }
        }
        return result;
    }

    protected class AttributesOfSetOfPoints implements Serializable {
        private List<Integer> numbersOfPoints;
        private double mu;
        private double z;

        public AttributesOfSetOfPoints() {
            numbersOfPoints = new ArrayList<>();
            mu = 1.0;
            z = 0.0;
        }

        public double getMu() {
            return mu;
        }

        public double getZ() {
            return z;
        }

        public void setInitialValue() {
            numbersOfPoints.clear();
            mu = 1.0;
            z = 0.0;
        }

        public void addNumberInClassification(final int number) {
            numbersOfPoints.add(number);
        }

        public void calculateMu() {
            final double EPS = 1.0e-12;
            if (numbersOfPoints.size() < 2) {
                mu = 1.0;
                return;
            }
            double currentMu;
            mu = 0.0;
            int index;
            Dot leftDot, rightDot;
            for (int j = 0; j < numbersOfPoints.size() - 1; j++) {
                index = numbersOfPoints.get(j);
                leftDot = testDots.getDot(index);
                for (int i = j + 1; i < numbersOfPoints.size(); i++) {
                    index = numbersOfPoints.get(i);
                    rightDot = testDots.getDot(index);
                    currentMu = Math.abs(rightDot.y - leftDot.y) / (rightDot.x - leftDot.x);
                    if (currentMu > mu) {
                        mu = currentMu;
                    }
                }
            }
            if (mu < EPS) {
                mu = 1.0;
            }
        }

        public void calculateZ() {
            if (numbersOfPoints.isEmpty()) {
                return;
            }
            Dot dot = testDots.getDot(numbersOfPoints.get(0));
            if (dot.index < M) {
                z = 0.0;
            } else {
                z = dot.y;
                for (int i = 1; i < numbersOfPoints.size(); i++) {
                    dot = testDots.getDot(numbersOfPoints.get(i));
                    if (dot.y < z) {
                        z = dot.y;
                    }
                }
            }
        }
    }
}
