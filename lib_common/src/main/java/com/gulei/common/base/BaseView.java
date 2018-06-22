package com.gulei.common.base;

import com.gulei.common.utils.ErrorBody;

/**
 * Created by gl152 on 2018/6/15.
 */

public interface BaseView<T> {

    void onSuccess(T t);

    void onFailed(ErrorBody errorBody);
}
