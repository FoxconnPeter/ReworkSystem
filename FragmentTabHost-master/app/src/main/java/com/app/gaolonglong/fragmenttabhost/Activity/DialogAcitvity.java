package com.app.gaolonglong.fragmenttabhost.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.gaolonglong.fragmenttabhost.Adapter.DepartAdapter;
import com.app.gaolonglong.fragmenttabhost.LEI.LoginService;
import com.app.gaolonglong.fragmenttabhost.MainActivity;
import com.app.gaolonglong.fragmenttabhost.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/12/13.
 */

public class DialogAcitvity extends Activity {
    public String name;
    @Bind(R.id.tvoldmima)
    TextView tvoldmima;
    @Bind(R.id.oldmima)
    EditText oldmima;
    @Bind(R.id.tvnewmima)
    TextView tvnewmima;
    @Bind(R.id.newmima)
    EditText newmima;
    @Bind(R.id.tvsuremima)
    TextView tvsuremima;
    @Bind(R.id.surenewmima)
    EditText surenewmima;
    @Bind(R.id.dialog_exit)
    Button dialogExit;
    @Bind(R.id.sure)
    Button sure;
    @Bind(R.id.tccc)
    TextView tccc;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_mima);
        ButterKnife.bind(this);


        dialogExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   finish();
            }
        });


        Message msg = new Message();
        msg.what = 0;
        handler.sendMessage(msg);




        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String name = getIntent().getStringExtra("name");
                final String password = oldmima.getText().toString().trim();
                final String newpassword = newmima.getText().toString().trim();

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                                //调用Search上网类.
                                final String fanhui = LoginService.mimaxiugai(name, password, newpassword);
                                if (fanhui != null) {
                                    try {
                                        JSONObject object3 = new JSONObject(fanhui);

                                        int code = object3.optInt("code");
                                        System.out.print(code);

                                        if (code == 1 ){
                                            Looper.prepare();
                                            Toast.makeText(DialogAcitvity.this, "密码错误", Toast.LENGTH_SHORT).show();
                                            Looper.loop();

                                        }
                                        else if (code==0){
                                            Looper.prepare();
                                            Toast.makeText(DialogAcitvity.this, "密码修改成功", Toast.LENGTH_SHORT).show();
                                            DialogAcitvity.this.finish();
                                            Looper.loop();

                                        }
                                        else if (code==2){
                                            Looper.prepare();
                                            Toast.makeText(DialogAcitvity.this, "失败！！！密码与旧密码相同", Toast.LENGTH_SHORT).show();
                                            Looper.loop();
                                        }




                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);
                                    }


                                }








                    }
                }).start();




            }
        });


    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            try {
                switch (msg.what) {
                    case 0:


                        break;
                    case 1:
                        name = getIntent().getStringExtra("name");
                        tccc.setText(name);

                    default:
                        break;
                }
            } catch (Exception e) {

                e.printStackTrace();
            }
            super.handleMessage(msg);
        }
    };












}
