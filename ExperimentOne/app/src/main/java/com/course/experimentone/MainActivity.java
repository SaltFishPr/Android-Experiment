package com.course.experimentone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Hello, World");
        Toast.makeText(getApplicationContext(), "OnCreate", Toast.LENGTH_SHORT).show();
    }
}
