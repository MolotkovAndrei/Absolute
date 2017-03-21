package com.example.absolute;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import model.IModel;
import observer.ViewerWorkSpace;

public class WorkSpaceActivity extends Activity {
    private ViewerWorkSpace viewerWorkSpace;
    private IModel model;
    private static final String EXTRA_MODEL = "com.example.absolute.model";

    public static Intent newIntent(Context packageContext, IModel model) {
        Intent intent = new Intent(packageContext, WorkSpaceActivity.class);
        intent.putExtra(EXTRA_MODEL, model);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        model = (IModel)intent.getSerializableExtra(EXTRA_MODEL);

        viewerWorkSpace = new ViewerWorkSpace(this, model);
        setContentView(viewerWorkSpace);
    }
}
