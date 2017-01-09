package com.app.gaolonglong.fragmenttabhost.FunctionActvity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.app.gaolonglong.fragmenttabhost.R;

/**
 * Created by Administrator on 2016/12/26.
 */

public class MyListView extends ListView implements GestureDetector.OnGestureListener {

    //内部接口
    private onDeleteListener listener;

    //选择的第几个item
    private int selectedItemIndex;

    //手势检测
    private GestureDetector mGestureDetector;

    //屏幕内，选择的item的布局。
    private LinearLayout itemLayout;

    //删除按钮
    private Button deleteButton;

    //全局控制删除按钮变量，boolean默认值是false。
    private boolean deleteButtonIsVisible;

    private ViewGroup deleteButtonLayout;

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //new一个手势检测对象，表明在MyListView这个类注册手势探测
        mGestureDetector = new GestureDetector(context, this);
    }

    /**
     * 按下的时候触发事件
     *
     * @param motionEvent 按下时候触发的事件。
     * @return
     */
    @Override
    public boolean onDown(MotionEvent motionEvent) {
        //通过pointToPosition获得点选的item
        selectedItemIndex = pointToPosition(((int) motionEvent.getX()), ((int) motionEvent.getY()));

        //通过getChildAt获得选择的布局。
        itemLayout = (LinearLayout) getChildAt(selectedItemIndex - getFirstVisiblePosition());
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    /**
     * 滑动触发事件
     *
     * @param e1        开始滑动触发的事件
     * @param e2        结束滑动触发的事件
     * @param velocityX X轴的滑动速度，为矢量，左向右滑，大于零。右向左滑，小于零。
     * @param velocityY Y轴的滑动速度，同上。
     * @return
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        /**
         *触发条件为：没有删除按钮，并且X轴的滑动速度大于Y轴滑动速度，并且是从右向左滑。
         */
        if (!deleteButtonIsVisible && Math.abs(velocityX) > Math.abs(velocityY) && velocityX < 0) {
            //获取删除按钮View
            deleteButtonLayout = (ViewGroup) View.inflate(getContext(), R.layout.delete_button, null);
            deleteButton = (Button) deleteButtonLayout.findViewById(R.id.delete_button);

            //把删除按钮从原先的布局去除，方便等会被添加到另一个布局中。
            deleteButtonLayout.removeView(deleteButton);

            //布局参数设定
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            params.addRule(RelativeLayout.CENTER_IN_PARENT);

            //把删除按钮添加入选定的滑动item
            itemLayout.addView(deleteButton, params);

            //由于添加了，更改deleteButtonIsVisible的值。
            deleteButtonIsVisible = true;

            deleteButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //把结果反馈给Acitivity，然后移除对应的数据。
                    listener.onDelete(selectedItemIndex);
                    itemLayout.removeView(deleteButton);
                    deleteButtonIsVisible = false;
                }
            });


        }
        //由于由右向左滑动时，改变了deleteButtonIsVisible的值，所以现在是按钮存在的情况下，X轴速度大于Y轴速度，并且是从由左向右滑动，触发。
        else if (deleteButtonIsVisible && Math.abs(velocityX) > Math.abs(velocityY) && velocityX > 0) {
            itemLayout.removeView(deleteButton);
            deleteButtonIsVisible = false;
        }
        return false;
    }


    /**
     * 事件先传递到这，然后再传递到手势探测的事件里
     *
     * @param ev 触摸事件
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //把触摸事件传递给手势探测器
        mGestureDetector.onTouchEvent(ev);
        return super.onTouchEvent(ev);
    }


    //实现一个内部接口，用于把结果反馈回Activity。
    public interface onDeleteListener {
        void onDelete(int index);
    }

    /**
     * 提供给外部，从外部传递进来一个接口，用于等会的回调
     *
     * @param listener
     */
    public void setOnDeleteListener(onDeleteListener listener) {
        this.listener = listener;
    }
}