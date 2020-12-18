package com.xiaoyou.face.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.arcsoft.face.ActiveFileInfo;
import com.arcsoft.face.ErrorInfo;
import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.enums.RuntimeABI;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.xiaoyou.face.R;
import com.xiaoyou.face.adapter.FunctionAdapter;
import com.xiaoyou.face.common.Constants;
import com.xiaoyou.face.databinding.ActivityMainBinding;
import com.xiaoyou.face.fragment.IndexFragment;
import com.xiaoyou.face.fragment.MeFragment;
import com.xiaoyou.face.fragment.ToolFragment;
import com.xiaoyou.face.model.Channel;
import com.xiaoyou.face.utils.ConfigUtil;
import com.xuexiang.xui.XUI;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.arcsoft.face.enums.DetectFaceOrientPriority.ASF_OP_270_ONLY;
import static com.arcsoft.face.enums.DetectFaceOrientPriority.ASF_OP_ALL_OUT;


/**
 * 主activity
 * @author 小游
 * @date 2020/12/14
 */
public class MainActivity extends BaseActivity {

    private static final String TAG = "ChooseFunctionActivity";
    private static final int ACTION_REQUEST_PERMISSIONS = 0x001;
    // 在线激活所需的权限
    private static final String[] NEEDED_PERMISSIONS = new String[]{Manifest.permission.READ_PHONE_STATE};
    boolean libraryExists = true;
    // Demo 所需的动态库文件
    private static final String[] LIBRARIES = new String[]{
            // 人脸相关
            "libarcsoft_face_engine.so",
            "libarcsoft_face.so",
            // 图像库相关
            "libarcsoft_image_util.so",
    };

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 不在AndroidManifest.xml里面设置因为这个会无法继承AppCompatActivity
        XUI.initTheme(this);
        super.onCreate(savedInstanceState);
        // 这里我们使用视图绑定来获取view里面的对象 https://developer.android.com/topic/libraries/view-binding#java
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        // 创建一个自己的fragment
        IndexFragment indexFragment = new IndexFragment();
        // 给我我们的fragment添加内容
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, indexFragment).commit();
        // tab初始化
        initTab();
        // 激活引擎
//        activeEngine(getWindow().getDecorView());
        //选择检测角度 默认270度
//        ConfigUtil.setFtOrient(getApplicationContext(), ASF_OP_ALL_OUT);
    }

    /**
     * tab初始化
     */
   private void initTab(){
       BottomNavigationBar bar = binding.bottomNavigationBar;
       bar.addItem(new BottomNavigationItem(R.drawable.ic_face, "签到"))
               .addItem(new BottomNavigationItem(R.drawable.ic_pie_chart, "统计"))
               .addItem(new BottomNavigationItem(R.drawable.ic_search, "查询"))
               .setFirstSelectedPosition(0).initialise();
       // 设置底部导航栏的点击事件
       bar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
           @Override
           public void onTabSelected(int position) {
               // 获取transaction对象，用户切换fragment
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                // 获取选择的位置，然后进行设置
                switch (position){
                    case 0:
                        transaction.replace(R.id.fragment_container,new IndexFragment());
                        break;
                    case 1:
                        transaction.replace(R.id.fragment_container,new ToolFragment());
                        break;
                    case 2:
                        transaction.replace(R.id.fragment_container,new MeFragment());
                    default:
                        break;
                }
                // 提交更改
                transaction.commit();
           }
           @Override
           public void onTabUnselected(int position) {
           }

           @Override
           public void onTabReselected(int position) {
           }
       });
    }


    /**
     * 激活引擎
     * @param view
     */
    @SuppressLint("CheckResult")
    public void activeEngine(final View view) {
        if (!libraryExists) {
            showToast(getString(R.string.library_not_found));
            return;
        }
        if (!checkPermissions(NEEDED_PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, NEEDED_PERMISSIONS, ACTION_REQUEST_PERMISSIONS);
            return;
        }
        if (view != null) {
            view.setClickable(false);
        }
        Observable<Integer> integerObservable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) {
                RuntimeABI runtimeABI = FaceEngine.getRuntimeABI();
                Log.i(TAG, "subscribe: getRuntimeABI() " + runtimeABI);

                long start = System.currentTimeMillis();
                int activeCode = FaceEngine.activeOnline(MainActivity.this, Constants.APP_ID, Constants.SDK_KEY);
                Log.i(TAG, "subscribe cost: " + (System.currentTimeMillis() - start));
                emitter.onNext(activeCode);
            }
        });
        integerObservable.subscribeOn(Schedulers.io());
        integerObservable.observeOn(AndroidSchedulers.mainThread());
        integerObservable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer activeCode) {
                if (activeCode == ErrorInfo.MOK) {
                    showToast(getString(R.string.active_success));
                } else if (activeCode == ErrorInfo.MERR_ASF_ALREADY_ACTIVATED) {
                    showToast(getString(R.string.already_activated));
                } else {
                    showToast(getString(R.string.active_failed, activeCode));
                }

                if (view != null) {
                    view.setClickable(true);
                }
                ActiveFileInfo activeFileInfo = new ActiveFileInfo();
                int res = FaceEngine.getActiveFileInfo(MainActivity.this, activeFileInfo);
                if (res == ErrorInfo.MOK) {
                    Log.i(TAG, activeFileInfo.toString());
                }

            }

            @Override
            public void onError(Throwable e) {
                showToast(e.getMessage());
                if (view != null) {
                    view.setClickable(true);
                }
            }

            @Override
            public void onComplete() {

            }
        });

    }

    @Override
    void afterRequestPermission(int requestCode, boolean isAllGranted) {

    }
}