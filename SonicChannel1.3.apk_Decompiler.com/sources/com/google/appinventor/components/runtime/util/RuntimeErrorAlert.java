package com.google.appinventor.components.runtime.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

public final class RuntimeErrorAlert {
    public static void alert(final Object context, String message, String title, String buttonText) {
        Class<RuntimeErrorAlert> cls = RuntimeErrorAlert.class;
        Log.i("RuntimeErrorAlert", "in alert");
        AlertDialog alertDialog = new AlertDialog.Builder((Context) context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(buttonText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ((Activity) context).finish();
            }
        });
        if (message == null) {
            Log.e(cls.getName(), "No error message available");
        } else {
            Log.e(cls.getName(), message);
        }
        alertDialog.show();
    }
}
