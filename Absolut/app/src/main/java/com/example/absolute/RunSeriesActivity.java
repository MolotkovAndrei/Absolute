package com.example.absolute;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class RunSeriesActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView lvTaskType;
    private Button select, cancel;
    private EditText countFunctionsET;
    private Toolbar toolbar;
    private TaskType taskType;
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

    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, RunSeriesActivity.class);
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

        lvTaskType = (ListView)findViewById(R.id.lvTaskType);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.seriesActivityItems,
                android.R.layout.simple_list_item_single_choice);

        lvTaskType.setAdapter(adapter);
        lvTaskType.setItemChecked(0, true);

        select = (Button)findViewById(R.id.select);
        select.setOnClickListener(this);

        cancel = (Button)findViewById(R.id.cancel);
        cancel.setOnClickListener(this);

        countFunctionsET = (EditText)findViewById(R.id.countFunctionsET);
    }

    @Override
    public void onClick(View v) {
        int countFunctions = Integer.parseInt(countFunctionsET.getText().toString());
        if (countFunctions < 1) {
            return;
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
                    return;
                }
                taskType.setCountFunctions(countFunctions);
                break;
        }
        switch (v.getId()) {
            case R.id.select:
                sendIntent();
                break;
            case R.id.cancel:
                finish();
                break;
        }
    }

    private void sendIntent() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_TASK_TYPE, taskType);
        setResult(RESULT_OK, intent);
        finish();
    }
}
