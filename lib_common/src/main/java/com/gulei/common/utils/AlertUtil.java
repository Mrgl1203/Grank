package com.gulei.common.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by gl152 on 2018/7/10.
 */

public class AlertUtil {

    public static void showAlert(Context context, String msg, String posMsg, String negMsg, DialogInterface.OnClickListener posListener) {
        new AlertDialog.Builder(context)
                .setMessage(msg)
                .setPositiveButton(posMsg, posListener)
                .setNegativeButton(negMsg, null)
                .show();
    }
}
