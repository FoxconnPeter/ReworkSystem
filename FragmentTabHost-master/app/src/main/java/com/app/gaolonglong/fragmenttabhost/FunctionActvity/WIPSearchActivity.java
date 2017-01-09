package com.app.gaolonglong.fragmenttabhost.FunctionActvity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.app.gaolonglong.fragmenttabhost.Activity.DepatmentActivity;
import com.app.gaolonglong.fragmenttabhost.Adapter.DepartAdapter;
import com.app.gaolonglong.fragmenttabhost.Adapter.WIsearchAdapter;
import com.app.gaolonglong.fragmenttabhost.LEI.LoginService;
import com.app.gaolonglong.fragmenttabhost.MainActivity;
import com.app.gaolonglong.fragmenttabhost.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/12/15.
 */

public class WIPSearchActivity extends Activity {
    @Bind(R.id.title_home)
    TextView titleHome;
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.wipsearch_time)
    EditText wipsearchTime;
    @Bind(R.id.wipsearch_search)
    Button wipsearchSearch;
    @Bind(R.id.listView)
    ListView listView;
    private ArrayList<HashMap<String, Object>> newlist;
    private WIsearchAdapter adapter;
    private final static int SCANNIN_GREQUEST_CODE = 1;
    StringBuilder sts = new StringBuilder("");
    private String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_wipsearch);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ButterKnife.bind(this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(WIPSearchActivity.this, MainActivity.class);
                intent.putExtra(result, "myname");
                WIPSearchActivity.this.setResult(RESULT_OK, intent);

                WIPSearchActivity.this.finish();


            }
        });

        wipsearchTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar c = Calendar.getInstance();
// 直接创建一个DatePickerDialog对话框实例，并将它显示出来
                Dialog dateDialog = new DatePickerDialog(WIPSearchActivity.this,
// 绑定监听器
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker dp, int year,
                                                  int month, int dayOfMonth) {
                                sts.append(year + "-" + (month + 1) + "-"
                                        + dayOfMonth + " ");
                                Calendar time = Calendar.getInstance();
                                Dialog timeDialog = new TimePickerDialog(
                                        WIPSearchActivity.this,
// 绑定监听器
                                        new TimePickerDialog.OnTimeSetListener() {
                                            @Override
                                            public void onTimeSet(
                                                    TimePicker tp,
                                                    int hourOfDay, int minute) {
                                                sts.append(hourOfDay + ":"

                                                        + minute);


                                                wipsearchTime.setText(sts);
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
            final View headerView = LayoutInflater.from(WIPSearchActivity.this).inflate(R.layout.wipsearch_listview_header, null);
            listView.addHeaderView(headerView);
        }
        listView.setAdapter(adapter);


        wipsearchSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String Date = wipsearchTime.getText().toString().trim();


                new Thread() {
                    public void run() {

                        //调用Search上网类.
                        final String fanhui = LoginService.wipsearch(Date);
                        if (fanhui != null) {
                            try {
                                //解析数据
                                JSONObject jsonObjs = new JSONObject(fanhui);

                                JSONArray jsonObja = jsonObjs.getJSONArray("data");
                                newlist = new ArrayList<HashMap<String, Object>>();

                                for (int i = 0; i < jsonObja.length(); i++) {
                                    JSONObject jsonObj = jsonObja.getJSONObject(i);
                                    HashMap<String, Object> map = new HashMap<String, Object>();
                                    map.put("failReason", jsonObj.getString("failReason"));
                                    map.put("sn", jsonObj.getString("sn"));
                                    map.put("time", jsonObj.getString("time"));

                                    newlist.add(map);
                                    System.out.println("++++++++++++++++++++++++++++++++++++++++"+map);

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


    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            try {
                switch (msg.what) {
                    case 0:
                        WIsearchAdapter lvAdapter = new WIsearchAdapter(WIPSearchActivity.this, newlist);
                        listView.setAdapter(lvAdapter);

                        break;
                    case 1:
                        Toast.makeText(WIPSearchActivity.this, "<JSON>����Ϊ��", Toast.LENGTH_SHORT).show();
                        System.out.println("<JSON>����Ϊ��");
                        break;


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
            if (WIPSearchActivity.this.getCurrentFocus() != null) {
                if (WIPSearchActivity.this.getCurrentFocus().getWindowToken() != null) {
                    imm.hideSoftInputFromWindow(WIPSearchActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
        return super.onTouchEvent(event);
    }





}
