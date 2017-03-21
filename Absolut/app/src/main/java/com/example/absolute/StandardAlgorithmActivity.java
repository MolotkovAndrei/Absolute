package com.example.absolute;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import algorithm.withoutLimitation.BAGS;
import algorithm.withoutLimitation.BAGS_WithLocalSettings;
import algorithm.IAlgorithm;
import algorithm.withoutLimitation.Koushner;
import algorithm.withoutLimitation.LocallyAdaptive_BAGS;
import algorithm.withoutLimitation.Mix_BAGS;
import algorithm.withoutLimitation.PolygonalMethod;
import algorithm.withoutLimitation.SequentalScan;
import algorithm.Settings;

public class StandardAlgorithmActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String EXTRA_STANDARD_ALGORITHM = "com.example.absolute.standard_algorithm";
    private ListView lvStandardAlgorithms;
    private Button select, cancel;
    private Toolbar toolbar;
    private final double ACCURACY = 0.001;
    private final int NUMBER_ITERATIONS = 200;
    private final double PARAMETER_POLYGONAL = 10.0;
    private final double PARAMETER_KOUSHNER = 0.9;
    private final double PARAMETER_BAGS = 2.0;
    private final double PARAMETER_LOCALLY_ADAPTIVE_BAGS = 3.5;
    private final double PARAMETER_MIX_BAGS = 2.5;
    private List<IAlgorithm> algorithms = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard_algorithm);

        toolbar = (Toolbar)findViewById(R.id.standard_algorithm_toolbar);
        toolbar.setSubtitle(R.string.subTitleToolBarStandardAlgorithms);
        setSupportActionBar(toolbar);

        algorithms.add(new SequentalScan(new Settings(ACCURACY, NUMBER_ITERATIONS, PARAMETER_KOUSHNER)));
        algorithms.add(new PolygonalMethod(new Settings(ACCURACY, NUMBER_ITERATIONS, PARAMETER_POLYGONAL)));
        algorithms.add(new Koushner(new Settings(ACCURACY, NUMBER_ITERATIONS, PARAMETER_KOUSHNER)));
        algorithms.add(new BAGS(new Settings(ACCURACY, NUMBER_ITERATIONS, PARAMETER_BAGS)));
        algorithms.add(new LocallyAdaptive_BAGS(new Settings(ACCURACY, NUMBER_ITERATIONS, PARAMETER_LOCALLY_ADAPTIVE_BAGS)));
        algorithms.add(new Mix_BAGS(new Settings(ACCURACY, NUMBER_ITERATIONS, PARAMETER_MIX_BAGS)));
        algorithms.add(new BAGS_WithLocalSettings(new Settings(ACCURACY, NUMBER_ITERATIONS, PARAMETER_BAGS)));

        lvStandardAlgorithms = (ListView)findViewById(R.id.lvStandardAlgorithms);
        lvStandardAlgorithms.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.standardAlgorithmsItems,
                android.R.layout.simple_list_item_single_choice);

        lvStandardAlgorithms.setAdapter(adapter);
        Intent intent = getIntent();
        IAlgorithm algorithm = (IAlgorithm) intent.getSerializableExtra(EXTRA_STANDARD_ALGORITHM);

        if (algorithm instanceof SequentalScan) {
            lvStandardAlgorithms.setItemChecked(0, true);
        } else if (algorithm instanceof PolygonalMethod) {
            lvStandardAlgorithms.setItemChecked(1, true);
        } else if (algorithm instanceof Koushner) {
            lvStandardAlgorithms.setItemChecked(2, true);
        } else if (algorithm instanceof BAGS) {
            if (algorithm instanceof LocallyAdaptive_BAGS) {
                if (algorithm instanceof Mix_BAGS) {
                    lvStandardAlgorithms.setItemChecked(5, true);
                } else {
                    lvStandardAlgorithms.setItemChecked(4, true);
                }
            } else if (algorithm instanceof BAGS_WithLocalSettings) {
                lvStandardAlgorithms.setItemChecked(6, true);
            } else {
                lvStandardAlgorithms.setItemChecked(3, true);
            }
        }
        select = (Button)findViewById(R.id.select);
        select.setOnClickListener(this);

        cancel = (Button)findViewById(R.id.cancel);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = lvStandardAlgorithms.getCheckedItemPosition();
        switch (v.getId()) {
            case R.id.select:
                sendAlgorithm(algorithms.get(id));
                break;
            case R.id.cancel:
                finish();
                break;
        }
    }

    public static Intent newIntent(Context packageContext, IAlgorithm algorithm) {
        Intent intent = new Intent(packageContext, StandardAlgorithmActivity.class);
        intent.putExtra(EXTRA_STANDARD_ALGORITHM, algorithm);
        return intent;
    }

    public static IAlgorithm getAlgorithm(Intent result) {
        return (IAlgorithm)result.getSerializableExtra(EXTRA_STANDARD_ALGORITHM);
    }

    private void sendAlgorithm(IAlgorithm algorithm) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_STANDARD_ALGORITHM, algorithm);
        setResult(RESULT_OK, intent);
        finish();
    }
}
