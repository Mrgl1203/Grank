package com.gulei.common.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by gl152 on 2018/6/15.
 */

public class BasePresent {

    private CompositeDisposable compositeDisposable;

    public BasePresent() {
        compositeDisposable = new CompositeDisposable();
    }

    public void addDisposable(Disposable disposable) {
        if (disposable != null) {
            compositeDisposable.add(disposable);
        }
    }

    //remove时也会执行dispose方法阻断
    public void removeDisposable(Disposable disposable) {
        if (disposable != null) {
            compositeDisposable.remove(disposable);
        }
    }

    //clear时也会执行dispose方法阻断
    public void clearDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }


    public void onDestory() {
        clearDisposable();
    }
}
