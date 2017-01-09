package com.app.gaolonglong.fragmenttabhost;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.app.gaolonglong.fragmenttabhost.Activity.DepatmentActivity;
import com.app.gaolonglong.fragmenttabhost.Activity.DialogAcitvity;
import com.app.gaolonglong.fragmenttabhost.Activity.SuccessActivity;
import com.app.gaolonglong.fragmenttabhost.View.CircleImageView;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

/**
 * Created by donglinghao on 2016-01-28.
 */
public class MessageFragment extends Fragment {


    @Bind(R.id.title_home)
    TextView titleHome;


    @Bind(R.id.mine_ChineseName)
    TextView mineChineseName;
    @Bind(R.id.mine_name)
    TextView mineName;
    @Bind(R.id.mine_Came)
    TextView mineCame;
    @Bind(R.id.xinxi)
    LinearLayout xinxi;






    @Bind(R.id.conect_img)
    ImageView conectImg;
    @Bind(R.id.conect_des)
    TextView conectDes;
    private View mRootView;
    public String name;
    public String result;
    public String Chinese;
    private LinearLayout sucess;
    private LinearLayout version;
    private CircleImageView picture;
    private Uri imageUri;
//    private Bitmap bitmap;
    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;


//    Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case 0:
//                    String data = (String)msg.obj;
//                    mineCame.setText(name);
//                    mineName.setText(mingzi);
//                    break;
//                default:
//                    break;
//            }
//        }

//    private Context context;
//    public showPopwindow(Context context){
//        this.context = context;
//    }
//    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            Log.e("666", "MessageFragment");
            mRootView = inflater.inflate(R.layout.message_fragment, container, false);
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }//    \




//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
////耗时操作
                Message msg = new Message();
                msg.what = 0;
                handler.sendMessage(msg);
//            }
//        }).start();





         sucess=(LinearLayout)mRootView.findViewById(R.id.succes);
        sucess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(new Intent(getActivity(),SuccessActivity.class));

                Chinese = ((MainActivity) getActivity()).getCn();
                name = ((MainActivity) getActivity()).getName();
                intent.putExtra("name",name);
                intent.putExtra("Chinese",Chinese);

                startActivityForResult(intent,2);

            }
        });


        picture = (CircleImageView)mRootView.findViewById(R.id.mine_tou);

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                initPopWindow();

            }
        });








        LinearLayout connectWe=(LinearLayout)mRootView.findViewById(R.id.connect_we);


        //联系我们
        connectWe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("联系方式");
                dialog.setMessage("分机：78675\n邮箱：peterli.q.li@mail.foxconn.com");
                dialog.setCancelable(false);
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }


                });
                dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });


        LinearLayout minPassword=(LinearLayout)mRootView.findViewById(R.id.min_password);



        minPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent intent = new Intent(getActivity(), DialogAcitvity.class);

                name = ((MainActivity) getActivity()).getName();
                intent.putExtra("name",name);

                startActivity(intent);

            }
        });






        LinearLayout department = (LinearLayout)mRootView.findViewById(R.id.department);

        //人员信息

        department.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(),DepatmentActivity.class),1);




            }


        });

















        ButterKnife.bind(this, mRootView);
        return mRootView;
    }


    private void initPopWindow() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.messagepopwindow_item, null);
        final PopupWindow popupWindow = new PopupWindow(view);


        popupWindow.setWidth(Toolbar.LayoutParams.MATCH_PARENT);
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
        popupWindow.showAsDropDown(conectDes);


        Button takephotp=(Button)view.findViewById(R.id.takephoto);
        Button xiangce=(Button)view.findViewById(R.id.xiangce);

        takephotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("第一个按钮被点击了");


                //创建file对象
                File outputImage= new File(MyApplication.getContext().getExternalCacheDir(),"output_image.jpg");
                try{
                    if (outputImage.exists()){
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT >=24)
                {
                    imageUri = Uri.fromFile(outputImage);
                }
                else
                {
                    imageUri= FileProvider.getUriForFile(getActivity(),"com.app.gaolonglong.fragmenttabhost.fileprovider",outputImage);
                }

                // 启动相机程序
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_PHOTO);



            }
        });



        xiangce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("第er个按钮被点击了");

                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{ Manifest.permission. WRITE_EXTERNAL_STORAGE }, 1);
                    openAlbum();
                } else {
                    openAlbum();
                }




            }
        });



        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });






    }
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO); // 打开相册
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(getActivity(), "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

















    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {

                    try {
                        // 将拍摄的照片显示出来
                    Bitmap  bitmap = BitmapFactory.decodeStream(MyApplication.getContext().getContentResolver().openInputStream(imageUri));

                        picture.setImageBitmap(bitmap);
//                        Message msg = new Message();
//                        msg.what = 1;
//                        handler.sendMessage(msg);





                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    // 判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        // 4.4及以上系统使用这个方法处理图片
                         handleImageOnKitKat(data);
                    } else {
                        // 4.4以下系统使用这个方法处理图片
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }



    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
        if (uri != null) {
            if (DocumentsContract.isDocumentUri(getActivity(), uri)) {
                // 如果是document类型的Uri，则通过document id处理
                String docId = DocumentsContract.getDocumentId(uri);
                if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                    String id = docId.split(":")[1]; // 解析出数字格式的id
                    String selection = MediaStore.Images.Media._ID + "=" + id;
                    imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
                } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                    Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                    imagePath = getImagePath(contentUri, null);
                }
            } else if ("content".equalsIgnoreCase(uri.getScheme())) {
                // 如果是content类型的Uri，则使用普通方式处理
                imagePath = getImagePath(uri, null);
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                // 如果是file类型的Uri，直接获取图片路径即可
                imagePath = uri.getPath();
            }
            displayImage(imagePath); // 根据图片路径显示图片
        }
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = MyApplication.getContext().getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {

        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            picture.setImageBitmap(bitmap);
          //  uploadPic(bitmap);





        } else {
            Toast.makeText(getActivity(), "failed to get image", Toast.LENGTH_SHORT).show();
        }






    }



    private void uploadPic(Bitmap bitmap) {
        // 上传至服务器
        // ... 可以在这里把Bitmap转换成file，然后得到file的url，做文件上传操作
        // 注意这里得到的图片已经是圆形图片了
        // bitmap是没有做个圆形处理的，但已经被裁剪了

        String imagePath = Utils.savePhoto(bitmap, Environment
                .getExternalStorageDirectory().getAbsolutePath(), String
                .valueOf(System.currentTimeMillis()));
        Log.e("imagePath", imagePath+"");
        if(imagePath != null){
            // 拿着imagePath上传了
            // ...


        }
    }










    private void startActivityForResult(Intent intent,int requestCode,int resultCode) {
         result = intent.getExtras().getString(result);
        System.out.println(result);
    }


    /**
     * 设置按钮点击的回调
     * @author zhy
     *
     */


    /**
     * 交给宿主Activity处理，如果它希望处理
     */










    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            try {
                switch (msg.what) {
                    case 0:

                        Chinese = ((MainActivity) getActivity()).getCn();
                        name = ((MainActivity) getActivity()).getName();
                        mineCame.setText(name);
                        mineName.setText(Chinese);

                        break;
                    case 1:
//                     picture.setImageBitmap(bitmap);

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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
