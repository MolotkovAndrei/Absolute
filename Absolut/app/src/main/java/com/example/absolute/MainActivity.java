package com.example.absolute;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.*;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;

import com.example.absolute.dialog.CloseTabFragment;
import com.example.absolute.dialog.DialogListener;
import com.example.absolute.dialog.NameNewTabFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import algorithm.Settings;
import function.F1;
import function.F10;
import function.F11;
import function.F12;
import function.F13;
import function.F14;
import function.F15;
import function.F16;
import function.F17;
import function.F18;
import function.F19;
import function.F2;
import function.F20;
import function.F3;
import function.F4;
import function.F5;
import function.F6;
import function.F7;
import function.F8;
import function.F9;
import function.HillFunction;
import function.IFunction;
import function.ShekelFunction;
import model.DisplayOptions;
import model.Model;
import results.Results;
import task.ITask;
import task.IndexTask;
import task.PenaltyTask;
import task.StorageTasks;
import task.Task;
import task.TaskWithLimitations;

public class MainActivity extends AppCompatActivity implements DialogListener, DisplayOptionsListener {
    private Model model;
    private int currentTab;

    private Toolbar toolbar;
    private TabHost tabHost;
    private TabHost.TabSpec tabSpec;
    private List<TabHost.TabSpec> listTabs = new ArrayList<>();
    private int numberTabs = 0;

    private final int REQUEST_CODE_STANDARD_FUNCTION = 1;
    private final int REQUEST_CODE_STANDARD_ALGORITHM = 2;
    private final int REQUEST_CODE_SENSORS = 3;
    private final int REQUEST_CODE_DISPLAY_OPTIONS = 4;
    private final int REQUEST_CODE_RUN_SERIES = 5;
    private final int REQUEST_CODE_SETTINGS_ALGORITHM = 6;
    private final int REQUEST_CODE_TOTAL_RESULTS = 7;
    private final int REQUEST_CODE_SET_LIMITED_FUNCTIONS = 8;

    private static final String DIALOG_NAME_NEW_TAB = "DialogNameNewTab";
    private static final String DIALOG_CLOSE_TAB = "DialogCloseTab";

    private final String TAB_TAG = "tag";
    private boolean canCloseMenuBetweenSteps = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        model = new Model(MainActivity.this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabHost = (TabHost)findViewById(android.R.id.tabhost);
        LocalActivityManager localActivityManager = new LocalActivityManager(this, false);
        localActivityManager.dispatchCreate(savedInstanceState);
        tabHost.setup(localActivityManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (hideMenuItems(menu)) {
            return super.onPrepareOptionsMenu(menu);
        }

        currentTab = tabHost.getCurrentTab();
        MenuItem item;
        if (model.getDisplayOptions().isDrawingFinish()) {
            menu.clear();
            getMenuInflater().inflate(R.menu.menu_main, menu);
            item = menu.findItem(R.id.sub_item_continue_task);
            item.setVisible(false);
            item = menu.findItem(R.id.menu_item_remove_task);
            item.setVisible(true);
            canCloseMenuBetweenSteps = false;
        } else if (model.getDisplayOptions().isDrawing()) {
            menu.clear();
            getMenuInflater().inflate(R.menu.menu_stop_drawing, menu);
            canCloseMenuBetweenSteps = false;
        } else if (model.getDisplayOptions().isCurrentWithStop()) {
            menu.clear();
            if (canCloseMenuBetweenSteps) {
                getMenuInflater().inflate(R.menu.menu_main, menu);
                item = menu.findItem(R.id.sub_item_continue_task);
                item.setVisible(true);
            } else {
                getMenuInflater().inflate(R.menu.menu_between_steps, menu);
            }
        } else if (model.getDisplayOptions().inBeginWithStop()) {
            menu.clear();
            getMenuInflater().inflate(R.menu.menu_between_steps, menu);
            model.getDisplayOptions().setCurrentStopFlag(!model.getDisplayOptions().isCurrentWithStop());
        } else {
            menu.clear();
            getMenuInflater().inflate(R.menu.menu_main, menu);
            item = menu.findItem(R.id.sub_item_continue_task);
            item.setVisible(true);
            item = menu.findItem(R.id.menu_item_remove_task);
            item.setVisible(true);
        }
        setVisibleItemMenu(menu);
        return super.onPrepareOptionsMenu(menu);
    }

    private void setVisibleItemMenu(Menu menu) {
        if (model.getStorageTask(currentTab).getCurrentTask() instanceof TaskWithLimitations) {
            menu.setGroupVisible(R.id.group_unlimited_task, false);
            menu.setGroupVisible(R.id.group_limited_task, true);

            menu.setGroupVisible(R.id.group_unlimited_algorithms, false);
            menu.setGroupVisible(R.id.group_limited_algorithms, true);
        } else {
            menu.setGroupVisible(R.id.group_unlimited_task, true);
            menu.setGroupVisible(R.id.group_limited_task, false);

            menu.setGroupVisible(R.id.group_unlimited_algorithms, true);
            menu.setGroupVisible(R.id.group_limited_algorithms, false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        currentTab = tabHost.getCurrentTab();
        StorageTasks storageTasks = model.getStorageTask(currentTab);

        switch (item.getItemId()) {
            case R.id.menu_item_remove_task:
                createDialogCloseTab();
                break;
            case R.id.menu_item_new_task:
                createDialogNameNewTab();
                break;
            case R.id.sub_item_execute_task:
                actionSubItemExecuteTask();
                break;
            case R.id.sub_item_continue_task:
                actionSubItemContinueTask();
                break;
            case R.id.sub_item_display_tempo:
                model.getDisplayOptions().setListener(null);
                intent = DisplayOptionsActivity.newIntent(MainActivity.this, model.getDisplayOptions());
                startActivityForResult(intent, REQUEST_CODE_DISPLAY_OPTIONS);
                break;
            case R.id.sub_item_execute_serial:
                intent = RunSeriesActivity.newIntent(MainActivity.this);
                startActivityForResult(intent, REQUEST_CODE_RUN_SERIES);
                break;
            case R.id.sub_item_standard_functions:
                intent = StandardFunctionsActivity.newIntent(MainActivity.this);
                startActivityForResult(intent, REQUEST_CODE_STANDARD_FUNCTION);
                break;
            case R.id.sub_item_random_functions:
                createRandomFunction(storageTasks);
                break;
            case R.id.sub_item_standard_algorithms:
                intent = StandardAlgorithmActivity.newIntent(MainActivity.this, storageTasks.getAlgorithm());
                startActivityForResult(intent, REQUEST_CODE_STANDARD_ALGORITHM);
                break;
            case R.id.sub_item_settings:
                intent = SettingsAlgorithmActivity.newIntent(MainActivity.this, storageTasks.getAlgorithm());
                startActivityForResult(intent, REQUEST_CODE_SETTINGS_ALGORITHM);
                break;
            case R.id.sub_item_sensors:
                Settings settings = storageTasks.getSettings();
                intent = SetterSensors.newIntent(MainActivity.this, settings.getCheckedSensors());
                startActivityForResult(intent, REQUEST_CODE_SENSORS);
                break;
            case R.id.sub_item_total_results:
                model.getResults().setNumberCurrentTask(currentTab);
                intent = TotalResultViewerActivity.newIntent(MainActivity.this, model.getResults());
                startActivityForResult(intent, REQUEST_CODE_TOTAL_RESULTS);
                break;
            case R.id.sub_item_operational_characteristics:
                model.getResults().setNumberCurrentTask(currentTab);
                intent = OperationalCharacteristicsActivity.newIntent(MainActivity.this, model.getResults());
                startActivity(intent);
                break;
            case R.id.stopExecute:
                model.stopDrawing();
                invalidateOptionsMenu();
                if (storageTasks.getTaskList().size() > 1) {
                    ITask task = storageTasks.getCurrentTask();
                    storageTasks.clearStorage();
                    storageTasks.addTask(task);
                    model.setStorageTask(currentTab, storageTasks);
                    model.getDisplayOptions().setDrawingFinish(true);
                }
                break;
            case R.id.itemNextStep:
                model.continueDrawing(model.getDisplayOptions().isCurrentWithStop(), currentTab);
                invalidateOptionsMenu();
                break;
            case R.id.itemExecuteWithoutStop:
                model.continueDrawing(!model.getDisplayOptions().isCurrentWithStop(), currentTab);
                invalidateOptionsMenu();
                break;
            case R.id.itemCloseMenu:
                canCloseMenuBetweenSteps = true;
                invalidateOptionsMenu();
                break;
            case R.id.indexAlgorithm:
                final double EPS = 0.001;
                final int NUMBER_ITERATIONS = 200;
                final double PARAMETER = 2.0;
                ITask newTask = new IndexTask(new Settings(EPS, NUMBER_ITERATIONS, PARAMETER));
                storageTasks = new StorageTasks(newTask);
                model.setStorageTask(currentTab, storageTasks);
                break;
            case R.id.penaltyAlgorithm:
                ITask newTask1 = new PenaltyTask(new Settings(0.001, 200, 2.0));
                storageTasks = new StorageTasks(newTask1);
                model.setStorageTask(currentTab, storageTasks);
                break;
            case R.id.item_set_limited_task:
                intent = SetLimitedFunctionsActivity.newIntent(MainActivity.this, storageTasks);
                startActivityForResult(intent, REQUEST_CODE_SET_LIMITED_FUNCTIONS);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        currentTab = tabHost.getCurrentTab();
        if (data == null) {
            return;
        }
        if (resultCode == RESULT_OK) {
            StorageTasks storageTasks = model.getStorageTask(currentTab);
            switch (requestCode) {
                case REQUEST_CODE_DISPLAY_OPTIONS:
                    DisplayOptions displayOptions = DisplayOptionsActivity.getDisplayOptions(data);
                    canCloseMenuBetweenSteps = true;
                    displayOptions.setListener(MainActivity.this);
                    updateMenuItems();
                    model.setDisplayOptions(displayOptions);
                    break;
                case REQUEST_CODE_RUN_SERIES:
                    RunSeriesActivity.TaskType taskType = RunSeriesActivity.getTaskType(data);
                    createSerialTasks(storageTasks, taskType);
                    //model.getDisplayOptions().setStopFlag(false);
                    model.getDisplayOptions().setDrawing(true);
                    model.getDisplayOptions().setDrawingFinish(false);
                    model.setStorageTask(currentTab, storageTasks);
                    model.calculateTask(currentTab);
                    break;
                case REQUEST_CODE_STANDARD_FUNCTION:
                    storageTasks.setMinimizedFunction(StandardFunctionsActivity.getFunction(data));
                    model.setStorageTask(currentTab, storageTasks);
                    break;
                case REQUEST_CODE_STANDARD_ALGORITHM:
                    storageTasks.setAlgorithm(StandardAlgorithmActivity.getAlgorithm(data));
                    model.setStorageTask(currentTab, storageTasks);
                    break;
                case REQUEST_CODE_SETTINGS_ALGORITHM:
                    storageTasks.setAlgorithm(SettingsAlgorithmActivity.getAlgorithm(data));
                    model.setStorageTask(currentTab, storageTasks);
                    break;
                case REQUEST_CODE_SENSORS:
                    storageTasks.getSettings().setCheckedSensors(SetterSensors.getCheckedSensors(data));
                    model.setStorageTask(currentTab, storageTasks);
                    break;
                case REQUEST_CODE_TOTAL_RESULTS:
                    Results results = TotalResultViewerActivity.getResults(data);
                    model.setResults(results);
                    break;
                case REQUEST_CODE_SET_LIMITED_FUNCTIONS:
                    SetLimitedFunctionsActivity.setLimitedFunctions(data, storageTasks);
                    int i = storageTasks.getCurrentLimitationFunctions().size();

                    model.setStorageTask(currentTab, storageTasks);
                    break;
            }
        }
    }

    @Override
    public void createNewTab(boolean isUnlimitedTask, String nameNewTab) {
        final double EPS = 0.01;
        final int NUMBER_ITERATIONS = 200;
        final double PARAMETER = 2.0;

        ITask newTask;
        if (isUnlimitedTask) {
            newTask = new Task(new Settings(EPS, NUMBER_ITERATIONS, PARAMETER));
        } else {
            //newTask = new PenaltyTask(new Settings(EPS, NUMBER_ITERATIONS, PARAMETER));
            newTask = new IndexTask(new Settings(EPS, NUMBER_ITERATIONS, PARAMETER)); //TaskWithLimitations(new Settings(EPS, NUMBER_ITERATIONS, PARAMETER));
        }

        StorageTasks newStorage = new StorageTasks(newTask);
        newStorage.setName(nameNewTab);
        model.addTask(newStorage);

        Intent intent = WorkSpaceActivity.newIntent(MainActivity.this, model);
        tabSpec = tabHost.newTabSpec(TAB_TAG + numberTabs++);
        tabSpec.setIndicator(nameNewTab);
        tabSpec.setContent(intent);
        tabHost.addTab(tabSpec);
        listTabs.add(tabSpec);
        tabHost.setCurrentTab(listTabs.size() - 1);
        currentTab = tabHost.getCurrentTab();
        invalidateOptionsMenu();
    }

    @Override
    public  void closeTab() {
        currentTab = tabHost.getCurrentTab();
        listTabs.remove(currentTab);
        model.removeTask(currentTab);
        tabHost.setCurrentTab(0);
        tabHost.clearAllTabs();

        for (int i = 0; i < listTabs.size(); i++) {
            tabHost.addTab(listTabs.get(i));
            tabHost.setCurrentTab(i);
        }

        if (listTabs.isEmpty()) {
            invalidateOptionsMenu();
            return;
        }
        if (currentTab == listTabs.size()) {
            tabHost.setCurrentTab(currentTab - 1);
        } else {
            tabHost.setCurrentTab(currentTab);
        }
        invalidateOptionsMenu();
    }

    private void actionSubItemContinueTask() {
        model.continueDrawing(model.getDisplayOptions().isCurrentWithStop(), tabHost.getCurrentTab());
        canCloseMenuBetweenSteps = false;
        invalidateOptionsMenu();
    }

    private void actionSubItemExecuteTask() {
        model.getDisplayOptions().setDrawing(true);
        model.getDisplayOptions().setDrawingFinish(false);
        model.getDisplayOptions().setBeginnerStopFlag(model.getDisplayOptions().isCurrentWithStop());
        invalidateOptionsMenu();
        model.calculateTask(currentTab);
    }

    private void createSerialTasks(StorageTasks storageTasks, RunSeriesActivity.TaskType taskType) {
        Settings settings = storageTasks.getSettings();
        storageTasks.clearStorage();
        switch (taskType) {
            case SHEKEL_AND_HILL:
                Random random = new Random();
                for (int i = 0; i < taskType.getCountFunctions(); i++) {
                    if (random.nextInt(1) == 0) {
                        ITask task = new Task(settings);
                        task.setMinimizedFunction(new ShekelFunction());
                        storageTasks.addTask(task);
                    } else {
                        ITask task = new Task(settings);
                        task.setMinimizedFunction(new HillFunction());
                        storageTasks.addTask(task);
                    }
                }
                break;
            case HILL:
                for (int i = 0; i < taskType.getCountFunctions(); i++) {
                    ITask task = new Task(settings);
                    task.setMinimizedFunction(new HillFunction());
                    storageTasks.addTask(task);
                }
                break;
            case SHEKEL:
                for (int i = 0; i < taskType.getCountFunctions(); i++) {
                    ITask task = new Task(settings);
                    task.setMinimizedFunction(new ShekelFunction());
                    storageTasks.addTask(task);
                }
                break;
            case HANSEN:
                for (int i = 0; i < taskType.getCountFunctions(); i++) {
                    ITask task = new Task(settings);
                    task.setMinimizedFunction(getStandardFunctionList().get(i));
                    storageTasks.addTask(task);
                }
                break;
        }
    }

    private ArrayList<IFunction> getStandardFunctionList() {
        ArrayList<IFunction> list = new ArrayList<>();
        list.add(new F1()); list.add(new F2());
        list.add(new F3()); list.add(new F4());
        list.add(new F5()); list.add(new F6());
        list.add(new F7()); list.add(new F8());
        list.add(new F9()); list.add(new F10());
        list.add(new F11());list.add(new F12());
        list.add(new F13());list.add(new F14());
        list.add(new F15());list.add(new F16());
        list.add(new F17());list.add(new F18());
        list.add(new F19());list.add(new F20());

        return list;
    }

    private void createRandomFunction(StorageTasks storageTasks) {
        IFunction function = storageTasks.getMinimizedFunction();
        if (function != null) {
            if (function instanceof ShekelFunction) {
                function = new HillFunction();
            } else {
                function = new ShekelFunction();
            }
        }
        storageTasks.setMinimizedFunction(function);
        model.setStorageTask(currentTab, storageTasks);
    }

    private boolean hideMenuItems(Menu menu) {
        MenuItem item;
        if (listTabs.size() == 0) {
            item = menu.findItem(R.id.menu_item_remove_task);
            item.setVisible(false);
            item = menu.findItem(R.id.menu_item_search);
            item.setVisible(false);
            item = menu.findItem(R.id.menu_item_task);
            item.setVisible(false);
            item = menu.findItem(R.id.menu_item_unlimited_algorithm);
            item.setVisible(false);
            item = menu.findItem(R.id.menu_item_graphics);
            item.setVisible(false);
            item = menu.findItem(R.id.menu_item_results);
            item.setVisible(false);
            item = menu.findItem(R.id.item_set_limited_task);
            item.setVisible(false);
            item = menu.findItem(R.id.menu_item_limited_algorithm);
            item.setVisible(false);
            return true;
        }
        return false;
    }

    private void createDialogNameNewTab() {
        FragmentManager manager = getSupportFragmentManager();
        NameNewTabFragment dialog = new NameNewTabFragment();
        dialog.show(manager, DIALOG_NAME_NEW_TAB);
    }

    private void createDialogCloseTab() {
        FragmentManager manager = getSupportFragmentManager();
        CloseTabFragment dialog = new CloseTabFragment();
        dialog.show(manager, DIALOG_CLOSE_TAB);
    }

    @Override
    public void updateMenuItems() {
        invalidateOptionsMenu();
    }
}

