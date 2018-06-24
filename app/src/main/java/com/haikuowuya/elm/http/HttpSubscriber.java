package com.haikuowuya.elm.http;

import com.haikuowuya.elm.utils.SoutUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public  abstract  class HttpSubscriber<T > implements Observer<HttpResult<T>> {
    public  abstract void   onSuccess(T t);
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(HttpResult<T> tHttpResult) {
        SoutUtils.out("code = " + tHttpResult.code);
        SoutUtils.out("message = " + tHttpResult.message);
        onSuccess(tHttpResult.data);
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
