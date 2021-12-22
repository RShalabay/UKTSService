package kz.ukteplo.uktsrepairs.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import kz.ukteplo.uktsrepairs.MainActivity;
import kz.ukteplo.uktsrepairs.utils.FragmentDataListener;
import kz.ukteplo.uktsrepairs.utils.OnDialogClickListener;

public class AlertDialog extends DialogFragment implements DialogInterface.OnClickListener {
    private FragmentDataListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (FragmentDataListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        String title = getArguments().getString("title");
        String msg = getArguments().getString("msg");
        String tag = getTag();
        builder.setMessage(msg)
                .setTitle(title)
                .setPositiveButton("OK", this)
                .setNegativeButton("Отмена", this);
        builder.setCancelable(true);
        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case Dialog.BUTTON_POSITIVE:
                listener.alert(true);
            case Dialog.BUTTON_NEGATIVE:
                listener.alert(false);
                break;
        }
        dialog.cancel();
    }
}
