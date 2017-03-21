package com.example.absolute;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import draw.ViewerSelectSensors;

public class SetterSensors extends AppCompatActivity implements View.OnClickListener {
    private Button selectButton, cancelButton;
    private Toolbar toolbar;
    private ViewerSelectSensors viewerSelectSensors;
    private static final String EXTRA_SENSORS = "com.example.absolute.sensors";
    private static final String EXTRA_NEW_SENSORS = "com.example.absolute.newSensors";

    public static Intent newIntent(Context packageContext, boolean[] checkedSensors) {
        Intent intent = new Intent(packageContext, SetterSensors.class);
        intent.putExtra(EXTRA_SENSORS, checkedSensors);
        return intent;
    }

    public static boolean[] getCheckedSensors(Intent data) {
        return data.getBooleanArrayExtra(EXTRA_NEW_SENSORS);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setter_sensors);

        toolbar = (Toolbar)findViewById(R.id.setter_sensors_toolbar);
        toolbar.setSubtitle(R.string.subItemTitleSensors);
        setSupportActionBar(toolbar);

        selectButton = (Button)findViewById(R.id.selectButton);
        selectButton.setOnClickListener(this);
        cancelButton = (Button)findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(this);
        viewerSelectSensors = (ViewerSelectSensors)findViewById(R.id.viewerSelectSensors);

        Intent intent = getIntent();
        viewerSelectSensors.setCheckedSensors((boolean[])intent.getSerializableExtra(EXTRA_SENSORS));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.selectButton:
                sendCheckedSensors(viewerSelectSensors.getCheckedSensors());
                break;
            case R.id.cancelButton:
                finish();
        }

    }

    private void sendCheckedSensors(boolean[] isCheckedSensors) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_NEW_SENSORS, isCheckedSensors);
        setResult(RESULT_OK, intent);
        finish();
    }
}
