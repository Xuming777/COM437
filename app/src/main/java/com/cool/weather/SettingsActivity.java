package com.cool.weather;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.cool.weather.Business.AMBEE_API_KEY;
import static com.cool.weather.Business.SP_NAME;
import static com.cool.weather.Business.SP_WEATHER_APP_ID;
import static com.cool.weather.Business.SP_X_API_KEY;
import static com.cool.weather.Business.WEATHER_APP_ID;


public class SettingsActivity extends AppCompatActivity {
    private TextInputLayout weatherAppIdEt;
    private TextInputLayout xApiKeyEt;
    private Button settingBtn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initView();
    }

    private void initView() {

        xApiKeyEt = findViewById(R.id.xApiKeyEt);
        weatherAppIdEt = findViewById(R.id.weatherAppIdEt);

        SharedPreferences sharedPreferences = getSharedPreferences(SP_NAME, MODE_PRIVATE);
        String weatherAppId = sharedPreferences.getString(SP_WEATHER_APP_ID, "");
        String xApiKey = sharedPreferences.getString(SP_X_API_KEY, "");
        Objects.requireNonNull(weatherAppIdEt.getEditText()).setText(weatherAppId.isEmpty() ? WEATHER_APP_ID : weatherAppId);
        Objects.requireNonNull(xApiKeyEt.getEditText()).setText(xApiKey.isEmpty() ? AMBEE_API_KEY : xApiKey);
        settingBtn = findViewById(R.id.settingBtn);
        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String weatherAppId = weatherAppIdEt.getEditText().getText().toString();
                String xApiKey = xApiKeyEt.getEditText().getText().toString();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (!TextUtils.isEmpty(weatherAppId)) {
                    editor.putString(SP_WEATHER_APP_ID, weatherAppId);
                }
                if (!TextUtils.isEmpty(xApiKey)) {
                    editor.putString(SP_X_API_KEY, xApiKey);
                }
                editor.apply();
                ToastUtils.showToast("Setting Success");
            }
        });
    }


}
