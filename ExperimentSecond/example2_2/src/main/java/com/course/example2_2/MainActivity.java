package com.course.example2_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText mEt_1, mEt_2;
    private Button mBtn_1, mBtn_2;
    private CheckBox mCb_1;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private boolean store = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEt_1 = findViewById(R.id.et_1);
        mEt_2 = findViewById(R.id.et_2);
        mBtn_1 = findViewById(R.id.btn_1);
        mBtn_2 = findViewById(R.id.btn_2);
        mCb_1 = findViewById(R.id.cb_1);

        mSharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        mBtn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = mEt_1.getText().toString();
                String passwd = mEt_2.getText().toString();
                if (store){
                    mEditor.putString("username", uname);
                    mEditor.putString("password", passwd);
                    mEditor.apply();
                }else {
                    mEditor.remove("username");
                    mEditor.remove("password");
                }
                Toast.makeText(MainActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
            }
        });

        mBtn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEt_1.setText(mSharedPreferences.getString("username", ""));
                mEt_2.setText(mSharedPreferences.getString("password", ""));
            }
        });

        mCb_1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switch (buttonView.getId()){
                    case R.id.cb_1:
                        store = isChecked;
                }
            }
        });
    }
}
