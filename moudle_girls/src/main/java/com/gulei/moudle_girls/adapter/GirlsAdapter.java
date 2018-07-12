package com.gulei.moudle_girls.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gulei.common.utils.GlideApp;
import com.gulei.moudle_girls.R;
import com.gulei.moudle_girls.bean.GirlsBean;
import com.gulei.moudle_girls.constant.PlaceHolderImg;

/**
 * Created by gl152 on 2018/6/15.
 */

public class GirlsAdapter extends BaseQuickAdapter<GirlsBean.ResultsBean, BaseViewHolder> {

    public GirlsAdapter() {
        super(R.layout.girls_adapter_item, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, GirlsBean.ResultsBean item) {
        ImageView imageView = helper.getView(R.id.ivImage);
        GlideApp.with(imageView.getContext())
                .load(item.getUrl())
                .placeholder(PlaceHolderImg.getDefaultImg())
                .error(PlaceHolderImg.getDefaultImg())
                .into(imageView);
    }
}
