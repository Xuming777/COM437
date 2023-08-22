package com.cool.weather;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class Business {

    public static final String TAG = "Business";
    public static final String WEATHER_APP_ID = "6a02262b0dec87825957eb907ca8b123";
    public static final String AMBEE_API_KEY = "10acdf088eaf64a575bf71fe23e51b9ef8b5ec597562f234c71aa61817e4ba8f";
    // Windsor Carifornia US
    public static final String WEATHER_API = "https://api.openweathermap.org/data/2.5/weather?q=%s,%s,%s" +
            "&units=imperial&appid=%s";


    public static final String AMBEE_API = "https://api.ambeedata.com/latest/by-city?city=%s";
    public static final String SP_NAME = "user";
    public static final String SP_X_API_KEY = "x_api_key";
    public static final String SP_WEATHER_APP_ID = "app_id";

    public void getWeatherInfo(Context context, String town, String state, String country, IBeanCallback<WeatherBean> callback) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SP_NAME, MODE_PRIVATE);
        String weatherAppId = sharedPreferences.getString(SP_WEATHER_APP_ID, "");
        String url = String.format(WEATHER_API, town, state, country, weatherAppId.isEmpty() ? WEATHER_APP_ID : weatherAppId);
        OkhttpUtil.get(url, new OkhttpUtil.IHttpResult() {
            @Override
            public void onSuccess(String bodyTxt) {
                try {
                    JSONObject object = new JSONObject(bodyTxt);
                    JSONObject weather0 = object.optJSONArray("weather").optJSONObject(0);
                    WeatherBean bean = WeatherBean.parseBaseInfoFromJSONObject(weather0);
                    double fTemp = object.optJSONObject("main").optDouble("temp");
                    bean.setTemp(fTemp);
                    callback.onSuccess(bean);
                } catch (Exception e) {
                    callback.onError("Json Exception");
                }
            }

            @Override
            public void onFail(String errorCode, String errorMsg) {
                Log.e(TAG, "getWeatherInfo-onFail-- " + errorCode + ": " + errorMsg);
                callback.onError(errorCode + ":" + errorMsg);

            }
        });
    }

    public void getAmbeeInfo(Context context, String city, IBeanCallback<AmbeeBean> callback) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SP_NAME, MODE_PRIVATE);
        String xApiKey = sharedPreferences.getString(SP_X_API_KEY, "");
        String url = String.format(AMBEE_API, city);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("x-api-key", xApiKey.isEmpty() ? AMBEE_API_KEY : xApiKey);
        headMap.put("Content-type", "application/json");
        OkhttpUtil.get(url, headMap, new OkhttpUtil.IHttpResult() {
            @Override
            public void onSuccess(String bodyTxt) {
                try {
                    AmbeeBean bean = new AmbeeBean();
                    JSONObject object = new JSONObject(bodyTxt);
                    JSONObject station0 = object.optJSONArray("stations").optJSONObject(0);
                    JSONObject aqiInfoObj = station0.optJSONObject("aqiInfo");

                    String pollutant = aqiInfoObj.optString("pollutant");
                    String concentration = aqiInfoObj.optString("concentration");
                    String category = aqiInfoObj.optString("category");
                    String AQI = station0.optString("AQI");

                    bean.setPollutant(pollutant);
                    bean.setConcentration(concentration);
                    bean.setCategory(category);
                    bean.setAQI(AQI);

                    callback.onSuccess(bean);
                } catch (Exception e) {
                    callback.onError("Json Exception");
                }
            }

            @Override
            public void onFail(String errorCode, String errorMsg) {
                Log.e(TAG, "getAmbeeInfo-onFail-- " + errorCode + ": " + errorMsg);
                callback.onError(errorCode + ":" + errorMsg);

            }
        });
    }

}