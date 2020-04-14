package com.course.example1_4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button mBtn1;
    private Button mBtn2;
    private Button mBtn3;
    private ListView mLV1;
    // 不是很懂这个实验的内容
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindView();
    }

    private void bindView() {
        mBtn1 = findViewById(R.id.btn_1);
        mBtn2 = findViewById(R.id.btn_2);
        mBtn3 = findViewById(R.id.btn_3);
        mLV1 = findViewById(R.id.lv_1);
        OnClick onClick = new OnClick();
        mBtn1.setOnClickListener(onClick);
        mBtn2.setOnClickListener(onClick);
        mBtn3.setOnClickListener(onClick);
    }

    static class ViewHolder{
        public TextView tvContent;
    }

    class OnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_1: {
                    ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.list, android.R.layout.simple_list_item_1);
                    mLV1.setAdapter(arrayAdapter);
                    mLV1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String result = parent.getItemAtPosition(position).toString();
                            Toast.makeText(MainActivity.this, "您点击了" + result, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                case R.id.btn_2: {
                    List<Map<String, Object>> strings = new ArrayList<>();
                    String[] temp = getResources().getStringArray(R.array.list);
                    for (String s : temp) {
                        Map<String, Object> showitem = new HashMap<>();
                        showitem.put("content", s);
                        strings.add(showitem);
                    }
                    SimpleAdapter simpleAdapter = new SimpleAdapter(MainActivity.this, // 上下文
                            strings,
                            android.R.layout.simple_list_item_2,
                            new String[]{"content"},
                            new int[]{android.R.id.text1});
                    mLV1.setAdapter(simpleAdapter);
                    mLV1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String result = parent.getItemAtPosition(position).toString();
                            Toast.makeText(MainActivity.this, "您点击了" + result, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                case R.id.btn_3: {
                    ViewHolder viewHolder = null;

                }
            }
        }
    }
}
