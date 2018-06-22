package com.gulei.moudle_girls.model;

import com.gulei.common.http.HttpClient;
import com.gulei.moudle_girls.bean.GirlsBean;
import com.gulei.moudle_girls.girls_api.GirlApiService;
import com.gulei.moudle_girls.view.GirlsConstants;

import io.reactivex.Observable;

/**
 * Created by gl152 on 2018/6/15.
 */

public class ImplGirlsModel implements GirlsConstants.GirlsModel {
    GirlApiService service;

    public ImplGirlsModel() {
        service = HttpClient.getsInstance().createGirlsService(GirlApiService.class);
    }


    @Override
    public Observable<GirlsBean> getGirlsData(int size, int page) {
        return service.getGirlsData(size, page);
    }
}
