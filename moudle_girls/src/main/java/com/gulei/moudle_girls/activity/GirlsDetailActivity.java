package com.gulei.moudle_girls.activity;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.gulei.common.base.BaseActivity;
import com.gulei.common.utils.DensityUtil;
import com.gulei.common.utils.GlideApp;
import com.gulei.common.view.photoview.Info;
import com.gulei.common.view.photoview.PhotoView;
import com.gulei.moudle_girls.R;
import com.gulei.moudle_girls.adapter.GirlsDetailAdapter;
import com.gulei.moudle_girls.bean.EventType;
import com.gulei.moudle_girls.bean.GirlsBean;
import com.gulei.moudle_girls.constant.IntentString;
import com.gulei.moudle_girls.ui.MorePopupWindow;
import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class GirlsDetailActivity extends BaseActivity implements View.OnClickListener {

    private ViewPager viewPager;
    private ConstraintLayout constraintLayout;
    private TextView tvPageNum;
    private PhotoView transView;
    private ImageView ivMore;
    List<GirlsBean.ResultsBean> girls;
    int currentPage;
    GirlsDetailAdapter adapter;

    AlphaAnimation alphaUp;
    AlphaAnimation alphaDowm;
    private long AnimationDuration = 300;
    MorePopupWindow popupWindow;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar = ImmersionBar.with(this)
                .hideBar(BarHide.FLAG_HIDE_STATUS_BAR);
        mImmersionBar.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_girls_detail;
    }

    private void initView() {
        constraintLayout = findViewById(R.id.contentView);
        tvPageNum = findViewById(R.id.tvPageNum);
        viewPager = findViewById(R.id.viewPager);
        ivMore = findViewById(R.id.ivMore);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initView();
        girls = getIntent().getParcelableArrayListExtra(IntentString.GIRLS_DETAIL);
        currentPage = getIntent().getIntExtra(IntentString.GIRLS_CURRENTPAGE, 1);

        ivMore.setOnClickListener(this);
        constraintLayout.setVisibility(View.GONE);
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
                EventBus.getDefault().post(new EventType.PageSelected(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        Info info = getIntent().getParcelableExtra(IntentString.IMAGE_INFO);
//        Logger.e("图片信息：" + info.toString());
//        alphaUp = new AlphaAnimation(0f, 1.0f);
//        alphaUp.setDuration(AnimationDuration);
        alphaDowm = new AlphaAnimation(1.0f, 0f);
        alphaDowm.setDuration(AnimationDuration + 100);

        transView = findViewById(R.id.transView);
        transView.setAnimaDuring((int) AnimationDuration);//设置 动画持续时间
        String imgUrl = girls.get(currentPage - 1).getUrl();
        GlideApp.with(this)
                .load(imgUrl)
//                .thumbnail(0.2f)不要给缩略图，影响photoView获取图片宽高导致动画变形
                .into(transView);
        transView.animaFrom(info);
        transView.postDelayed(new Runnable() {
            @Override
            public void run() {
                constraintLayout.setVisibility(View.VISIBLE);
                transView.setVisibility(View.GONE);
            }
        }, AnimationDuration);

        popupWindow = new MorePopupWindow(this);
    }


    @Override
    public void onBackPressed() {
        Back();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getInfo(EventType.GetInfo getInfo) {
        constraintLayout.startAnimation(alphaDowm);
        transView.setVisibility(View.VISIBLE);
        GlideApp.with(this)
                .asBitmap()
                .load(girls.get(currentPage - 1).getUrl())
                .into(transView);
        transView.animaTo(getInfo.info, new Runnable() {
            @Override
            public void run() {
                finish();
                overridePendingTransition(0, 0);
            }
        });
    }

    public void Back() {
        EventBus.getDefault().post(new EventType.BackInfo(currentPage - 1));
    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.ivMore) {
            popupWindow.actionDownload(girls.get(currentPage - 1).getUrl());
            popupWindow.actionShare();
            popupWindow.showAsDropDown(ivMore, DensityUtil.dip2px(-60), DensityUtil.dip2px(5));
        }
    }
}
