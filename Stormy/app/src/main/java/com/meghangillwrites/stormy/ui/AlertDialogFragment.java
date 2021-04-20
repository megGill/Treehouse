package com.meghangillwrites.stormy.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.meghangillwrites.stormy.R;

public class AlertDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        /*We can't use 'this' or Activity.this
        // bc we're not in MainActivity. The DialogFragment class that we are extending
        has a useful method to get the activity where this dialog fragment was created,
        i.e. getActivity() That will give us the activity and context we need
        */
        Context context = getActivity();
        /* Our data type is AlertDialog.Builder. Builder is a nested class inside the
        AlertDialog class. That's due to the Factory Method Pattern

         */
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.error_title)
        .setMessage(R.string.error_message)
        .setPositiveButton(R.string.error_button_ok_text, null);
        /*To create a message use builder.setMessage() or chain methods together
        A dialog can have up to 3 buttons: a positive button, a negative button and a neutral
        button. For each btn we set the text and the onClickListener. Here we put null bc
        we don't want to do anything specific when the button is tapped. A null onClickListener
        will just close the dialog. Now that our dialog is defined we have to create and return it.
         */
        return builder.create();
    }
}
