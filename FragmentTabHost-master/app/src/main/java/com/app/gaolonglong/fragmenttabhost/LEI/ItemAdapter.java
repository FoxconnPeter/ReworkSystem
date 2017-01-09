package com.app.gaolonglong.fragmenttabhost.LEI;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;

import java.util.List;

/**
 * Created by Administrator on 2016/12/8.
 */

public class ItemAdapter extends ArrayAdapter<Item> {
    private int resourceId;

    public ItemAdapter(Context context, int textViewResourceId, List<Item> objects) {
        super(context, textViewResourceId, objects);
        resourceId= textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Item item = getItem(position);//获取当前实例
        View view;
        RecyclerView.ViewHolder viewHolder;
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);


        }
        else {
            view = convertView;
        }

        ImageView itemImage=(ImageView)view.findViewById(R.id.img);
        TextView itemtv=(TextView)view.findViewById(R.id.tv);
        TextView iteminfo=(TextView)view.findViewById(R.id.info);
        itemImage.setImageResource(item.getImageId());
        itemtv.setText(item.getName());
        iteminfo.setText(item.getDes());

        return view;

    }


}
