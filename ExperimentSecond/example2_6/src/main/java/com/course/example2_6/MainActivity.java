package com.course.example2_6;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ImageView mImv_1;
    private Button mBtn_1;

    private final int REQUEST_PERMISSION_CODE = 106;
    private final int REQUEST_TAKE_PHOTO_CODE = 105;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        // 判断权限
        if (!checkPermission()) { //没有或没有全部授权
            requestPermissions(); //请求权限
        } else {
            //如果开启了 直接调用相机
            takePhoto();//拍照逻辑
        }
        mImv_1 = findViewById(R.id.imageView);
        mBtn_1 = findViewById(R.id.btn_1);

        mBtn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_TAKE_PHOTO_CODE) {
            Log.i("aaa", "默认content地址："+data.getData());
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            mImv_1.setImageBitmap(photo);
        }
    }

    //检查权限
    private boolean checkPermission() {
        boolean haveCameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        boolean haveWritePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        boolean haveReadPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        return haveCameraPermission && haveWritePermission && haveReadPermission;
    }

    private void requestPermissions() {
        String[] permissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        };
        requestPermissions(permissions, REQUEST_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            boolean allowAllPermission = false;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {//被拒绝授权
                    allowAllPermission = false;
                    break;
                }
                allowAllPermission = true;
            }
            if (allowAllPermission) {
                takePhoto();//开始拍照或从相册选取照片
            } else {
                Toast.makeText(this, "该功能需要授权方可使用", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // 调用相机
    private void takePhoto() {
        Intent intent = new Intent();
        // 指定开启系统相机的Action
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_TAKE_PHOTO_CODE);
    }

}
