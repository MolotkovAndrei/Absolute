package com.example.absolute;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class SeriesUnlimitedTaskActivity extends RunSeriesActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.functionsUnlimitedTaskSeries,
                android.R.layout.simple_list_item_single_choice);

        lvTaskType.setAdapter(adapter);
        lvTaskType.setItemChecked(START_CHECKED_POSITION_LIST_VIEW, true);
    }

    @Override
    protected boolean isCorrectValuesForSeries() {
        int countFunctions = 1;
        try {
            countFunctions = getCountFunctions();
        } catch (NumberFormatException e) {
            Toast.makeText(this, getString(R.string.inputNumberFunctionsInSeries), Toast.LENGTH_SHORT).show();
            return false;
        } catch (IllegalArgumentException e) {
            Toast.makeText(this, getString(R.string.numberFunctionsInSeries), Toast.LENGTH_SHORT).show();
            return false;
        }
        switch (lvTaskType.getCheckedItemPosition()) {
            case 0:
                taskType = TaskType.SHEKEL_AND_HILL;
                taskType.setCountFunctions(countFunctions);
                break;
            case 1:
                taskType = TaskType.HILL;
                taskType.setCountFunctions(countFunctions);
                break;
            case 2:
                taskType = TaskType.SHEKEL;
                taskType.setCountFunctions(countFunctions);
                break;
            case 3:
                taskType = TaskType.HANSEN;
                if (countFunctions > 20) {
                    Toast.makeText(this, getString(R.string.maxNumberHansenFunctions), Toast.LENGTH_SHORT).show();
                    return false;
                }
                taskType.setCountFunctions(countFunctions);
                break;
        }
        return true;
    }
}
