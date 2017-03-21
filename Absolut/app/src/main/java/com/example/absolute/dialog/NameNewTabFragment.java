package com.example.absolute.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.absolute.MainActivity;
import com.example.absolute.R;

public class NameNewTabFragment extends DialogFragment {
    private EditText tabNameET;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private DialogListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement DialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_name_new_tab, null);

        tabNameET = (EditText) view.findViewById(R.id.experimentName);
        radioGroup = (RadioGroup) view.findViewById(R.id.select_taskType_radioGroup);
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.titleDialogNameNewTab)
                .setMessage(R.string.messageDialogNameNewTab)
                .setPositiveButton(R.string.select, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                        radioButton = (RadioButton)view.findViewById(checkedRadioButtonId);
                        int indexRadioButton = radioGroup.indexOfChild(radioButton);
                        if (indexRadioButton == 0) {
                            listener.createNewTab(true, tabNameET.getText().toString());
                        } else {
                            listener.createNewTab(false, tabNameET.getText().toString());
                        }
                        /*if (getActivity() instanceof MainActivity) {
                            MainActivity activity = (MainActivity) getActivity();
                            activity.createNewTab(tabNameET.getText().toString());
                        }*/
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .create();
    }
}
