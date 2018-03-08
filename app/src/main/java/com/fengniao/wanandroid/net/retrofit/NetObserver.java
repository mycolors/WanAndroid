package com.fengniao.wanandroid.net.retrofit;


import android.content.Intent;


import com.fengniao.wanandroid.net.api.HttpResult;
import com.fengniao.wanandroid.util.UIUtils;

import java.net.SocketException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class NetObserver<T> implements Observer<HttpResult<T>> {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(HttpResult<T> value) {
        if (value.isSuccess()) {
            T t = value.getData();
            onHandleSuccess(t);
            onHandleSuccess(t, value.getErrorMsg());
        } else {
            onHandleError(value.getErrorMsg());
            onHandleError(value.getErrorCode(), value.getErrorMsg());
        }
    }

    @Override
    public void onError(Throwable e) {
//        if (e instanceof SocketException) {
//            onHandleError(StringUtils.getString(R.string.net_error));
//        } else if (e instanceof TimeoutException) {
//            onHandleError(StringUtils.getString(R.string.net_time_out));
//        }
////        else if (e instanceof JsonParseException) {
////            onHandleError("数据解析失败");
////        }
//        if (!NetworkUtils.isNetworkAvailable(ChainUpApp.appContext)) {
//            onHandleError(StringUtils.getString(R.string.net_error));
//        }
    }

    @Override
    public void onComplete() {

    }

    protected abstract void onHandleSuccess(T t);

    protected void onHandleError(String msg) {
        UIUtils.showToast(msg);
    }

    protected void onHandleSuccess(T t, String msg) {

    }

    protected void onHandleError(int code, String msg) {

    }
}
