package com.xiaoyou.face.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.just.agentweb.core.AgentWeb;
import com.xiaoyou.face.R;
import com.xiaoyou.face.databinding.ActivityMainBinding;
import com.xiaoyou.face.databinding.ActivityStatisticsBinding;
import com.xiaoyou.face.fragment.IndexFragment;
import com.xiaoyou.face.utils.PieChartManager;
import com.xiaoyou.face.utils.Tools;
import com.xuexiang.xui.XUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 考勤统计效果显示
 * @author 小游
 * @date 2020/12/16
 */
public class StatisticsActivity extends AppCompatActivity {

    private ActivityStatisticsBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 不在AndroidManifest.xml里面设置因为这个会无法继承AppCompatActivity
        XUI.initTheme(this);
        super.onCreate(savedInstanceState);
        // 这里我们使用视图绑定来获取view里面的对象 https://developer.android.com/topic/libraries/view-binding#java
        binding = ActivityStatisticsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initView();
    }




    /**
     * 视图初始化
     */
    private void initView(){
         // 指示器绑定
        binding.functionChoose.setTabTitles(new String[]{"考勤情况统计","考勤历史"});
        // 指示器点击事件
        binding.functionChoose.setOnTabClickListener(((title, position) -> {
            switch (position){
                case 0:
                    showRingPieChart();
                   break;
                case 1:
                    showAttendanceHistory();
                    break;
                default:
                    break;
            }
        }));
        // 显示考勤情况统计
        showRingPieChart();
        // 返回按钮点击
        binding.navigation.setLeftClickListener(v->{
            finish();
        });
    }

    /**
     * 显示考勤情况统计
     */
    private void showRingPieChart() {
        binding.flContainer.setVisibility(View.VISIBLE);
        binding.linearContainer.setVisibility(View.INVISIBLE);
        // 设置每份所占数量
        List<PieEntry> yvals = new ArrayList<>();
        yvals.add(new PieEntry(50, "已签到"));
        yvals.add(new PieEntry(30, "未签到"));
        //设置每份的颜色
        List<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#2ecc71"));
        colors.add(Color.parseColor("#bdc3c7"));
        // 图表初始化
        PieChart pieChart = binding.flContainer;
        //  是否显示中间的洞
        pieChart.setDrawHoleEnabled(true);
        //设置中间洞的大小
        pieChart.setHoleRadius(30f);

        // 半透明圈
        pieChart.setTransparentCircleRadius(35f);
        //设置半透明圆圈的颜色
        pieChart.setTransparentCircleColor(Color.WHITE);
        //设置半透明圆圈的透明度
        pieChart.setTransparentCircleAlpha(125);


        //饼状图中间可以添加文字
        pieChart.setDrawCenterText(true);
        //设置中间文字
        pieChart.setCenterText("签到情况统计");
        //中间问题的颜色
        pieChart.setCenterTextColor(Color.parseColor("#a1a1a1"));
        //中间文字的大小px
        pieChart.setCenterTextSizePixels(25);
        pieChart.setCenterTextRadiusPercent(1f);
        //中间文字的样式
        pieChart.setCenterTextTypeface(Typeface.DEFAULT);
        //中间文字的偏移量
        pieChart.setCenterTextOffset(0, 0);

        // 初始旋转角度
        pieChart.setRotationAngle(0);
        // 可以手动旋转
        pieChart.setRotationEnabled(true);
        //显示成百分比
        pieChart.setUsePercentValues(true);
        //取消右下角描述
        pieChart.getDescription().setEnabled(false);

        //是否显示每个部分的文字描述
        pieChart.setDrawEntryLabels(true);
        //描述文字的颜色
        pieChart.setEntryLabelColor(Color.WHITE);
        //描述文字的大小
        pieChart.setEntryLabelTextSize(14);
        //描述文字的样式
        pieChart.setEntryLabelTypeface(Typeface.DEFAULT_BOLD);

        //图相对于上下左右的偏移
        pieChart.setExtraOffsets(20, 0, 20, 8);
        //图标的背景色
        pieChart.setBackgroundColor(Color.TRANSPARENT);
        // 设置pieChart图表转动阻力摩擦系数[0,1]
        pieChart.setDragDecelerationFrictionCoef(0.75f);

        //获取图例
        Legend legend = pieChart.getLegend();
        //设置图例水平显示
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        //顶部
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        //右对其
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);

        //x轴的间距
        legend.setXEntrySpace(7f);
        //y轴的间距
        legend.setYEntrySpace(10f);
        //图例的y偏移量
        legend.setYOffset(10f);
        //图例x的偏移量
        legend.setXOffset(10f);
        //图例文字的颜色
        legend.setTextColor(Color.parseColor("#a1a1a1"));
        //图例文字的大小
        legend.setTextSize(13);

        //数据集合
        PieDataSet dataset = new PieDataSet(yvals, "");
        //填充每个区域的颜色
        dataset.setColors(colors);
        //是否在图上显示数值
        dataset.setDrawValues(true);
        // 文字的大小
        dataset.setValueTextSize(14);
        // 文字的颜色
        dataset.setValueTextColor(Color.parseColor("#3498db"));
        //文字的样式
        dataset.setValueTypeface(Typeface.DEFAULT_BOLD);

        // 当值位置为外边线时，表示线的前半段长度。
        dataset.setValueLinePart1Length(0.4f);
        //  当值位置为外边线时，表示线的后半段长度。
        dataset.setValueLinePart2Length(0.4f);
        // 当ValuePosits为OutsiDice时，指示偏移为切片大小的百分比
        dataset.setValueLinePart1OffsetPercentage(80f);
        // 当值位置为外边线时，表示线的颜色。
        dataset.setValueLineColor(Color.parseColor("#a1a1a1"));
        //  设置Y值的位置是在圆内还是圆外
        dataset.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        //  设置Y轴描述线和填充区域的颜色一致
        dataset.setUsingSliceColorAsValueLineColor(true);
        // 设置每条之前的间隙
        dataset.setSliceSpace(0);

        //设置饼状Item被选中时变化的距离
        dataset.setSelectionShift(5f);
        //填充数据
        PieData pieData = new PieData(dataset);
        // 格式化显示的数据为%百分比
        pieChart.setUsePercentValues(false);
        // 显示图表
        pieChart.setData(pieData);
        // 显示动画效果
        pieChart.animateY(500, Easing.EaseInOutQuad);
    }

    /**
     * 显示考勤历史
     */
    private void showAttendanceHistory(){
        // 设置控件显示
        binding.flContainer.setVisibility(View.INVISIBLE);
        binding.linearContainer.setVisibility(View.VISIBLE);
        //寻找到控件
        BarChart attendanceChart = binding.linearContainer;
        // 不显示描述
        attendanceChart.getDescription().setEnabled(false);
        //y轴右边关闭
        YAxis leftAxis = attendanceChart.getAxisLeft();
        // 从零开始
        leftAxis.setAxisMinimum(0f);
        attendanceChart.getAxisRight().setEnabled(false);
        //x轴设置无网格
        XAxis xAxis = attendanceChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        // 设置x做显示
        ArrayList<String> data2 = new ArrayList<>();
        data2.add("2019-08-1");
        data2.add("2019-08-2");
        data2.add("2019-08-3");
        data2.add("2019-08-4");
        data2.add("2019-08-5");
        data2.add("2019-08-6");
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return data2.get((int) value);
            }
        });
        //在放大到为轴设置的小数位数不再允许在两个轴值之间进行区分的点时，可以使用此方法来避免值重复。
        //创建两个数据集
        xAxis.setGranularity(1f);
        //数据集1
        List<BarEntry> valsComp1 = new ArrayList<BarEntry>();
        valsComp1.add(new BarEntry(0,new float[] { 5, 20}));
        valsComp1.add(new BarEntry(1,new float[] { 10,6 }));
        valsComp1.add(new BarEntry(2,new float[] { 10, 20}));
        valsComp1.add(new BarEntry(3,new float[] { 10, 20}));
        valsComp1.add(new BarEntry(4,new float[] { 10, 20}));
        valsComp1.add(new BarEntry(5,new float[] { 10, 20}));


//        XAxis xl = mChart.getXAxis();
//        xl.setValueFormatter(new IAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                return String.valueOf(data.get((int) value));
//            }
//            @Override
//            public int getDecimalDigits() {
//                return 0;
//            }
//        });
        //创建条形图对象
        BarDataSet setComp1 = new BarDataSet(valsComp1, "历史考勤记录");
        setComp1.setDrawIcons(false);
        setComp1.setColors(Color.parseColor("#2ecc71"),Color.parseColor("#bdc3c7"));
        setComp1.setStackLabels(new String[]{"已签到", "未签到"});
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(setComp1);
        //显示
        BarData data = new BarData(dataSets);
        attendanceChart.setData(data);
        attendanceChart.setFitBars(true);
        attendanceChart.invalidate();
        //设置动画样式
        attendanceChart.animateY(2000,Easing. EaseInOutQuad);
    }





}