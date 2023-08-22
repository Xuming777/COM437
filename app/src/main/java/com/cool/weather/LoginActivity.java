package com.cool.weather;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity {
    private TextInputLayout userNameEt;
    private TextInputLayout passwordEt;
    private Button loginButton;
    private TextView registerButton;
    private Handler threadHandler;
    private HandlerThread handlerThread;
    private String name;
    private String password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {

        userNameEt = findViewById(R.id.userNameEt);
        passwordEt = findViewById(R.id.passwordEt);
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check account and password
                name = userNameEt.getEditText().getText().toString();
                password = passwordEt.getEditText().getText().toString();
                if (name.isEmpty() || password.isEmpty()) {
                    ToastUtils.showToast("The username and password cannot be empty");
                    return;
                }
                Message message = new Message();
                message.what = 1;
                threadHandler.sendMessage(message);
            }
        });
        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //                goto register
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));

            }
        });
        handlerThread = new HandlerThread("handler-thread");
        handlerThread.start(); // 必须在Handler创建前调用，因为线程start后才会创建Looper
        threadHandler = new Handler(handlerThread.getLooper()) {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                // 处理消息，因为这个方法是在子线程调用，所以可以在这执行耗时任务
                switch (msg.what) {
                    case 1:
                        checkAccountAndPassword(name, password);
                        break;
                }
            }
        };
    }

    /**
     * 1.get all User information from room
     * 2.compare  account & password with database
     *
     * @param name
     * @param password
     */
    private void checkAccountAndPassword(String name, String password) {
        UserDao userDao = AppDatabase.getInstance(getApplicationContext()).userDao();
        List<UserEntity> users = userDao.getAll();
        if (users.isEmpty()) {
            ToastUtils.showToast(" user name or password error,please check again");
            return;
        }
        boolean isUserPasswordChecked = false;
        for (UserEntity user : users) {
            isUserPasswordChecked = name.equals(user.name) && password.equals(user.password);
        }
        if (isUserPasswordChecked) {
            //goto mainActivity
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        } else {
            ToastUtils.showToast(" user name or password error,please check again");
        }
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        handlerThread.quit();
    }
}
