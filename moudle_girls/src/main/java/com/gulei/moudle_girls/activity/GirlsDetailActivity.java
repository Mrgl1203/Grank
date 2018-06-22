package com.gulei.moudle_girls.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.WindowManager;
import android.widget.TextView;

import com.gulei.common.base.BaseActivity;
import com.gulei.common.view.photoview.Info;
import com.gulei.common.view.photoview.PhotoView;
import com.gulei.moudle_girls.R;
import com.gulei.moudle_girls.adapter.GirlsDetailAdapter;
import com.gulei.moudle_girls.bean.GirlsBean;
import com.gulei.moudle_girls.constant.IntentString;
import com.gulei.moudle_girls.constant.ResultCode;

import java.util.List;

public class GirlsDetailActivity extends BaseActivity {
    List<GirlsBean.ResultsBean> girls;
    int currentPage;
    ViewPager viewPager;
    TextView tvPageNum;
    GirlsDetailAdapter adapter;
    PhotoView photoView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_girls_detail);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        girls = getIntent().getParcelableArrayListExtra(IntentString.GIRLS_DETAIL);
        currentPage = getIntent().getIntExtra(IntentString.GIRLS_CURRENTPAGE, 1);

        tvPageNum = findViewById(R.id.tvPageNum);
        viewPager = findViewById(R.id.viewPager);
        photoView = findViewById(R.id.PhotoView);

        tvPageNum.setText(String.format(getResources().getString(R.string.page_num), currentPage, girls.size()));
        adapter = new GirlsDetailAdapter(girls, this);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(currentPage - 1);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position + 1;
                tvPageNum.setText(String.format(getResources().getString(R.string.page_num), currentPage, adapter.getCount()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        Info info = getIntent().getParcelableExtra(IntentString.IMAGE_INFO);
//        photoView.animaFrom(info);
    }

    @Override
    public void onBackPressed() {
        finishWithResult();
    }

    public void finishWithResult() {
        Intent intent = new Intent();
        intent.putExtra(IntentString.GIRLS_CURRENTPAGE, currentPage);
        setResult(ResultCode.GirlDetail, intent);
        finish();
    }
}
