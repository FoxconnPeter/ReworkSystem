package com.app.gaolonglong.fragmenttabhost.LEI;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by Peterliqi on 2016/5/5.
 */
public class StreamTools {
    //把输入流的内容转成字符串
    public static String readInputStream(InputStream is){
        try {
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            int len=0;
            byte[] buffer=new byte[1024];
            while ((len=is.read(buffer))!=-1){
                baos.write(buffer,0,len);
            }
            is.close();
            baos.close();
            byte[] result=baos.toByteArray();
            //试着解析 result里面的字符串
            String temp=new String(result);
            return temp;
        }catch (Exception e){
            e.printStackTrace();
            return "获取失败";
        }
    }
}
