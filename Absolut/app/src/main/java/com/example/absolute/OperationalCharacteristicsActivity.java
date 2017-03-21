package com.example.absolute;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.example.absolute.adapters.OperationCharacteristicsAdapter;

import draw.ViewerOperationalCharacteristics;
import results.Results;

public class OperationalCharacteristicsActivity extends AppCompatActivity {
    private Results results;
    private ViewerOperationalCharacteristics viewerOperationalCharacteristics;
    private ListView listView;
    private Toolbar toolbar;
    private static final String EXTRA_RESULTS = "com.example.absolute.operationalCharacteristicsResults";

    public static Intent newIntent(Context packageContext, Results results) {
        Intent intent = new Intent(packageContext, OperationalCharacteristicsActivity.class);
        intent.putExtra(EXTRA_RESULTS, results);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operational_characteristics);

        toolbar = (Toolbar) findViewById(R.id.operational_characteristic_toolbar);
        toolbar.setSubtitle(R.string.subtitleOperationalCharacteristicsActivity);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        results = (Results) intent.getSerializableExtra(EXTRA_RESULTS);

        viewerOperationalCharacteristics = (ViewerOperationalCharacteristics)findViewById(R.id.operationalCharacteristicsView);
        viewerOperationalCharacteristics.setResultsForView(results.getJournalList());

        listView = (ListView)findViewById(R.id.listView);

        if (!results.getJournalList().isEmpty()) {
            OperationCharacteristicsAdapter adapter = new OperationCharacteristicsAdapter(this, android.R.layout.simple_list_item_1, results.getJournalList());
            listView.setAdapter(adapter);
        }
    }
}
