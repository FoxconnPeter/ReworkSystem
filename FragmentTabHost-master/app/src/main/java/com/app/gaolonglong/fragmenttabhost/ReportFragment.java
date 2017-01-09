package com.app.gaolonglong.fragmenttabhost;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.FunctionActvity.CheckinListActivity;
import com.app.gaolonglong.fragmenttabhost.Service.HttpThread;
import com.app.gaolonglong.fragmenttabhost.Service.Http_Status_Tips;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by donglinghao on 2016-01-28.
 */
public class ReportFragment extends Fragment {

    @Bind(R.id.title_home)
    TextView titleHome;
    @Bind(R.id.reprotl1_barChart)
    BarChart reprotl1BarChart;

    private View mRootView;


    private BarData mBarData;
    private String l1Bg;

    private String[] area;
    private String[] name = {};
    private String[] wip = {};
    private String[] nametwo = {};

    private String[] caseTotal1 = {};

    private List<String> list1;
    private List<String> list2;



    private String[] failReason = {};
    private String[] number2 = {};
    private int[] areaData;
    private HttpThread jsonThread2;
    private ProgressDialog proDialog;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            Http_Status_Tips status = new Http_Status_Tips();
            if (what == 1) { // 返回成功数据时
                // 处理数组型json
                setupAdapter();
            } else if (what == 2) { // 根据服务器端返回数据,自定义提示
                status.setTips("无法连接远程服务器！");
            } else {
                status.setTips("网络连接失败！");
            }
            proDialog.dismiss();
        }
    };



    private String[] color = {"#C4FF8E", "#FFF88D", "#FFD38C", "#8CEBFF",
            "#FF8F9D", "#6BF3AD", "#426ab3", "#00a6ac", "#ed1941", "#8CEBFF",
            "#FF8F9D", "#6BF3AD", "#C4FF8E", "#FFF88D", "#FFD38C", "#8CEBFF",
            "#FF8F9D", "#6BF3AD", "#C4FF8E", "#FFF88D", "#FFD38C", "#8CEBFF"};







    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {

            mRootView = inflater.inflate(R.layout.report_fragment, container, false);
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }




        ButterKnife.bind(this, mRootView);
        initAdapter();



        return mRootView;

    }

    public void initAdapter() {
        String[] key1 = {"1"};
        String[] value1 = {"2"};
        String url1 = "http://10.132.44.79:8083//ServerMonitor/Chart";
        Search(key1, value1, url1);
    }

    public void Search(String[] key1, String[] value1, String url1) {
        proDialog = ProgressDialog.show(getActivity(), "", "正在获取数据……", true,
                true);
        jsonThread2 = new HttpThread(getActivity(), handler);
        String[] jsonKey2 = {"failReason", "number"};
        String jsonName2 = "data";
        // 传递相应的参数
        jsonThread2.setUrl(url1);
        jsonThread2.setKey(key1);
        jsonThread2.setValue(value1);
        jsonThread2.setJsonName(jsonName2);
        jsonThread2.setJsonKey(jsonKey2);
        jsonThread2.start();


    }


    public void setupAdapter() {

        failReason = new String[jsonThread2.getList().size()];
        number2 = new String[jsonThread2.getList().size()];
        for (int a = 0; a < jsonThread2.getList().size(); a++) {
            failReason[a] = jsonThread2.getList().get(a).get("failReason").toString();
            number2[a] = jsonThread2.getList().get(a).get("number").toString();
        }
        areaData = new int[number2.length];
        for (int i = 0; i < number2.length; i++) {
            areaData[i] = Integer.parseInt(number2[i]);

        }
        mBarData = getBarData(number2.length, 100);
        showBarChart(reprotl1BarChart, mBarData);
    }
    private void showBarChart(BarChart barChart, BarData barData) {
        // 数据描述与其相关属性
        barChart.setDescription("This is Fail Chart");
        //barChart.setDescriptionColor(Color.BLACK);
        //barChart.setDescriptionPosition(400f, 600f);
        //barChart.setDescriptionTextSize(16);


        // 如果没有数据的时候，会显示这个，类似ListView的EmptyView
        barChart.setNoDataTextDescription("You need to provide data for the chart.");
        // 是否显示表格颜色
        barChart.setDrawGridBackground(true);
        // 设置是否可以触摸
        barChart.setTouchEnabled(true);
        // 是否可以拖拽
        barChart.setDragEnabled(true);
        // 是否可以缩放
        barChart.setScaleEnabled(true);
        // 集双指缩放
        barChart.setPinchZoom(false);
        // 设置背景
        barChart.setBackgroundColor(Color.parseColor("#01000000"));
        // 如果打开，背景矩形将出现在已经画好的绘图区域的后边。
        barChart.setDrawGridBackground(false);
        // 集拉杆阴影
        barChart.setDrawBarShadow(false);
        // 图例
        barChart.getLegend().setEnabled(false);
        Legend mLegend = barChart.getLegend();
        mLegend.setForm(Legend.LegendForm.CIRCLE);// 样式
        mLegend.setFormSize(6f); // 字体
        mLegend.setTextColor(Color.BLACK); // 字体颜色
        // 设置数据
        barChart.setData(barData);
        // 隐藏右边的坐标轴 (就是右边的0 - 100 - 200 - 300 ... 和图表中横线)
        barChart.getAxisRight().setEnabled(false);
        // 隐藏左边的左边轴 (同上)
        // barChart.getAxisLeft().setEnabled(false);

        // 网格背景颜色
        barChart.setGridBackgroundColor(Color.parseColor("#00000000"));
        // 是否显示表格颜色
        barChart.setDrawGridBackground(false);
        // 设置边框颜色
        barChart.setBorderColor(Color.parseColor("#00000000"));
        // 说明颜色
        barChart.setDescriptionColor(Color.parseColor("#00000000"));
        // 拉杆阴影
        barChart.setDrawBarShadow(false);
        // 打开或关闭绘制的图表边框。（环绕图表的线）
        barChart.setDrawBorders(false);
        //隐藏网格线，保留水平线
        XAxis xAxis = barChart.getXAxis();
        //把X坐标轴发在上下
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //是否显示X坐标轴的线，即含坐标那条线，默认是true
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(4);
        xAxis.setTextSize(10);

        //设置每个x轴坐标相隔多少
        xAxis.setLabelsToSkip(0);
        YAxis yAxis = barChart.getAxisLeft();
        //yAxis.setAxisMaxValue(1);
        //yAxis.setSpaceTop(1);
        //强制设置Y轴的显示数量
        //barChart.getAxisLeft().setLabelCount(3, true);
        YAxisValueFormatter custom = new MyYAxisValueFormatter();
        yAxis.setValueFormatter(custom);
        barChart.animateY(1000); // 立即执行的动画,Y轴
    }


    public BarData getBarData(int count, float range) {
        ArrayList<String> xValues = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            xValues.add(failReason[i]);// 设置每个壮图的文字描述
        }
        ArrayList<BarEntry> yValues = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            yValues.add(new BarEntry(areaData[i], i));
        }
        // y轴的数据集合
        BarDataSet barDataSet = new BarDataSet(yValues, "测试饼状图....");
        //柱子间的距离
        barDataSet.setBarSpacePercent(50f);
        //设置柱子颜色
        ArrayList<Integer> colors = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            colors.add(Color.parseColor(color[i]));
        }
        //        barDataSet.setColor(Color.RED);
        barDataSet.setColors(colors);
        // 设置栏阴影颜色
        // barDataSet.setBarShadowColor(Color.parseColor("#01000000"));
        ArrayList<BarDataSet> barDataSets = new ArrayList<>();
        barDataSets.add(barDataSet);
        // barDataSet.setValueTextColor(Color.parseColor("#FF8F9D"));
        // 设置柱状图顶部不显示数值
        barDataSet.setDrawValues(true);
        BarData barData = new BarData(xValues, barDataSets);
        return barData;
    }


    public class MyYAxisValueFormatter implements YAxisValueFormatter {
        private DecimalFormat mFormat;

        public MyYAxisValueFormatter() {
            mFormat = new DecimalFormat("###,###,###,##0");
        }

        @Override
        public String getFormattedValue(float value, YAxis yAxis) {
            // TODO Auto-generated method stub
            return mFormat.format(value);
        }
    }









}
