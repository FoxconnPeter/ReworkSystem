package com.app.gaolonglong.fragmenttabhost.LEI;





import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Peterliqi on 2016/5/5.
 */
public class LoginService {
    int code = -1;
    public int getCode(){




        return code;


    }


    public static String loginByPost (String userName, String password ,String BU){
        try {
            String path=
                    "http://10.132.44.79:8083/semsys/login?userName="+ URLEncoder.encode(userName,"UTF-8")
                            +"&password="+ URLEncoder.encode(password)+"&BU="+URLEncoder.encode(BU);
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








    public static String mimaxiugai(String name, String password,String newpassword){
        try {
            String path=
                    "http://10.132.44.79:8083//ServerMonitor/RevisePassword?name="+ URLEncoder.encode(name,"UTF-8")
                            +"&password="+ URLEncoder.encode(password)+"&newpassword="+ URLEncoder.encode(newpassword);
            URL url=new URL(path);
            HttpURLConnection conn=(HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            int code = conn.getResponseCode();

            if (code==200){
                InputStream is=conn.getInputStream();
                String text= com.app.gaolonglong.fragmenttabhost.Service.StreamTools.readInputStream(is);
                return text;
            }else {
                return null;
            }


        }catch (Exception e){
            e.printStackTrace();
        }
        return null;






    }



    public static String checkinservice(String sn, String time,String failReason){
        try {
            String path=
                    "http://10.132.44.79:8083//ServerMonitor/CheckinServl?sn="+ URLEncoder.encode(sn,"UTF-8")
                            +"&time="+ URLEncoder.encode(time)+"&failReason="+ URLEncoder.encode(failReason);
            URL url=new URL(path);
            HttpURLConnection conn=(HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            int code = conn.getResponseCode();

            if (code==200){
                InputStream is=conn.getInputStream();
                String text= com.app.gaolonglong.fragmenttabhost.Service.StreamTools.readInputStream(is);
                return text;
            }else {
                return null;
            }


        }catch (Exception e){
            e.printStackTrace();
        }
        return null;






    }


    public static String checkoutservice(String time,String sn, String model,String partsn,String station,
                                         String repair_engneer,String location,String description,String type,
                                         String failReason,String manufacturer,String lc,String dc){
        try {
            String path=
                    "http://10.132.44.79:8083//ServerMonitor/CheckoutServl?time="+ URLEncoder.encode(time)
                            +"&sn="+ URLEncoder.encode(sn)+"&model="+ URLEncoder.encode(model)
                            +"&partsn="+ URLEncoder.encode(partsn)+"&station="+ URLEncoder.encode(station)
                            +"&repair_engneer="+ URLEncoder.encode(repair_engneer)+"&location="+ URLEncoder.encode(location)
                            +"&description="+ URLEncoder.encode(description)+"&type="+ URLEncoder.encode(type)
                            +"&failReason="+ URLEncoder.encode(failReason)+"&manufacturer="+ URLEncoder.encode(manufacturer)+"&dc="+ URLEncoder.encode(dc)
                            +"&lc="+ URLEncoder.encode(lc);
            URL url=new URL(path);
            HttpURLConnection conn=(HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            int code = conn.getResponseCode();

            if (code==200){
                InputStream is=conn.getInputStream();
                String text= com.app.gaolonglong.fragmenttabhost.Service.StreamTools.readInputStream(is);
                return text;
            }else {
                return null;
            }


        }catch (Exception e){
            e.printStackTrace();
        }
        return null;






    }


    public static String wipsearch(String Date){
        try {
            String path=
                    "http://10.132.44.79:8083//ServerMonitor/InlistServl?Date="+ URLEncoder.encode(Date);

            URL url=new URL(path);
            HttpURLConnection conn=(HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            int code = conn.getResponseCode();

            if (code==200){
                InputStream is=conn.getInputStream();
                String text= com.app.gaolonglong.fragmenttabhost.Service.StreamTools.readInputStream(is);
                return text;
            }else {
                return null;
            }


        }catch (Exception e){
            e.printStackTrace();
        }
        return null;






    }

    public static String deleteservice(String sn){
        try {
            String path=
                    "http://10.132.44.79:8083//ServerMonitor/delete?sn="+ URLEncoder.encode(sn);

            URL url=new URL(path);
            HttpURLConnection conn=(HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            int code = conn.getResponseCode();

            if (code==200){
                InputStream is=conn.getInputStream();
                String text= com.app.gaolonglong.fragmenttabhost.Service.StreamTools.readInputStream(is);
                return text;
            }else {
                return null;
            }


        }catch (Exception e){
            e.printStackTrace();
        }
        return null;






    }





    public static String reportsearch(String sn, String model,String station,
                                         String repair_engneer,String datafrom,String datato){
        try {
            String path=
                    "http://10.132.44.79:8083//ServerMonitor/OutlistServl?sn="+ URLEncoder.encode(sn)
                            +"&model="+ URLEncoder.encode(model)+"&station="+ URLEncoder.encode(station)
                            +"&repair_engneer="+ URLEncoder.encode(repair_engneer)+"&datafrom="+ URLEncoder.encode(datafrom)
                            +"&datato="+ URLEncoder.encode(datato);
            URL url=new URL(path);
            HttpURLConnection conn=(HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            int code = conn.getResponseCode();

            if (code==200){
                InputStream is=conn.getInputStream();
                String text= com.app.gaolonglong.fragmenttabhost.Service.StreamTools.readInputStream(is);
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
