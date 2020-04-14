package com.course.example3_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myLog("onCreate");
    }

    @Override
    protected void onStart() {
        myLog("onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        myLog("onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        myLog("onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        myLog("onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        myLog("onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        myLog("onRestart");
        super.onRestart();
    }

    public void myLog(String string){
        Log.i(string, string);
    }
}
