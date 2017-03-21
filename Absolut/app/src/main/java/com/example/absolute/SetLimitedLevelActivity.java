package com.example.absolute;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import draw.ViewerSetLimitedLevel;
import function.IFunction;

public class SetLimitedLevelActivity extends AppCompatActivity implements ChangeLimitedLevelListener {
    private Button btnSelect;
    private Button btnCancel;
    private Toolbar toolbar;
    private IFunction function;
    private double currentLimitedLevel;
    private ViewerSetLimitedLevel viewerSetLimitedLevel;
    private static final String EXTRA_FUNCTION = "com.example.absolute.Function";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_limited_level);

        Intent intent = getIntent();
        function = (IFunction)intent.getSerializableExtra(EXTRA_FUNCTION);
        currentLimitedLevel = function.getLimitationLevel();

        toolbar = (Toolbar)findViewById(R.id.set_limited_level_toolbar);
        setSupportActionBar(toolbar);
        double limitedLevel = currentLimitedLevel;
        toolbar.setSubtitle(Double.toString(roundValue(limitedLevel)));

        btnSelect = (Button)findViewById(R.id.btnSelect);
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                function.setLimitationLevel(currentLimitedLevel);
                Intent intent = new Intent();
                intent.putExtra(EXTRA_FUNCTION, function);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        viewerSetLimitedLevel = (ViewerSetLimitedLevel)findViewById(R.id.setLimitedLevelView);
        viewerSetLimitedLevel.setFunction(function);
    }

    public static Intent newIntent(Context packageContext, IFunction function) {
        Intent intent = new Intent(packageContext, SetLimitedLevelActivity.class);
        intent.putExtra(EXTRA_FUNCTION, function);
        return intent;
    }

    public static IFunction getFunction(Intent result) {
        return (IFunction) result.getSerializableExtra(EXTRA_FUNCTION);
    }


    @Override
    public void updateLimitedLevelValue(double limitedLevel) {
        currentLimitedLevel = limitedLevel;
        toolbar.setSubtitle(Double.toString(roundValue(limitedLevel)));
    }

    private double roundValue(double value) {
        final double ROUND_ORDER = 100;
        value *= ROUND_ORDER;
        value = Math.round(value);
        value /= ROUND_ORDER;
        return value;
    }
}
