package com.test.iocdemo;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.test.library.annotations.ContentView;
import com.test.library.annotations.InjectView;
import com.test.library.annotations.OnClick;
import com.test.library.annotations.OnLongClick;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    @InjectView(R.id.btn)
    Button btn;

    @OnClick(R.id.btn)
    public void clickEvent(View view){
        Toast.makeText(this,"IOC注入点击事件",Toast.LENGTH_SHORT).show();
    }

    @OnLongClick(R.id.tv)
    public boolean longClickEvent(View view){
        Toast.makeText(this,"IOC注入长按事件",Toast.LENGTH_SHORT).show();
        return false;
    }
}
