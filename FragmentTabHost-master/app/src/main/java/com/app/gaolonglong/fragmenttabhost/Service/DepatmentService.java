package com.app.gaolonglong.fragmenttabhost.Service;





import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Peterliqi on 2016/5/5.
 */
public class DepatmentService {
    int code = -1;
    public int getCode(){




        return code;


    }









    public static String departmentsearch(String keyword){
        try {
            String path= "http://10.132.44.79:8083/semsys/userInfo?keyword="+ URLEncoder.encode(keyword,"UTF-8");

            URL url=new URL(path);
            HttpURLConnection conn=(HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            int code = conn.getResponseCode();

            if (code==200){
                InputStream is=conn.getInputStream();
                String text= StreamTools.readInputStream(is);
                return text;
            }else {
                return null;
            }


        }catch (Exception e){
            e.printStackTrace();
        }
        return null;






    }

    public static String departmentsearchall(String keyword){
        try {
            String path= "http://10.132.44.79:8083/semsys/userInfo?keyword="+ URLEncoder.encode(keyword,"UTF-8");

            URL url=new URL(path);
            HttpURLConnection conn=(HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            int code = conn.getResponseCode();

            if (code==200){
                InputStream is=conn.getInputStream();
                String text= StreamTools.readInputStream(is);
                return text;
            }else {
                return null;
            }


        }catch (Exception e){
            e.printStackTrace();
        }
        return null;






    }






    public static String successservice(String name,String feedback){
        try {
            String path= "http://10.132.44.79:8083//ServerMonitor/Feedback?name="+ URLEncoder.encode(name,"UTF-8")+"&feedback="+ URLEncoder.encode(feedback,"UTF-8");

            URL url=new URL(path);
            HttpURLConnection conn=(HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            int code = conn.getResponseCode();

            if (code==200){
                InputStream is=conn.getInputStream();
                String text= StreamTools.readInputStream(is);
                return text;
            }else {
                return null;
            }


        }catch (Exception e){
            e.printStackTrace();
        }
        return null;






    }










}
