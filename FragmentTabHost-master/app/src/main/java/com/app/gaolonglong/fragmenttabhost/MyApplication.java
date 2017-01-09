package com.app.gaolonglong.fragmenttabhost;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by Administrator on 2016/12/15.
 */

public class MyApplication extends Application {
    private static Context context;
    public static int localVersion = 0;// 本地安装版本

    public static int serverVersion = 2;// 服务器版本

    public static String downloadDir = "jj/";// 安装目录



    @Override
    public void onCreate(){



        context=getApplicationContext();

        super.onCreate();
        try {
            PackageInfo packageInfo = getApplicationContext()
                    .getPackageManager().getPackageInfo(getPackageName(), 0);
            localVersion = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }



    }
    public static Context getContext(){
        return context;
    }

}