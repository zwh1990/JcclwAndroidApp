package com.zwh.jcclwapplication.widgets;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.zwh.jcclwapplication.R;

/**
 * Created by admin on 2017/4/12.
 */

/**
 * @author by zwh 
 * @description：加载数据弹窗
 * @date  2021/2/3 11:19
 * @邮箱：zhaowh@zgjzd.cn
 */
public class LoadingDialog extends Dialog {


    private TextView mLoadingText;


    public LoadingDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCanceledOnTouchOutside(false);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_loading_dialog, null, false);

        setContentView(view);

    }

    /**
     * 禁止返回事件
     */
    @Override
    public void onBackPressed() {
    }


}
