package com.example.absolute.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.example.absolute.MainActivity;
import com.example.absolute.R;

public class CloseTabFragment extends DialogFragment {
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
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.titleDialogCloseTab)
                .setMessage(R.string.messageDialogCloseTab)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.closeTab();
                        /*if (getActivity() instanceof MainActivity) {
                            MainActivity activity = (MainActivity) getActivity();
                            activity.closeTab();
                        }*/
                    }
                })
                .setNegativeButton(R.string.no, null)
                .create();
    }
}
