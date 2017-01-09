package com.app.gaolonglong.fragmenttabhost.View;

import android.content.Context;
import android.widget.Toast;

/**
 * 封装tost工具类
 * Time on 16-8-4.
 */
public class Util {
    private static Toast toast;

    public static void showTosat(Context context, String content){
        if(toast == null){
            toast = Toast.makeText(context,content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content) ;
        }
        toast.show();
    }
}
