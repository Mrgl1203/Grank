package com.gulei.moudle_girls.girls_api;

import com.gulei.moudle_girls.bean.GirlsBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by gl152 on 2018/6/15.
 */

public interface GirlApiService {
    @GET("福利/{size}/{page}")
    Observable<GirlsBean> getGirlsData(@Path("size") int size, @Path("page") int page);
}
