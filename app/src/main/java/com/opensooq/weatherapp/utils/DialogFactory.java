package com.opensooq.weatherapp.utils;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.opensooq.weatherapp.R;

/**
 * Created by a7mad on 6/21/2017.
 */

public class DialogFactory {

    private DialogFactory() {
        /*to hide implicit public constructor*/
    }

    public static Dialog createRemoveAlertDialog(Context context, String message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.dialog_remove_location_title))
                .setMessage(message)
                .setPositiveButton(R.string.dialog_action_continue, null);

        return alertDialog.create();
    }

}
