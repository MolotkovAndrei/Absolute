package com.example.absolute;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import task.ITask;
import task.StorageTasks;
import task.TaskWithLimitations;

public abstract class RunSeriesActivity extends AppCompatActivity implements View.OnClickListener {
    protected ListView lvTaskType;
    protected Button select, cancel;
    protected EditText countFunctionsET;
    protected Toolbar toolbar;
    protected TaskType taskType;
    private final int MAX_NUMBER_FUNCTIONS_IN_SERIES = 50;
    private final int MIN_NUMBER_FUNCTIONS_IN_SERIES = 1;
    protected final int START_CHECKED_POSITION_LIST_VIEW = 0;
    private static final String EXTRA_TASK_TYPE = "com.example.absolute.taskType";

    public enum TaskType {
        SHEKEL_AND_HILL(10),
        HILL(10),
        SHEKEL(10),
        HANSEN(10);

        TaskType(final int countFunctions) {
            this.countFunctions = countFunctions;
        }

        public int getCountFunctions() {
            return countFunctions;
        }

        public void setCountFunctions(final int countFunctions) {
            this.countFunctions = countFunctions;
        }

        private int countFunctions;
    }

    public static Intent newIntent(Context packageContext, StorageTasks storageTasks) {
        ITask task = storageTasks.getCurrentTask();
        if (task instanceof TaskWithLimitations) {
            return new Intent(packageContext, SeriesLimitedTaskActivity.class);
        } else {
            return new Intent(packageContext, SeriesUnlimitedTaskActivity.class);
        }
    }

    public static TaskType getTaskType(Intent data) {
        return (TaskType) data.getSerializableExtra(EXTRA_TASK_TYPE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_series);

        toolbar = (Toolbar) findViewById(R.id.run_series_toolbar);
        toolbar.setSubtitle(R.string.subTitleToolBarRunSeries);
        setSupportActionBar(toolbar);

        lvTaskType = (ListView) findViewById(R.id.lvTaskType);

        select = (Button) findViewById(R.id.select);
        select.setOnClickListener(this);

        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(this);

        countFunctionsET = (EditText) findViewById(R.id.countFunctionsET);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select:
                sendIntent();
                break;
            case R.id.cancel:
                finish();
                break;
        }
    }

    protected void sendIntent() {
        if (!isCorrectValuesForSeries()) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_TASK_TYPE, taskType);
        setResult(RESULT_OK, intent);
        finish();
    }

    protected abstract boolean isCorrectValuesForSeries();

    protected int getCountFunctions() {
        int countFunctions;
        try {
            countFunctions = Integer.parseInt(countFunctionsET.getText().toString());
        } catch (NumberFormatException e) {
            throw new NumberFormatException();
        }
        if (countFunctions < MIN_NUMBER_FUNCTIONS_IN_SERIES || countFunctions > MAX_NUMBER_FUNCTIONS_IN_SERIES) {
            throw new IllegalArgumentException();
        }
        return countFunctions;
    }
}
