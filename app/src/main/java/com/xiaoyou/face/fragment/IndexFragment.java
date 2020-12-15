package com.xiaoyou.face.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.xiaoyou.face.R;
import com.xiaoyou.face.activity.CameraActivity;
import com.xiaoyou.face.databinding.FragmentIndexBinding;
import com.xiaoyou.face.engine.GlideEngine;
import com.xiaoyou.face.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * 打卡的fragment类
 * @author 小游
 * @date 2020/12/14
 */
public class IndexFragment extends Fragment implements
        CalendarView.OnCalendarSelectListener,
        CalendarView.OnYearChangeListener,
        View.OnClickListener{

    private FragmentIndexBinding binding;
    TextView mTextMonthDay;
    TextView mTextYear;
    TextView mTextLunar;
    TextView mTextCurrentDay;
    CalendarView mCalendarView;
    RelativeLayout mRelativeTool;
    private int mYear;
    CalendarLayout mCalendarLayout;

    private final static int REQUEST_CODE = 45;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 这里我们使用bindview 来进行视图绑定
        binding = FragmentIndexBinding.inflate(inflater, container, false);
        // 我们这里给控件赋值
        // 月份显示
        mTextMonthDay = binding.tvMonthDay;
        // 年份显示
        mTextYear = binding.tvYear;
        // 时间显示
        mTextLunar = binding.tvLunar;
        // 现在时间
        mTextCurrentDay = binding.tvCurrentDay;
        // 日历视图组件
        mCalendarView = binding.calendarView;
        // 时间显示控件
        mRelativeTool = binding.rlTool;
        // 日历布局
        mCalendarLayout = binding.calendarLayout;
        // 数据初始化
        initView();
        initData();
        // 设置签到按钮点击事件
        binding.login.setOnClickListener(v->{
//            EasyPhotos.createAlbum(this, true, GlideEngine.getInstance())//参数说明：上下文，是否显示相机按钮，[配置Glide为图片加载引擎](https://github.com/HuanTanSheng/EasyPhotos/wiki/12-%E9%85%8D%E7%BD%AEImageEngine%EF%BC%8C%E6%94%AF%E6%8C%81%E6%89%80%E6%9C%89%E5%9B%BE%E7%89%87%E5%8A%A0%E8%BD%BD%E5%BA%93)
//                    .setFileProviderAuthority("com.huantansheng.easyphotos.sample.fileprovider")//参数说明：见下方`FileProvider的配置`
//                    .start(REQUEST_CODE);
            startActivity(new Intent(getContext(),CameraActivity.class));
        });

        // 返回视图view
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    /**
     *  日历视图点击事件
      * @param calendar 日历控件
     * @param isClick 是否点击
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        // 显示时间控件
        mTextLunar.setVisibility(View.VISIBLE);
        mTextYear.setVisibility(View.VISIBLE);

        // 我们这里修改导航栏的时间显示
        mTextMonthDay.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
        mTextYear.setText(String.valueOf(calendar.getYear()));
        mTextLunar.setText(calendar.getLunar());
        mYear = calendar.getYear();
    }

    @Override
    public void onYearChange(int year) {
        // 年份视图点击的时候修改顶部的时间
        mTextMonthDay.setText(String.valueOf(year));
    }

    /**
     * 设置标记
     * @param year 年份
     * @param month 月份
     * @param day 日
     * @param color 颜色
     * @return calendar对象
     */
    private Calendar getSchemeCalendar(int year, int month, int day, int color) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        // 如果单独标记颜色、则会使用这个颜色
        calendar.setSchemeColor(color);
        return calendar;
    }


    /**
     * 视图初始化
      */
    @SuppressLint("SetTextI18n")
    protected void initView() {
        // 点击月份显示月份切换
        mTextMonthDay.setOnClickListener(v -> {
            if (!mCalendarLayout.isExpand()) {
                mCalendarLayout.expand();
                return;
            }
            // 显示月份
            mCalendarView.showYearSelectLayout(mYear);
            // 隐藏隐藏年份和时间
            mTextLunar.setVisibility(View.GONE);
            mTextYear.setVisibility(View.GONE);
            // 月份哪里显示时间
            mTextMonthDay.setText(String.valueOf(mYear));
        });
        // 点击今天就直接跳转到今天
        binding.flCurrent.setOnClickListener(v -> mCalendarView.scrollToCurrent());

        // 日历点击事件(包括年份点击)
        mCalendarView.setOnCalendarSelectListener(this);
        mCalendarView.setOnYearChangeListener(this);

        // 这里我们显示当前时间到顶部导航栏
        mTextYear.setText(String.valueOf(mCalendarView.getCurYear()));
        mYear = mCalendarView.getCurYear();
        mTextMonthDay.setText(mCalendarView.getCurMonth() + "月" + mCalendarView.getCurDay() + "日");
        mTextLunar.setText("今日");
        // 显示今天日期
        mTextCurrentDay.setText(String.valueOf(mCalendarView.getCurDay()));
    }


    /**
     * 日历初始化
     */
    private void initData(){


        int year = mCalendarView.getCurYear();
        int month = mCalendarView.getCurMonth();

        Map<String, Calendar> map = new HashMap<>();
//        map.put(getSchemeCalendar(year, month, 3, 0xFF40db25).toString(),
//                getSchemeCalendar(year, month, 3, 0xFF40db25));
//        map.put(getSchemeCalendar(year, month, 6, 0xFFe69138).toString(),
//                getSchemeCalendar(year, month, 6, 0xFFe69138));
//        map.put(getSchemeCalendar(year, month, 9, 0xFFdf1356).toString(),
//                getSchemeCalendar(year, month, 9, 0xFFdf1356));
//        map.put(getSchemeCalendar(year, month, 13, 0xFFedc56d).toString(),
//                getSchemeCalendar(year, month, 13, 0xFFedc56d));
//        map.put(getSchemeCalendar(year, month, 14, 0xFFedc56d).toString(),
//                getSchemeCalendar(year, month, 14, 0xFFedc56d));
//        map.put(getSchemeCalendar(year, month, 15, 0xFFaacc44).toString(),
//                getSchemeCalendar(year, month, 15, 0xFFaacc44));
//        map.put(getSchemeCalendar(year, month, 18, 0xFFbc13f0).toString(),
//                getSchemeCalendar(year, month, 18, 0xFFbc13f0));
//        map.put(getSchemeCalendar(year, month, 25, 0xFF13acf0).toString(),
//                getSchemeCalendar(year, month, 25, 0xFF13acf0));
        map.put(getSchemeCalendar(year, month, 27, 0xFF13acf0).toString(),
                getSchemeCalendar(year, month, 27, 0xFF13acf0));
        //此方法在巨大的数据量上不影响遍历性能，推荐使用
        mCalendarView.setSchemeDate(map);
    }

    /**
     * 拍照或者选择照片的回调事件
     * @param requestCode  requestCode
     * @param resultCode  resultCode
     * @param data data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
//
//            //返回对象集合：如果你需要了解图片的宽、高、大小、用户是否选中原图选项等信息，可以用这个
//            ArrayList<Photo> resultPhotos = data.getParcelableArrayListExtra(EasyPhotos.RESULT_PHOTOS);
//            ToastUtils.success("图片路径"+resultPhotos.get(0).path);
//            Log.e("xiaoyou",resultPhotos.toString());
//            //            mSelected.clear();
////            mSelected.addAll(resultPhotos);
//        }
    }
}