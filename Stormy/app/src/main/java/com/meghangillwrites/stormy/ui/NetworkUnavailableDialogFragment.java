package com.meghangillwrites.stormy.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.meghangillwrites.stormy.R;

public class NetworkUnavailableDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Context context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.network_error_title)
        .setMessage(R.string.network_message)
        .setPositiveButton(R.string.network_OK_btn, null);

        return builder.create();
    }
}
