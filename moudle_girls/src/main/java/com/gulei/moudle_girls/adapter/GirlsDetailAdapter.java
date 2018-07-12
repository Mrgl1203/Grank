package com.gulei.moudle_girls.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gulei.common.utils.GlideApp;
import com.gulei.common.view.photoview.PhotoView;
import com.gulei.moudle_girls.R;
import com.gulei.moudle_girls.bean.GirlsBean;

import java.util.List;

/**
 * Created by gl152 on 2018/6/20.
 */

public class GirlsDetailAdapter extends PagerAdapter {
    List<GirlsBean.ResultsBean> girls;
    private Context context;
    private ViewGroup mCurrentView;
    private PhotoView currentPhotoView;

    public GirlsDetailAdapter(List<GirlsBean.ResultsBean> girls, Context context) {
        this.girls = girls;
        this.context = context;
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.setPrimaryItem(container, position, object);
        //获取当前展示的view
        mCurrentView = (ViewGroup) object;
        int childCount = mCurrentView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = mCurrentView.getChildAt(i);
            if (childView instanceof PhotoView) {
                currentPhotoView = (PhotoView) childView;
            }
        }
    }

    public ViewGroup getPrimaryItem() {
        return mCurrentView;
    }

    public PhotoView getCurrentPhotoView() {
        return currentPhotoView;
    }

    @Override
    public int getCount() {
        return girls.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.girls_detail_item, container, false);
        PhotoView photoView = view.findViewById(R.id.PhotoView);
        photoView.enable();
        GlideApp.with(context)
                .load(girls.get(position).getUrl())
                .into(photoView);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }


}
