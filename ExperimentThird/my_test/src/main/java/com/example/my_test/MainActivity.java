package com.example.my_test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String data = "[[3, \"33333333\", \"1\", 1589106583.9971232], [2, \"33333333\", \"1\", 1589106581.4320571], [1, \"33333333\", \"1\", 1589106576.184838]]";
        try {
            JSONObject jsonObject = new JSONObject(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
