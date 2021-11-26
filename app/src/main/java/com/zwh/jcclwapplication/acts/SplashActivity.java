package com.zwh.jcclwapplication.acts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.zwh.jcclwapplication.R;
import com.zwh.jcclwapplication.utils.LOGUtils;

import java.util.List;

public class SplashActivity extends AppCompatActivity implements PermissionListener {

    private static final String TAG = SplashActivity.class.getSimpleName();
    private static final int REQUEST_CODE_PERMISSION_SD = 100;
    private static final int REQUEST_CODE_SETTING = 200;
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
    }


    private void initView() {
        getPermission();
    }

    private void getPermission() {
        AndPermission.with(this)
                .requestCode(REQUEST_CODE_PERMISSION_SD)
                .permission(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_PHONE_STATE)
                .rationale((requestCode, rationale) -> AndPermission.rationaleDialog(this, rationale).show()).send();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AndPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /**
     * 获取权限成功回调
     */
    @Override
    public void onSucceed(int requestCode, List<String> grantPermissions) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_SD: {
                int size = grantPermissions.size();
                LOGUtils.initLog();
                singleSignOn();
                break;
            }
            default:
                break;
        }
    }

    private void singleSignOn() {
        mHandler.postDelayed((Runnable) () -> skipActivity(), 1000L);
    }

    /**
     * 跳转主页面
     */
    private void skipActivity() {
        startActivity(new Intent(this, MainActivity.class));
        this.finish();
    }


    /**
     * 获取权限失败回调
     */
    @Override
    public void onFailed(int requestCode, List<String> deniedPermissions) {
        // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
        if (AndPermission.hasAlwaysDeniedPermission(this, deniedPermissions)) {
            AndPermission.defaultSettingDialog(this, REQUEST_CODE_SETTING)
                    .setTitle(R.string.dialog_permssion_title)
                    .setMessage(R.string.dialog_permssion_msg)
                    .setPositiveButton(R.string.dialog_permssion_button)
                    .show();
        }
    }
}