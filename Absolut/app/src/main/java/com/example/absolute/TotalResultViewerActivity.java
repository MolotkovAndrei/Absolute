package com.example.absolute;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.Iterator;

import draw.ViewerTotalResults;
import results.Results;
import results.TotalResults;

public class TotalResultViewerActivity extends AppCompatActivity {
    private TotalResults totalResults;
    private Results results;
    //private Button closeButton;
    private Toolbar toolbar;

    private ViewerTotalResults viewerTotalResults;

    private static final String EXTRA_TOTAL_RESULT = "com.example.absolute.totalResult";
    private static final String EXTRA_CLEAR_TOTAL_RESULT = "com.example.absolute.clearTotalResults";

    public static Intent newIntent(Context packageContext, Results results) {
        Intent intent = new Intent(packageContext, TotalResultViewerActivity.class);
        intent.putExtra(EXTRA_TOTAL_RESULT, results);
        return intent;
    }

    public static Results getResults(Intent data) {
        return (Results) data.getSerializableExtra(EXTRA_CLEAR_TOTAL_RESULT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_result_viewer);

        toolbar = (Toolbar) findViewById(R.id.total_results_toolbar);
        toolbar.setSubtitle(R.string.subItemTitleTotalResults);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        results = (Results) intent.getSerializableExtra(EXTRA_TOTAL_RESULT);
        totalResults = results.getTotalResults();

        viewerTotalResults = (ViewerTotalResults)findViewById(R.id.totalResultsView);
        viewerTotalResults.setResultsForView(totalResults);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_total_results, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.sub_item_clear_total_results);
        if (totalResults.getNumberExperiments() == 0) {
            item.setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sub_item_clear_total_results:
                totalResults.clear();
                sendResult();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendResult() {
        Intent intent = new Intent();
        results.setTotalResults(totalResults);
        intent.putExtra(EXTRA_CLEAR_TOTAL_RESULT, results);
        setResult(RESULT_OK, intent);
        finish();
    }
}
