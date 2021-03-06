package com.zwh.jcclwapplication.acts;

import static com.zwh.jcclwapplication.widgets.TakePhotoPopWindow.SELECTPHOTO;
import static com.zwh.jcclwapplication.widgets.TakePhotoPopWindow.TAKEPHOTO;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.internal.FlowLayout;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.zwh.jcclwapplication.R;
import com.zwh.jcclwapplication.entity.Picture;
import com.zwh.jcclwapplication.retrofit.BaseResponseData;
import com.zwh.jcclwapplication.retrofit.UrlHelper;
import com.zwh.jcclwapplication.retrofit.manager.RetrofitManager;
import com.zwh.jcclwapplication.retrofit.manager.SubscriberManger;
import com.zwh.jcclwapplication.utils.LOGUtils;
import com.zwh.jcclwapplication.utils.ScreenUtils;
import com.zwh.jcclwapplication.widgets.TakePhotoPopWindow;
import com.zwh.jcclwapplication.widgets.Utilty;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author admin
 */
public class AddPictureTwoActivity extends AppCompatActivity
        implements View.OnClickListener, TakePhoto.TakeResultListener, InvokeListener {

    private static final String TAG = AddPictureTwoActivity.class.getSimpleName();

    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private TakePhotoPopWindow mPhotoPopwindow;

    private FlowLayout container;
    private EditText etLocation;

    /**
     * ???????????????
     */
    private int width;

    /**
     * ????????????
     */
    private List<String> data = new ArrayList<>();

    /**
     * ????????????popwindow????????????
     **/
    private final Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TAKEPHOTO:
                    LOGUtils.w(TAG, "??????--->");
                    takePhoto(true);
                    break;
                case SELECTPHOTO:
                    LOGUtils.w(TAG, "??????????????????--->");
                    takePhoto(false);
                    break;
                default:
                    break;
            }
        }
    };
    private String key;

    public static void launchSelf(Context mCxt, String key) {
        Intent intent = new Intent(mCxt, AddPictureTwoActivity.class);
        intent.putExtra("key", key);
        mCxt.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_picture_two);
        key = getIntent().getStringExtra("key");
        initView();
    }

    private void initView() {
        mPhotoPopwindow = new TakePhotoPopWindow(this, mHandler);
        container = findViewById(R.id.container);
        etLocation = findViewById(R.id.et_location);

        Button btAdd = findViewById(R.id.bt_add);
        Button btCommit = findViewById(R.id.bt_commit);
        btAdd.setOnClickListener(this);
        btCommit.setOnClickListener(this);

        width = ScreenUtils.getScreenWidth(this);
    }

    /**
     * ??????TakePhoto??????
     *
     * @return
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }

    /**
     * @param isPick ????????????
     */
    private void takePhoto(boolean isPick) {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" +
                System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        Uri imageUri = Uri.fromFile(file);

        //????????????????????????
        configCompress(takePhoto);
        //???????????????
        if (!isPick) {
            getTakePhoto().onPickFromGalleryWithCrop(imageUri, getCropOptions());
        } else {
            getTakePhoto().onPickFromCaptureWithCrop(imageUri, getCropOptions());
        }

    }

    /**
     * ????????????????????????
     *
     * @param takePhoto
     */
    private void configCompress(TakePhoto takePhoto) {
        CompressConfig config = new CompressConfig.Builder().setMaxSize(102400)
                .setMaxPixel(200)
                .enableReserveRaw(true)
                .create();
        takePhoto.onEnableCompress(config, true);

    }

    /**
     * ????????????????????????
     */
    private CropOptions getCropOptions() {
        CropOptions.Builder builder = new CropOptions.Builder();
        builder.setWithOwnCrop(false);
        return builder.create();
    }

    /**
     * ????????????????????????
     */
    private void showPopWindow() {
        if (mPhotoPopwindow != null) {
            if (!mPhotoPopwindow.isShowing()) {
                mPhotoPopwindow.showAtLocation(container,
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            } else {
                mPhotoPopwindow.dismiss();
            }
        }
    }

    @Override
    public void takeSuccess(TResult result) {
        final TImage tImage = result.getImage();
        LOGUtils.w(TAG, "??????????????????--->" + tImage.getOriginalPath());
        LOGUtils.w(TAG, "??????????????????--->" + tImage.getCompressPath());
        ImageView imv = new ImageView(this);
        int surplus_5 = ScreenUtils.dip2px(this, 5);
        int imgWidth = (width - surplus_5 * 6) / 3;
        imv.setScaleType(ImageView.ScaleType.FIT_XY);

        imv.setOnClickListener(v -> PictureDetailActivity.launchSelf(AddPictureTwoActivity.this, tImage.getOriginalPath()));
        imv.setOnLongClickListener(view -> {
            new File(tImage.getOriginalPath()).delete();
            new File(tImage.getCompressPath()).delete();
            container.removeView(view);
            data.remove(tImage.getOriginalPath());
            Toast.makeText(AddPictureTwoActivity.this, "????????????", Toast.LENGTH_SHORT).show();
            return false;
        });
        Glide.with(this).load(new File(tImage.getOriginalPath())).into(new CustomTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                int intrinsicWidth = resource.getIntrinsicWidth();
                int intrinsicHeight = resource.getIntrinsicHeight();
                int imgHeight = 2 * imgWidth / 3;
                LOGUtils.w(TAG, "imgHeight--->" + imgHeight);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(imgWidth,
                        imgHeight);
                lp.setMargins(surplus_5, surplus_5, 0, 0);
                imv.setLayoutParams(lp);
                imv.setImageDrawable(resource);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        });
        data.add(tImage.getOriginalPath());
        container.addView(imv);
    }

    @Override
    public void takeFail(TResult result, String msg) {
        LOGUtils.w(TAG, "?????????????????? --->");
    }

    @Override
    public void takeCancel() {
        LOGUtils.w(TAG, "?????????????????? --->");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //?????????????????????Android6.0???7.0??????????????????
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_add:
                /**
                 * ????????????
                 */
                LOGUtils.w(TAG, "????????????--->");
                showPopWindow();
                break;

            case R.id.bt_commit:
                /**
                 * ????????????
                 */
                LOGUtils.w(TAG, "????????????--->");
                commitPictures();
                break;

            default:
                break;

        }
    }

    /**
     * ????????????
     */
    private void commitPictures() {
        String location = etLocation.getText().toString().trim();
        List<Picture> pics = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            String path = data.get(i);
            Picture picture = new Picture();
            picture.setKey(key);
            picture.setLocation(location);
            picture.setContent(Utilty.imageToBase64(path));
            picture.setName(path.substring(path.lastIndexOf("/") + 1));
            pics.add(picture);
        }
        RetrofitManager.getInstance().createApiManager().httpPostRequest2Json(
                UrlHelper.SAVEPICTURE,
                UrlHelper.setParameterJson(UrlHelper.SAVEPICTURE, pics),
                new SubscriberManger<String>(this, true) {

                    @Override
                    public void onSuccess(BaseResponseData<String> responseData) {
                        LOGUtils.w(TAG, "????????????--->");
                        Toast.makeText(AddPictureTwoActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        LOGUtils.w(TAG, "??????????????????--->");
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

}