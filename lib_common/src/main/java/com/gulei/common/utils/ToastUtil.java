package com.gulei.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import com.gulei.common.base.BaseApplication;

/**
 * Created by gl152 on 2018/6/13.
 */

public class ToastUtil {
    private static Toast toast;
    private static Context context = BaseApplication.getContext();

    static {
        checkNotNull("", Toast.LENGTH_LONG);
    }

    @SuppressLint("ShowToast")
    private static void checkNotNull(String msg, int duration) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, duration);
        }
    }

    public static void showToast(String msg, int duration) {
        toast.setDuration(duration);
        toast.setText(msg);
        toast.show();
    }

    public static void showLongToast(String msg) {
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setText(msg);
        toast.show();
    }

    public static void showShortToast(String msg) {
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.show();
    }

    public static void cancelToast() {
        if (toast != null) {
            toast.cancel();
        }
    }

}
