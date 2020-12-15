package com.xiaoyou.face.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;
import com.xiaoyou.face.databinding.ActivityCameraBinding;
import com.xiaoyou.face.databinding.ActivityMainBinding;
import com.xuexiang.xui.XUI;

/**
 * 简单拍照库
 * @author 小游
 * @date 2020/12/15
 */
public class CameraActivity extends AppCompatActivity {

    private ActivityCameraBinding binding;
    private CameraView cameraKitView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 不在AndroidManifest.xml里面设置因为这个会无法继承AppCompatActivity
        XUI.initTheme(this);
        super.onCreate(savedInstanceState);
        binding = ActivityCameraBinding.inflate(getLayoutInflater());
        cameraKitView = binding.camera;
        View view = binding.getRoot();
        setContentView(view);
        // 视图初始化
        initView();
    }

    /**
     *  视图初始化操作
     */
    private void initView() {
        // 切换到前置摄像头
        cameraKitView.toggleFacing();
        // 切换摄像头点击事件
        binding.ivFace.setOnClickListener(v->{
            Log.e("xiaoyou","切换摄像头");
            cameraKitView.toggleFacing();
        });
        // 拍照点击事件
        binding.ivTakePhoto.setOnClickListener(v->{
            cameraKitView.captureImage();
        });
        // 拍照监听事件
        cameraKitView.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) {
                Bitmap bitmap = cameraKitImage.getBitmap();
                binding.ivPhoto.setImageBitmap(bitmap);
            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });
//        try {
//            // 设置摄像头为前置摄像头
//            Thread.sleep(1000);
//            binding.ivFace.performClick();
//        }catch (Exception ignored){
//        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        cameraKitView.start();
    }

    @Override
    protected void onPause() {
        cameraKitView.stop();
        super.onPause();
    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        cameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }
}