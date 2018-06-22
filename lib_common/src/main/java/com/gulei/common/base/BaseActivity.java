package com.gulei.common.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;


public class BaseActivity extends AppCompatActivity {
    List<BasePresent> basePresentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewManager.getInstance().addActivity(this);
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ViewManager.getInstance().finishActivity(this);
        unBindPresent();
    }
}
