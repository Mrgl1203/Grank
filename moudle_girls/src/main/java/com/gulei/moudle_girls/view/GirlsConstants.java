package com.gulei.moudle_girls.view;

import com.gulei.common.base.BaseView;
import com.gulei.moudle_girls.bean.GirlsBean;

import io.reactivex.Observable;

/**
 * Created by gl152 on 2018/6/15.
 */

public class GirlsConstants {

    public interface GirlsView<T> extends BaseView<T>{

    }

    public interface GirlsModel {
        Observable<GirlsBean> getGirlsData(int size, int page);
    }
}
