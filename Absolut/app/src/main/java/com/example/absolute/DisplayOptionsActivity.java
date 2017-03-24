package com.example.absolute;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import model.DisplayOptions;

import static model.DisplayOptions.DisplaySpeed.QUICK;

public class DisplayOptionsActivity extends AppCompatActivity {
    private Button btnSelect, btnCancel;
    private CheckBox cbExecuteOneStep, cbExecuteNumberStep;
    private RadioGroup rgValueDelay, rgExecuteSteps;
    private Toolbar toolbar;
    private DisplayOptions displayOptions;
    private EditText etNumberSteps;
    private static final String EXTRA_DISPLAY_OPTIONS = "com.example.absolute.displayOptions";
    private static final String EXTRA_NEW_DISPLAY_OPTIONS = "com.example.absolute.newDisplayOptions";

    public static Intent newIntent(Context packageContext, DisplayOptions displayOptions) {
        Intent intent = new Intent(packageContext, DisplayOptionsActivity.class);
        intent.putExtra(EXTRA_DISPLAY_OPTIONS, displayOptions);
        return intent;
    }

    public static DisplayOptions getDisplayOptions(Intent data) {
        return (DisplayOptions) data.getSerializableExtra(EXTRA_NEW_DISPLAY_OPTIONS);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_options);

        toolbar = (Toolbar) findViewById(R.id.display_options_toolbar);
        toolbar.setSubtitle(R.string.subTitleToolBarDisplayOptions);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        displayOptions = (DisplayOptions) intent.getSerializableExtra(EXTRA_DISPLAY_OPTIONS);

        rgValueDelay = (RadioGroup)findViewById(R.id.rgValueDelay);
        rgExecuteSteps = (RadioGroup)findViewById(R.id.rgExecuteSteps);

        cbExecuteOneStep =    (CheckBox) rgExecuteSteps.findViewById(R.id.cbExecuteOneStep);
        cbExecuteOneStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbExecuteNumberStep.isChecked()) {
                    cbExecuteNumberStep.setChecked(false);
                }
            }
        });

        cbExecuteNumberStep = (CheckBox) rgExecuteSteps.findViewById(R.id.cbExecuteNumberStep);
        cbExecuteNumberStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbExecuteOneStep.isChecked()) {
                    cbExecuteOneStep.setChecked(false);
                }
            }
        });

        etNumberSteps = (EditText)findViewById(R.id.etNumberSteps);

        setStartValues();

        btnSelect = (Button)findViewById(R.id.btnSelect);
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDisplaySpeed();

                if (!setNumberStepsBeforeStop()) {
                    return;
                }
                sendResult();
            }
        });

        btnCancel = (Button)findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void sendResult() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_NEW_DISPLAY_OPTIONS, displayOptions);
        setResult(RESULT_OK, intent);
        finish();
    }

    private boolean setNumberStepsBeforeStop() {
        if (cbExecuteOneStep.isChecked()) {
            displayOptions.setCurrentStopFlag(true);
            displayOptions.setNumberStepsBeforeStop(1);
            displayOptions.setBeginnerStopFlag(true);
        } else if (cbExecuteNumberStep.isChecked()) {
            displayOptions.setCurrentStopFlag(true);
            int numberSteps = Integer.parseInt(etNumberSteps.getText().toString());

            if (numberSteps < 0) {
                return false;
            }
            displayOptions.setNumberStepsBeforeStop(numberSteps);
            displayOptions.setBeginnerStopFlag(true);
        } else {
            displayOptions.setCurrentStopFlag(false);
            //if (displayOptions.inBeginWithStop()) {
            displayOptions.setBeginnerStopFlag(false);
            //}
        }
        return true;
    }

    private void setDisplaySpeed() {
        int rbId = rgValueDelay.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton)findViewById(rbId);
        int checkedIndex = rgValueDelay.indexOfChild(radioButton);
        switch (checkedIndex) {
            case 0:
                displayOptions.setDisplaySpeed(QUICK);
                break;
            case 1:
                displayOptions.setDisplaySpeed(DisplayOptions.DisplaySpeed.NORMAL);
                break;
            case 2:
                displayOptions.setDisplaySpeed(DisplayOptions.DisplaySpeed.SLOW);
                break;
        }
    }

    private void setStartValues() {
        if (displayOptions.isCurrentWithStop()) {
            if (displayOptions.getNumberStepsBeforeStop() == 1) {
                cbExecuteOneStep.setChecked(true);
            } else {
                cbExecuteNumberStep.setChecked(true);
                etNumberSteps.setText(Integer.toString(displayOptions.getNumberStepsBeforeStop()));
            }
        }

        RadioButton radioButton;
        switch (displayOptions.getDisplaySpeed()) {
            case QUICK:
                radioButton = (RadioButton) rgValueDelay.findViewById(R.id.rbQuick);
                radioButton.setChecked(true);
                break;
            case NORMAL:
                radioButton = (RadioButton) rgValueDelay.findViewById(R.id.rbNormal);
                radioButton.setChecked(true);
                break;
            case SLOW:
                radioButton = (RadioButton) rgValueDelay.findViewById(R.id.rbSlow);
                radioButton.setChecked(true);
                break;
        }
    }
}
