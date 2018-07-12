package com.gulei.moudle_news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gulei.common.base.BaseActivity;
import com.gulei.common.utils.ARouterPath;

@Route(path = ARouterPath.newsPath)
public class NewsActivity extends BaseActivity {
    ImageView iv;

    @Override
    protected int getLayoutId() {
        return R.layout.news_newsactivity;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        iv = findViewById(R.id.iv);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewsActivity.this, NewsDetailActivity.class);
                startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(NewsActivity.this, iv, "iv").toBundle());
            }
        });
    }
}
