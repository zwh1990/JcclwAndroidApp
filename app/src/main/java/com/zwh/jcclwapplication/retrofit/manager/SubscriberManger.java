package com.zwh.jcclwapplication.retrofit.manager;

import android.content.Context;
import android.widget.Toast;

import com.zwh.jcclwapplication.retrofit.BaseResponseData;
import com.zwh.jcclwapplication.utils.LOGUtils;
import com.zwh.jcclwapplication.widgets.LoadingDialog;

import java.io.IOException;
import java.net.ConnectException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;


/**
 * @author by zwh
 * @description：
 * @date 2021/2/3 11:03
 * @邮箱：zhaowh@zgjzd.cn
 */

public abstract class SubscriberManger<T> implements Observer<BaseResponseData<T>> {

    private static final String TAG = SubscriberManger.class.getSimpleName();
    private final Context context;
    private final boolean isShowDialog;
    private LoadingDialog loadDialog;

    public SubscriberManger(Context context) {
        this(context, true);
    }

    public SubscriberManger(Context context, boolean isShowDialog) {
        this.context = context;
        this.isShowDialog = isShowDialog;
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (!NetworkManger.isNetworkConnected(context)) {
            onError(new ConnectException());
        }
        if (isShowDialog) {
            loadDialog = new LoadingDialog(context);
            loadDialog.show();
        }
    }

    @Override
    public void onNext(BaseResponseData<T> t) {
        //0  代表成功
        if (t.getCode() == 0) {
            try {
                onSuccess(t);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            onError(new RuntimeException());
        }
    }

    public abstract void onSuccess(BaseResponseData<T> t) throws IOException;

    @Override
    public void onError(Throwable e) {
        if (e instanceof HttpException || e instanceof ConnectException) {
            Toast.makeText(context,"网络错误，请检查网络",Toast.LENGTH_SHORT).show();
            LOGUtils.w(TAG, "HttpException--->");
        } else {
            String message = e.getMessage();
            LOGUtils.w(TAG, "--->" + e.getMessage());
            if ("timeout".equals(message)) {
                message = "请求超时";
            }
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
        }
        if (loadDialog != null && loadDialog.isShowing()) {
            loadDialog.dismiss();
        }
    }

    @Override
    public void onComplete() {
        if (loadDialog != null && loadDialog.isShowing()) {
            loadDialog.dismiss();
        }
    }

}
