package com.app.gaolonglong.fragmenttabhost.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Peterliqi on 5/31/2016.
 */
public class WIsearchAdapter extends BaseAdapter {
    private ArrayList<HashMap<String, Object>> infoList;
    private LayoutInflater inflater;
    private Context context;

    public WIsearchAdapter(Context context , ArrayList<HashMap<String, Object>> infoList) {
        super();
        this.inflater= LayoutInflater.from(context);
        this.context = context;
        this.infoList=infoList;

    }


    @Override
    public int getCount() {
        return infoList==null?0:infoList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.infoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder=null;

        if (convertView == null){


            convertView=this.inflater.inflate(R.layout.wipsearch_listview_item,null);
            viewHolder=new ViewHolder();

            viewHolder.timeTextView=(TextView)convertView.findViewById(R.id.wipsearch_item_time);
            viewHolder.leixingTextView=(TextView)convertView.findViewById(R.id.wipsearch_item_leixing);
            viewHolder.snTextView=(TextView)convertView.findViewById(R.id.wipsearch_item_sn);


            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
       try{
           String time=infoList.get(position).get("time").toString();
           String leixing=infoList.get(position).get("failReason").toString();
           String sn=infoList.get(position).get("sn").toString();




           viewHolder.timeTextView.setText(time);
           viewHolder.leixingTextView.setText(leixing);
           viewHolder.snTextView.setText(sn);




       }catch (Exception e){
           e.printStackTrace();
       }




        int[]colors={Color.WHITE, Color.rgb(219,238,244)};
        convertView.setBackgroundColor(colors[position%2]);
        return convertView;
    }
    private class ViewHolder{
        ViewHolder(){

        }

        TextView timeTextView,leixingTextView,snTextView;

    }
}