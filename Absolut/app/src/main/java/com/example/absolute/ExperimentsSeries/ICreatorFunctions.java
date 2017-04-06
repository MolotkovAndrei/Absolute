package com.example.absolute.ExperimentsSeries;

import java.util.List;

import function.IFunction;

public interface ICreatorFunctions {
    IFunction getFunction();
    List<IFunction> getListFunctions(int countFunctions);
}
