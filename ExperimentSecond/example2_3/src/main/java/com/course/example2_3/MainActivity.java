package com.course.example2_3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private EditText mEt_1;
    private Button mBtn_1, mBtn_2;
    private final String mFileName = "filedata.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEt_1 = findViewById(R.id.et_1);
        mBtn_1 = findViewById(R.id.btn_1);
        mBtn_2 = findViewById(R.id.btn_2);

        mBtn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save(mEt_1.getText().toString());
                Toast.makeText(MainActivity.this, "保存成功！",Toast.LENGTH_SHORT).show();
            }
        });

        mBtn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = load();
                mEt_1.setText(string);
            }
        });
    }

    // 保存
    private void save(String content) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = openFileOutput(mFileName, MODE_PRIVATE);
            fileOutputStream.write(content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 加载
    private String load() {
        FileInputStream fileInputStream = null;
        StringBuilder sb = new StringBuilder();
        try {
            fileInputStream = openFileInput(mFileName);
            byte[] buff = new byte[1024];
            // 拼接字符串
            int len;
            while ((len = fileInputStream.read(buff)) > 0) {
                sb.append(new String(buff, 0, len));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}
