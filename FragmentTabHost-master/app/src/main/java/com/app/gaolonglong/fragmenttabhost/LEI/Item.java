package com.app.gaolonglong.fragmenttabhost.LEI;

/**
 * Created by Administrator on 2016/12/8.
 */

public class Item {
    private String name;
    private String des;
    private int imageId;

    public Item(String name, String des, int imageId) {
        this.name=name;
        this.des=des;
        this.imageId=imageId;
    }
    public String getName(){
        return name;
    }
    public int getImageId(){
        return imageId;
    }
    public String getDes(){
        return des;
    }

}
