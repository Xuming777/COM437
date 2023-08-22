package com.cool.weather;

import android.util.Log;
import androidx.annotation.NonNull;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OkhttpUtil {

    private static final OkHttpClient sClient = new OkHttpClient();

    public static void get(@NonNull String url, @NonNull IHttpResult callback) {
        get(url, null, callback);
    }

    public static void get(@NonNull String url, Map<String, String> header, @NonNull IHttpResult callback) {
        Request.Builder requestBuilder = new Request.Builder().get().url(url);
        if (header != null && !header.isEmpty()) {
            Set<Map.Entry<String, String>> enrtrySet = header.entrySet();
            for (Map.Entry<String, String> entry : enrtrySet) {
                requestBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        sClient.newCall(requestBuilder.build()).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callback.onFail("Req-IOException", Log.getStackTraceString(e));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                if (response.code() != 200) {
                    callback.onFail(String.valueOf(response.code()), "response fail !");
                    return;
                }
                ResponseBody body = response.body();
                if (body == null) {
                    callback.onFail("BodyError", "body is null");
                } else {
                    try {
                        String bodyStr = body.string();
                        callback.onSuccess(bodyStr);
                    } catch (IOException e) {
                        callback.onFail("body-IOException", Log.getStackTraceString(e));
                    }
                }
            }
        });
    }

    interface IHttpResult {
        void onSuccess(String bodyTxt);
        void onFail(String errorCode, String errorMsg);
    }

}