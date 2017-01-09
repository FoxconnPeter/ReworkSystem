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
public class DepartAdapter extends BaseAdapter {
    private ArrayList<HashMap<String, Object>> infoList;
    private LayoutInflater inflater;
    private Context context;

    public DepartAdapter(Context context , ArrayList<HashMap<String, Object>> infoList) {
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


            convertView=this.inflater.inflate(R.layout.department_listview_item,null);
            viewHolder=new ViewHolder();

            viewHolder.departmentTextView=(TextView)convertView.findViewById(R.id.depart_depart);
            viewHolder.BUTextView=(TextView)convertView.findViewById(R.id.depart_bu);
            viewHolder.nameTextView=(TextView)convertView.findViewById(R.id.depart_name);
            viewHolder.mailTextView=(TextView)convertView.findViewById(R.id.depart_mail);
            viewHolder.gonghaoTextview=(TextView)convertView.findViewById(R.id.depart_gonghao);


            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
       try{
           String gonghao=infoList.get(position).get("userName").toString();
           String mail=infoList.get(position).get("mailAdd").toString();
           String name=infoList.get(position).get("chineseName").toString();
           String BU=infoList.get(position).get("BU").toString()+"";
           String depart=infoList.get(position).get("department").toString();



           viewHolder.gonghaoTextview.setText(gonghao);
           viewHolder.mailTextView.setText(mail);
           viewHolder.nameTextView.setText(name);
           viewHolder.BUTextView.setText(BU);
           viewHolder.departmentTextView.setText(depart);



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

        TextView gonghaoTextview,mailTextView,nameTextView,BUTextView,departmentTextView;

    }
}