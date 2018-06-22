package com.gulei.moudle_girls.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gulei.common.base.BaseActivity;
import com.gulei.common.utils.ARouterPath;
import com.gulei.common.utils.ErrorBody;
import com.gulei.common.utils.PageBean;
import com.gulei.common.utils.ToastUtil;
import com.gulei.common.view.photoview.Info;
import com.gulei.common.view.photoview.PhotoView;
import com.gulei.moudle_girls.R;
import com.gulei.moudle_girls.adapter.GirlsAdapter;
import com.gulei.moudle_girls.bean.GirlsBean;
import com.gulei.moudle_girls.constant.IntentString;
import com.gulei.moudle_girls.constant.RequestCode;
import com.gulei.moudle_girls.constant.ResultCode;
import com.gulei.moudle_girls.present.GirlsPresent;
import com.gulei.moudle_girls.view.GirlsConstants;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

@Route(path = ARouterPath.girlsPath)
public class GirlsActivity extends BaseActivity implements GirlsConstants.GirlsView<GirlsBean> {
    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private PageBean<GirlsBean.ResultsBean> pageBean = new PageBean<>(10);
    private GirlsPresent present;
    private GirlsAdapter listAdapter;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.girls_girlsactivity);
        present = new GirlsPresent(this);

        mToolbar = findViewById(R.id.ToolBar);
        initToolBar();


        refreshLayout = findViewById(R.id.refreshLayout);
        recyclerView = findViewById(R.id.recyclerView);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        listAdapter = new GirlsAdapter();
        recyclerView.setAdapter(listAdapter);

        refreshLayout.autoRefresh();
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                present.getGirlsData(pageBean, true);
            }
        });

        listAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                present.getGirlsData(pageBean, false);
            }
        }, recyclerView);


        listAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<GirlsBean.ResultsBean> resultsBean = listAdapter.getData();
                PhotoView iv = (PhotoView) adapter.getViewByPosition(position, R.id.ivImage);
                Info info = iv.getInfo();
                iv.animaFrom(info);
                Intent intent = new Intent(GirlsActivity.this, GirlsDetailActivity.class);
                intent.putExtra(IntentString.GIRLS_CURRENTPAGE, position + 1);
                intent.putParcelableArrayListExtra(IntentString.GIRLS_DETAIL, (ArrayList<? extends Parcelable>) resultsBean);
                intent.putExtra(IntentString.IMAGE_INFO, info);
                startActivityForResult(intent, RequestCode.GirlDetail);
            }
        });
        bindPresent(present);
    }

    private void initToolBar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    public void onSuccess(GirlsBean girlsBean) {
        listAdapter.addData(girlsBean.getResults());
        refreshLayout.finishRefresh();
        if (!pageBean.hasNextPage()) {
            listAdapter.loadMoreEnd(false);
        } else {
            listAdapter.loadMoreComplete();
        }
    }

    @Override
    public void onFailed(ErrorBody errorBody) {
        ToastUtil.showToast(errorBody.getErrorMessage(), Toast.LENGTH_SHORT);
        refreshLayout.finishRefresh(false);
        listAdapter.loadMoreFail();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCode.GirlDetail && resultCode == ResultCode.GirlDetail) {
            int currentPage = data.getIntExtra(IntentString.GIRLS_CURRENTPAGE, 0);
            recyclerView.scrollToPosition(currentPage - 1);
        }
    }
}
