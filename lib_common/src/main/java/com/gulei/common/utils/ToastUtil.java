package com.gulei.common.utils;

import android.widget.Toast;

import com.gulei.common.base.BaseApplication;

/**
 * Created by gl152 on 2018/6/13.
 */

public class ToastUtil {
    private static Toast toast;

    public static void showToast(String msg, int duration) {
        if (toast == null) {
            toast = Toast.makeText(BaseApplication.getContext(), msg, duration);
        }
        toast.setDuration(duration);
        toast.setText(msg);
        toast.show();
    }

    public static void showToast(int res, int duration) {
        if (toast == null) {
            toast = Toast.makeText(BaseApplication.getContext(), res, duration);
        }
        toast.setDuration(duration);
        toast.setText(res);
        toast.show();
    }

    public static void cancelToast() {
        if (toast != null) {
            toast.cancel();
        }
    }

}
