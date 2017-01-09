package com.app.gaolonglong.fragmenttabhost.FunctionActvity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.app.gaolonglong.fragmenttabhost.Adapter.ReporthAdapter;
import com.app.gaolonglong.fragmenttabhost.LEI.LoginService;
import com.app.gaolonglong.fragmenttabhost.MainActivity;
import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.SWIP.MyBaseAdapter;
import com.app.gaolonglong.fragmenttabhost.SWIP.SlideCutListView;
import com.app.gaolonglong.fragmenttabhost.SWIP.XListView;
import com.app.gaolonglong.fragmenttabhost.View.ClearEditText;
import com.app.gaolonglong.fragmenttabhost.View.MyListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/12/16.
 */

public class ReportSearch extends Activity {


    @Bind(R.id.title_home)
    TextView titleHome;
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.query_model)
    EditText queryModel;
    @Bind(R.id.query_sn)
    ClearEditText querySn;
    @Bind(R.id.query_station)
    ClearEditText queryStation;
    @Bind(R.id.query_repair_Engneer)
    ClearEditText queryRepairEngneer;
    @Bind(R.id.query_date_fromm)
    ClearEditText queryDateFromm;
    @Bind(R.id.query_date_to)
    ClearEditText queryDateTo;

    @Bind(R.id.spiner_text)
    TextView spinerText;

    StringBuilder str = new StringBuilder("");
    StringBuilder sts = new StringBuilder("");
    @Bind(R.id.query_button)
    Button queryButton;
    @Bind(R.id.listView)
    com.app.gaolonglong.fragmenttabhost.FunctionActvity.MyListView listView;
    @Bind(R.id.erweima)
    ImageButton erweima;









    private Boolean isRefresh = true;
    private Boolean isLoadMore = false;
    private Integer start = 1;
    private Integer size = 14;
    private ReporthAdapter reporthAdapter;

    private ArrayList<HashMap<String, Object>> newlist;
    private ArrayList<HashMap<String, Object>> newlist2;

    private final static int SCANNIN_GREQUEST_CODE = 1;
    public String bc;
    private String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.reportsearch);
        ButterKnife.bind(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ReportSearch.this, MainActivity.class);
                intent.putExtra(result, "myname");
                ReportSearch.this.setResult(RESULT_OK, intent);

                ReportSearch.this.finish();


            }
        });


        erweima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(ReportSearch.this, MipcaActivityCapture.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
            }
        });


        // 为“设置日期”按钮绑定监听器。
        queryDateFromm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View source) {

                Calendar c = Calendar.getInstance();
// 直接创建一个DatePickerDialog对话框实例，并将它显示出来
                final Dialog dateDialog = new DatePickerDialog(ReportSearch.this,
// 绑定监听器
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker dp, int year,
                                                  int month, int dayOfMonth) {
                                str.append(year + "-" + (month + 1) + "-"
                                        + dayOfMonth + " ");
                                Calendar time = Calendar.getInstance();
                                Dialog timeDialog = new TimePickerDialog(
                                        ReportSearch.this,
// 绑定监听器
                                        new TimePickerDialog.OnTimeSetListener() {
                                            @Override
                                            public void onTimeSet(
                                                    TimePicker tp,
                                                    int hourOfDay, int minute) {
                                                str.append(hourOfDay + ":"
                                                        + minute);


                                                queryDateFromm.setText(str);
                                                str.replace(0, str.length(), "");


                                            }
                                        }
// 设置初始时间
                                        , time.get(Calendar.HOUR_OF_DAY), time
                                        .get(Calendar.MINUTE)
// true表示采用24小时制
                                        , true);
                                timeDialog.setTitle("请选择日期");
                                timeDialog.show();


                            }
                        }
// 设置初始日期
                        , c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
                        .get(Calendar.DAY_OF_MONTH));
                dateDialog.setTitle("请选择日期");
                dateDialog.show();
            }
        });



        //设置结束时间
        queryDateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View source) {


                Calendar c = Calendar.getInstance();
// 直接创建一个DatePickerDialog对话框实例，并将它显示出来
                Dialog dateDialog = new DatePickerDialog(ReportSearch.this,
// 绑定监听器
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker dp, int year,
                                                  int month, int dayOfMonth) {
                                sts.append(year + "-" + (month + 1) + "-"
                                        + dayOfMonth + " ");
                                Calendar time = Calendar.getInstance();
                                Dialog timeDialog = new TimePickerDialog(
                                        ReportSearch.this,
// 绑定监听器
                                        new TimePickerDialog.OnTimeSetListener() {
                                            @Override
                                            public void onTimeSet(
                                                    TimePicker tp,
                                                    int hourOfDay, int minute) {
                                                sts.append(hourOfDay + ":"

                                                        + minute);


                                                queryDateTo.setText(sts);
                                                sts.replace(0, sts.length(), "");


                                            }
                                        }
// 设置初始时间
                                        , time.get(Calendar.HOUR_OF_DAY), time
                                        .get(Calendar.MINUTE)
// true表示采用24小时制
                                        , true);
                                timeDialog.setTitle("请选择日期");
                                timeDialog.show();


                            }
                        }
// 设置初始日期
                        , c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
                        .get(Calendar.DAY_OF_MONTH));
                dateDialog.setTitle("请选择日期");
                dateDialog.show();
            }
        });


        if (listView.getHeaderViewsCount() == 0) {
            final View headerView = LayoutInflater.from(ReportSearch.this).inflate(R.layout.reportsearch_listview_header, null);
            listView.addHeaderView(headerView);
        }

//        findData();
//        listener();

        listView.setOnDeleteListener(new com.app.gaolonglong.fragmenttabhost.FunctionActvity.MyListView.onDeleteListener() {
            @Override
            public void onDelete(int index) {


                    LinearLayout layout = (LinearLayout)listView.getChildAt(index);
                TextView bc=(TextView)layout.findViewById(R.id.report_item_sn);

                newlist.clear();

            final String sn =bc.getText().toString();
                //                reporthAdapter.notifyDataSetChanged();


                new Thread() {
                    public void run() {

                        //调用Search上网类.
                        final String fanhui = LoginService.deleteservice(sn);
                        if (fanhui != null) {
                            try {
                                //解析数据
                                JSONObject jsonObjs = new JSONObject(fanhui);


                                JSONArray jsonObja = jsonObjs.getJSONArray("data");

                                if (jsonObja != null) {


                                    newlist = new ArrayList<HashMap<String, Object>>();

                                    for (int i = 0; i < jsonObja.length(); i++) {
                                        JSONObject jsonObj = jsonObja.getJSONObject(i);
                                        HashMap<String, Object> map = new HashMap<String, Object>();

                                        map.put("dc", jsonObj.getString("dc"));
                                        map.put("type", jsonObj.getString("type"));
                                        map.put("sn", jsonObj.getString("sn"));
                                        map.put("time", jsonObj.getString("time"));
                                        map.put("model", jsonObj.getString("model"));
                                        map.put("station", jsonObj.getString("station"));
                                        map.put("repair_engneer", jsonObj.getString("repair_engneer"));
                                        map.put("partsn", jsonObj.getString("partsn"));
                                        map.put("location", jsonObj.getString("location"));
                                        map.put("description", jsonObj.getString("description"));
                                        map.put("failReason", jsonObj.getString("failReason"));
                                        map.put("lc", jsonObj.getString("lc"));
                                        map.put("dc", jsonObj.getString("dc"));
                                        map.put("manufacturer", jsonObj.getString("manufacturer"));
                                        newlist.add(map);

                                        System.out.println("++++++++++++++++++++++++++++++++++++++++" + map);

                                    }
                                } else {
                                    Toast.makeText(ReportSearch.this, "查询不成功，该数据不存在", Toast.LENGTH_SHORT).show();
                                }


                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            //把数据显示到ListView上
                            Message msg = new Message();
                            msg.what = 0;

                            handler.sendMessage(msg);


                        } else {
                            //弹出提示消息
                            Message msg = new Message();
                            msg.what = 1;
                            handler.sendMessage(msg);


                        }


                    }

                }.start();







            }
        });







    }








//    public void findData() {
//
//
//        if (isRefresh) {
//            size = 14;
//            MyBaseAdapter myBaseAdapter = new MyBaseAdapter(
//                    ReportSearch.this, newlist);
//            listView.setAdapter(myBaseAdapter);
//            isRefresh = false;
//            return;
//        }
//        if (isLoadMore&&size<100) {
//            MyBaseAdapter myBaseAdapter = new MyBaseAdapter(
//                    ReportSearch.this, newlist);
//            listView.setAdapter(myBaseAdapter);
//            listView.stopLoadMore();
//            listView.setSelection(size-12);
//            // 时间戳
//            listView.setRefreshTime("刚刚");
//            myBaseAdapter.notifyDataSetChanged();
//
//            isLoadMore = false;
//            return;
//        }else if (size>=100) {
//            Toast.makeText(getApplicationContext(), "无更多内容", Toast.LENGTH_LONG).show();
//            listView.stopLoadMore();
//            size = size-14;
//        }
//    }
//









//
//
//    public void listener() {
//        myBaseAdapter = new MyBaseAdapter(ReportSearch.this, newlist);
//        listView.setAdapter(myBaseAdapter);
//        listView.setPullLoadEnable(true);
//        listView.setXListViewListener((XListView.IXListViewListener) this);
//    }

    // ˢ�²���

//    public void onRefresh(){
//        // �ӵ�һҳ��ʼ��ȡ
//        isRefresh = true;
//        findData();
//        listView.stopRefresh();
//        // ʱ���
//        listView.setRefreshTime("刚刚");
//    }
//
//    // ���ظ��
//
//    public void onLoadMore() {
//        size = size+10;
//        isLoadMore = true;
//        findData();
//    }
//




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.
                menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


//    public void click2(View view) {
//
//        String[] key1 = {"sn", "model","station","repair_engneer","datafrom","datato"};
//        String[] value1 = {querySn.getText(),"2016-01-25 00:00", "2050-01-01 00:00"};
//
//
//        String url1 = "http://10.132.45.177:14494//ServerMonitor/OutlistServl";
//        Search(key1, value1, url1);
//
//
//
//
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    //��ʾɨ�赽������
                    querySn.setText(bundle.getString("result"));

                }
                break;
        }
    }






    public void click2(View view) {
        //首先获取界面用户输入的用户名和密码

        final String sn = querySn.getText().toString();
        final String model = queryModel.getText().toString();
        final String station = queryStation.getText().toString();
        final String repair_engneer = queryRepairEngneer.getText().toString();
        final String datafrom = queryDateFromm.getText().toString();
        final String datato = queryDateTo.getText().toString();


        new Thread() {
            public void run() {

                //调用Search上网类.
                final String fanhui = LoginService.reportsearch(sn, model, station, repair_engneer, datafrom, datato);
                if (fanhui != null) {
                    try {
                        //解析数据
                        JSONObject jsonObjs = new JSONObject(fanhui);


                        JSONArray jsonObja = jsonObjs.getJSONArray("data");

                        if (jsonObja != null) {


                            newlist = new ArrayList<HashMap<String, Object>>();

                            for (int i = 0; i < jsonObja.length(); i++) {
                                JSONObject jsonObj = jsonObja.getJSONObject(i);
                                HashMap<String, Object> map = new HashMap<String, Object>();

                                map.put("dc", jsonObj.getString("dc"));
                                map.put("type", jsonObj.getString("type"));
                                map.put("sn", jsonObj.getString("sn"));
                                map.put("time", jsonObj.getString("time"));
                                map.put("model", jsonObj.getString("model"));
                                map.put("station", jsonObj.getString("station"));
                                map.put("repair_engneer", jsonObj.getString("repair_engneer"));
                                map.put("partsn", jsonObj.getString("partsn"));
                                map.put("location", jsonObj.getString("location"));
                                map.put("description", jsonObj.getString("description"));
                                map.put("failReason", jsonObj.getString("failReason"));
                                map.put("lc", jsonObj.getString("lc"));
                                map.put("dc", jsonObj.getString("dc"));
                                map.put("manufacturer", jsonObj.getString("manufacturer"));
                                newlist.add(map);

                                System.out.println("++++++++++++++++++++++++++++++++++++++++" + map);

                            }
                        } else {
                            Toast.makeText(ReportSearch.this, "查询不成功，该数据不存在", Toast.LENGTH_SHORT).show();
                        }


                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    //把数据显示到ListView上
                    Message msg = new Message();
                    msg.what = 0;

                    handler.sendMessage(msg);


                } else {
                    //弹出提示消息
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);


                }


            }

        }.start();


    }





    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            try {
                switch (msg.what) {
                    case 0:
                        ReporthAdapter adapter = new ReporthAdapter(ReportSearch.this, newlist);
                        listView.setAdapter(adapter);



                        break;
                    case 1:
                        Toast.makeText(ReportSearch.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                        System.out.println("<JSON>����Ϊ��");
                        break;
                    case 3:
















                    case 4:



                    default:
                        break;
                }
            } catch (Exception e) {

                e.printStackTrace();
            }
            super.handleMessage(msg);
        }
    };


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (ReportSearch.this.getCurrentFocus() != null) {
                if (ReportSearch.this.getCurrentFocus().getWindowToken() != null) {
                    imm.hideSoftInputFromWindow(ReportSearch.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
        return super.onTouchEvent(event);
    }






}
