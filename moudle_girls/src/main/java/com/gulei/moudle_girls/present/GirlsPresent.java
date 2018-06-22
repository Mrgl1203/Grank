package com.gulei.moudle_girls.present;

import com.gulei.common.base.BasePresent;
import com.gulei.common.utils.ErrorBody;
import com.gulei.common.utils.PageBean;
import com.gulei.moudle_girls.bean.GirlsBean;
import com.gulei.moudle_girls.model.ImplGirlsModel;
import com.gulei.moudle_girls.view.GirlsConstants;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by gl152 on 2018/6/15.
 */

public class GirlsPresent extends BasePresent {

    GirlsConstants.GirlsModel model;
    GirlsConstants.GirlsView<GirlsBean> view;

    public GirlsPresent(GirlsConstants.GirlsView<GirlsBean> view) {
        this.view = view;
        model = new ImplGirlsModel();
    }

    public void getGirlsData(final PageBean<GirlsBean.ResultsBean> pageBean, boolean pullToRefresh) {
        if (pullToRefresh) {
            pageBean.clear();
        }
        model.getGirlsData(pageBean.getPageSize(), pageBean.getCurrentPage())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GirlsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(GirlsBean girlsBean) {
                        if (girlsBean.isError()) {
                            view.onFailed(new ErrorBody(ErrorBody.Default_errorMsg));
                        } else {
                            view.onSuccess(girlsBean);
                            if (girlsBean.getResults().size() < pageBean.getPageSize()) {//说明没有下一页
                                pageBean.noNextPage();
                            } else {
                                pageBean.autoIncrementCurrentPage();
                                pageBean.hasNextPage();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.onFailed(new ErrorBody(e.getMessage()));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
