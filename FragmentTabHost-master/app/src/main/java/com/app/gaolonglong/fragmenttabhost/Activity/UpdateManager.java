package com.app.gaolonglong.fragmenttabhost.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.Service.HttpThread;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 16-7-1.
 */
public class UpdateManager {
    private Context mContext;
    //提示语
    private String updateMsg = "新版本";
    //返回安装包url
    private String apkUrl = "";
    private Dialog noticeDialog;
    private Dialog downloadDialog;
    //下载包安装路径
    private static final String savePath = "/sdcard/updatedemo";
    private static final String saveFileName = savePath + "UpdateDemoRelease.apk";
    //进度条与通知UI刷新的handler和msg常量
    private ProgressBar mProgressBar;
    private boolean updateFlag;
    private static final int VERSION_CHECK = 1;
    private static final int DOWN_UPDATE = 2;
    private static final int DOWN_OVER = 3;
    private int progress;
    private String[] array;
    private HttpThread jsonThread;
    private Thread downLoadThread;
    private AlertDialog.Builder builder;
    private boolean interceptFlag = false;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWN_UPDATE:
                    mProgressBar.setProgress(progress);
                    break;
                case DOWN_OVER:
                    installApk();
                    break;
                case VERSION_CHECK:
                    versionCheck();
                    break;
                default:
                    break;
            }
        }
    };

    public UpdateManager(Context context) {
        this.mContext = context;
    }

    //外部接口让主Activity调用
    public void checkUpdateInfo() {
        isUpdate();
    }

    /**
     * 检查软件是否有更新版本
     *
     * @return
     */
    private void versionCheck() {
        final int versionCode = getVersionCode(mContext);
        //处理单个字符json
        array = jsonThread.getArray();
        if (array != null) {
            final String re = "ok";
            if (array[2].equals(re)) {
                apkUrl = array[0];
                if (Integer.parseInt(array[1]) > versionCode) {
                    showNoticeDialog();
                }
            }
        }
    }

    private void isUpdate() {
        // 获取当前软件版本
        jsonThread = new HttpThread(mContext, mHandler);
        String url = "http://10.132.44.79:8083//ServerMonitor/aaa";
        // url传递参数
        String[] key = {"type"};
        String[] value = {"0"};
        // php页面返回的json键
        String[] jsonKey = {"apkurl", "version", "result", "content"};
        //传递相应的参数
        jsonThread.setUrl(url);
        jsonThread.setKey(key);
        jsonThread.setValue(value);
        jsonThread.setJsonKey(jsonKey);
        jsonThread.start();
    }

    /**
     * 获取软件版本号
     *
     * @param context
     * @return
     */
    private int getVersionCode(Context context) {
        int versionCode = 0;
        try { // 获取软件版本号，
            versionCode = context.getPackageManager().getPackageInfo("com.app.gaolonglong.fragmenttabhost", 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    private void showNoticeDialog() {
        builder = new AlertDialog.Builder(mContext);
        builder.setTitle(apkUrl);
        builder.setMessage(updateMsg + array[3]);
        builder.setPositiveButton("立即下载", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                showDownloadDialog();
            }
        });
        builder.setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        noticeDialog = builder.create();
        noticeDialog.show();
    }

    private void showDownloadDialog() {
        builder = new AlertDialog.Builder(mContext);
        builder.setTitle("软件下载");
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.progress, null);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress);
        builder.setView(view);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                interceptFlag = true;
            }
        });
        downloadDialog = builder.create();
        downloadDialog.show();
        downloadApk();
    }

    private Runnable mdownApkRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                URL url = new URL(apkUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                int length = conn.getContentLength();
                InputStream is = conn.getInputStream();

                File file = new File(savePath);
                if (!file.exists()) {
                    file.mkdir();
                }
                String apkFile = saveFileName;
                File ApkFile = new File(apkFile);
                FileOutputStream fos = new FileOutputStream(apkFile);
                int count = 0;
                byte buf[] = new byte[1024];
                do {
                    int numread = is.read(buf);
                    count += numread;
                    progress = (int) (((float) count / length) * 100);
                    //更新进度
                    mHandler.sendEmptyMessage(DOWN_UPDATE);
                    if (numread <= 0) {
                        //下载完成通知安装
                        mHandler.sendEmptyMessage(DOWN_OVER);
                        break;
                    }
                    fos.write(buf, 0, numread);
                } while (!interceptFlag);//点击取消就停止下载.
                fos.close();
                is.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };


    /**
     * 下载apk
     */
    private void downloadApk() {
        downLoadThread = new Thread(mdownApkRunnable);
        downLoadThread.start();
    }

    /**
     * 安装apk
     */
    public boolean getStatus() {
        return interceptFlag;
    }

    private void installApk() {
        File apkfile = new File(saveFileName);
        if (!apkfile.exists()) {
            return;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        mContext.startActivity(i);
    }
}

