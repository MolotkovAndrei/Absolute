package com.example.absolute.ExperimentsSeries;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import function.F1;
import function.F10;
import function.F11;
import function.F12;
import function.F13;
import function.F14;
import function.F15;
import function.F16;
import function.F17;
import function.F18;
import function.F19;
import function.F2;
import function.F20;
import function.F3;
import function.F4;
import function.F5;
import function.F6;
import function.F7;
import function.F8;
import function.F9;
import function.IFunction;

public class CreatorHansenFunctions implements ICreatorFunctions {
    private List<IFunction> standardFunctions = new ArrayList<>();
    private final int MAX_NUMBER_FUNCTIONS = 20;
    private Random random = new Random();

    public CreatorHansenFunctions() {
        createStandardFunctionList();
    }

    @Override
    public IFunction getFunction() {
        return standardFunctions.get(random.nextInt(MAX_NUMBER_FUNCTIONS));
    }

    @Override
    public List<IFunction> getListFunctions(int countFunctions) {
        if (countFunctions < MAX_NUMBER_FUNCTIONS) {
            return standardFunctions.subList(0, countFunctions);
        } else {
            return standardFunctions;
        }
    }

    private void createStandardFunctionList() {
        standardFunctions.add(new F1());  standardFunctions.add(new F2());
        standardFunctions.add(new F3());  standardFunctions.add(new F4());
        standardFunctions.add(new F5());  standardFunctions.add(new F6());
        standardFunctions.add(new F7());  standardFunctions.add(new F8());
        standardFunctions.add(new F9());  standardFunctions.add(new F10());
        standardFunctions.add(new F11()); standardFunctions.add(new F12());
        standardFunctions.add(new F13()); standardFunctions.add(new F14());
        standardFunctions.add(new F15()); standardFunctions.add(new F16());
        standardFunctions.add(new F17()); standardFunctions.add(new F18());
        standardFunctions.add(new F19()); standardFunctions.add(new F20());
    }
}
