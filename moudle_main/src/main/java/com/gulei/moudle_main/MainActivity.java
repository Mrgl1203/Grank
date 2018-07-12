package com.gulei.moudle_main;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.gulei.common.base.BaseActivity;
import com.gulei.common.utils.ARouterPath;
import com.gyf.barlibrary.ImmersionBar;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar = ImmersionBar.with(this)
                .statusBarColor(R.color.colorPrimary)
                .statusBarView(R.id.view);
        mImmersionBar.init();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        findViewById(R.id.news_button).setOnClickListener(this);
        findViewById(R.id.girls_button).setOnClickListener(this);
        findViewById(R.id.fragment_button).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.news_button) {
            ARouter.getInstance().build(ARouterPath.newsPath).navigation();
        } else if (view.getId() == R.id.girls_button) {
            ARouter.getInstance().build(ARouterPath.girlsPath).navigation();
        } else if (view.getId() == R.id.fragment_button) {

        }
    }
}
