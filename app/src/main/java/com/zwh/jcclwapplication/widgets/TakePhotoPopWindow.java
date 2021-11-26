package com.zwh.jcclwapplication.widgets;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zwh.jcclwapplication.R;
import com.zwh.jcclwapplication.utils.ScreenUtils;


/**
 * @Created by zwh.
 * @description： 选择照片弹出的popWindow
 * @date 2018/7/24 19:32
 * @邮箱：zhaowh@zgjzd.cn
 */
public class TakePhotoPopWindow extends PopupWindow implements View.OnClickListener{

    private Handler mHandler;

    public static final int TAKEPHOTO = 0x100;
    public static final int SELECTPHOTO = 0x101;
    public static final int CANCEL = 0x102;

    public TakePhotoPopWindow(Context mContext, Handler handler) {
        this.mHandler = handler;
        View view = LayoutInflater.from(mContext).inflate(R.layout.popwindow_takephoto, new LinearLayout(mContext), false);
        setContentView(view);
        //设置点击事件
        LinearLayout ll_container = view.findViewById(R.id.ll_container);
        TextView takePhoto =  view.findViewById(R.id.takePhoto);
        TextView chooseFromAlbum = view.findViewById(R.id.chooseFromAlbum);
        TextView tv_cancle = view.findViewById(R.id.tv_cancle);
        ll_container.setOnClickListener(this);
        takePhoto.setOnClickListener(this);
        chooseFromAlbum.setOnClickListener(this);
        tv_cancle.setOnClickListener(this);
        //设置宽高
        setWidth(ScreenUtils.getScreenWidth(mContext));
        setHeight(ScreenUtils.getScreenHeight(mContext) - ScreenUtils.getStatusBarHeight(mContext));

        setBackgroundDrawable(new BitmapDrawable());
        //设置外部点击消失
        setOutsideTouchable(true);
        setFocusable(true);
        //设置动画
        setAnimationStyle(R.style.take_photo_anim);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_container:
                mHandler.sendEmptyMessage(CANCEL);
                this.dismiss();
                break;
            case R.id.takePhoto:
                mHandler.sendEmptyMessage(TAKEPHOTO);
                this.dismiss();
                break;
            case R.id.chooseFromAlbum:
                mHandler.sendEmptyMessage(SELECTPHOTO);
                this.dismiss();
                break;
            case R.id.tv_cancle:
                mHandler.sendEmptyMessage(CANCEL);
                this.dismiss();
                break;
            default:
                break;
        }
    }


}
