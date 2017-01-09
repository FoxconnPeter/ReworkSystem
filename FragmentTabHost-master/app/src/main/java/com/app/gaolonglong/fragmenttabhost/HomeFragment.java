package com.app.gaolonglong.fragmenttabhost;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toolbar;
import android.widget.ViewFlipper;

import com.app.gaolonglong.fragmenttabhost.Activity.LoginActivity;
import com.app.gaolonglong.fragmenttabhost.Activity.SearchActivity;
import com.app.gaolonglong.fragmenttabhost.Activity.SuccessActivity;
import com.app.gaolonglong.fragmenttabhost.FunctionActvity.CheckOutActivity;
import com.app.gaolonglong.fragmenttabhost.FunctionActvity.CheckinListActivity;
import com.app.gaolonglong.fragmenttabhost.FunctionActvity.ReportSearch;
import com.app.gaolonglong.fragmenttabhost.FunctionActvity.WIPSearchActivity;
import com.app.gaolonglong.fragmenttabhost.LEI.Item;
import com.app.gaolonglong.fragmenttabhost.View.CircleImageView;
import com.app.gaolonglong.fragmenttabhost.View.ClearEditText;
import com.app.gaolonglong.fragmenttabhost.adbanner.BaseWebActivity;
import com.app.gaolonglong.fragmenttabhost.adbanner.ImagePagerAdapter;
import com.app.gaolonglong.fragmenttabhost.bannerview.CircleFlowIndicator;
import com.app.gaolonglong.fragmenttabhost.bannerview.ViewFlow;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.R.attr.name;

/**
 * Created by donglinghao on 2016-01-28.
 */
public class HomeFragment extends Fragment {
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.install)
    ImageView install;
    @Bind(R.id.title_home)
    TextView titleHome;



    private Context mContext;
    private View mRootView;
    private ViewFlow mViewFlow;
    private CircleFlowIndicator mFlowIndicator;
    private ArrayList<String> imageUrlList = new ArrayList<String>();
    ArrayList<String> linkUrlArray = new ArrayList<String>();
    ArrayList<String> titleList = new ArrayList<String>();
    private LinearLayout notice_parent_ll;
    private LinearLayout notice_ll;
    private ViewFlipper notice_vf;
    private int mCurrPos;
    private List<Item> itemList = new ArrayList<>();
    private GridView gv_home;
    private ClearEditText search;
    private String[] mTitleStrs;
    private int[] mDrawableIds;
    private String[] mDrawabdecripte;

    public String name,Chinese ;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.home_fragment, container, false);


        initView();
        imageUrlList
                .add("http://10.132.44.79:8083/files/h001.png");
        imageUrlList
                .add("http://10.132.44.79:8083/files/h002.png");
        imageUrlList
                .add("http://10.132.44.79:8083/files/h003.png");
        imageUrlList
                .add("http://10.132.44.79:8083/files/h004.png");

        linkUrlArray
                .add("http://10.167.1.104/App/App/?app_id=42");
        linkUrlArray
                .add("http://10.167.1.104/App/App/?app_id=46");
        linkUrlArray
                .add("http://10.167.1.104/App/App/?app_id=43");
        linkUrlArray
                .add("http://10.132.44.79");
        titleList.add("查询ICT测试数据以及状态");
        titleList.add("企业级产品查询系统");
        titleList.add("TroubleShooting大数据查询 ");
        titleList.add("IPC实时查询的员工信息 ");
        initBanner(imageUrlList);
        initData();
        initRollNotice();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivityForResult(intent,2);

            }
        });

        search.setInputType(InputType.TYPE_NULL);



        ButterKnife.bind(this, mRootView);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        install.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initPopWindow();
            }
        });


        gv_home.setLayoutAnimation(getAnimationController());

        gv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //点中列表条目索引position

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                switch (position) {
                    case 0:
                        //checkin

                        Intent intent = new Intent(new Intent(getActivity(),CheckinListActivity.class));
                        startActivityForResult(intent,2);



                        break;
                    case 1:
                        //checkout
                        Intent intent1 = new Intent(new Intent(getActivity(),CheckOutActivity.class));
                        startActivityForResult(intent1,2);

                        break;
                    case 2:
                        //wip
                        Intent intent2 = new Intent(new Intent(getActivity(),WIPSearchActivity.class));
                        startActivityForResult(intent2,2);

                        break;
                    case 3:
                        //Summary
                        Intent intent3 = new Intent(new Intent(getActivity(),ReportSearch.class));
                        startActivityForResult(intent3,2);

                        break;


                }
            }

        });


        return mRootView;
    }

//    private void initPopWindow() {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.item_popwindow, null, false);
//
//        //1.构造一个PopupWindow，参数依次是加载的View，宽高
//        final PopupWindow popWindow = new PopupWindow(view,
//                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
//
//        popWindow.setAnimationStyle(R.anim.anim_pop);  //设置加载动画
//
//        //这些为了点击非PopupWindow区域，PopupWindow会消失的，如果没有下面的
//        //代码的话，你会发现，当你把PopupWindow显示出来了，无论你按多少次后退键
//        //PopupWindow并不会关闭，而且退不出程序，加上下述代码可以解决这个问题
//        popWindow.setTouchable(true);
//        popWindow.setTouchInterceptor(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return false;
//                // 这里如果返回true的话，touch事件将被拦截
//                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
//            }
//        });
//        popWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));    //要为popWindow设置一个背景才有效
//
//
//        //设置popupWindow显示的位置，参数依次是参照View，x轴的偏移量，y轴的偏移量
//        popWindow.showAsDropDown(v 50, 0);
//        String name = ((MainActivity) getActivity()).getName();
//
//        //设置popupWindow里的按钮的事件
//
//    }

    private LayoutAnimationController getAnimationController() {
        LayoutAnimationController controller;

        Animation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(500);
        controller = new LayoutAnimationController(anim, 0.1f);
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
        return controller;


    }

    private void initData() {
        //准备数据(文字(9组),图片(9张))
        mTitleStrs = new String[]{
                "Check In List", "Check Out", "WIP Search", "Report Search"
        };


        mDrawableIds = new int[]{
                R.drawable.checklist, R.drawable.checkout, R.drawable.baobiao,
                R.drawable.report,

        };
        mDrawabdecripte = new String[]{
                "可以Check In SN ", "可以Check Out SN", "可以查询WIP图", "可以查询报表详细"
        };
        //九宫格控件设置数据适配器(等同ListView数据适配器)
        gv_home.setAdapter(new MyAdapter());


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            //条目的总数	文字组数 == 图片张数
            return mTitleStrs.length;
        }

        @Override
        public Object getItem(int position) {
            return mTitleStrs[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            View view = View.inflate(getActivity(), R.layout.home_listview_item, null);

            TextView tv_title = (TextView) view.findViewById(R.id.tv);
            ImageView iv_icon = (ImageView) view.findViewById(R.id.img);
            TextView tv_des = (TextView) view.findViewById(R.id.info);

            tv_title.setText(mTitleStrs[position]);
            tv_des.setText(mDrawabdecripte[position]);
            tv_title.setTextColor(Color.BLACK);
            iv_icon.setBackgroundResource(mDrawableIds[position]);

            return view;
        }
    }




    private void initView() {
        gv_home = (GridView) mRootView.findViewById(R.id.gv_home);
        mViewFlow = (ViewFlow) mRootView.findViewById(R.id.viewflow);
        mFlowIndicator = (CircleFlowIndicator) mRootView.findViewById(R.id.viewflowindic);
        search=(ClearEditText)mRootView.findViewById(R.id.search);
    }

    private void initRollNotice() {
        FrameLayout main_notice = (FrameLayout) mRootView.findViewById(R.id.main_notice);
        notice_parent_ll = (LinearLayout) getActivity().getLayoutInflater().inflate(
                R.layout.layout_notice, null);
        notice_ll = ((LinearLayout) this.notice_parent_ll
                .findViewById(R.id.homepage_notice_ll));
        notice_vf = ((ViewFlipper) this.notice_parent_ll
                .findViewById(R.id.homepage_notice_vf));
        main_notice.addView(notice_parent_ll);
        moveNext();

    }


    private void moveNext() {

        setView(this.mCurrPos, this.mCurrPos + 1);
        this.notice_vf.setInAnimation(getContext(), R.anim.in_bottomtop);
        this.notice_vf.setOutAnimation(getContext(), R.anim.out_bottomtop);
        this.notice_vf.showNext();
    }

    private void setView(int curr, int next) {

        View noticeView = getActivity().getLayoutInflater().inflate(R.layout.notice_item,
                null);
        TextView notice_tv = (TextView) noticeView.findViewById(R.id.notice_tv);
        if ((curr < next) && (next > (titleList.size() - 1))) {
            next = 0;
        } else if ((curr > next) && (next < 0)) {
            next = titleList.size() - 1;
        }
        notice_tv.setText(titleList.get(next));
        notice_tv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Bundle bundle = new Bundle();
                bundle.putString("url", linkUrlArray.get(mCurrPos));
                bundle.putString("title", titleList.get(mCurrPos));
                Intent intent = new Intent(getContext(),
                        BaseWebActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        //弹出提示消息

        if (notice_vf.getChildCount() > 1) {
            notice_vf.removeViewAt(0);
        }
        notice_vf.addView(noticeView, notice_vf.getChildCount());
        mCurrPos = next;


    }

    private void initBanner(ArrayList<String> imageUrlList) {

        mViewFlow.setAdapter(new ImagePagerAdapter(getContext(), imageUrlList,
                linkUrlArray, titleList).setInfiniteLoop(true));
        mViewFlow.setmSideBuffer(imageUrlList.size()); // 实际图片张数，
        // 我的ImageAdapter实际图片张数为3

        mViewFlow.setFlowIndicator(mFlowIndicator);
        mViewFlow.setTimeSpan(4500);
        mViewFlow.setSelection(imageUrlList.size() * 1000); // 设置初始位置
        mViewFlow.startAutoFlowTimer(); // 启动自动播放
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            try {
                switch (msg.what) {
                    case 0:


                        break;
                    case 1:


                        break;
                    default:
                        break;
                }
            } catch (Exception e) {

                e.printStackTrace();
            }
            super.handleMessage(msg);
        }
    };


    private void initPopWindow() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_popwindow, null);
        final PopupWindow popupWindow = new PopupWindow(view);
        final TextView tvid=(TextView)view.findViewById(R.id.tv_username);
        final TextView tvname=(TextView)view.findViewById(R.id.tv_cnname);
        LinearLayout llexit=(LinearLayout) view.findViewById(R.id.ll_exit);

        popupWindow.setWidth(Toolbar.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(Toolbar.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体动画效果
        popupWindow.setAnimationStyle(R.style.AnimRight);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.setTouchable(true);
        //设置SelectPicPopupWindow弹出窗体可点击
        popupWindow.setFocusable(true);
        /*
          * popupWindow.showAsDropDown（View view）弹出对话框，位置在紧挨着view组件
          * showAsDropDown(View anchor, int xoff, int yoff)弹出对话框，位置在紧挨着view组件，x y 代表着偏移量
          * showAtLocation(View parent, int gravity, int x, int y)弹出对话框
          * parent 父布局 gravity 依靠父布局的位置如Gravity.CENTER  x y 坐标值
          */
        popupWindow.showAsDropDown(install);

        //显示工号
         name = ((MainActivity) getActivity()).getName();
        Chinese = ((MainActivity) getActivity()).getCn();


        new Thread(new Runnable() {
            @Override
            public void run() {
                tvid.setText(name);
                tvname.setText(Chinese);

            }
        }).start();


        llexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("你确定要注销该账号吗？");
                dialog.setMessage("");
                dialog.setCancelable(false);
                dialog.setPositiveButton("注销", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        //注销功能（杀掉所有activity，返回登入界面）
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        dialog.dismiss();
                    }


                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });


    }







}
