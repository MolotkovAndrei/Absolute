package com.example.absolute;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import draw.DrawerPlot;
import draw.DrawerSensor;
import draw.HiderInvalidPoints;
import draw.ViewerSetLimitedFunctions;
import function.AbstractFunction;
import function.HillFunction;
import function.IFunction;
import function.PenaltyFunction;
import task.IndexTask;
import task.StorageTasks;

enum TypeLimitedTask {
    INDEX_TASK,
    PENALTY_TASK;
}

public class SetLimitedFunctionsActivity extends AppCompatActivity {
    private RecyclerView limitedFunctionsRecyclerView;
    private LimitedFunctionsAdapter adapter;
    private Toolbar toolbar;
    private Button selectButton;
    private Button cancelButton;
    private List<DrawerSensor> drawerSensors = new ArrayList<>();
    private HiderInvalidPoints hiderInvalidPoints;
    private IFunction minimizeFunction;
    private List<IFunction> limitedFunctions = new ArrayList<>();
    private IFunction penaltyFunction;
    private int positionChangeLimitedLevel;

    private static final String EXTRA_MINIMIZED_FUNCTION = "com.example.absolute.minimizedFunction";
    private static final String EXTRA_LIMITED_FUNCTION = "com.example.absolute.limitedFunction";
    private static final String EXTRA_NUMBER_LIMITED_FUNCTIONS = "com.example.absolute.numberLimitedFunctions";
    private static final String EXTRA_TYPE_LIMITED_TASK = "com.example.absolute.typeLimitedTask";

    private final int REQUEST_CODE_LIMITED_LEVEL_FUNCTION = 1;

    private final String[] nameFunctions = {"f", "g1", "g2", "g3"};
    private final String RESULT_FUNCTION = "result";

    private TypeLimitedTask typeLimitedTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_limited_functions);

        Intent intent = getIntent();
        minimizeFunction = (IFunction)intent.getSerializableExtra(EXTRA_MINIMIZED_FUNCTION);
        drawerSensors.add(new DrawerPlot(minimizeFunction, new Rect()));
        int numberLimitedFunction = (int)intent.getSerializableExtra(EXTRA_NUMBER_LIMITED_FUNCTIONS);
        for (int i = 0; i < numberLimitedFunction; i++) {
            limitedFunctions.add((IFunction)intent.getSerializableExtra(EXTRA_LIMITED_FUNCTION + i));
            drawerSensors.add(new DrawerPlot(limitedFunctions.get(i), new Rect()));
        }
        typeLimitedTask = (TypeLimitedTask)intent.getSerializableExtra(EXTRA_TYPE_LIMITED_TASK);

        toolbar = (Toolbar)findViewById(R.id.set_limited_functions_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setSubtitle(R.string.subTitleToolBarSetLimitedFunctions);

        limitedFunctionsRecyclerView = (RecyclerView)findViewById(R.id.limited_functions_recycler_view);
        limitedFunctionsRecyclerView.setLayoutManager(new LinearLayoutManager(SetLimitedFunctionsActivity.this));

        Rect hiderRect = new Rect();
        if (typeLimitedTask == TypeLimitedTask.INDEX_TASK) {
            drawerSensors.add(new DrawerPlot(minimizeFunction, hiderRect));
            hiderInvalidPoints = new HiderInvalidPoints(hiderRect, limitedFunctions);
        } else {
            penaltyFunction = new PenaltyFunction(minimizeFunction, limitedFunctions);
            drawerSensors.add(new DrawerPlot(penaltyFunction, hiderRect));
        }

        adapter = new LimitedFunctionsAdapter(drawerSensors);
        limitedFunctionsRecyclerView.setAdapter(adapter);

        selectButton = (Button) findViewById(R.id.selectButton);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNewLimitedTask();
            }
        });

        cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public static Intent newIntent(Context packageContext, StorageTasks storageTasks) {
        Intent intent = new Intent(packageContext, SetLimitedFunctionsActivity.class);
        intent.putExtra(EXTRA_MINIMIZED_FUNCTION, storageTasks.getMinimizedFunction());
        int numberLimitedFunctions = storageTasks.getCurrentLimitationFunctions().size();
        intent.putExtra(EXTRA_NUMBER_LIMITED_FUNCTIONS, numberLimitedFunctions);
        for (int i = 0; i < numberLimitedFunctions; i++) {
            intent.putExtra(EXTRA_LIMITED_FUNCTION + i, storageTasks.getCurrentLimitationFunctions().get(i));
        }
        if (storageTasks.getCurrentTask() instanceof IndexTask) {
            intent.putExtra(EXTRA_TYPE_LIMITED_TASK, TypeLimitedTask.INDEX_TASK);
        } else {
            intent.putExtra(EXTRA_TYPE_LIMITED_TASK, TypeLimitedTask.PENALTY_TASK);
        }
        return intent;
    }

    public static void setLimitedFunctions(Intent result, StorageTasks storageTasks) {
        storageTasks.setMinimizedFunction((IFunction)result.getSerializableExtra(EXTRA_MINIMIZED_FUNCTION));
        int numberLimitedFunction = (int)result.getSerializableExtra(EXTRA_NUMBER_LIMITED_FUNCTIONS);
        storageTasks.getCurrentLimitationFunctions().clear();
        List<IFunction> limitedFunctions = new ArrayList<>();
        for (int i = 0; i < numberLimitedFunction; i++) {
            limitedFunctions.add((IFunction)result.getSerializableExtra(EXTRA_LIMITED_FUNCTION + i));
        }
        storageTasks.setLimitationFunctions(limitedFunctions);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_set_limited_functions, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        final int MAX_NUMBER_LIMITED_FUNCTIONS = 3;
        MenuItem menuItemAdd = menu.findItem(R.id.menu_item_add_limited_function);
        //MenuItem menuItemDelete = menu.findItem(R.id.menu_item_delete_limited_function);
        if (limitedFunctions.size() == MAX_NUMBER_LIMITED_FUNCTIONS) {
            menuItemAdd.setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int lastElement = drawerSensors.size() - 1;
        switch (item.getItemId()) {
            case R.id.menu_item_add_limited_function:
                IFunction function = new HillFunction();
                limitedFunctions.add(function);
                DrawerSensor drawerSensor = drawerSensors.get(lastElement);
                drawerSensors.set(lastElement, new DrawerPlot(function, drawerSensor.getDrawPanel()));
                drawerSensors.add(drawerSensor);
                if (typeLimitedTask == TypeLimitedTask.INDEX_TASK) {
                    hiderInvalidPoints.updateFunctions(limitedFunctions);
                } else {
                    penaltyFunction = new PenaltyFunction(minimizeFunction, limitedFunctions);
                    drawerSensors.get(lastElement + 1).setContent(penaltyFunction);
                }
                invalidateOptionsMenu();
                adapter.notifyDataSetChanged();
                break;
            /*case R.id.menu_item_delete_limited_function:
                drawerSensors.remove(lastElement - 1);
                limitedFunctions.remove(lastElement - 2);
                invalidateOptionsMenu();
                adapter.notifyDataSetChanged();
                break;*/
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_LIMITED_LEVEL_FUNCTION:
                    IFunction function = SetLimitedLevelActivity.getFunction(data);
                    limitedFunctions.set(positionChangeLimitedLevel, function);
                    drawerSensors.get(positionChangeLimitedLevel + 1).setContent(function);
                    if (typeLimitedTask == TypeLimitedTask.INDEX_TASK) {
                        hiderInvalidPoints.updateFunctions(limitedFunctions);
                    } else {
                        penaltyFunction = new PenaltyFunction(minimizeFunction, limitedFunctions);
                        drawerSensors.get(limitedFunctions.size() + 1).setContent(penaltyFunction);
                    }
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    private void sendNewLimitedTask() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_MINIMIZED_FUNCTION, minimizeFunction);
        int numberLimitedFunctions = limitedFunctions.size();
        intent.putExtra(EXTRA_NUMBER_LIMITED_FUNCTIONS, numberLimitedFunctions);
        for (int i = 0; i < numberLimitedFunctions; i++) {
            intent.putExtra(EXTRA_LIMITED_FUNCTION + i, limitedFunctions.get(i));
        }
        setResult(RESULT_OK, intent);
        finish();
    }

    private class LimitedFunctionsHolder extends RecyclerView.ViewHolder {
        public ViewerSetLimitedFunctions viewerSetLimitedFunctions;
        public ImageButton refreshButton;
        public ImageButton removeLimitationButton;
        public ImageButton limitedLevelButton;
        public TextView nameFunction;
        public int position;

        public LimitedFunctionsHolder(View itemView) {
            super(itemView);

            viewerSetLimitedFunctions = (ViewerSetLimitedFunctions) itemView.findViewById(R.id.list_item_limited_function_view);
            nameFunction = (TextView) itemView.findViewById(R.id.nameFunction);
            refreshButton = (ImageButton) itemView.findViewById(R.id.refresh_button);
            refreshButton.setOnClickListener(new View.OnClickListener() {
                int i = 0;
                @Override
                public void onClick(View view) {
                    IFunction function = new HillFunction();
                    if (position > 0 && position <= limitedFunctions.size()) {
                        limitedFunctions.set(position - 1, function);
                        drawerSensors.get(position).setContent(function);
                    } else {
                        minimizeFunction = function;
                        drawerSensors.get(0).setContent(function);
                        if (typeLimitedTask == TypeLimitedTask.INDEX_TASK) {
                            drawerSensors.get(limitedFunctions.size() + 1).setContent(function);
                        }
                    }
                    //drawerSensors.get(position).setContent(function);
                    if (typeLimitedTask == TypeLimitedTask.INDEX_TASK) {
                        hiderInvalidPoints.updateFunctions(limitedFunctions);
                    } else {
                        penaltyFunction = new PenaltyFunction(minimizeFunction, limitedFunctions);
                        drawerSensors.get(limitedFunctions.size() + 1).setContent(penaltyFunction);
                    }
                    //colors.set(position, Color.BLACK);

                    viewerSetLimitedFunctions.invalidate();
                    adapter.notifyDataSetChanged();
                }
            });

            removeLimitationButton = (ImageButton) itemView.findViewById(R.id.delete_button);
            removeLimitationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (limitedFunctions.size() == 1) {
                        return;
                    }
                    if (position > 0 && position <= limitedFunctions.size()) {
                        drawerSensors.remove(position);
                        limitedFunctions.remove(position - 1);
                        invalidateOptionsMenu();
                        if (typeLimitedTask == TypeLimitedTask.INDEX_TASK) {
                            hiderInvalidPoints.updateFunctions(limitedFunctions);
                        } else {
                            penaltyFunction = new PenaltyFunction(minimizeFunction, limitedFunctions);
                            drawerSensors.get(limitedFunctions.size() + 1).setContent(penaltyFunction);
                        }
                        viewerSetLimitedFunctions.invalidate();
                        adapter.notifyDataSetChanged();
                    }
                }
            });

            limitedLevelButton = (ImageButton) itemView.findViewById(R.id.set_level_button);
            limitedLevelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (position > 0 && position <= limitedFunctions.size()) {
                        positionChangeLimitedLevel = position - 1;
                        Intent intent = SetLimitedLevelActivity.newIntent(SetLimitedFunctionsActivity.this,
                                limitedFunctions.get(positionChangeLimitedLevel));
                        startActivityForResult(intent, REQUEST_CODE_LIMITED_LEVEL_FUNCTION);
                    }
                }
            });
        }
    }

    private class LimitedFunctionsAdapter extends RecyclerView.Adapter<LimitedFunctionsHolder> {
        private List<DrawerSensor> drawerSensors;

        public LimitedFunctionsAdapter(List<DrawerSensor> drawerSensors) {
            this.drawerSensors = drawerSensors;
        }

        @Override
        public LimitedFunctionsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(SetLimitedFunctionsActivity.this);
            View view = layoutInflater.inflate(R.layout.set_limited_functions_list_item, parent, false);
            return new LimitedFunctionsHolder(view);
        }

        @Override
        public void onBindViewHolder(LimitedFunctionsHolder holder, int position) {
            DrawerSensor drawerSensor = drawerSensors.get(position);
            holder.viewerSetLimitedFunctions.setDrawerSensor(drawerSensor);
            holder.viewerSetLimitedFunctions.setPos(position);
            holder.position = position;
            if (position == limitedFunctions.size() + 1) {
                holder.viewerSetLimitedFunctions.setHiderInvalidPoints(hiderInvalidPoints);
                holder.refreshButton.setEnabled(false);
                holder.removeLimitationButton.setEnabled(false);
                holder.nameFunction.setText(RESULT_FUNCTION);
            } else {
                holder.viewerSetLimitedFunctions.setHiderInvalidPoints(null);
                holder.refreshButton.setEnabled(true);
                holder.removeLimitationButton.setEnabled(true);
                holder.nameFunction.setText(nameFunctions[position]);
            }
        }

        @Override
        public int getItemCount() {
            return drawerSensors.size();
        }
    }
}
