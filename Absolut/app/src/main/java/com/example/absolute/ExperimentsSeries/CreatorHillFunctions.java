package com.example.absolute.ExperimentsSeries;

import java.util.ArrayList;
import java.util.List;

import function.HillFunction;
import function.IFunction;

public class CreatorHillFunctions implements ICreatorFunctions {
    @Override
    public IFunction getFunction() {
        return new HillFunction();
    }

    @Override
    public List<IFunction> getListFunctions(int countFunctions) {
        List<IFunction> functions = new ArrayList<>();
        for (int i = 0; i < countFunctions; i++) {
            functions.add(new HillFunction());
        }
        return functions;
    }
}
