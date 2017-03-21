package algorithm.withoutLimitation;

import algorithm.Settings;

public class Mix_BAGS extends LocallyAdaptive_BAGS {
    private final int LOCAL_PATTERN = 9;
    private int currentStep;

    public Mix_BAGS(Settings settings) {
        super(settings);
        pattern.LOCAL.setDegreeLocal(LOCAL_PATTERN);
        currentStep = settings.getNumberGlobalSteps();
        name = "Mix_BAGS";
    }

    @Override
    public void reset() {
        super.reset();
        currentStep = settings.getNumberGlobalSteps();
    }

    @Override
    protected void setPattern() {
        if (pattern == Pattern.GLOBAL) {
            if (currentStep >= settings.getNumberGlobalSteps()) {
                pattern = Pattern.LOCAL;
                currentStep = 0;
                return;
            }
        } else {
            if (currentStep >= settings.getNumberLocalSteps()) {
                pattern = Pattern.GLOBAL;
                currentStep = 0;
                return;
            }
        }
        currentStep++;
    }
}
