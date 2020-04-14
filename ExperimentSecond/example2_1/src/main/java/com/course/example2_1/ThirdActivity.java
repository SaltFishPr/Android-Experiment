package com.course.example2_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ThirdActivity extends AppCompatActivity {

    private TextView mTvDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        mTvDisplay = findViewById(R.id.tv_display);

        Intent intent = getIntent();
        if (intent.hasExtra(Intent.EXTRA_TEXT)){
            String string = intent.getStringExtra(Intent.EXTRA_TEXT);
            mTvDisplay.setText(string);
        }
    }

    @Override
    public void finish() {
        Intent data = new Intent();
        data.putExtra("returnData", "info");
        setResult(RESULT_OK, data);
        super.finish();
    }
}
