package com.course.example1_3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button mBtn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtn1 = findViewById(R.id.btn);
        mBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取LayoutInflater对象
                LayoutInflater layoutInflater = getLayoutInflater();
                // 获取AlertDialog对象
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                AlertDialog dialog = builder.create();
                // 将login_view添加到dialog中
                dialog.setView(layoutInflater.inflate(R.layout.login_view, null));
                dialog.show();
            }
        });
    }
}
