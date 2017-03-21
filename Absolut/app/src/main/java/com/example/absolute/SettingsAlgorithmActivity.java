package com.example.absolute;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import algorithm.IAlgorithm;
import algorithm.Settings;

public class SettingsAlgorithmActivity extends AppCompatActivity {
    private static final String EXTRA_SETTINGS_ALGORITHM = "com.example.absolute.settings_algorithm";
    private IAlgorithm algorithm;
    private AlgorithmType algorithmType;
    private Settings settings;
    private Button buttonSelectSetting, buttonCancelSetting;
    private Toolbar toolbar;

    private enum AlgorithmType {
        BAGS, BAGS_INSIDE_INTERVAL, BAGS_WITH_LOCAL_SETTING,
        KOUSHNER, BAGS_LOCALLY_ADAPTIVE, BAGS_MIX,
        BAGS_MONOTONIC, POLYGONAL_METHOD, RANDOM_SEARCH, SEQUENTAL_SCAN
    }

    private abstract class ItemSetting {
        protected TextView titleTV;
        protected TextView valueTV;
        protected SeekBar mSeekBar;
        protected View item;
        protected LinearLayout itemLinearLayout;
        private LayoutInflater layoutInflater;

        public ItemSetting() {
            itemLinearLayout = (LinearLayout)findViewById(R.id.itemLinearLayout);
            layoutInflater = getLayoutInflater();
            item = layoutInflater.inflate(R.layout.item_settings, itemLinearLayout, false);
            titleTV = (TextView) item.findViewById(R.id.textViewItemTitle);
            valueTV = (TextView) item.findViewById(R.id.textViewItemValue);
            mSeekBar = (SeekBar) item.findViewById(R.id.seekBar);
        }

        public abstract void createSetting();

        protected void setWidthView() {
            item.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            itemLinearLayout.addView(item);
        }

        protected double roundNumber(double accuracy, int roundMark) {
            accuracy *= roundMark;
            accuracy = Math.round(accuracy);
            accuracy /= roundMark;
            return accuracy;
        }
    }

    private class ItemNumberIterationsSetting extends ItemSetting {
        private final int MIN_NUMBER_ITERATIONS = 10;
        private final int MAX_NUMBER_ITERATIONS = 300;
        private int numberIterations;

        public ItemNumberIterationsSetting(int startNumberIterations) {
            numberIterations = startNumberIterations;
        }

        @Override
        public void createSetting() {
            titleTV.setText(R.string.titleSettingsNumberIterations);
            valueTV.setText(String.valueOf(settings.getNumberIterations()));
            mSeekBar.setMax(MAX_NUMBER_ITERATIONS - MIN_NUMBER_ITERATIONS);
            mSeekBar.setProgress(settings.getNumberIterations() - MIN_NUMBER_ITERATIONS);
            mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    numberIterations = progress + MIN_NUMBER_ITERATIONS;
                    valueTV.setText(String.valueOf(numberIterations));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            setWidthView();
        }
    }

    private class ItemAccuracySetting extends ItemSetting {
        private final double LEFT_POINT_OF_RANGE = -6.0;
        private final int NUMBER_INTERVALS = 5;
        private final int MAX_VALUE_SEEK_BAR = 25;
        private final int LOGARITHM_BASE = 10;
        private double accuracy;

        public ItemAccuracySetting(double startAccuracy) {
            accuracy = startAccuracy;
        }

        @Override
        public void createSetting() {
            titleTV.setText(R.string.titleSettingsAccuracy);
            valueTV.setText(String.valueOf(settings.getEps()));
            mSeekBar.setMax(MAX_VALUE_SEEK_BAR);
            mSeekBar.setProgress((int)((Math.log10(accuracy) - LEFT_POINT_OF_RANGE) * MAX_VALUE_SEEK_BAR / NUMBER_INTERVALS));
            mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    accuracy = Math.pow(LOGARITHM_BASE, ((double) (progress * NUMBER_INTERVALS) / MAX_VALUE_SEEK_BAR) + LEFT_POINT_OF_RANGE);
                    int roundMark = 1000000 / (int) Math.pow(LOGARITHM_BASE, (progress * NUMBER_INTERVALS) / MAX_VALUE_SEEK_BAR);
                    accuracy = roundNumber(accuracy, roundMark);
                    valueTV.setText(String.valueOf(accuracy));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            setWidthView();
        }
    }

    private class ItemParameterSetting extends ItemSetting {
        private final int MAX_VALUE_PARAMETER_POLYGONAL_METHOD = 10000;
        private final int MIN_VALUE_PARAMETER_POLYGONAL_METHOD = 1;
        private final int MAX_VALUE_PARAMETER_BAGS = 100;
        private final int MIN_VALUE_PARAMETER_BAGS = 11;
        private final double LEFT_POINT_OF_RANGE = -6;
        private final int NUMBER_INTERVALS = 6;
        private final int LOGARITHM_BASE = 10;
        private double parameter;

        public ItemParameterSetting(double startParameter) {
            parameter = startParameter;
        }

        private void createParameterItem() {
            switch (algorithmType) {
                case POLYGONAL_METHOD:
                    createParameterForPolygonalMethod();
                    break;
                case KOUSHNER:
                    createParameterForKoushnerMethod();
                    break;
                case BAGS:
                case BAGS_INSIDE_INTERVAL:
                case BAGS_LOCALLY_ADAPTIVE:
                case BAGS_MIX:
                case BAGS_MONOTONIC:
                case BAGS_WITH_LOCAL_SETTING:
                    createParameterForOtherMethods();
                    break;
            }
        }

        private void createParameterForPolygonalMethod() {
            titleTV.setText(R.string.titleSettingParameterPolygonal);
            mSeekBar.setMax(MAX_VALUE_PARAMETER_POLYGONAL_METHOD - MIN_VALUE_PARAMETER_POLYGONAL_METHOD);
            mSeekBar.setProgress((int)parameter - MIN_VALUE_PARAMETER_POLYGONAL_METHOD);
        }

        private void createParameterForKoushnerMethod() {
            final int MAX_VALUE_SEEK_BAR = 66;
            titleTV.setText(R.string.titleSettingParameterKoushner);
            mSeekBar.setMax(MAX_VALUE_SEEK_BAR);
            mSeekBar.setProgress((int)((Math.log10(parameter) - LEFT_POINT_OF_RANGE) * MAX_VALUE_SEEK_BAR / NUMBER_INTERVALS));
        }

        private void createParameterForOtherMethods() {
            titleTV.setText(R.string.titleSettingParameterOtherMethods);
            mSeekBar.setMax(MAX_VALUE_PARAMETER_BAGS - MIN_VALUE_PARAMETER_BAGS);
            mSeekBar.setProgress((int)(parameter * LOGARITHM_BASE - MIN_VALUE_PARAMETER_BAGS));
        }

        @Override
        public void createSetting() {
            createParameterItem();
            valueTV.setText(String.valueOf(parameter));
            mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    switch (algorithmType) {
                        case POLYGONAL_METHOD:
                            parameter = ++progress;
                            valueTV.setText(String.valueOf(parameter));
                            break;
                        case KOUSHNER:
                            parameter = Math.pow(LOGARITHM_BASE, ((double) (progress * NUMBER_INTERVALS) / mSeekBar.getMax()) + LEFT_POINT_OF_RANGE);
                            valueTV.setText(String.valueOf(parameter));
                            break;
                        case BAGS:
                        case BAGS_INSIDE_INTERVAL:
                        case BAGS_LOCALLY_ADAPTIVE:
                        case BAGS_MIX:
                        case BAGS_MONOTONIC:
                        case BAGS_WITH_LOCAL_SETTING:
                            parameter = (double)(progress + 1) / 10 + 1;
                            valueTV.setText(String.valueOf(parameter));
                            break;
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            setWidthView();
        }
    }

    private class ItemStepTurnOnMix extends ItemSetting {
        private final int MAX_VALUE_STEP_TURN_ON_MIX = 300;
        private final int MIN_VALUE_STEP_TURN_ON_MIX = 1;
        private int stepTurnOnMix;

        public ItemStepTurnOnMix(int startStepTurnOnMix) {
            stepTurnOnMix = startStepTurnOnMix;
        }

        private void createTitle() {
            switch (algorithmType) {
                case BAGS_LOCALLY_ADAPTIVE:
                    titleTV.setText(R.string.titleSettingStepTurnOnMixLocallyAdaptive);
                    break;
                case BAGS_MIX:
                    titleTV.setText(R.string.titleSettingStepTurnOnMix);
                    break;
            }
        }

        @Override
        public void createSetting() {
            createTitle();
            valueTV.setText(String.valueOf(stepTurnOnMix));
            mSeekBar.setMax(MAX_VALUE_STEP_TURN_ON_MIX - MIN_VALUE_STEP_TURN_ON_MIX);
            mSeekBar.setProgress(stepTurnOnMix - MIN_VALUE_STEP_TURN_ON_MIX);
            mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    stepTurnOnMix = ++progress;
                    valueTV.setText(String.valueOf(stepTurnOnMix));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            setWidthView();
        }
    }

    private class ItemDegreeLocally extends ItemSetting {
        final int MAX_VALUE_DEGREE_LOCALLY = 9;
        final int MIN_VALUE_DEGREE_LOCALLY = 1;
        int degreeLocally;

        public ItemDegreeLocally(int startDegreeLocally) {
            degreeLocally = startDegreeLocally;
        }

        @Override
        public void createSetting() {
            titleTV.setText(R.string.titleSettingDegreeLocally);
            valueTV.setText(String.valueOf(settings.getDegreeLocally()));
            mSeekBar.setMax(MAX_VALUE_DEGREE_LOCALLY - MIN_VALUE_DEGREE_LOCALLY);
            mSeekBar.setProgress(degreeLocally - MIN_VALUE_DEGREE_LOCALLY);
            mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    degreeLocally = ++progress;
                    valueTV.setText(String.valueOf(degreeLocally));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            item.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            itemLinearLayout.addView(item);
        }
    }

    private class ItemPortionGlobalIterations extends ItemSetting {
        private final int MAX_VALUE = 20;
        private final int MIDDLE_VALUE = MAX_VALUE / 2;
        private int numberGlobalSteps;
        private int numberLocalSteps;

        public ItemPortionGlobalIterations(int startNumberGlobalSteps, int startNumberLocalSteps) {
            numberGlobalSteps = startNumberGlobalSteps;
            numberLocalSteps = startNumberLocalSteps;
        }

        @Override
        public void createSetting() {
            titleTV.setText(R.string.titleSettingPortionGlobalIterations);
            valueTV.setText(String.valueOf(numberGlobalSteps)
                    + ":" +
                    String.valueOf(numberLocalSteps));
            mSeekBar.setMax(MAX_VALUE);

            if (settings.getNumberGlobalSteps() > 1) {
                mSeekBar.setProgress(MIDDLE_VALUE - numberGlobalSteps);
            } else if (settings.getNumberLocalSteps() > 1) {
                mSeekBar.setProgress(MIDDLE_VALUE + numberLocalSteps);
            } else {
                mSeekBar.setProgress(MIDDLE_VALUE);
            }
            mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (progress > MIDDLE_VALUE) {
                        numberGlobalSteps = 1;
                        numberLocalSteps = progress - MIDDLE_VALUE;
                        valueTV.setText(String.valueOf(numberGlobalSteps)
                                + ":" +
                                String.valueOf(numberLocalSteps));
                    } else if (progress < MIDDLE_VALUE) {
                        numberGlobalSteps = MIDDLE_VALUE - progress;
                        numberLocalSteps = 1;
                        valueTV.setText(String.valueOf(numberGlobalSteps)
                                + ":" +
                                String.valueOf(numberLocalSteps));
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            setWidthView();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_algorithm);

        toolbar = (Toolbar)findViewById(R.id.settings_algorithm_toolbar);
        toolbar.setSubtitle(R.string.algorithmParameters);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        algorithm = (IAlgorithm)intent.getSerializableExtra(EXTRA_SETTINGS_ALGORITHM);
        setAlgorithmType();
        settings = algorithm.getSettings();

        buttonCancelSetting = (Button)findViewById(R.id.buttonCancelSettings);
        buttonCancelSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final ItemNumberIterationsSetting settingNumberIteration = new ItemNumberIterationsSetting(settings.getNumberIterations());
        settingNumberIteration.createSetting();

        final ItemAccuracySetting settingAccuracy = new ItemAccuracySetting(settings.getEps());
        settingAccuracy.createSetting();

        final ItemParameterSetting settingParameter = new ItemParameterSetting(settings.getParameter());
        final ItemStepTurnOnMix settingStepTurnOnMix = new ItemStepTurnOnMix(settings.getStepTurnOnMix());
        final ItemDegreeLocally settingDegreeLocally = new ItemDegreeLocally(settings.getDegreeLocally());
        final ItemPortionGlobalIterations settingPortionGlobalSteps=
                new ItemPortionGlobalIterations(settings.getNumberGlobalSteps(), settings.getNumberLocalSteps());
        switch (algorithmType) {
            case POLYGONAL_METHOD:
            case KOUSHNER:
            case BAGS:
            case BAGS_MONOTONIC:
            case BAGS_WITH_LOCAL_SETTING:
                settingParameter.createSetting();
                break;
            case BAGS_LOCALLY_ADAPTIVE:
                settingParameter.createSetting();
                settingStepTurnOnMix.createSetting();
                settingDegreeLocally.createSetting();
                break;
            case BAGS_MIX:
                settingParameter.createSetting();
                settingPortionGlobalSteps.createSetting();
                settingStepTurnOnMix.createSetting();
                break;
        }

        buttonSelectSetting = (Button)findViewById(R.id.buttonSelectSettings);
        buttonSelectSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings.setNumberIterations(settingNumberIteration.numberIterations);
                settings.setAccuracy(settingAccuracy.accuracy);
                settings.setParameterMethod(settingParameter.parameter);
                settings.setStepTurnOnMix(settingStepTurnOnMix.stepTurnOnMix);
                settings.setDegreeLocally(settingDegreeLocally.degreeLocally);
                settings.setNumberGlobalSteps(settingPortionGlobalSteps.numberGlobalSteps);
                settings.setNumberLocalSteps(settingPortionGlobalSteps.numberLocalSteps);
                algorithm.setSettings(settings);

                Intent intent = new Intent();
                intent.putExtra(EXTRA_SETTINGS_ALGORITHM, algorithm);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    public static Intent newIntent(Context packageContext, IAlgorithm algorithm) {
        Intent intent = new Intent(packageContext, SettingsAlgorithmActivity.class);
        intent.putExtra(EXTRA_SETTINGS_ALGORITHM, algorithm);
        return intent;
    }

    public static IAlgorithm getAlgorithm(Intent result) {
        return (IAlgorithm)result.getSerializableExtra(EXTRA_SETTINGS_ALGORITHM);
    }

    private void setAlgorithmType() {
        switch (algorithm.toString()) {
            case "SequentalScan":
                algorithmType = AlgorithmType.SEQUENTAL_SCAN;
                break;
            case "Koushner":
                algorithmType = AlgorithmType.KOUSHNER;
                break;
            case "PolygonalMethod":
                algorithmType = AlgorithmType.POLYGONAL_METHOD;
                break;
            case "RandomSearch":
                algorithmType = AlgorithmType.RANDOM_SEARCH;
                break;
            case "BAGS":
                algorithmType = AlgorithmType.BAGS;
                break;
            case "BAGS_InsideInterval":
                algorithmType = AlgorithmType.BAGS_INSIDE_INTERVAL;
                break;
            case "BAGS_WithLocalSettings":
                algorithmType = AlgorithmType.BAGS_WITH_LOCAL_SETTING;
                break;
            case "Mix_BAGS":
                algorithmType = AlgorithmType.BAGS_MIX;
                break;
            case "Monotonic_BAGS":
                algorithmType = AlgorithmType.BAGS_MONOTONIC;
                break;
            case "LocallyAdaptive_BAGS":
                algorithmType = AlgorithmType.BAGS_LOCALLY_ADAPTIVE;
                break;
        }
    }
}
