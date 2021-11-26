package com.zwh.jcclwapplication.acts;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.zwh.jcclwapplication.R;
import com.zwh.jcclwapplication.utils.LOGUtils;
import com.zwh.jcclwapplication.utils.ScreenUtils;

/**
 * @author admin
 */
public class PictureDetailActivity extends AppCompatActivity {

    private static final String TAG = PictureDetailActivity.class.getSimpleName();

    public static void launchSelf(Context mCxt, String path) {
        Intent intent = new Intent(mCxt, PictureDetailActivity.class);
        intent.putExtra("path", path);
        mCxt.startActivity(intent);
    }

    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_detail);
        initView();
    }

    private void initView() {
        String path = getIntent().getStringExtra("path");
        container = findViewById(R.id.container);
        ImageView imageView = new ImageView(this);
        int screenWidth = ScreenUtils.getScreenWidth(this);
        int surplus = ScreenUtils.dip2px(this, 20);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(screenWidth - surplus, screenWidth - surplus);
        imageView.setLayoutParams(lp);
        int surplus_10 = ScreenUtils.dip2px(this, 10);
        lp.setMargins(surplus_10, surplus_10, 0, surplus_10);

        Glide.with(this).load(path).into(new CustomTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                int intrinsicWidth = resource.getIntrinsicWidth();
                int intrinsicHeight = resource.getIntrinsicHeight();
                LOGUtils.w(TAG, "intrinsicWidth --->" + intrinsicWidth);
                LOGUtils.w(TAG, "intrinsicHeight --->" + intrinsicHeight);

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(intrinsicWidth, intrinsicHeight);
                imageView.setLayoutParams(lp);
                imageView.setImageDrawable(resource);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        });
        container.addView(imageView);
    }

}