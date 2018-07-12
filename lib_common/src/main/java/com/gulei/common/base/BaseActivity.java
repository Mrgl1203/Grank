package com.gulei.common.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseActivity extends AppCompatActivity {
    List<BasePresent> basePresentList = new ArrayList<>();
    protected ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        EventBus.getDefault().register(this);
        ViewManager.getInstance().addActivity(this);
        init(savedInstanceState);
        //初始化沉浸式
        if (isImmersionBarEnabled())
            initImmersionBar();
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void init(Bundle savedInstanceState);

    protected void initImmersionBar() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
    }

    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    public void bindPresent(BasePresent... basePresents) {
        for (int i = 0; i < basePresents.length; i++) {
            if (!basePresentList.contains(basePresents[i])) {
                basePresentList.add(basePresents[i]);
            }
        }
    }

    //Activity销毁时自动解除绑定
    public void unBindPresent() {
        for (int i = 0; i < basePresentList.size(); i++) {
            basePresentList.get(i).onDestory();
        }
        basePresentList.clear();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventString(String event) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        ViewManager.getInstance().finishActivity(this);
        unBindPresent();
        if (mImmersionBar != null)
            mImmersionBar.destroy(); //在BaseActivity里销毁
    }
}
