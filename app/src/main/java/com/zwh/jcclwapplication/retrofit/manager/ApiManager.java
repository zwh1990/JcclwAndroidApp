package com.zwh.jcclwapplication.retrofit.manager;


import com.zwh.jcclwapplication.entity.Picture;
import com.zwh.jcclwapplication.retrofit.BaseResponseData;
import com.zwh.jcclwapplication.retrofit.UrlHelper;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;


/**
 *
 * @author harbor
 * @date 2017/7/14
 * 管理请求接口
 */

public interface ApiManager<T> {

    /**
     * 保存图片接口
     */
    @POST(UrlHelper.SAVEPICTURE)
    Observable<BaseResponseData<String>> savePicture(@Body RequestBody pBody);

    /**
     * 获取图片接口
     */
    @POST(UrlHelper.GETPICTURE)
    Observable<BaseResponseData<List<Picture>>> getPictures(@Body RequestBody pBody);
}
