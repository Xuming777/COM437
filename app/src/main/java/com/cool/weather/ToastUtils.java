package com.cool.weather;


import android.widget.Toast;


public class ToastUtils {
    public static void showToast(String msg) {
        Toast.makeText(MyApplication.instance, msg, Toast.LENGTH_SHORT).show();
    }

}
