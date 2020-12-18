package com.xiaoyou.face.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.xiaoyou.face.R;
import com.xiaoyou.face.activity.RegisterAndRecognizeActivity;
import com.xiaoyou.face.activity.SignDetailActivity;
import com.xiaoyou.face.adapter.FunctionAdapter;
import com.xiaoyou.face.databinding.FragmentIndexBinding;
import com.xiaoyou.face.model.Channel;
import com.xiaoyou.face.utils.Tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 打卡的fragment类
 * @author 小游
 * @date 2020/12/14
 */
public class IndexFragment extends Fragment implements
        CalendarView.OnCalendarSelectListener,
        CalendarView.OnYearChangeListener,
        View.OnClickListener{

    /**
     * 日历组件显示的时间map值
     */
    Map<String, Calendar> timeMap;

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
        // 宫格布局初始化
        // 9宫格布局初始化
        ArrayList<Channel> channelList = new ArrayList<>();
        channelList.add(new Channel(R.mipmap.face,"人脸录入"));
        channelList.add(new Channel(R.mipmap.login,"开始签到"));
        channelList.add(new Channel(R.mipmap.statistics,"签到详情"));
        binding.toolList.setAdapter(new FunctionAdapter(channelList,getContext()));
        // grad 布局点击事件监听
        binding.toolList.setOnItemClickListener((parent, view, position, id) -> {
            switch (position){
                case 0:
                    startActivity(new Intent(getContext(), RegisterAndRecognizeActivity.class));
                    break;
                case 1:
                    // 签到不需要显示录入按钮
                    Intent intent = new Intent(getContext(), RegisterAndRecognizeActivity.class);
                    intent.putExtra("login",false);
                    startActivity(intent);
                    break;
                case 2:
                    startActivity(new Intent(getContext(), SignDetailActivity.class));
                    break;
                default:
                    break;
            }
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

        timeMap = new HashMap<>();
        addMark(year,month,1);
        addMark(year,month,2);
        addMark(year,month,3);
        addMark(year,month,4);
        addMark(year,month,5);
        addMark(year,month,8);
        addMark(year,month,9);
        addMark(year,month,10);
        addMark(year,month,11);
        addMark(year,month,12);
        addMark(year,month,13);
        addMark(year,month,14);
        addMark(year,month,15);
        addMark(year,month,16);
        addMark(year,month,17);
        addMark(year,month,18);
        addMark(year,month,19);
        addMark(year,month,20);
        addMark(year,month,21);
        //此方法在巨大的数据量上不影响遍历性能，推荐使用
        mCalendarView.setSchemeDate(timeMap);
    }

    /**
     * 添加时间标记
     */
    private void addMark(int year, int month, int day){
       timeMap.put(getSchemeCalendar(year, month, day, Tools.getRandomColor()).toString(),
                getSchemeCalendar(year, month, day, Tools.getRandomColor()));
    }
}