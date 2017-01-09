package com.app.gaolonglong.fragmenttabhost.FunctionActvity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.gaolonglong.fragmenttabhost.Activity.SearchActivity;
import com.app.gaolonglong.fragmenttabhost.Activity.SuccessActivity;
import com.app.gaolonglong.fragmenttabhost.LEI.LoginService;
import com.app.gaolonglong.fragmenttabhost.MainActivity;
import com.app.gaolonglong.fragmenttabhost.MessageFragment;
import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.Service.HttpThread;
import com.app.gaolonglong.fragmenttabhost.Service.Http_Status_Tips;
import com.app.gaolonglong.fragmenttabhost.View.ClearEditText;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/12/14.
 */

public class CheckinListActivity extends Activity {

    @Bind(R.id.title_home)
    TextView titleHome;
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.checkin_time)
    EditText checkinTime;
    @Bind(R.id.checkin_sn)
    ClearEditText checkinSn;
    @Bind(R.id.checkin_spinner)
    Spinner checkinSpinner;
    @Bind(R.id.spiner_text)
    TextView spinerText;
    @Bind(R.id.checkin_button)
    Button checkinButton;
    @Bind(R.id.l1_barChart)
    BarChart l1BarChart;
    @Bind(R.id.spiner_edit)
    ClearEditText spinerEdit;
    @Bind(R.id.erweima)
    ImageButton erweima;

    private String result;
    private BarData mBarData;
    private String[] failReason = {};
    private String[] number = {};
    private int[] areaData;
    private HttpThread jsonThread, jsonThread2, jsonThread3;
    private ProgressDialog proDialog;
    private String[] color = {"#C4FF8E", "#FFF88D", "#FFD38C", "#8CEBFF",
            "#FF8F9D", "#6BF3AD", "#426ab3", "#00a6ac", "#ed1941", "#8CEBFF",
            "#FF8F9D", "#6BF3AD", "#C4FF8E", "#FFF88D", "#FFD38C", "#8CEBFF",
            "#FF8F9D", "#6BF3AD", "#C4FF8E", "#FFF88D", "#FFD38C", "#8CEBFF"};

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
    private final static int SCANNIN_GREQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.checkinlist_activity);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CheckinListActivity.this, MainActivity.class);
                intent.putExtra(result, "myname");
                CheckinListActivity.this.setResult(RESULT_OK, intent);

                CheckinListActivity.this.finish();





            }
        });
        erweima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(CheckinListActivity.this, MipcaActivityCapture.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
            }
        });


        SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dff.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        String ee = dff.format(new Date());
//
//        Calendar c = Calendar.getInstance();
//
//        int year = c.get(Calendar.YEAR);
//
//        int month = c.get(Calendar.MONTH);
//        int day = c.get(Calendar.DAY_OF_MONTH);
//        int hour = c.get(Calendar.HOUR_OF_DAY);;
//        int minute = c.get(Calendar.MINUTE);
        checkinTime.setText(ee);

        checkinTime.setEnabled(false);

        String ziyuan[]=getResources().getStringArray(R.array.ctype);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.simple_spinner_item,ziyuan);
        adapter.setDropDownViewResource(R.layout.my_drop_down_item);
        checkinSpinner.setAdapter(adapter);

        checkinSpinner.getSelectedItem();
        checkinSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                // String[] languages = getResources().getStringArray(R.array.ctype);
                String BU = parent.getItemAtPosition(pos).toString();
                spinerEdit.setText(BU);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });


        initAdapter();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    //��ʾɨ�赽������
                    checkinSn.setText(bundle.getString("result"));

                }
                break;
        }
    }


    public void click1(View view) {
        //首先获取界面用户输入的用户名和密码
        final String sn = checkinSn.getText().toString().trim();
        final String time = checkinTime.getText().toString().trim();
        final String failReason = spinerEdit.getText().toString().trim();
        String liyou = "其他(选择此项请手动输入)";
        if (TextUtils.isEmpty(sn)) {
            Toast.makeText(CheckinListActivity.this, "SN不能为空", Toast.LENGTH_SHORT).show();
        } else if (failReason.equals(liyou) || TextUtils.isEmpty(failReason)) {
            Toast.makeText(CheckinListActivity.this, "不良原因错误", Toast.LENGTH_SHORT).show();

        } else {


            new Thread(new Runnable() {
                @Override
                //开启子线程
                //调用联网的类
                public void run() {
                    try {


                        final String result = LoginService.checkinservice(sn, time, failReason);


                        if (result != null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    try {

                                        JSONObject object3 = new JSONObject(result);

                                        int code = object3.optInt("code");
                                        System.out.print(code);

                                        switch (code) {
                                            case 0:
                                                Toast.makeText(CheckinListActivity.this, "导入成功", Toast.LENGTH_SHORT).show();
                                                break;
                                            case 1:
                                                Toast.makeText(CheckinListActivity.this, "导入失败", Toast.LENGTH_SHORT).show();
                                                break;

                                        }


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });


                        }


                        /****
                         runOnUiThread(new Runnable() {
                        @Override public void run() {

                        Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();
                        System.out.println("result=:"+result);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        LoginActivity.this.finish();




                        }
                        });
                         ***/

                        else {
                            //请求失败 发出消息
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(CheckinListActivity.this, "请求失败....", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            }).start();
        }


    }


    public void initAdapter() {
        String[] key1 = {"1"};
        String[] value1 = {"2"};
        String url1 = "http://10.132.44.79:8083//ServerMonitor/Chart";
        Search(key1, value1, url1);
    }


    public void Search(String[] key1, String[] value1, String url1) {
        proDialog = ProgressDialog.show(CheckinListActivity.this, "", "正在获取数据……", true,
                true);
        jsonThread = new HttpThread(CheckinListActivity.this, handler);
        String[] jsonKey = {"failReason", "number"};
        String jsonName = "data";
        // 传递相应的参数
        jsonThread.setUrl(url1);
        jsonThread.setKey(key1);
        jsonThread.setValue(value1);
        jsonThread.setJsonName(jsonName);
        jsonThread.setJsonKey(jsonKey);
        jsonThread.start();


    }

    public void setupAdapter() {

        failReason = new String[jsonThread.getList().size()];
        number = new String[jsonThread.getList().size()];
        for (int a = 0; a < jsonThread.getList().size(); a++) {
            failReason[a] = jsonThread.getList().get(a).get("failReason").toString();
            number[a] = jsonThread.getList().get(a).get("number").toString();
        }
        areaData = new int[number.length];
        for (int i = 0; i < number.length; i++) {
            areaData[i] = Integer.parseInt(number[i]);

        }
        mBarData = getBarData(number.length, 100);
        showBarChart(l1BarChart, mBarData);
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


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (CheckinListActivity.this.getCurrentFocus() != null) {
                if (CheckinListActivity.this.getCurrentFocus().getWindowToken() != null) {
                    imm.hideSoftInputFromWindow(CheckinListActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
        return super.onTouchEvent(event);
    }


}
