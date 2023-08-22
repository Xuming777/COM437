package com.cool.weather;

import androidx.annotation.Nullable;

public interface IBeanCallback<T> {

    void onSuccess(@Nullable T t);

    void onError(String error);

}