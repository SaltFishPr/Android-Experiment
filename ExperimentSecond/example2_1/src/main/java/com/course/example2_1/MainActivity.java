package com.course.example2_1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button mBtn1, mBtn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtn1 = findViewById(R.id.btn_1);
        mBtn2 = findViewById(R.id.btn_2);

        mBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.course.example2_1.intent.MY_ACTION");
                startActivityForResult(intent,2333);
            }
        });

        mBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
                String string = "Information";
                intent.putExtra(Intent.EXTRA_TEXT, string);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        String string = "None infofoofofo";
        if ((requestCode == 2333) && (resultCode == RESULT_OK)) {
            if (data.hasExtra("returnString")) {
                string = data.getExtras().getString("returnString");
            }
        }
        System.out.println(string);
        System.out.println(requestCode);
        System.out.println(resultCode);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
