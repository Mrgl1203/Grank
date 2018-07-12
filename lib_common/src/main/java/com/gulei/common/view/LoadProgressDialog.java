package com.gulei.common.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.gulei.lib_common.R;

/**
 * Created by gl152 on 2018/7/3.
 */

public class LoadProgressDialog {
    private Dialog dialog;
    private View view;
    private Context context;
    private TextProgress textProgress;

    public LoadProgressDialog(Context context) {
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.dialog_loadprogress, null, false);
        textProgress = view.findViewById(R.id.textProgress);

        dialog = new Dialog(context, R.style.dialogNoBg);
        dialog.setContentView(view);
        dialog.setCancelable(false);

        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dismiss();
                }
                return false;
            }
        });
    }

    public void setProgress(int progress) {
        textProgress.setProgress(progress);
    }

    public void show() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
