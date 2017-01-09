package com.app.gaolonglong.fragmenttabhost.Activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.app.gaolonglong.fragmenttabhost.Adapter.DepartAdapter;
import com.app.gaolonglong.fragmenttabhost.LEI.JellyInterpolator;
import com.app.gaolonglong.fragmenttabhost.LEI.LoginService;
import com.app.gaolonglong.fragmenttabhost.MainActivity;
import com.app.gaolonglong.fragmenttabhost.MyApplication;
import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.Service.HttpThread;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Peterliqi on 2016/5/5.
 */
public class LoginActivity extends Activity implements View.OnClickListener {
//    private EditText et_userName;
//    private EditText et_password;
//    private Button login;
//
//
//    private TextView tv;
//    private LinearLayout login1;
//    private Spinner spinner;
//    private SharedPreferences.Editor editor;
//
//    private Context mContext;
//    private View progress;
//    private View mInputLayout;
//    private float mWidth,mHeight;
//    private LinearLayout mName,mPsw;
//
//
//
//
//
//
//
//
//
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        requestWindowFeature(Window.FEATURE_NO_TITLE);//去标题
//        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//设置横屏
//
//
//
//        setContentView(R.layout.activity_login);
//        //找到控件
//        et_password=(EditText) findViewById(R.id.jiushimima) ;
//        et_userName=(EditText) findViewById(R.id.userName);
//        login=(Button)findViewById(R.id.login);
//
//
//        final SharedPreferences sp =getSharedPreferences("config",0);
//
//        //在config.xm中读取 账号密码，显示在控件上
//        String name =sp.getString("userName","");
//        String passworda =sp.getString("passworda","");
//       // String buq = sp.getString("BU","");
//
//
//        et_userName.setText(name);
//        et_password.setText(passworda);
//
//
//
//        login1=(LinearLayout) findViewById(R.id.login1);
//        login.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                InputMethodManager imm = (InputMethodManager)LoginActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
//
//                return imm.hideSoftInputFromWindow(login1.getWindowToken(), 0);
//            }
//        });
//
//       // img_round = (RoundImageView) findViewById(R.id.logo);
//       // Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bc3);
//       // img_round.setBitmap(bitmap);
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//    }
//
//
//
//    public void click1(View view){
//        //首先获取界面用户输入的用户名和密码
//        final String userName=et_userName.getText().toString().trim();
//        final String password=et_password.getText().toString().trim();
//         mWidth = login.getMeasuredWidth();
//        mHeight = login.getMeasuredHeight();
//        // 隐藏输入框
//        mName.setVisibility(View.INVISIBLE);
//        mPsw.setVisibility(View.INVISIBLE);
//
//        inputAnimator(mInputLayout, mWidth, mHeight);
//
//
//
//
//
//        //判断数据是否为空
//
//        if (TextUtils.isEmpty(userName)|| TextUtils.isEmpty(password)){
//            Toast.makeText(LoginActivity.this,"用户名或密码不能为空", Toast.LENGTH_SHORT).show();
//        }
//        else {
//            System.out.println("连接成功");
//
//
//                //如果被选中了，使用SharedPreferences 拿到实例
//                SharedPreferences sp =getSharedPreferences("config",0);
//                //获取SP的编辑器
//                SharedPreferences.Editor edit= sp.edit();
//                edit.putString("userName",userName);
//                edit.putString("passworda",password);
//
//
//
//                //存储一个状态
//               // edit.putBoolean("ischecked",true);
//
//                edit.apply();
//
//
//
//
//
//
//        }
//
//        new Thread(new Runnable() {
//            @Override
//           //开启子线程
//            //调用联网的类
//            public  void run() {
//                try {
//
//
//                    final String result = DepatmentService.loginByPost(userName, password);
//
//
//                    if (result != null) {
//                                        runOnUiThread(new Runnable() {
//                                            @Override
//                                            public void run() {
//
//                                                try {
//
//                                                    JSONObject object3 = new JSONObject(result);
//
//                                                    int code = object3.optInt("code");
//                                                    System.out.print(code);
//
//                                    switch (code) {
//                                        case 0:
//                                            Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
////                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
////                                            intent.putExtra("name",et_userName.getText().toString().trim());
////                                            intent.putExtra("BUS",tv.getText().toString().trim());
////                                            startActivity(intent);
////                                            LoginActivity.this.finish();
//
//                                            break;
//                                        case 1:
//                                            Toast.makeText(LoginActivity.this, "账号不正确", Toast.LENGTH_SHORT).show();
//                                            break;
//                                        case 2:
//                                            Toast.makeText(LoginActivity.this, "BU不正确", Toast.LENGTH_SHORT).show();
//                                            break;
//                                        case 3:
//                                            Toast.makeText(LoginActivity.this, "密码不正确", Toast.LENGTH_SHORT).show();
//                                            break;
//                                    }
//
//
//                                }catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        });
//
//
//
//
//
//
//                    }
//
//
//                    /****
//                     runOnUiThread(new Runnable() {
//                    @Override public void run() {
//
//                    Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();
//                    System.out.println("result=:"+result);
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    LoginActivity.this.finish();
//
//
//
//
//                    }
//                    });
//                     ***/
//
//                    else {
//                        //请求失败 发出消息
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(LoginActivity.this, "请求失败....", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//
//
//        }).start();
//
//
//
//    }
//
//
//
//
//
//
//    private void inputAnimator(final View view, float w, float h) {
//
//        AnimatorSet set = new AnimatorSet();
//
//        ValueAnimator animator = ValueAnimator.ofFloat(0, w);
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                float value = (Float) animation.getAnimatedValue();
//                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view
//                        .getLayoutParams();
//                params.leftMargin = (int) value;
//                params.rightMargin = (int) value;
//                view.setLayoutParams(params);
//            }
//        });
//
//        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout,
//                "scaleX", 1f, 0.5f);
//        set.setDuration(1000);
//        set.setInterpolator(new AccelerateDecelerateInterpolator());
//        set.playTogether(animator, animator2);
//        set.start();
//        set.addListener(new Animator.AnimatorListener() {
//
//            @Override
//            public void onAnimationStart(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                /**
//                 * 动画结束后，先显示加载的动画，然后再隐藏输入框
//                 */
//                progress.setVisibility(View.VISIBLE);
//                progressAnimator(progress);
//                mInputLayout.setVisibility(View.INVISIBLE);
//
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//
//            }
//        });
//
//    }
//
//
//
//
//    /**
//     * 出现进度动画
//     *
//     * @param view
//     */
//    private void progressAnimator(final View view) {
//        PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("scaleX",
//                0.5f, 1f);
//        PropertyValuesHolder animator2 = PropertyValuesHolder.ofFloat("scaleY",
//                0.5f, 1f);
//        ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(view,
//                animator, animator2);
//        animator3.setDuration(1000);
//        animator3.setInterpolator(new JellyInterpolator());
//        animator3.start();
//
//    }
//
//
//
//
//
//
//
//
//
//
//
//
private SharedPreferences.Editor editor;

    private TextView mBtnLogin;

    private View progress;

    private View mInputLayout;

    private float mWidth, mHeight;

    private LinearLayout mName, mPsw;
    private EditText et_userName;
    private EditText et_password;
    private TextView bu,cnn;
    private Button tiaozhuan;
    private ImageView exit;
    private String appname;
    private String VersionCode;
    private String updateurl;
    private String des;
    private ArrayList<HashMap<String, Object>> newlist=null;



    private MyApplication myApplication;
    private TextView upversion;

    private HttpThread jsonThread2;
    private ProgressDialog proDialog;
    private UpdateManager mUpdateManager;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        upversion=(TextView)findViewById(R.id.version_text);
        mBtnLogin = (TextView) findViewById(R.id.main_btn_login);
        progress = findViewById(R.id.layout_progress);
        mInputLayout = findViewById(R.id.input_layout);
        mName = (LinearLayout) findViewById(R.id.input_layout_name);
        mPsw = (LinearLayout) findViewById(R.id.input_layout_psw);
        et_password=(EditText) findViewById(R.id.username) ;
        et_userName=(EditText) findViewById(R.id.password);
        cnn=(TextView)findViewById(R.id.login_cn);

        bu=(TextView)findViewById(R.id.bu);
        exit=(ImageView)findViewById(R.id.exit);

        exit.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);






        checkUpdate(this);

        final SharedPreferences sp =getSharedPreferences("config",0);

        //在config.xm中读取 账号密码，显示在控件上
        String name =sp.getString("userName","");
        String passworda =sp.getString("passworda","");
        et_userName.setText(name);
        et_password.setText(passworda);


//        mUpdateManager = new UpdateManager(this);
//        mUpdateManager.checkUpdateInfo();




    }

    private void checkUpdate(Context context) {
        mUpdateManager = new UpdateManager(context);
        mUpdateManager.checkUpdateInfo();
    }




//
//    public void initAdapter() {
//        String[] key1 = {"2.0"};
//        String[] value1 = {"aaa"};
//        String url1 = "http://10.132.45.177:14494//ServerMonitor/aaa";
//        Search(key1, value1, url1);
//    }
//
//    public void Search(String[] key1, String[] value1, String url1) {
//        proDialog = ProgressDialog.show(LoginActivity.this, "", "正在获取数据……", true,
//                true);
//        jsonThread2 = new HttpThread(LoginActivity.this, handler);
//        String[] jsonKey2 = {"failReason", "number"};
//        String jsonName2 = "data";
//        // 传递相应的参数
//        jsonThread2.setUrl(url1);
//        jsonThread2.setKey(key1);
//        jsonThread2.setValue(value1);
//        jsonThread2.setJsonName(jsonName2);
//        jsonThread2.setJsonKey(jsonKey2);
//        jsonThread2.start();
//
//
//    }
//








//private void sendRequestWithOkHttp(){
//    new Thread(new Runnable() {
//        @Override
//        public void run() {
//            try {
//                OkHttpClient client = new OkHttpClient();
//                Request request = new Request.Builder().url("http://10.132.44.79:8083//ServerMonitor/aaa?aaa=2").build();
//                Response response = client.newCall(request).execute();
//                String responseData = response.body().string();
//                parseJSONWithJSONObject(responseData);
//
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//
//        }
//    }).start();
//
//}
//
//    private void parseJSONWithJSONObject(String jsonData) {
//        try {
//            JSONObject object3 = new JSONObject(jsonData);
//            int VersionCode = object3.optInt("VersionCode");
//            String appname=object3.optString("appname");
//            String updateurl=object3.optString("appname");
//            String des=object3.optString("appname");
//
//            switch (VersionCode) {
//                case 2:
//                    AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
//                    dialog.setTitle(appname);
//                    dialog.setMessage(des);
//                    dialog.setCancelable(false);
//                    dialog.setPositiveButton("更新", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            String url = "http://10.132.44.79:8083/files/app-debug.apk";
//                            downloadBinder.startDownload(url);
//                            dialog.dismiss();
//                        }
//
//
//                    });
//                    dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//                    dialog.show();
//                    break;
//                case 3:
//                    Toast.makeText(LoginActivity.this, "已经是最新版本了", Toast.LENGTH_SHORT).show();
//                    break;
//            }
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//    }




    /**
     * 输入框的动画效果
     *
     * @param view
     *      控件
     * @param w
     *      宽
     * @param h
     *      高
     */
    private void inputAnimator(final View view, float w, float h) {

        AnimatorSet set = new AnimatorSet();

        final ValueAnimator animator = ValueAnimator.ofFloat(0, w);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view
                        .getLayoutParams();
                params.leftMargin = (int) value;
                params.rightMargin = (int) value;
                view.setLayoutParams(params);
            }
        });

        final ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout,
                "scaleX", 1f, 0.5f);
        set.setDuration(1000L);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.playTogether(animator, animator2);
        set.start();
        set.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                /**
                 * 动画结束后，先显示加载的动画，然后再隐藏输入框
                 */

                progress.setVisibility(View.VISIBLE);
                progressAnimator(progress);
                mInputLayout.setVisibility(View.INVISIBLE);



            }

            @Override
            public void onAnimationCancel(Animator animation) {


            }
        });

    }





    /**
     * 出现进度动画
     *
     * @param view
     */
    private void progressAnimator(final View view) {
        PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("scaleX",
                0.5f, 1f);
        PropertyValuesHolder animator2 = PropertyValuesHolder.ofFloat("scaleY",
                0.5f, 1f);
        ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(view,
                animator, animator2);
        animator3.setDuration(1000);

        animator3.setInterpolator(new JellyInterpolator());
        animator3.start();


    }





    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_btn_login:


            // 计算出控件的高与宽
            mWidth = mBtnLogin.getMeasuredWidth();
            mHeight = mBtnLogin.getMeasuredHeight();
            // 隐藏输入框
            mName.setVisibility(View.INVISIBLE);
            mPsw.setVisibility(View.INVISIBLE);

            inputAnimator(mInputLayout, mWidth, mHeight);



            //首先获取界面用户输入的用户名和密码
            final String userName = et_userName.getText().toString().trim();
            final String password = et_password.getText().toString().trim();
            final String BU = bu.getText().toString().trim();


            //判断数据是否为空

            if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
                Toast.makeText(LoginActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
            } else {
                System.out.println("连接成功");


                //如果被选中了，使用SharedPreferences 拿到实例
                SharedPreferences sp = getSharedPreferences("config", 0);
                //获取SP的编辑器
                SharedPreferences.Editor edit = sp.edit();
                edit.putString("userName", userName);
                edit.putString("passworda", password);


                //存储一个状态
                // edit.putBoolean("ischecked",true);

                edit.apply();


            }
            new Thread(new Runnable() {
                @Override
                //开启子线程
                //调用联网的类
                public void run() {
                    try {

                        Thread.sleep(2000);

                        final String result = LoginService.loginByPost(userName, password, BU);


                        if (result != null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    try {

                                        JSONObject object3 = new JSONObject(result);

                                        int code = object3.optInt("code");
                                        String cn=object3.optString("chinesename")+"";
                                        cnn.setText(cn);
                                        System.out.print(code);


                                        switch (code) {
                                            case 0:

                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                intent.putExtra("name",et_userName.getText().toString().trim());
                                                intent.putExtra("cn",cnn.getText().toString().trim());
                                                startActivity(intent);
                                                LoginActivity.this.finish();

                                                break;
                                            case 1:

                                                Toast.makeText(LoginActivity.this, "账号不正确", Toast.LENGTH_SHORT).show();
                                                break;
                                            case 2:
                                                Toast.makeText(LoginActivity.this, "BU不正确", Toast.LENGTH_SHORT).show();
                                                break;
                                            case 3:
                                                Toast.makeText(LoginActivity.this, "密码不正确", Toast.LENGTH_SHORT).show();
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



                                    AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
                                    dialog.setTitle("错误");
                                    dialog.setMessage("无法连接网络");
                                    dialog.setCancelable(false);
                                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            dialog.dismiss();
                                        }


                                    });
                                    dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            LoginActivity.this.finish();
                                        }
                                    });
                                    dialog.show();














                                    Toast.makeText(LoginActivity.this, "请求失败....", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            }).start();


                break;

            case R.id.exit:
                finish();
                break;

            default:
                break;


        }








    }


























    /***
     * int resultCode=result.resultCode();
     ***/
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (LoginActivity.this.getCurrentFocus() != null) {
                if (LoginActivity.this.getCurrentFocus().getWindowToken() != null) {
                    imm.hideSoftInputFromWindow(LoginActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
        return super.onTouchEvent(event);
    }







}




