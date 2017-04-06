package com.example.absolute.ExperimentsSeries;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import function.HillFunction;
import function.IFunction;
import function.ShekelFunction;

public class CreatorHillAndShekelFunctions implements ICreatorFunctions {
    private Random random = new Random();

    @Override
    public IFunction getFunction() {
        return (random.nextInt(1) == 0) ? new ShekelFunction() : new HillFunction();
    }

    @Override
    public List<IFunction> getListFunctions(int countFunctions) {
        List<IFunction> functions = new ArrayList<>();
        for (int i = 0; i < countFunctions; i++) {
            functions.add((random.nextInt(1) == 0) ? new ShekelFunction() : new HillFunction());
        }
        return functions;
    }
}
