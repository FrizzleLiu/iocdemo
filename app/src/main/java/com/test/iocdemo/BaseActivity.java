package com.test.iocdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.test.library.InjectManager;

/**
 * desc   : BaseActivity
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //帮助子类实现布局,控件,点击事件注入
        InjectManager.inject(this);
    }
}
