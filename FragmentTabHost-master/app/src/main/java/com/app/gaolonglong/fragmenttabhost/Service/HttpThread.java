package com.app.gaolonglong.fragmenttabhost.Service;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 16-6-22.
 */
public class HttpThread extends Thread {
    private Context context;

    private ProgressDialog proDialog;

    private String url;

    private String[] key;

    private String[] value;

    private String[] jsonKey;

    private String jsonName;

    private String webContent;

    private String[] array;

    ArrayList<HashMap<String, Object>> list;
    ArrayList<HashMap<String, Object>> map;

    private Handler handler;

    public HttpThread(Context context, ProgressDialog proDialog, Handler handler) {

        this.context = context;

        this.proDialog = proDialog;

        this.handler = handler;
    }

    public HttpThread(Context context, Handler handler) {

        this.context = context;

        this.handler = handler;
    }

    public HttpThread(Handler handler) {

        this.handler = handler;
    }

    @Override
    public void run() {

        Message msg = handler.obtainMessage();

        HttpPostRequest post = new HttpPostRequest();

        int res = post.requestHttp(url, key, value);

        webContent = post.getWebContext();

        msg.what = res;

        if (res == 1) {

            // 解析json

            Json json = new Json();

            if (jsonName != null) {

                // 解析数组型的json

                list = json.getJSONArray(webContent, jsonKey, jsonName);
            } else {

                // 解析单个json值

                array = json.getJSON(webContent.toString(), jsonKey);

            }
        }

        handler.sendMessage(msg);

    }

    // activity界面传递的参数

    public String getUrl() {

        return url;

    }

    public void setUrl(String url) {

        this.url = url;

    }

    public String[] getKey() {

        return key;

    }

    public void setKey(String[] key) {

        this.key = key;

    }

    public String[] getValue() {

        return value;

    }

    public void setValue(String[] value) {

        this.value = value;

    }

    public String[] getJsonKey() {

        return jsonKey;

    }

    public void setJsonKey(String[] jsonKey) {

        this.jsonKey = jsonKey;

    }

    public String getJsonName() {

        return jsonName;

    }

    public void setJsonName(String jsonName) {

        this.jsonName = jsonName;

    }

    public String[] getArray() {

        return array;

    }

    public ArrayList<HashMap<String, Object>> getList() {

        return list;
    }

}
