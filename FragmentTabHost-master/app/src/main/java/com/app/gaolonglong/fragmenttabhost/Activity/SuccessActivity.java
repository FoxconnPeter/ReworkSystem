package com.app.gaolonglong.fragmenttabhost.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
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

import com.app.gaolonglong.fragmenttabhost.Adapter.SucessAdapter;
import com.app.gaolonglong.fragmenttabhost.MessageFragment;
import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.Service.DepatmentService;
import com.app.gaolonglong.fragmenttabhost.View.ClearEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/12/13.
 */

public class SuccessActivity extends Activity {


    @Bind(R.id.title_home)
    TextView titleHome;
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.mine_xingming)
    TextView mineXingming;
    @Bind(R.id.mine_gonghao)
    TextView mineGonghao;
    @Bind(R.id.succes_text)
    ClearEditText succesText;

    @Bind(R.id.submit_success)
    Button submitSuccess;
    @Bind(R.id.listView)
    ListView listView;

    private String result;
    private String name, Chinese;
    private ArrayList<HashMap<String, Object>> newlist;
    private SucessAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.success_activity);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(SuccessActivity.this, MessageFragment.class);
                intent.putExtra(result, "myname");
                SuccessActivity.this.setResult(RESULT_OK, intent);

                SuccessActivity.this.finish();

            }
        });
//        if (listView.getHeaderViewsCount() == 0) {
//            final View headerView = LayoutInflater.from(SuccessActivity.this).inflate(R.layout.success_listview_header, null);
//            listView.addHeaderView(headerView);
//        }
//        listView.setAdapter(adapter);


        submitSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = mineXingming.getText().toString().trim();
                final String feedback = succesText.getText().toString().trim();
                if (TextUtils.isEmpty(feedback)){
                    Toast.makeText(SuccessActivity.this, "建议不能为空", Toast.LENGTH_SHORT).show();
                }else {

                    new Thread() {
                        public void run() {

                            //调用Search上网类.
                            final String fanhui = DepatmentService.successservice(name, feedback);
                            if (fanhui != null) {
                                try {
                                    //解析数据
                                    JSONObject jsonObjs = new JSONObject(fanhui);

                                    JSONArray jsonObja = jsonObjs.getJSONArray("data");
                                    newlist = new ArrayList<HashMap<String, Object>>();


                                    for (int i = 0; i < jsonObja.length(); i++) {
                                        JSONObject jsonObj = jsonObja.getJSONObject(i);
                                        HashMap<String, Object> map = new HashMap<String, Object>();
                                        map.put("feedback", jsonObj.getString("feedback"));
                                        map.put("name", jsonObj.getString("name"));

                                        map.put("time", jsonObj.getString("time"));


                                        newlist.add(map);
                                        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++" + map);

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


            }
        });


        Message msg = new Message();
        msg.what = 5;
        handler.sendMessage(msg);


    }


//单击空白处 软键盘消失

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (SuccessActivity.this.getCurrentFocus() != null) {
                if (SuccessActivity.this.getCurrentFocus().getWindowToken() != null) {
                    imm.hideSoftInputFromWindow(SuccessActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
        return super.onTouchEvent(event);
    }


    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            try {
                switch (msg.what) {
                    case 5:

                        Chinese = getIntent().getStringExtra("Chinese");
                        name = getIntent().getStringExtra("name");
                        mineGonghao.setText(name);
                        mineXingming.setText(Chinese);

                        break;
                    case 0:
                        SucessAdapter lvAdapter = new SucessAdapter(SuccessActivity.this, newlist);
                        listView.setAdapter(lvAdapter);
                        Toast.makeText(SuccessActivity.this, "提交成功", Toast.LENGTH_SHORT).show();

                        break;

                    case 1:
                        Toast.makeText(SuccessActivity.this, "<JSON>����Ϊ��", Toast.LENGTH_SHORT).show();
                        System.out.println("<JSON>����Ϊ��");
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {

                e.printStackTrace();
            }
         }
    };


}



