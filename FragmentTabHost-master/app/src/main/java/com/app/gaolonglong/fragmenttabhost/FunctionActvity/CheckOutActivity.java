package com.app.gaolonglong.fragmenttabhost.FunctionActvity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.gaolonglong.fragmenttabhost.Activity.SuccessActivity;
import com.app.gaolonglong.fragmenttabhost.Adapter.SucessAdapter;
import com.app.gaolonglong.fragmenttabhost.LEI.LoginService;
import com.app.gaolonglong.fragmenttabhost.MainActivity;
import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.View.ClearEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/12/15.
 */

public class CheckOutActivity extends Activity {
    @Bind(R.id.title_home)
    TextView titleHome;
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.checkout_time)
    EditText checkoutTime;
    @Bind(R.id.checkout_model)
    ClearEditText checkoutModel;
    @Bind(R.id.checkout_station)
    ClearEditText checkoutStation;
    @Bind(R.id.checkout_partsn)
    ClearEditText checkoutPartsn;
    @Bind(R.id.checkout_sn)
    ClearEditText checkoutSn;
    @Bind(R.id.checkout_location)
    ClearEditText checkoutLocation;
    @Bind(R.id.checkout_des)
    ClearEditText checkoutDes;
    @Bind(R.id.checkout_endneer)
    ClearEditText checkoutEndneer;
    @Bind(R.id.spiner_edit_leixing)
    ClearEditText spinerEditLeixing;
    @Bind(R.id.checkout_spinner_leixing)
    Spinner checkoutSpinnerLeixing;
    @Bind(R.id.spiner_edit_xianxiang)
    ClearEditText spinerEditXianxiang;
    @Bind(R.id.checkout_spinner_xianxiang)
    Spinner checkoutSpinnerXianxiang;
    @Bind(R.id.checkin_button)
    Button checkinButton;
    @Bind(R.id.spiner_text)
    TextView spinerText;
    @Bind(R.id.checkout_dc)
    ClearEditText checkoutDc;
    @Bind(R.id.checkout_lc)
    ClearEditText checkoutLc;
    @Bind(R.id.checkout_changshang)
    ClearEditText checkoutChangshang;

    private final static int SCANNIN_GREQUEST_CODE = 1;
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_checkout);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CheckOutActivity.this, MainActivity.class);
                intent.putExtra(result, "myname");
                CheckOutActivity.this.setResult(RESULT_OK, intent);

                CheckOutActivity.this.finish();


            }
        });
//        Time time = new Time("GMT+8");
//        time.setToNow();
//        int year = time.year;
//        int month = time.month;
//        int day = time.monthDay;
//        int minute = time.minute;
//        int hour = time.hour;
//
//        checkoutTime.setText(year +
//                "-" + month +
//                "-" + day +
//                " " + hour +
//                ":" + minute);
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
        checkoutTime.setText(ee);

//                checkoutTime.setText(year +
//                "-" + month +
//                "-" + day +
//                " " + hour +
//                ":" + minute);


        checkoutTime.setEnabled(false);


        checkoutSpinnerLeixing.getSelectedItem();
        checkoutSpinnerLeixing.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                // String[] languages = getResources().getStringArray(R.array.ctype);
                String BU = parent.getItemAtPosition(pos).toString();
                spinerEditLeixing.setText(BU);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }

        });


        checkoutSpinnerXianxiang.getSelectedItem();
        checkoutSpinnerXianxiang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                // String[] languages = getResources().getStringArray(R.array.ctype);
                String BU = parent.getItemAtPosition(pos).toString();
                spinerEditXianxiang.setText(BU);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }

        });


    }


    public void click1(View view) {
        //首先获取界面用户输入的用户名和密码
        final String time = checkoutTime.getText().toString().trim();
        final String sn = checkoutSn.getText().toString().trim();
        final String model = checkoutModel.getText().toString().trim();
        final String partsn = checkoutPartsn.getText().toString().trim();
        final String station = checkoutStation.getText().toString().trim();
        final String repair_engneer = checkoutEndneer.getText().toString().trim();
        final String location = checkoutLocation.getText().toString().trim();
        final String description = checkoutDes.getText().toString().trim();
        final String type = spinerEditLeixing.getText().toString().trim();
        final String failReason = spinerEditXianxiang.getText().toString().trim();
        final String manufacturer= checkoutChangshang.getText().toString().trim();
        final String lc=checkoutLc.getText().toString().trim();
        final String dc=checkoutDc.getText().toString().trim();

        String liyou = "其他(选择此项请手动输入)";
        String leixing = "选择不良类型";
        String xianxiang = "选择不良现象";
        if (TextUtils.isEmpty(sn) || TextUtils.isEmpty(model) || TextUtils.isEmpty(partsn) || TextUtils.isEmpty(station) ||
                TextUtils.isEmpty(repair_engneer) || TextUtils.isEmpty(location) ||
                TextUtils.isEmpty(description) || TextUtils.isEmpty(type)|| TextUtils.isEmpty(lc) || TextUtils.isEmpty(dc)|| TextUtils.isEmpty(manufacturer)|| TextUtils.isEmpty(failReason)) {
            Toast.makeText(CheckOutActivity.this, "不能有空的数据", Toast.LENGTH_SHORT).show();
        } else if (failReason.equals(xianxiang) || type.equals(leixing) || failReason.equals(liyou)) {
            Toast.makeText(CheckOutActivity.this, "手动输入项有误", Toast.LENGTH_SHORT).show();

        } else {


            new Thread(new Runnable() {
                @Override
                //开启子线程
                //调用联网的类
                public void run() {
                    try {


                        final String result = LoginService.checkoutservice(time, sn, model, partsn, station, repair_engneer, location, description
                                , type, failReason,lc,dc,manufacturer);


                        if (result != null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    try {

                                        JSONObject object3 = new JSONObject(result);

                                        int code = object3.optInt("code");
                                        System.out.print(code);




                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }





                                }
                            });

                            Message msg = new Message();
                            msg.what = 0;

                            handler.sendMessage(msg);


                        }




                        else {
                            //弹出提示消息
                            Message msg = new Message();
                            msg.what = 1;
                            handler.sendMessage(msg);


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

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            }).start();
        }


    }




    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            try {
                switch (msg.what) {

                    case 0:

                        Toast.makeText(CheckOutActivity.this, "提交成功", Toast.LENGTH_SHORT).show();

                        break;

                    case 1:
                        Toast.makeText(CheckOutActivity.this, "提交失败", Toast.LENGTH_SHORT).show();
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
