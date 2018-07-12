package com.gulei.moudle_girls.ui;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.gulei.common.utils.AlertUtil;
import com.gulei.common.utils.GlideApp;
import com.gulei.common.utils.ImageUtils;
import com.gulei.common.utils.ToastUtil;
import com.gulei.common.utils.progressmanager.ProgressListener;
import com.gulei.common.utils.progressmanager.ProgressManager;
import com.gulei.common.utils.progressmanager.body.ProgressInfo;
import com.gulei.common.view.LoadProgressDialog;
import com.gulei.moudle_girls.R;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import java.util.List;

/**
 * Created by gl152 on 2018/7/2.
 */

public class MorePopupWindow extends PopupWindow {
    LinearLayout linearShare, linearDownload;
    LoadProgressDialog loadProgressDialog;
    private Context context;

    public MorePopupWindow(Context context) {
        super(context);
        this.context = context;
        View popView = LayoutInflater.from(context).inflate(R.layout.pop_more, null, false);
        linearShare = popView.findViewById(R.id.linearShare);
        linearDownload = popView.findViewById(R.id.linearDownload);
        setContentView(popView);
//        ViewGroup.LayoutParams lp = popView.getLayoutParams();
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(false);
        this.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_more));
        // 设置PopupWindow是否能响应外部点击事件
        this.setOutsideTouchable(true);
        // 设置PopupWindow是否能响应点击事件
        this.setTouchable(true);
        this.setAnimationStyle(R.style.Morepop);

        loadProgressDialog = new LoadProgressDialog(context);

    }

    public void actionDownload(final String url) {
        linearDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AndPermission.with(context)
                        .runtime()
                        .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .onGranted(new Action<List<String>>() {
                            @Override
                            public void onAction(List<String> data) {
                                //权限通过，下载保存
                                GlideDownLoadListenerProgress(url);
                            }
                        })
                        .onDenied(new Action<List<String>>() {
                            @Override
                            public void onAction(List<String> data) {
                                if (AndPermission.hasAlwaysDeniedPermission(context, data)) {//不再提醒，提示去设置页面
                                    AlertUtil.showAlert(context, "请去设置页面打开权限", "允许", "拒绝", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            AndPermission.with(context).runtime().setting().start();
                                        }
                                    });
                                } else {
                                    ToastUtil.showToast("没有权限无法保存", Toast.LENGTH_LONG);
                                }
                            }
                        }).start();

                dismiss();
            }
        });
    }


    public void actionShare() {
        linearShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showToast("分享", Toast.LENGTH_SHORT);
                dismiss();
            }
        });
    }

    private void GlideDownLoadListenerProgress(final String url) {
        loadProgressDialog.setProgress(0);
        loadProgressDialog.show();
        initProgressListener(url);
        GlideApp.with(context)
                .asBitmap()
                .load(url)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        ImageUtils.saveToImageDir(resource);
                        ToastUtil.showLongToast("保存成功");
                    }
                });
    }

    public void initProgressListener(final String url) {
        ProgressManager.getInstance().setRefreshTime(10);
        ProgressManager.getInstance().addResponseListener(url, new ProgressListener() {
            @Override
            public void onProgress(ProgressInfo progressInfo) {
                loadProgressDialog.setProgress(progressInfo.getPercent());
                if (progressInfo.isFinish()){
                    loadProgressDialog.dismiss();
                }
            }

            @Override
            public void onError(long id, Exception e) {
                loadProgressDialog.dismiss();
                ToastUtil.showToast(e.getMessage(), Toast.LENGTH_SHORT);
            }
        });
    }


}
