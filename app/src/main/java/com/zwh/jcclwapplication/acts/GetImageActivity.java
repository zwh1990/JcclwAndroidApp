package com.zwh.jcclwapplication.acts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.zwh.jcclwapplication.R;
import com.zwh.jcclwapplication.adapter.GetImageAdapter;
import com.zwh.jcclwapplication.entity.Picture;
import com.zwh.jcclwapplication.retrofit.BaseResponseData;
import com.zwh.jcclwapplication.retrofit.UrlHelper;
import com.zwh.jcclwapplication.retrofit.manager.RetrofitManager;
import com.zwh.jcclwapplication.retrofit.manager.SubscriberManger;
import com.zwh.jcclwapplication.utils.LOGUtils;

import java.util.List;

public class GetImageActivity extends AppCompatActivity {

    private static final String TAG = GetImageActivity.class.getSimpleName();
    private RecyclerView mRev;
    private GetImageAdapter mAdapter;
    private List<Picture> data;

    public static void launchSelf(Context mCxt) {
        Intent intent = new Intent(mCxt, GetImageActivity.class);
        mCxt.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_image);
        getDatas();
        initView();
    }

    private void initView() {
        mRev = findViewById(R.id.recycleView);
        mRev.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new GetImageAdapter(data, R.layout.item_get_image);
        mRev.setAdapter(mAdapter);
    }

    private void getDatas() {
        RetrofitManager.getInstance().createApiManager().httpPostRequest2Json(
                UrlHelper.GETPICTURE,
                UrlHelper.setParameterJson(UrlHelper.GETPICTURE, ""),
                new SubscriberManger<List<Picture>>(this, true) {

                    @Override
                    public void onSuccess(BaseResponseData<List<Picture>> responseData) {
                        int size = responseData.getData().size();
                        LOGUtils.w(TAG, "获取图片成功 --->" + size);
                        data = responseData.getData();
                        initView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        LOGUtils.w(TAG, "登录接口失败--->");
                    }
                });
    }


}