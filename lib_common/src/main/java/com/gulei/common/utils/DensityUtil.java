package com.gulei.common.utils;

/**
 * Created by gl152 on 2018/7/3.
 */

public class DensityUtil {
    public static int dip2px(float dpValue) {
        float scale = Utils.getApp().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(float pxValue) {
        final float scale = Utils.getApp().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
