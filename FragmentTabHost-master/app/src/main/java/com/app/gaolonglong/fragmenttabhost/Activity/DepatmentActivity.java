package com.app.gaolonglong.fragmenttabhost.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.gaolonglong.fragmenttabhost.Adapter.DepartAdapter;
import com.app.gaolonglong.fragmenttabhost.MessageFragment;
import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.Service.DepatmentService;
import com.app.gaolonglong.fragmenttabhost.Service.HttpThread;
import com.app.gaolonglong.fragmenttabhost.Service.Http_Status_Tips;
import com.app.gaolonglong.fragmenttabhost.View.ClearEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/12/10.
 */

public class DepatmentActivity extends Activity {


    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.title_home)
    TextView titleHome;
    @Bind(R.id.search)
    ClearEditText search;
    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.department_search_button)
    Button departmentSearchButton;
    private String result;
//    private String All;


    private ArrayList<HashMap<String, Object>> newlist=null;


    private DepartAdapter adapter;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_department);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        search.setText("All");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DepatmentActivity.this, MessageFragment.class);
                intent.putExtra(result, "myname");
                DepatmentActivity.this.setResult(RESULT_OK, intent);
//                startActivity(intent);
                DepatmentActivity.this.finish();

            }
        });







        if (listView.getHeaderViewsCount() == 0) {
            final View headerView = LayoutInflater.from(DepatmentActivity.this).inflate(R.layout.department_listview_header, null);
            listView.addHeaderView(headerView);
        }
        listView.setAdapter(adapter);



        departmentSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final  String keyword = search.getText().toString().trim();

                new Thread() {
                    public void run() {

                        //调用Search上网类.
                        final String fanhui = DepatmentService.departmentsearch(keyword);
                        if (fanhui != null) {
                            try {
                                //解析数据
                                JSONObject jsonObjs = new JSONObject(fanhui);

                                JSONArray jsonObja = jsonObjs.getJSONArray("data");
                                newlist = new ArrayList<HashMap<String, Object>>();

                                for (int i = 0; i < jsonObja.length(); i++) {
                                    JSONObject jsonObj = jsonObja.getJSONObject(i);
                                    HashMap<String, Object> map = new HashMap<String, Object>();
                                    map.put("userName", jsonObj.getString("userName"));
                                    map.put("mailAdd", jsonObj.getString("mailAdd"));
                                    map.put("chineseName", jsonObj.getString("chineseName"));
                                    map.put("BU", jsonObj.getString("BU")+"");
                                    map.put("department", jsonObj.getString("department"));

                                    newlist.add(map);


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
                        DepartAdapter lvAdapter = new DepartAdapter(DepatmentActivity.this, newlist);
                        listView.setAdapter(lvAdapter);

                        break;
                    case 1:
                        Toast.makeText(DepatmentActivity.this, "<JSON>����Ϊ��", Toast.LENGTH_SHORT).show();
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
            if (DepatmentActivity.this.getCurrentFocus() != null) {
                if (DepatmentActivity.this.getCurrentFocus().getWindowToken() != null) {
                    imm.hideSoftInputFromWindow(DepatmentActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
        return super.onTouchEvent(event);
    }





}

