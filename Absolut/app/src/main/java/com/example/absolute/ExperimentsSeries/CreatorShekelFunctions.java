package com.example.absolute.ExperimentsSeries;

import java.util.ArrayList;
import java.util.List;

import function.IFunction;
import function.ShekelFunction;

public class CreatorShekelFunctions implements ICreatorFunctions {
    @Override
    public IFunction getFunction() {
        return new ShekelFunction();
    }

    @Override
    public List<IFunction> getListFunctions(int countFunctions) {
        List<IFunction> functions = new ArrayList<>();
        for (int i = 0; i < countFunctions; i++) {
            functions.add(new ShekelFunction());
        }
        return functions;
    }
}
