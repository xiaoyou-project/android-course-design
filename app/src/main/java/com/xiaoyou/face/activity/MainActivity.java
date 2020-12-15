package com.xiaoyou.face.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.xiaoyou.face.R;
import com.xiaoyou.face.databinding.ActivityMainBinding;
import com.xiaoyou.face.fragment.IndexFragment;
import com.xiaoyou.face.fragment.MeFragment;
import com.xiaoyou.face.fragment.ToolFragment;
import com.xuexiang.xui.XUI;


/**
 * 主activity
 * @author 小游
 * @date 2020/12/14
 */
public class MainActivity extends AppCompatActivity {


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
    }

    /**
     * tab初始化
     */
   private void initTab(){
       BottomNavigationBar bar = binding.bottomNavigationBar;
       bar.addItem(new BottomNavigationItem(R.drawable.ic_face, "签到"))
               .addItem(new BottomNavigationItem(R.drawable.ic_tool_box, "工具"))
               .addItem(new BottomNavigationItem(R.drawable.ic_person, "我的"))
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
}