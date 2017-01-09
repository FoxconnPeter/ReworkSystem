package com.app.gaolonglong.fragmenttabhost.SWIP;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.gaolonglong.fragmenttabhost.FunctionActvity.Car;
import com.app.gaolonglong.fragmenttabhost.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Peterliqi on 5/31/2016.
 */
public class MyBaseAdapter extends BaseAdapter {
    private ArrayList<HashMap<String, Object>> infoList;
    private LayoutInflater inflater;
    private float down_x, move_x;
    private boolean closeOnclick = false;
    private boolean isOpen = true;
    private Context context;



    public MyBaseAdapter(Context context , ArrayList<HashMap<String, Object>> infoList) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;


        if (convertView == null){


            convertView=this.inflater.inflate(R.layout.reportsearch_listview_item,null);
            viewHolder=new ViewHolder();


            viewHolder.timefromTextView=(TextView)convertView.findViewById(R.id.report_item_time);
            viewHolder.leixingTextView=(TextView)convertView.findViewById(R.id.report_item_leixing);
            viewHolder.snTextView=(TextView)convertView.findViewById(R.id.report_item_sn);
            viewHolder.locationTextView=(TextView)convertView.findViewById(R.id.report_item_location);
            viewHolder.liyouTextView=(TextView)convertView.findViewById(R.id.report_item_xianxiang);
            viewHolder.modelTextView=(TextView)convertView.findViewById(R.id.report_item_model);
            viewHolder.repairengnnerTextView=(TextView)convertView.findViewById(R.id.report_item_engneer);
            viewHolder.partsnTextView=(TextView)convertView.findViewById(R.id.report_item_liaohao);
            viewHolder.StationTextView=(TextView)convertView.findViewById(R.id.report_item_station);
            viewHolder.desTextView=(TextView)convertView.findViewById(R.id.report_item_des);


            viewHolder.lcTextView=(TextView)convertView.findViewById(R.id.report_item_lc);
            viewHolder.dcTextView=(TextView)convertView.findViewById(R.id.report_item_dc);
            viewHolder.manufacturerTextView=(TextView)convertView.findViewById(R.id.report_item_changshang);



            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }









       try{

           String lc=infoList.get(position).get("lc").toString();
           String dc=infoList.get(position).get("dc").toString();
           String manufacturer=infoList.get(position).get("manufacturer").toString();


           String time=infoList.get(position).get("time").toString();
           String model=infoList.get(position).get("model").toString();
           String sn=infoList.get(position).get("sn").toString();
           String station=infoList.get(position).get("station").toString();
           String location=infoList.get(position).get("location").toString();
           String partsn=infoList.get(position).get("partsn").toString();
           String des=infoList.get(position).get("description").toString();
           String repair_engneer=infoList.get(position).get("repair_engneer").toString();
           String leixing=infoList.get(position).get("type").toString();
           String liyou=infoList.get(position).get("failReason").toString();


           viewHolder.timefromTextView.setText(time);
           viewHolder.leixingTextView.setText(leixing);
           viewHolder.StationTextView.setText(station);
           viewHolder.snTextView.setText(sn);
           viewHolder.liyouTextView.setText(liyou);
           viewHolder.modelTextView.setText(model);
           viewHolder.repairengnnerTextView.setText(repair_engneer);
           viewHolder.partsnTextView.setText(partsn);
           viewHolder.locationTextView.setText(location);
           viewHolder.desTextView.setText(des);

           viewHolder.manufacturerTextView.setText(manufacturer);
           viewHolder.dcTextView.setText(dc);
           viewHolder.lcTextView.setText(lc);




       }catch (Exception e){
           e.printStackTrace();
       }

//        if (isDeleteMode) {
//            viewHolder.Checkbox.setVisibility(View.VISIBLE);
//        } else {
//            viewHolder.Checkbox.setVisibility(View.INVISIBLE);
//        }

        viewHolder.deletBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation anim = AnimationUtils.loadAnimation(context,
                        R.anim.closeanim);
                viewHolder.animLy.setAnimation(anim);
                anim.setAnimationListener(new Animation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        viewHolder.deleteLy.setVisibility(View.INVISIBLE);
                        viewHolder.animLy.setVisibility(View.INVISIBLE);
                    }
                });
                viewHolder.animLy.setVisibility(View.VISIBLE);
                closeOnclick = true;
                isOpen = false;
                infoList.remove(position);
                notifyDataSetChanged();
                Toast.makeText(context, "删除" + position, Toast.LENGTH_SHORT).show();


            }
        });


        viewHolder.layout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (viewHolder.deleteLy.getVisibility() == View.VISIBLE) {
                    Animation anim = AnimationUtils.loadAnimation(context,
                            R.anim.closeanim);
                    viewHolder.animLy.setAnimation(anim);
                    anim.setAnimationListener(new Animation.AnimationListener() {

                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            viewHolder.deleteLy.setVisibility(View.INVISIBLE);
                            viewHolder.animLy.setVisibility(View.INVISIBLE);
                        }
                    });
                    viewHolder.animLy.setVisibility(View.VISIBLE);
                    // �ر�
                    closeOnclick = true;
                    isOpen = false;
                } else {
                    // ����¼�����
                    Toast.makeText(context, "���", Toast.LENGTH_SHORT).show();
                }
            }
        });



        viewHolder.layout.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        down_x = event.getX();
                        closeOnclick = false;
                        isOpen = true;
                        return closeOnclick;
                    case MotionEvent.ACTION_UP:
                        return closeOnclick;
                    case MotionEvent.ACTION_MOVE:
                        move_x = event.getX();
                        float distance = Math.abs(move_x - down_x);
                        if (distance > 20 && isOpen) {
                            if (viewHolder.deleteLy.getVisibility() == View.INVISIBLE) {// �������
                                Animation anim = AnimationUtils.loadAnimation(
                                        context, R.anim.openanim);
                                viewHolder.animLy.setAnimation(anim);
                                anim.setAnimationListener(new Animation.AnimationListener() {

                                    @Override
                                    public void onAnimationStart(Animation animation) {
                                    }

                                    @Override
                                    public void onAnimationRepeat(
                                            Animation animation) {
                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        viewHolder.animLy
                                                .setVisibility(View.INVISIBLE);
                                    }
                                });
                                viewHolder.animLy.setVisibility(View.VISIBLE);
                                viewHolder.deleteLy.setVisibility(View.VISIBLE);
                                // �رյ���¼�
                                closeOnclick = true;
                                isOpen = false;
                            } else {
                                Animation anim = AnimationUtils.loadAnimation(
                                        context, R.anim.closeanim);
                                viewHolder.animLy.setAnimation(anim);
                                anim.setAnimationListener(new Animation.AnimationListener() {

                                    @Override
                                    public void onAnimationStart(Animation animation) {
                                    }

                                    @Override
                                    public void onAnimationRepeat(
                                            Animation animation) {
                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        viewHolder.deleteLy
                                                .setVisibility(View.INVISIBLE);
                                    }
                                });
                                viewHolder.animLy.setVisibility(View.VISIBLE);
                                viewHolder.deleteLy.setVisibility(View.VISIBLE);
                                // �رյ��
                                closeOnclick = true;
                                isOpen = false;
                            }
                        }
                        break;
                }
                return false;
            }
        });











        int[]colors={Color.WHITE, Color.rgb(219,238,244)};
        convertView.setBackgroundColor(colors[position%2]);
        return convertView;
    }
    private class ViewHolder{

        LinearLayout layout;
        Button deletBtn;
        LinearLayout deleteLy;
        LinearLayout animLy;
        TextView manufacturerTextView,dcTextView,lcTextView,timefromTextView,leixingTextView,snTextView,locationTextView,liyouTextView,modelTextView,repairengnnerTextView,partsnTextView,desTextView,StationTextView;

    }
}