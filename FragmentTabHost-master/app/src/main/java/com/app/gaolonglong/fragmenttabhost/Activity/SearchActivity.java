package com.app.gaolonglong.fragmenttabhost.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.gaolonglong.fragmenttabhost.Adapter.ReporthAdapter;
import com.app.gaolonglong.fragmenttabhost.FunctionActvity.MipcaActivityCapture;
import com.app.gaolonglong.fragmenttabhost.HomeFragment;
import com.app.gaolonglong.fragmenttabhost.LEI.LoginService;
import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.View.ClearEditText;
import com.app.gaolonglong.fragmenttabhost.View.MyListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/12/23.
 */

public class SearchActivity extends Activity {

    @Bind(R.id.search)
    ClearEditText search;

    @Bind(R.id.title_home)
    TextView titleHome;
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.fanhui)
    LinearLayout fanhui;
    @Bind(R.id.erweima)
    ImageButton erweima;
    @Bind(R.id.bigsearch_button)
    Button bigsearchButton;
    @Bind(R.id.one)
    TextView one;
    @Bind(R.id.two)
    TextView two;
    @Bind(R.id.three)
    TextView three;
    @Bind(R.id.four)
    TextView four;
    @Bind(R.id.five)
    TextView five;
    @Bind(R.id.listView)
    ListView listView;
    private ArrayList<HashMap<String, Object>> newlist;
    private ReporthAdapter adapter;
    private final static int SCANNIN_GREQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.bigsearch);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, HomeFragment.class);

                SearchActivity.this.setResult(RESULT_OK, intent);
//                startActivity(intent);
                SearchActivity.this.finish();
            }
        });



        erweima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(SearchActivity.this, MipcaActivityCapture.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
            }
        });


        if (listView.getHeaderViewsCount() == 0) {
            final View headerView = LayoutInflater.from(SearchActivity.this).inflate(R.layout.reportsearch_listview_header, null);
            listView.addHeaderView(headerView);
        }
        listView.setAdapter(adapter);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    //��ʾɨ�赽������
                    search.setText(bundle.getString("result"));

                }
                break;
        }
    }




    public void click5(View view) {
        //首先获取界面用户输入的用户名和密码

        final String sn = search.getText().toString();
        final String model = one.getText().toString();
        final String station = two.getText().toString();
        final String repair_engneer = three.getText().toString();
        final String datafrom = four.getText().toString();
        final String datato = five.getText().toString();


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
                            Toast.makeText(SearchActivity.this, "查询不成功，该数据不存在", Toast.LENGTH_SHORT).show();
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
                        ReporthAdapter lvAdapter = new ReporthAdapter(SearchActivity.this, newlist);
                        listView.setAdapter(lvAdapter);

                        break;
                    case 1:
                        Toast.makeText(SearchActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
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
            if (SearchActivity.this.getCurrentFocus() != null) {
                if (SearchActivity.this.getCurrentFocus().getWindowToken() != null) {
                    imm.hideSoftInputFromWindow(SearchActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
        return super.onTouchEvent(event);
    }


}
