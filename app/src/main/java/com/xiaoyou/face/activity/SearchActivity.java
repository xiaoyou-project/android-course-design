package com.xiaoyou.face.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.xiaoyou.face.R;
import com.xiaoyou.face.databinding.ActivitySearchBinding;
import com.xuexiang.xui.XUI;

/**
 * 数据查询界面
 * @author 小游
 * @date 2020/12/16
 */
public class SearchActivity extends AppCompatActivity {

    private ActivitySearchBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 不在AndroidManifest.xml里面设置因为这个会无法继承AppCompatActivity
        XUI.initTheme(this);
        super.onCreate(savedInstanceState);
        // 这里我们使用视图绑定来获取view里面的对象 https://developer.android.com/topic/libraries/view-binding#java
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initView();
    }


    /**
     * 视图初始化
     */
    private void initView(){
        // 点击返回事件
        binding.navigation.setLeftClickListener(v->{
            finish();
        });
        // 下拉搜索框
        binding.searchType.setOnItemSelectedListener((view, position, id, item) -> {

        });
    }
}