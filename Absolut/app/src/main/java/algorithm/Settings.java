package algorithm;

import android.graphics.Color;

import java.io.Serializable;

import storage.Dot;

public class Settings implements Serializable {
    private double accuracy;
    private int numberIterations;
    private double parameterMethod;
    private int degreeLocally = 5;
    private int stepTurnOnMix = 10;
    private int numberGlobalSteps = 3;
    private int numberLocalSteps = 1;

    private final int COUNT_SENSORS = 6;
    private boolean[] isCheckedSensors = new boolean[COUNT_SENSORS];

    public Settings(final double accuracy,
                    final int numberIterations,
                    final double parameterMethod) {
        this.accuracy = accuracy;
        this.numberIterations = numberIterations;
        this.parameterMethod = parameterMethod;

        isCheckedSensors[0] = true;
        isCheckedSensors[1] = true;
        isCheckedSensors[2] = true;
        isCheckedSensors[3] = false;
        isCheckedSensors[4] = false;
        isCheckedSensors[5] = true;
    }

    public double getEps() {
        return accuracy;
    }

    public int getNumberIterations() {
        return numberIterations;
    }

    public double getParameter() {
        return parameterMethod;
    }

    public int getDegreeLocally() {
        return degreeLocally;
    }

    public int getStepTurnOnMix() {
        return stepTurnOnMix;
    }

    public int getNumberGlobalSteps() {
        return numberGlobalSteps;
    }

    public int getNumberLocalSteps() {
        return numberLocalSteps;
    }

    public boolean[] getCheckedSensors() {
        return isCheckedSensors;
    }

    public void setAccuracy(final double accuracy) {
        this.accuracy = accuracy;
    }

    public void setNumberIterations(final int numberIterations) {
        this.numberIterations = numberIterations;
    }

    public void setParameterMethod(final double parameterMethod) {
        this.parameterMethod = parameterMethod;
    }

    public void setDegreeLocally(final int degreeLocally) {
        this.degreeLocally = degreeLocally;
    }

    public void setStepTurnOnMix(final int stepTurnOnMix) {
        this.stepTurnOnMix = stepTurnOnMix;
    }

    public void setNumberGlobalSteps(final int numberGlobalSteps) {
        this.numberGlobalSteps = numberGlobalSteps;
    }

    public void setNumberLocalSteps(final int numberLocalSteps) {
        this.numberLocalSteps = numberLocalSteps;
    }

    public void setCheckedSensors(final boolean[] isCheckedSensors) {
        this.isCheckedSensors = isCheckedSensors;
    }
}
