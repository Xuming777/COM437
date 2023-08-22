package com.cool.weather;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout userNameEt;
    private TextInputLayout passwordEt;
    private TextInputLayout repeatPasswordEt;
    private Button registerButton;
    private Handler threadHandler;
    private HandlerThread handlerThread;
    private String name;
    private String password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        setTitle("Register");
        userNameEt = findViewById(R.id.userNameEt);
        passwordEt = findViewById(R.id.passwordEt);
        repeatPasswordEt = findViewById(R.id.repeatPasswordEt);
        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = userNameEt.getEditText().getText().toString();
                password = passwordEt.getEditText().getText().toString();
                String repeatPassword = repeatPasswordEt.getEditText().getText().toString();
                if (name.isEmpty() || password.isEmpty()) {
                    ToastUtils.showToast("The username and password cannot be empty");
                    return;
                }
                if (!TextUtils.equals(password, repeatPassword)) {
                    ToastUtils.showToast("The password entered twice needs to be the same!");
                    return;
                }
                Message message = new Message();
                message.what = 1;
                threadHandler.sendMessage(message);

            }
        });
        Objects.requireNonNull(userNameEt.getEditText()).setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        StringBuilder sb = new StringBuilder();
                        for (int i = start; i < end; i++) {
                            char ch = source.charAt(i);
                            if ((ch >= 'a' && ch < 'z') || (ch >= 'A' && ch < 'Z') || ch == '_' || (ch >= '0' && ch <= '9')) {
                                sb.append(ch);
                            }
                        }
                        if (sb.length() == end - start) {
                            return null;
                        } else {
                            return sb;
                        }
                    }
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
                        // add the new user into database
                        addUser(name, password);
                        break;
                }
            }
        };

    }

    /**
     * 1.Check whether the account already exists
     * 2.if no, add the user
     * 3.if yes,show error toast
     *
     * @param name
     * @param password
     */
    private void addUser(String name, String password) {
        UserDao userDao = AppDatabase.getInstance(getApplicationContext()).userDao();
        List<UserEntity> users = userDao.getAll();
        for (UserEntity user : users) {
            if (name.equals(user.name)) {
                ToastUtils.showToast(" the account already exists");
                return;
            }
        }
        UserEntity user = new UserEntity();
        user.name = name;
        user.password = password;
        userDao.insert(user);
        //goto mainActivity
        ToastUtils.showToast(" Register Success");
        startActivity(new Intent(RegisterActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handlerThread.quit();
    }
}
