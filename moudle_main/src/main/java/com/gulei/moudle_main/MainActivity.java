package com.gulei.moudle_main;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.gulei.common.base.BaseActivity;
import com.gulei.common.utils.ARouterPath;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
