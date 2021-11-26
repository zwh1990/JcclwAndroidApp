package com.zwh.jcclwapplication.retrofit.manager;

import android.content.Context;

import androidx.annotation.NonNull;

import com.zwh.jcclwapplication.JcclwApplication;
import com.zwh.jcclwapplication.retrofit.UrlHelper;
import com.zwh.jcclwapplication.utils.LOGUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * @author by zwh
 * @description：网络请求配置
 * @date 2021/2/3 10:51
 * @邮箱：zhaowh@zgjzd.cn
 */

public class RetrofitManager {

    public static final String TAG = RetrofitManager.class.getSimpleName();

    private static Retrofit mRetrofit;
    private static Context sContext;
    private static final int DEFAULT_TIMEOUT = 60;
    private ApiManager apiManager;

    private static class SingletonHolder {
        private static final RetrofitManager INSTANCE = new RetrofitManager();
    }

    public static RetrofitManager getInstance() {
        if (sContext == null) {
            sContext = JcclwApplication.getAppContext();
        }
        return SingletonHolder.INSTANCE;
    }

    private RetrofitManager() {

        // Log信息拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> {
            //打印retrofit日志
            LOGUtils.w(TAG, "message --->" + message);
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //设置 Debug Log 模式
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                // 设置超时
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(loggingInterceptor);

        OkHttpClient okHttpClient = builder.build();

        LOGUtils.w(TAG, "URL--->" + UrlHelper.API_SERVER_URL);
        mRetrofit = new Retrofit.Builder()
                .baseUrl(UrlHelper.API_SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    /**
     * create BaseApi  defalte ApiManager
     *
     * @return ApiManager
     */
    public RetrofitManager createApiManager() {
        apiManager = create(ApiManager.class);
        return this;
    }

    /**
     * create BaseApi  defalte ApiManager
     *
     * @return ApiManager
     */
    public ApiManager getApiManager() {
        apiManager = create(ApiManager.class);
        return apiManager;
    }

    /**
     * create you ApiService
     * Create an implementation of the API endpoints defined by the {@code service} interface.
     */
    private <T> T create(final Class<T> service) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        return mRetrofit.create(service);
    }

    /**
     * @author by zwh
     * @description：
     * @date 2021/2/3 10:56
     * @邮箱：zhaowh@zgjzd.cn
     */
    public void httpPostRequest2Json(String url, String parametersJson, SubscriberManger subscriber) {
        RequestBody body = getRequestBody(parametersJson);
        Observable observable = null;
        switch (url) {

            /**
             *  保存图片
             */
            case UrlHelper.SAVEPICTURE:
                observable = RetrofitManager.getInstance().apiManager.savePicture(body);
                break;

            /**
             *  保存图片
             */
            case UrlHelper.GETPICTURE:
                observable = RetrofitManager.getInstance().apiManager.getPictures(body);
                break;

            default:
                break;


        }
        observable.compose(schedulersTransformer()).subscribe(subscriber);

    }


    @NonNull
    private RequestBody getRequestBody(String json) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        LOGUtils.w(TAG, "json--->" + json);
        return RequestBody.create(JSON, json);
    }


    public static <T> ObservableTransformer<T, T> schedulersTransformer() {
        return observable -> ((Observable) observable).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Supply a swift shift from io-thread to main-thread of Android.
     *
     * @param <T> type of emitted object
     * @return {@linkplain SingleTransformer} instance
     */
    public static <T> SingleTransformer<T, T> scheduleIO2Main2() {
        return new SingleTransformer<T, T>() {
            @Override
            public SingleSource<T> apply(Single<T> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * Generate file multipart.
     *
     * @param partName part name
     * @param file     file
     * @return {@linkplain MultipartBody.Part} as file body
     */
    public static MultipartBody.Part generateFilePart(String partName, File file) {
        if (file != null) {
            return MultipartBody.Part.createFormData(partName, file.getName(), generateRequestPart(file));
        } else {
            return null;
        }
    }

    /**
     * Generate file body.
     *
     * @param data binary data
     * @return {@linkplain RequestBody} as file body
     */
    public static RequestBody generateRequestPart(File data) {
        if (data != null) {
            return RequestBody.create(MediaType.parse("multipart/otcet-stream"), data);
        } else {
            return null;
        }
    }


}
