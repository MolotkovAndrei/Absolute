package com.example.absolute;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import draw.ViewerStandardFunctions;
import function.IFunction;

public class StandardFunctionsActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String EXTRA_STANDARD_FUNCTION = "com.example.absolute.standard_function";
    private Button selectButton, cancelButton;
    private Toolbar toolbar;
    private ViewerStandardFunctions standardFunctionsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard_functions);

        toolbar = (Toolbar)findViewById(R.id.standard_functions_toolBar);
        toolbar.setSubtitle(R.string.subTitleToolBarStandardFunctions);
        setSupportActionBar(toolbar);

        selectButton = (Button)findViewById(R.id.selectButton);
        selectButton.setOnClickListener(this);
        cancelButton = (Button)findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(this);
        standardFunctionsView = (ViewerStandardFunctions)findViewById(R.id.standardFunctionsView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.selectButton:
                sendFunction(standardFunctionsView.getSelectFunction());
                break;
            case R.id.cancelButton:
                finish();
                break;
        }
    }

    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, StandardFunctionsActivity.class);
    }

    public static IFunction getFunction(Intent result) {
        return (IFunction)result.getSerializableExtra(EXTRA_STANDARD_FUNCTION);
    }

    private void sendFunction(IFunction function) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_STANDARD_FUNCTION, function);
        setResult(RESULT_OK, intent);
        finish();
    }
}
